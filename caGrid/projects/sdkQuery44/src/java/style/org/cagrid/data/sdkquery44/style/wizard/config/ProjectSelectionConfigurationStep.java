/**
*============================================================================
*  Copyright The Ohio State University Research Foundation, The University of Chicago - 
*	Argonne National Laboratory, Emory University, SemanticBits LLC, and 
*	Ekagra Software Technologies Ltd.
*
*  Distributed under the OSI-approved BSD 3-Clause License.
*  See http://ncip.github.com/cagrid-core/LICENSE.txt for details.
*============================================================================
**/
package org.cagrid.data.sdkquery44.style.wizard.config;

import gov.nih.nci.cagrid.common.JarUtilities;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.data.DataServiceConstants;
import gov.nih.nci.cagrid.data.QueryProcessorConstants;
import gov.nih.nci.cagrid.data.common.CastorMappingUtil;
import gov.nih.nci.cagrid.introduce.common.CommonTools;
import gov.nih.nci.cagrid.introduce.common.ServiceInformation;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.data.sdkquery44.processor.cql2.SDK44CQL2QueryProcessor;
import org.cagrid.grape.utils.CompositeErrorDialog;
import org.cagrid.iso21090.model.validator.ISODomainModelValidator;

/**
 * ProjectSelectionConfigurationStep
 * Configures basic aspects of the service such as application name, local / remote
 * API and directories, and service URL
 * 
 * @author David
 */
public class ProjectSelectionConfigurationStep extends AbstractStyleConfigurationStep {
    
    public static final String[] excludeSdkLibs = {
        "axis-1.4.jar", "caGrid-BulkDataHandler-client-1.4.jar", "caGrid-BulkDataHandler-common-1.4.jar",
        "caGrid-BulkDataHandler-stubs-1.4.jar", "caGrid-CQL-cql.1.0-1.4.jar", "caGrid-ServiceSecurityProvider-client-1.4.jar",
        "caGrid-ServiceSecurityProvider-common-1.4.jar", "caGrid-ServiceSecurityProvider-service-1.4.jar", 
        "caGrid-ServiceSecurityProvider-stubs-1.4.jar", "caGrid-core-1.4.jar", "caGrid-data-common-1.4.jar",
        "caGrid-data-cql-1.4.jar", "caGrid-data-service-1.4.jar", "caGrid-data-stubs-1.4.jar", "caGrid-data-utils-1.4.jar",
        "caGrid-data-validation-1.4.jar", "caGrid-metadata-common-1.4.jar", "caGrid-metadata-data-1.4.jar",
        "caGrid-metadata-security-1.4.jar", "caGrid-metadatautils-1.4.jar", "caGrid-wsEnum-1.4.jar", "caGrid-wsEnum-stubs-1.4.jar",
        "cog-jglobus-1.2.jar", "caGrid-iso21090DomainModelTools-1.4.jar", "caGrid-iso21090IntroduceExtensions-1.4.jar", 
        "caGrid-iso21090SdkQueryProcessor-style-1.3.jar", "caGrid-iso21090IntroduceExtensions-tests-1.4.jar"
    };
    
    private static Log LOG = LogFactory.getLog(ProjectSelectionConfigurationStep.class);
    
    private String applicationName = null;
    private boolean isLocalApi = false;
    private String localClientDir = null;
    private String remoteClientDir = null;
    private String applicationHostname = null;
    private Integer applicationPort = null;
    private boolean useHttps = false;
    private boolean useJaxB = false;

    public ProjectSelectionConfigurationStep(ServiceInformation serviceInfo) {
        super(serviceInfo);
    }


    public void applyConfiguration() throws Exception {
        // set the query processor class name for the data service
        CommonTools.setServiceProperty(getServiceInformation().getServiceDescriptor(),
            QueryProcessorConstants.QUERY_PROCESSOR_CLASS_PROPERTY, "", false);
        CommonTools.setServiceProperty(getServiceInformation().getServiceDescriptor(),
            QueryProcessorConstants.CQL2_QUERY_PROCESSOR_CLASS_PROPERTY, SDK44CQL2QueryProcessor.class.getName(), false);
        
        // change out the domain model validator class
        CommonTools.setServiceProperty(getServiceInformation().getServiceDescriptor(), 
            DataServiceConstants.DOMAIN_MODEL_VALIDATOR_CLASS, 
            ISODomainModelValidator.class.getName(), false);
        
        // set the service properties required by the CQL 2 query processor
        setCql2ProcessorProperty(SDK44CQL2QueryProcessor.PROPERTY_APPLICATION_NAME, getApplicationName(), false);
        setCql2ProcessorProperty(SDK44CQL2QueryProcessor.PROPERTY_USE_LOCAL_API, String.valueOf(isLocalApi()), false);
        setCql2ProcessorProperty(SDK44CQL2QueryProcessor.PROPERTY_HOST_NAME, 
            getApplicationHostname() != null ? getApplicationHostname() : "", false);
        setCql2ProcessorProperty(SDK44CQL2QueryProcessor.PROPERTY_HOST_PORT, 
            getApplicationPort() != null ? String.valueOf(getApplicationPort()) : "", false);
        setCql2ProcessorProperty(SDK44CQL2QueryProcessor.PROPERTY_HOST_HTTPS, String.valueOf(isUseHttps()), false);
        
        // store the information about the local and remote client dirs
        setStyleProperty(StyleProperties.SDK_REMOTE_CLIENT_DIR, getRemoteClientDir() != null ? getRemoteClientDir() : "");
        setStyleProperty(StyleProperties.SDK_LOCAL_CLIENT_DIR, getLocalClientDir() != null ? getLocalClientDir() : "");
        
        // roll up the local or remote configs as a jar file
        File serviceLibDir = new File(getServiceInformation().getBaseDirectory(), "lib");
        File configJarFile = new File(serviceLibDir, getApplicationName() + "-config.jar");
        File localConfigDir = new File(getLocalClientDir(), "conf");
        File sdkLibDir = null;
        if (isLocalApi()) {
            JarUtilities.jarDirectory(localConfigDir, configJarFile);
            sdkLibDir = new File(getLocalClientDir(), "lib");
        } else {
            File remoteConfigDir = new File(getRemoteClientDir(), "conf");
            JarUtilities.jarDirectory(remoteConfigDir, configJarFile);
            // add the IsoConstants from localConfigDir to the jar
            FileInputStream isoConstantsStream = 
                new FileInputStream(new File(localConfigDir, "IsoConstants.xml"));
            byte[] isoConfigBytes = Utils.inputStreamToByteArray(isoConstantsStream);
            isoConstantsStream.close();
            JarUtilities.insertEntry(configJarFile, "IsoConstants.xml", isoConfigBytes);
            sdkLibDir = new File(getRemoteClientDir(), "lib");
        }
        
        // are us using JaxB?
        setStyleProperty(StyleProperties.USE_JAXB_SERIALIZERS, String.valueOf(isUseJaxB()));
        if (!isUseJaxB()) {
            // grab the castor marshaling and unmarshaling xml mapping files
            // from the schemas jar and copy them into the service's package structure
            try {
                File schemasJar = new File(sdkLibDir, getApplicationName() + "-schema.jar");
                StringBuffer marshaling = JarUtilities.getFileContents(
                    new JarFile(schemasJar), CastorMappingUtil.CASTOR_MARSHALLING_MAPPING_FILE);
                StringBuffer unmarshalling = JarUtilities.getFileContents(
                    new JarFile(schemasJar), CastorMappingUtil.CASTOR_UNMARSHALLING_MAPPING_FILE);
                // copy the mapping files to the service's source dir + base package name
                String marshallOut = CastorMappingUtil.getMarshallingCastorMappingFileName(getServiceInformation());
                String unmarshallOut = CastorMappingUtil.getUnmarshallingCastorMappingFileName(getServiceInformation());
                Utils.stringBufferToFile(marshaling, marshallOut);
                Utils.stringBufferToFile(unmarshalling, unmarshallOut);
                LOG.debug("Extracted castor mapping files into service package structure");
            } catch (IOException ex) {
                String message = "Error extracting castor mapping files";
                LOG.error(message, ex);
                CompositeErrorDialog.showErrorDialog(message, ex.getMessage(), ex);
            }
        } else {
            LOG.info("Use JaxB == true; no castor mapping manipulation will be performed");
        }
        
        // copy SDK libs to the service
        // have to be selective here since there's LOTS of conflicts with things
        // cagrid and globus already provide and depend on
        Set<String> excludeNames = new HashSet<String>();
        Collections.addAll(excludeNames, excludeSdkLibs);
        File[] sdkLibs = sdkLibDir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".jar");
            }
        });
        for (File sdkLib : sdkLibs) {
            if (!excludeNames.contains(sdkLib.getName())) {
                File serviceLib = new File(serviceLibDir, sdkLib.getName());
                Utils.copyFile(sdkLib, serviceLib);
                LOG.debug("Copied SDK library " + sdkLib.getName() + " into service");
            } else {
                LOG.debug("SDK library " + sdkLib.getName() + " excluded from service");
            }
        }
        
        // CQL 2 query processor requires the -orm jar and the ISO-hibernate jar to read Hibernate configs
        File ormJar = new File(getLocalClientDir(), "lib" + File.separator + getApplicationName() + "-orm.jar");
        if (!ormJar.exists()) {
            throw new FileNotFoundException("Required ORM jar " + ormJar.getAbsolutePath() + " not found!");
        }
        Utils.copyFile(ormJar, new File(serviceLibDir, ormJar.getName()));
        LOG.debug("Copied SDK ORM jar " + ormJar.getName() + " into service");
        
        File isoHibernateJar = new File(getLocalClientDir(), "lib" + File.separator + "iso-datatypes-hibernate-2.1.1.jar");
        if (!isoHibernateJar.exists()) {
            throw new FileNotFoundException("Require jar " + isoHibernateJar.getAbsolutePath() + " not found!");
        }
        Utils.copyFile(isoHibernateJar, new File(serviceLibDir, isoHibernateJar.getName()));
        LOG.debug("Copied ISO-Hibernate jar " + isoHibernateJar.getName() + " into service");
    }
    
    
    public boolean isLocalClientDirValid() {
        boolean valid = false;
        File dir = new File(getLocalClientDir());
        if (dir.exists() && dir.isDirectory()) {
            File confDir = new File(dir, "conf");
            File libDir = new File(dir, "lib");
            valid = confDir.isDirectory() && confDir.exists() 
            && libDir.isDirectory() && libDir.exists();
        }
        return valid;
    }
    
    
    public boolean isRemoteClientDirValid() {
        boolean valid = false;
        File dir = new File(getRemoteClientDir());
        if (dir.exists() && dir.isDirectory()) {
            File confDir = new File(dir, "conf");
            File libDir = new File(dir, "lib");
            valid = confDir.isDirectory() && confDir.exists() 
            && libDir.isDirectory() && libDir.exists();
        }
        return valid;
    }


    public String getApplicationName() {
        return applicationName;
    }


    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }


    public boolean isLocalApi() {
        return isLocalApi;
    }


    public void setLocalApi(boolean isLocalApi) {
        this.isLocalApi = isLocalApi;
    }


    public String getLocalClientDir() {
        return localClientDir;
    }


    public void setLocalClientDir(String localClientDir) {
        this.localClientDir = localClientDir;
    }


    public String getRemoteClientDir() {
        return remoteClientDir;
    }


    public void setRemoteClientDir(String remoteClientDir) {
        this.remoteClientDir = remoteClientDir;
    }


    public String getApplicationHostname() {
        return applicationHostname;
    }


    public void setApplicationHostname(String applicationHostname) {
        this.applicationHostname = applicationHostname;
    }


    public Integer getApplicationPort() {
        return applicationPort;
    }


    public void setApplicationPort(Integer applicationPort) {
        this.applicationPort = applicationPort;
    }


    public boolean isUseHttps() {
        return useHttps;
    }


    public void setUseHttps(boolean useHttps) {
        this.useHttps = useHttps;
    }
    
    
    public boolean isUseJaxB() {
        return useJaxB;
    }
    
    
    public void setUseJaxB(boolean useJaxB) {
        this.useJaxB = useJaxB;
    }
}
