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
package gov.nih.nci.cagrid.sdkquery4.style.helper;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.data.common.CastorMappingUtil;
import gov.nih.nci.cagrid.data.style.StyleCreationPostProcessor;
import gov.nih.nci.cagrid.data.utilities.WsddUtil;
import gov.nih.nci.cagrid.introduce.IntroduceConstants;
import gov.nih.nci.cagrid.introduce.beans.extension.ServiceExtensionDescriptionType;
import gov.nih.nci.cagrid.introduce.beans.service.ServiceType;
import gov.nih.nci.cagrid.introduce.common.CommonTools;
import gov.nih.nci.cagrid.introduce.common.ServiceInformation;
import gov.nih.nci.cagrid.introduce.extension.CodegenExtensionException;
import gov.nih.nci.cagrid.sdkquery4.encoding.SDK40EncodingUtils;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 
 *  SDK4PostCreationHelper
 *  Post-creation helper for caCORE SDK 4.0 data service style
 * 
 * @author David Ervin
 * 
 * @created Jan 18, 2008 12:38:01 PM
 * @version $Id: SDK4PostCreationHelper.java,v 1.3 2008-04-17 15:25:51 dervin Exp $ 
 */
public class SDK4PostCreationHelper implements StyleCreationPostProcessor {
    
    public static final String OLD_CASTOR_JAR_NAME = "castor-0.9.9.jar";
    
    private static final Log LOG = LogFactory.getLog(SDK4PostCreationHelper.class);

    public void creationPostProcessStyle(ServiceExtensionDescriptionType desc, 
        ServiceInformation serviceInfo) throws Exception {
        deleteOldCastorJar(serviceInfo);
        editWsddForCastorMappings(serviceInfo);
    }
    
    
    private void deleteOldCastorJar(ServiceInformation info) {
        File castorLib = new File(info.getBaseDirectory().getAbsolutePath() 
            + File.separator + "lib" + File.separator + OLD_CASTOR_JAR_NAME);
        LOG.debug("Deleting old castor jar (" + castorLib.getAbsolutePath() + ")");
        castorLib.delete();
    }
    
    
    private void editWsddForCastorMappings(ServiceInformation info) throws Exception {
        String mainServiceName = info.getIntroduceServiceProperties().getProperty(
            IntroduceConstants.INTRODUCE_SKELETON_SERVICE_NAME);
        ServiceType mainService = CommonTools.getService(info.getServices(), mainServiceName);
        String servicePackageName = mainService.getPackageName();
        String packageDir = servicePackageName.replace('.', File.separatorChar);
        // find the client source directory, where the client-config will be located
        File clientConfigFile = new File(info.getBaseDirectory().getAbsolutePath() + File.separator + "src"
            + File.separator + packageDir + File.separator + "client" + File.separator + "client-config.wsdd");
        if (!clientConfigFile.exists()) {
            throw new CodegenExtensionException("Client config file " + clientConfigFile.getAbsolutePath()
                + " not found!");
        }
        // fine the server-config.wsdd, located in the service's root directory
        File serverConfigFile = new File(info.getBaseDirectory().getAbsolutePath() + File.separator
            + "server-config.wsdd");
        if (!serverConfigFile.exists()) {
            throw new CodegenExtensionException("Server config file " 
                + serverConfigFile.getAbsolutePath() + " not found!");
        }
        
        // edit the marshalling castor mapping to avoid serializing associations
        String marshallingXmlText = Utils.fileToStringBuffer(
            new File(CastorMappingUtil.getMarshallingCastorMappingFileName(info))).toString();
        String editedMarshallingText = CastorMappingUtil.removeAssociationMappings(marshallingXmlText);
        String editedMarshallingFileName = CastorMappingUtil.getEditedMarshallingCastorMappingFileName(info);
        Utils.stringBufferToFile(new StringBuffer(editedMarshallingText), editedMarshallingFileName);
        
        // edit the UNmarshalling castor mapping to avoid DEserializing associations
        String unmarshallingXmlText = Utils.fileToStringBuffer(
            new File(CastorMappingUtil.getUnmarshallingCastorMappingFileName(info))).toString();
        String editedUnmarshallingText = CastorMappingUtil.removeAssociationMappings(unmarshallingXmlText);
        String editedUnmarshallingFileName = CastorMappingUtil.getEditedUnmarshallingCastorMappingFileName(info);
        Utils.stringBufferToFile(new StringBuffer(editedUnmarshallingText), editedUnmarshallingFileName);
        
        // set properties in the client to use the edited marshaller
        WsddUtil.setGlobalClientParameter(clientConfigFile.getAbsolutePath(),
            SDK40EncodingUtils.CASTOR_MARSHALLER_PROPERTY, 
            CastorMappingUtil.getEditedMarshallingCastorMappingName(info));
        // and the edited unmarshaller
        WsddUtil.setGlobalClientParameter(clientConfigFile.getAbsolutePath(),
            SDK40EncodingUtils.CASTOR_UNMARSHALLER_PROPERTY, 
            CastorMappingUtil.getEditedUnmarshallingCastorMappingName(info));
        
        // set properties in the server to use the edited marshaller
        WsddUtil.setServiceParameter(serverConfigFile.getAbsolutePath(),
            info.getServices().getService(0).getName(),
            SDK40EncodingUtils.CASTOR_MARSHALLER_PROPERTY,
            CastorMappingUtil.getEditedMarshallingCastorMappingName(info));
        // and the edited unmarshaller
        WsddUtil.setServiceParameter(serverConfigFile.getAbsolutePath(),
            info.getServices().getService(0).getName(),
            SDK40EncodingUtils.CASTOR_UNMARSHALLER_PROPERTY,
            CastorMappingUtil.getEditedUnmarshallingCastorMappingName(info));
    }
}
