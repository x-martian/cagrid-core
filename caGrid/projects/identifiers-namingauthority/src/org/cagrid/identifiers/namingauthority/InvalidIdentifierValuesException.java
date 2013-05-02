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
package org.cagrid.identifiers.namingauthority;

public class InvalidIdentifierValuesException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    public InvalidIdentifierValuesException() {
    }


    public InvalidIdentifierValuesException(String message) {
        super(message);
    }


    public InvalidIdentifierValuesException(Throwable cause) {
        super(cause);
    }


    public InvalidIdentifierValuesException(String message, Throwable cause) {
        super(message, cause);
    }

}
