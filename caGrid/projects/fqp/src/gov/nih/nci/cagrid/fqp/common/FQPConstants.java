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
package gov.nih.nci.cagrid.fqp.common;

import org.cagrid.fqp.execution.QueryExecutionParameters;
import org.cagrid.fqp.execution.TargetDataServiceQueryBehavior;

public interface FQPConstants {

    /**
     * The name of the results service context
     */
    public static final String RESULTS_SERVICE_NAME = "FederatedQueryResults";
    
    /**
     * The name of the results retrieval service context for DCQL 2
     */
    public static final String RESULTS_RETRIEVAL_SERVICE_NAME = "FederatedQueryResultsRetrieval";
    
    /**
     * The default target data service query behavior.  Derived from the XSD
     * definition, since Axis doesn't set the defaults with the no-arg constructor
     */
    public static final TargetDataServiceQueryBehavior DEFAULT_TARGET_QUERY_BEHAVIOR = 
        new TargetDataServiceQueryBehavior(Boolean.TRUE, Integer.valueOf(0), Integer.valueOf(5));
    
    /**
     * The default Query Execution Parameters.  Derived from the XSD
     * definition, since Axis doesn't set the defaults with the no-arg constructor
     */
    public static final QueryExecutionParameters DEFAULT_QUERY_EXECUTION_PARAMETERS = 
        new QueryExecutionParameters(Boolean.FALSE, DEFAULT_TARGET_QUERY_BEHAVIOR);
}
