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
package org.cagrid.tests.data.styles.cacore44.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads database properties
 * 
 * @author David
 */
public class DatabaseProperties {
    
    public static final String TESTS_BASE_DIR_PROPERTY = "sdk44.tests.base.dir";
    public static final String PROPERTIES_FILENAME = "resources" + File.separator + "sdk44.test.database.properties";
    
    private static Properties props = null;
    
    private DatabaseProperties() {
        // prevents instantiating this thing
    }
    
    
    private static synchronized Properties getProps() throws IOException {
        if (props == null) {
            String baseDir = System.getProperty(TESTS_BASE_DIR_PROPERTY);
            props = new Properties();
            InputStream propsIn = new FileInputStream(new File(baseDir, PROPERTIES_FILENAME));
            props.load(propsIn);
            propsIn.close();
        }
        return props;
    }
    
    
    public static String getServer() throws IOException {
        return getProps().getProperty("db.server");
    }
    
    
    public static String getPort() throws IOException {
        return getProps().getProperty("db.port");
    }
    
    
    public static String getSchemaName() throws IOException {
        return getProps().getProperty("db.schema.name");
    }
    
    
    public static String getUsername() throws IOException {
        return getProps().getProperty("db.username");
    }
    
    
    public static String getPassword() throws IOException {
        return getProps().getProperty("db.password");
    }
}
