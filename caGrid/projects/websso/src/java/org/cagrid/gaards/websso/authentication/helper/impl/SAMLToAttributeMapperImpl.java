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
package org.cagrid.gaards.websso.authentication.helper.impl;

import gov.nih.nci.cagrid.common.XMLUtilities;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.gaards.websso.authentication.helper.SAMLToAttributeMapper;
import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.cagrid.gaards.websso.utils.WebSSOConstants;
import org.opensaml.XML.ParserPool;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SAMLToAttributeMapperImpl implements SAMLToAttributeMapper {

	private final Log log = LogFactory.getLog(getClass());
	private static final String EMAIL_EXP = "/*[local-name()='Assertion']/*[local-name()='AttributeStatement']/*[local-name()='Attribute' and @AttributeName='urn:mace:dir:attribute-def:mail']/*[local-name()='AttributeValue']/text()";
	private static final String FIRST_NAME_EXP = "/*[local-name()='Assertion']/*[local-name()='AttributeStatement']/*[local-name()='Attribute' and @AttributeName='urn:mace:dir:attribute-def:givenName']/*[local-name()='AttributeValue']/text()";
	private static final String LAST_NAME_EXP = "/*[local-name()='Assertion']/*[local-name()='AttributeStatement']/*[local-name()='Attribute' and @AttributeName='urn:mace:dir:attribute-def:sn']/*[local-name()='AttributeValue']/text()";

	public static final String DOCUMENT_BUILDER_FACTORY_IMPL = "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl";
	
	public HashMap<String, String> convertSAMLtoHashMap(
			SAMLAssertion samlAssertion)
			throws AuthenticationConfigurationException {

		HashMap<String, String> attributesMap = new HashMap<String, String>();
		try {
			DocumentBuilderFactory newInstance = XMLUtilities.getDocumentBuilderFactory();
			DocumentBuilder documentBuilder = newInstance.newDocumentBuilder();
			ByteArrayInputStream is = new ByteArrayInputStream(samlAssertion
					.toString().getBytes());
			Document document = documentBuilder.parse(is);

			XPath xpathEngine = XPathFactory.newInstance().newXPath();
			String emailId = (String) xpathEngine.evaluate(EMAIL_EXP, document,
					XPathConstants.STRING);
			String firstName = (String) xpathEngine.evaluate(FIRST_NAME_EXP,
					document, XPathConstants.STRING);
			String lastName = (String) xpathEngine.evaluate(LAST_NAME_EXP,
					document, XPathConstants.STRING);
			attributesMap.put(WebSSOConstants.CAGRID_SSO_EMAIL_ID, emailId);
			attributesMap.put(WebSSOConstants.CAGRID_SSO_FIRST_NAME, firstName);
			attributesMap.put(WebSSOConstants.CAGRID_SSO_LAST_NAME, lastName);
		} catch (Exception e) {
			handleException(e);
		}
		return attributesMap;
	}

	private void handleException(Exception e)
			throws AuthenticationConfigurationException {
		log.error(e);
		if (e instanceof ParserConfigurationException) {
			throw new AuthenticationConfigurationException(
					"Error processing the SAML Document: " + e.getMessage(), e);
		}
		if (e instanceof SAXException) {
			throw new AuthenticationConfigurationException(
					"Error processing the SAML Document: " + e.getMessage(), e);
		}
		if (e instanceof XPathExpressionException) {
			throw new AuthenticationConfigurationException(
					"Error retrieving user attributes from the SAML : "
							+ e.getMessage(), e);
		}
		if (e instanceof IOException) {
			throw new AuthenticationConfigurationException(
					"Error processing the SAML Document: " + e.getMessage(), e);
		}
	}
}
