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
package gov.nih.nci.cagrid.introduce.common;

import gov.nih.nci.cagrid.introduce.IntroduceConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public final class IntroducePropertiesManager {

    private static final Log logger = LogFactory.getLog(IntroducePropertiesManager.class);


    private IntroducePropertiesManager() {

    }


    public static String getIntroduceVersion() {
        return getIntroducePropertyValue(IntroduceConstants.INTRODUCE_VERSION_PROPERTY);
    }


    public static String getIntroducePatchVersion() {
        return getIntroducePropertyValue(IntroduceConstants.INTRODUCE_PATCH_VERSION_PROPERTY);
    }


    public static String getIntroduceConfigurationFile() {
        return getIntroducePropertyValue(IntroduceConstants.INTRODUCE_CONFIGURATION_FILE);
    }


    public static String getIntroducePropertyValue(String propertyKey) {
        Properties engineProps = new Properties();
        try {
            FileInputStream fis = new FileInputStream(IntroduceConstants.INTRODUCE_PROPERTIES);
            engineProps.load(fis);
            fis.close();
            return engineProps.getProperty(propertyKey);
        } catch (IOException e) {
            logger.error(e);
            return null;
        }
    }
}
