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
/**
 * CQLResult.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.2RC2 Apr 28, 2006
 * (12:42:00 EDT) WSDL2Java emitter.
 */

package org.cagrid.cql2.results;

/**
 * Single result from a CQL query
 */
public abstract class CQLResult implements java.io.Serializable {

    public CQLResult() {
    }


    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CQLResult))
            return false;
        CQLResult other = (CQLResult) obj;
        if (other == null)
            return false;
        if (this == other)
            return true;
        boolean _equals;
        _equals = true;
        return _equals;
    }


    public synchronized int hashCode() {
        int _hashCode = 1;
        return _hashCode;
    }
}
