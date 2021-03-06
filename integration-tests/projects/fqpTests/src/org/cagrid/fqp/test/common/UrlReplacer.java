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
package org.cagrid.fqp.test.common;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.dcql.DCQLQuery;
import gov.nih.nci.cagrid.fqp.common.DCQLConstants;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.cagrid.cql.utilities.DCQL2SerializationUtil;

/**
 * UrlReplacer
 * Replaces URL placeholders in a DCQL query
 * 
 * @author ervin
 */
public class UrlReplacer {

    public static DCQLQuery replaceUrls(DCQLQuery query, Map<String, String> replacements) throws Exception {
        StringWriter writer = new StringWriter();
        Utils.serializeObject(query, DCQLConstants.DCQL_QUERY_QNAME, writer);
        StringBuffer dcqlXml = writer.getBuffer();
        for (String placeholder : replacements.keySet()) {
            replaceAll(dcqlXml, placeholder, replacements.get(placeholder));
        }
        DCQLQuery cleaned = Utils.deserializeObject(new StringReader(dcqlXml.toString()), DCQLQuery.class);
        return cleaned;
    }
    
    
    public static org.cagrid.data.dcql.DCQLQuery replaceUrls(org.cagrid.data.dcql.DCQLQuery query, Map<String, String> replacements) throws Exception {
        StringWriter writer = new StringWriter();
        DCQL2SerializationUtil.serializeDcql2Query(query, writer);
        StringBuffer dcqlXml = writer.getBuffer();
        for (String placeholder : replacements.keySet()) {
            replaceAll(dcqlXml, placeholder, replacements.get(placeholder));
        }
        org.cagrid.data.dcql.DCQLQuery cleaned = DCQL2SerializationUtil.deserializeDcql2Query(new StringReader(dcqlXml.toString()));
        return cleaned;
    }
    
    
    private static void replaceAll(StringBuffer buffer, String placeholder, String replacement) {
        int index = -1;
        while ((index = buffer.indexOf(placeholder)) != -1) {
            buffer.replace(index, index + placeholder.length(), replacement);
        }
    }
}
