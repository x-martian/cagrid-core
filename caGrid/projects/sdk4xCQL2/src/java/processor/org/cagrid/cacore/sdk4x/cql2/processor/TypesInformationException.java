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
package org.cagrid.cacore.sdk4x.cql2.processor;

public class TypesInformationException extends Exception {

    public TypesInformationException(String message) {
        super(message);
    }
    
    
    public TypesInformationException(Throwable cause) {
        super(cause);
    }
    
    
    public TypesInformationException(String message, Throwable cause) {
        super(message, cause);
    }
}
