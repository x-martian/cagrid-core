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
package gov.nih.nci.cagrid.data.cql.validation;

import gov.nih.nci.cagrid.common.SchemaValidationException;
import gov.nih.nci.cagrid.common.SchemaValidator;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.data.CqlSchemaConstants;
import gov.nih.nci.cagrid.data.MalformedQueryException;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;


/** 
 *  CQLValidator
 *  Validates a CQL query document against the CQL schema
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * 
 * @created May 16, 2006 
 * @version $Id$ 
 */
public class JaxPCqlValidator implements CqlStructureValidator {
	
	private SchemaValidator validator;
		
	public JaxPCqlValidator(String xsdFilename) throws SchemaValidationException {
		validator = new SchemaValidator(xsdFilename);
	}
	
	
	public void validateCqlStructure(CQLQuery query) throws MalformedQueryException {
		// have to convert the query back to XML to be handed off to the schema validator
		StringWriter objectWriter = new StringWriter();
		try {
			FileInputStream configStream = new FileInputStream(new File("client-config.wsdd"));
			Utils.serializeObject(query, CqlSchemaConstants.CQL_QUERY_QNAME, objectWriter, configStream);
		} catch (Exception ex) {
			throw new MalformedQueryException("Error serializing the query: " + ex.getMessage(), ex);
		}
		String xmlText = objectWriter.getBuffer().toString();
		try {
			validator.validate(xmlText);
		} catch (SchemaValidationException ex) {
			throw new MalformedQueryException(ex.getMessage(), ex);
		}
	}
}
