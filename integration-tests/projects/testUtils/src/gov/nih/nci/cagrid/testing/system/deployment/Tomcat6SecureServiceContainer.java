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
package gov.nih.nci.cagrid.testing.system.deployment;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.common.XMLUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.Filter;


/**
 * Tomcat6SecureServiceContainer 
 * Service container implementation for Tomcat 6 with security
 * 
 * @author David Ervin
 */
public class Tomcat6SecureServiceContainer extends Tomcat6ServiceContainer implements SecureContainer {
    public static final String SECURITY_DESCRIPTOR_PLACEHOLDER = "<!-- @CONTAINER_SECURITY_DESCRIPTOR@ -->";
    public static final String DESCRIPTOR_FILE_PLACEHOLDER = "@@LOCATION@@";
    public static final String SECURITY_DESCRIPTOR_PARAMETER_TEMPLATE = "" +
            "<parameter name=\"containerSecDesc\"\n" +
            "\t\tvalue=\"" + DESCRIPTOR_FILE_PLACEHOLDER + "\"/>";
    
    public Tomcat6SecureServiceContainer(ContainerProperties properties) {
        super(properties);
    }
    
    
    public void unpackContainer() throws ContainerException {
        super.unpackContainer();
        File wsrfCoreDir = new File(getProperties().getContainerDirectory(), 
            "webapps" + File.separator + "wsrf" + File.separator + "WEB-INF" + File.separator +
            "etc" + File.separator + "globus_wsrf_core");
        // locate the security descriptor file
        File globalSecurityDescriptorFile = new File(wsrfCoreDir, "global_security_descriptor.xml");
        Document descriptorDocument = null;
        try {
            descriptorDocument = XMLUtilities.fileNameToDocument(
                globalSecurityDescriptorFile.getCanonicalPath());
        } catch (Exception ex) {
            throw new ContainerException("Error loading global security descriptor: " + ex.getMessage(), ex);
        }
        // locate certificates
        File hostKey = new File(getCertificatesDirectory(), "localhost_key.pem");
        File hostCert = new File(getCertificatesDirectory(), "localhost_cert.pem");
        File caCertDir = new File(getCertificatesDirectory(), "ca");
        // fix the security configuration
        Element securityConfigElement = descriptorDocument.getRootElement();
        Element credentialElement = securityConfigElement.getChild("credential", securityConfigElement.getNamespace());
        Element keyFileElement = credentialElement.getChild("key-file", credentialElement.getNamespace());
        Element certFileElement = credentialElement.getChild("cert-file", credentialElement.getNamespace());
        try {
            keyFileElement.setAttribute("value", hostKey.getCanonicalPath().replace(File.separatorChar, '/'));
            certFileElement.setAttribute("value", hostCert.getCanonicalPath().replace(File.separatorChar, '/'));
        } catch (IOException ex) {
            throw new ContainerException("Error setting host key and cert paths: " + ex.getMessage(), ex);
        }
        // write the config back to disk
        try {
            String fixedConfig = XMLUtilities.formatXML(XMLUtilities.documentToString(descriptorDocument));
            Utils.stringBufferToFile(new StringBuffer(fixedConfig), globalSecurityDescriptorFile.getCanonicalPath());
        } catch (Exception ex) {
            throw new ContainerException("Error writting edited global security descriptor: " + ex.getMessage(), ex);
        }
        
        // edit the server-config.wsdd to use the modified global security descriptor.
        File serverWsdd = new File(wsrfCoreDir, "server-config.wsdd");
        StringBuffer wsddContents = null;
        try {
            wsddContents = Utils.fileToStringBuffer(serverWsdd);
            String parameter = SECURITY_DESCRIPTOR_PARAMETER_TEMPLATE.replace(
                DESCRIPTOR_FILE_PLACEHOLDER, globalSecurityDescriptorFile.getCanonicalPath().replace(File.separatorChar, '/'));
            int placeholderIndex = wsddContents.indexOf(SECURITY_DESCRIPTOR_PLACEHOLDER);
            wsddContents.replace(placeholderIndex, placeholderIndex + SECURITY_DESCRIPTOR_PLACEHOLDER.length(), parameter);
            Utils.stringBufferToFile(wsddContents, serverWsdd.getCanonicalPath());
        } catch (Exception ex) {
            throw new ContainerException("Error editing server-config.wsdd for editing");
        }
        
        // edit the conf/server.xml to set the key, cert, and CA dirs for the https connector
        File serverConfFile = new File(getProperties().getContainerDirectory(), "conf" + File.separator + "server.xml");
        Document confDocument = null;
        try {
            confDocument = XMLUtilities.fileNameToDocument(serverConfFile.getCanonicalPath());
        } catch (Exception ex) {
            throw new ContainerException("Error loading server configuration document for editing: " + ex.getMessage(), ex);
        }
        // locate the https connector element
        Element serverElement = confDocument.getRootElement();
        Iterator httpsConnectorElements = serverElement.getDescendants(new Filter() {
            public boolean matches(Object o) {
                if (o instanceof Element) {
                    Element e = (Element) o;
                    if (e.getName().equals("Connector")) {
                        String socketFactoryValue = e.getAttributeValue("socketFactory");
                        if ("org.globus.tomcat.catalina.net.BaseHTTPSServerSocketFactory".equals(socketFactoryValue)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        // verify there is only one connector
        if (!httpsConnectorElements.hasNext()) {
            throw new ContainerException("No HTTPS connector could be located in the server configuration!");
        }
        Element connector = (Element) httpsConnectorElements.next();
        if (httpsConnectorElements.hasNext()) {
            throw new ContainerException("More than one HTTPS connector was found in the server configuration!");
        }
        // set up cert, key, cacertdir paths
        try {
            connector.setAttribute("cert", hostCert.getCanonicalPath().replace(File.separatorChar, '/'));
            connector.setAttribute("key", hostKey.getCanonicalPath().replace(File.separatorChar, '/'));
            // TODO: this might be case-sensitive in Tomcat 6, verify it works as is, or change to 'cacertdir'
            connector.setAttribute("cACertDir", caCertDir.getCanonicalPath().replace(File.separatorChar, '/'));
        } catch (IOException ex) {
            throw new ContainerException("Error configuring HTTPS connector: " + ex.getMessage(), ex);
        }
        // write the config back to disk
        String confXml = XMLUtilities.documentToString(confDocument);
        try {
            Utils.stringBufferToFile(new StringBuffer(confXml), serverConfFile.getCanonicalPath());
        } catch (Exception ex) {
            throw new ContainerException("Error writing server configuration file back to disk: " + ex.getMessage(), ex);
        }
    }


    public File getCertificatesDirectory() {
        return new File(this.getProperties().getContainerDirectory() + File.separator + "certificates");
    }
}
