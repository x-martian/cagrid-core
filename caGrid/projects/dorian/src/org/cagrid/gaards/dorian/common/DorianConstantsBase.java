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
package org.cagrid.gaards.dorian.common;

import javax.xml.namespace.QName;


/**
 * This class is autogenerated, DO NOT EDIT
 *
 * @created by Introduce Toolkit version 1.4
 */
public interface DorianConstantsBase {
	public static final String SERVICE_NS = "http://cagrid.nci.nih.gov/Dorian";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "DorianKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "DorianResourceProperties");
	public static final QName SERVICEMETADATA = new QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata", "ServiceMetadata");
	public static final QName AUTHENTICATIONPROFILES = new QName("http://gaards.cagrid.org/authentication", "AuthenticationProfiles");
	public static final QName TRUSTEDIDENTITYPROVIDERS = new QName("http://cagrid.nci.nih.gov/1/dorian-ifs", "TrustedIdentityProviders");
	public static final QName DORIANPOLICY = new QName("http://cagrid.nci.nih.gov/1/dorian-policy", "DorianPolicy");
	
}
