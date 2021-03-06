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
package gov.nih.nci.cagrid.introduce.portal;

import gov.nih.nci.cagrid.introduce.portal.modification.discovery.NamespaceTypeDiscoveryComponent;

import java.util.List;
import java.util.Properties;

import org.jdom.Element;


public class NamespaceTypeDiscoveryDescriptor {
    private static final String PROPERTIES = "properties";
    private static final String PROPERTY = "property";

    private String classname;
    private String type;
    private String displayName;

    private Properties properties;


    public NamespaceTypeDiscoveryDescriptor(Element el) {
        properties = new Properties();
        classname = el.getAttributeValue("class");
        type = el.getAttributeValue("type");
        displayName = el.getAttributeValue("displayName");
        Element propertiesEl = el.getChild(PROPERTIES, el.getNamespace());
        if (propertiesEl != null) {
            List propertyElArr = propertiesEl.getChildren(PROPERTY, el.getNamespace());
            for (int i = 0; i < propertyElArr.size(); i++) {
                Element propEl = (Element) propertyElArr.get(i);
                String key = propEl.getAttributeValue("key");
                String value = propEl.getAttributeValue("value");
                this.properties.put(key, value);
            }
        }
    }


    public String getClassname() {
        return this.classname;
    }


    public String getType() {
        return this.type;
    }


    public String getProperty(String key) {
        return properties.getProperty(key);
    }


    public NamespaceTypeDiscoveryComponent getNamespaceTypeDiscoveryComponent() throws Exception {
        // TODO: Does this need to come out of the ExtensionTools.loadExtensionClass?
        Class<?> c = Class.forName(getClassname());
        Object obj = c.newInstance();
        return (NamespaceTypeDiscoveryComponent) obj;
    }


    public String getDisplayName() {
        return displayName;
    }

}
