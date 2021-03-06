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

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.introduce.beans.configuration.IntroducePortalConfiguration;
import gov.nih.nci.cagrid.introduce.beans.configuration.IntroduceServiceDefaults;
import gov.nih.nci.cagrid.introduce.beans.extension.Properties;
import gov.nih.nci.cagrid.introduce.beans.extension.PropertiesProperty;

import org.cagrid.grape.ConfigurationManager;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.model.Application;


/**
 * Class for accessing the configuration files.
 * 
 * @author hastings
 */
public class ConfigurationUtil {

    private static ConfigurationUtil instance = null;
    
    private ConfigurationManager configurationManager = null;


    private ConfigurationUtil() throws Exception {
        if (GridApplication.getContext() != null) {
            configurationManager = GridApplication.getContext().getConfigurationManager();
        } else {
            Application app = null;
            app = Utils.deserializeDocument(IntroducePropertiesManager.getIntroduceConfigurationFile(),
                Application.class);
            configurationManager = new ConfigurationManager(app.getConfiguration(), null);
        }
    }


    public static synchronized ConfigurationUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new ConfigurationUtil();
        }
        return instance;
    }


    public static synchronized void saveConfiguration() throws Exception {
        getInstance().configurationManager.saveAll();
    }


    public static synchronized IntroducePortalConfiguration getIntroducePortalConfiguration() {
        try {
            return (IntroducePortalConfiguration) getInstance().configurationManager
                .getConfigurationObject("introducePortal");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static synchronized IntroduceServiceDefaults getIntroduceServiceDefaults() {
        try {
            return (IntroduceServiceDefaults) getInstance().configurationManager
                .getConfigurationObject("introduceServiceDefaults");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static synchronized Properties getGlobalExtensionProperties() {
        try {
            return (Properties) getInstance().configurationManager
                .getConfigurationObject("introduceGlobalExtensionProperties");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static synchronized PropertiesProperty getGlobalExtensionProperty(String key) throws Exception {
        getInstance();
        if (getGlobalExtensionProperties() != null && getGlobalExtensionProperties().getProperty() != null) {
            for (int i = 0; i < getGlobalExtensionProperties().getProperty().length; i++) {
                if (getGlobalExtensionProperties().getProperty()[i].getKey().equals(key)) {
                    return getGlobalExtensionProperties().getProperty(i);
                }
            }
        }
        return null;
    }

}
