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
package gov.nih.nci.cagrid.validator;

/** 
 *  ValidationLoadingException
 *  Exception thrown to indicate a problem with loading a 
 *  deployment validation description
 * 
 * @author David Ervin
 * 
 * @created Sep 5, 2007 11:42:17 AM
 * @version $Id: ValidationLoadingException.java,v 1.1 2008-03-25 14:20:30 dervin Exp $ 
 */
public class ValidationLoadingException extends Exception {

    public ValidationLoadingException(String message) {
        super(message);
    }
    
    
    public ValidationLoadingException(Exception cause) {
        super(cause);
    }
    
    
    public ValidationLoadingException(String message, Exception cause) {
        super(message, cause);
    }
}
