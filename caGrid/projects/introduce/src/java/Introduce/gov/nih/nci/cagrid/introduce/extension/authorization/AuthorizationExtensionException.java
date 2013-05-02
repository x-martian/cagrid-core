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
package gov.nih.nci.cagrid.introduce.extension.authorization;

/**
 * 
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella </A>
 * @created 
 */
public class AuthorizationExtensionException extends Exception {

	public AuthorizationExtensionException(String message) {
		super(message);
	}
	
	
	public AuthorizationExtensionException(Throwable th) {
		super(th);
	}
	
	
	public AuthorizationExtensionException(String message, Throwable th) {
		super(message, th);
	}
}
