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
package gov.nih.nci.cagrid.data.enumeration.service;

import gov.nih.nci.cagrid.data.MalformedQueryException;
import gov.nih.nci.cagrid.data.QueryProcessingException;
import gov.nih.nci.cagrid.data.faults.MalformedQueryExceptionType;
import gov.nih.nci.cagrid.data.faults.QueryProcessingExceptionType;
import gov.nih.nci.cagrid.data.service.BaseDataServiceImpl;
import gov.nih.nci.cagrid.data.service.DataServiceInitializationException;
import gov.nih.nci.cagrid.enumeration.stubs.response.EnumerationResponseContainer;
import gov.nih.nci.cagrid.wsenum.common.WsEnumConstants;
import gov.nih.nci.cagrid.wsenum.utils.EnumConfigDiscoveryUtil;
import gov.nih.nci.cagrid.wsenum.utils.EnumIteratorFactory;
import gov.nih.nci.cagrid.wsenum.utils.IterImplType;

import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.ws.enumeration.EnumIterator;
import org.globus.ws.enumeration.EnumProvider;
import org.globus.ws.enumeration.EnumResource;
import org.globus.ws.enumeration.EnumResourceHome;
import org.globus.ws.enumeration.VisibilityProperties;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.container.ServiceHost;
import org.globus.wsrf.utils.AddressingUtils;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;

/** 
 * EnumerationDataServiceImpl
 * Enumeration query method implementation for CQL 1 data services
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public class Cql1EnumerationDataServiceImpl extends BaseDataServiceImpl {

    private static Log LOG = LogFactory.getLog(Cql1EnumerationDataServiceImpl.class);
	
	public Cql1EnumerationDataServiceImpl() throws DataServiceInitializationException {
		super();
	}
    
	
	public EnumerationResponseContainer enumerationQuery(gov.nih.nci.cagrid.cqlquery.CQLQuery cqlQuery) 
	    throws MalformedQueryExceptionType, QueryProcessingExceptionType {
	    // perform base query, get an iterator over OBJECTS, NOT CQLResults
	    Iterator<?> resultsIterator;
        try {
            resultsIterator = processCql1QueryAndIterate(cqlQuery);
        } catch (QueryProcessingException e) {
            throw getTypedException(e, new QueryProcessingExceptionType());
        } catch (MalformedQueryException e) {
            throw getTypedException(e, new MalformedQueryExceptionType());
        }
	    
	    // need to know the data type of the results, no way to do that without a call to next()
	    QName datatypeQName = getQNameForClass(cqlQuery.getTarget().getName());
	    	    
        // get the service property for the enum iterator type
        IterImplType implType = EnumConfigDiscoveryUtil.getConfiguredIterImplType();

        // wrap up the results iterator in an EnumIterator implementation
        LOG.debug("Creating EnumIterator for results");
        EnumIterator enumIter = null;
        try {
            enumIter = EnumIteratorFactory.createIterator(implType, resultsIterator, 
                datatypeQName, getServerConfigWsddStream());
        } catch (Exception ex) {
            throw getTypedException(
                new QueryProcessingException("Error creating EnumIterator implementation: " + ex.getMessage(), ex),
                new QueryProcessingExceptionType());
        }

        LOG.debug("Creating enumeration resource");
        EnumerationResponseContainer container = null;
        try {
            // set up the enumeration resource
            EnumResourceHome resourceHome = EnumResourceHome.getEnumResourceHome();
            VisibilityProperties visibility = new VisibilityProperties(
                "cagrid/" + WsEnumConstants.CAGRID_ENUMERATION_SERVICE_NAME, null);

            EnumResource resource = resourceHome.createEnumeration(enumIter, visibility, false);
            ResourceKey key = resourceHome.getKey(resource);

            // create the enumeration context
            EnumerationContextType enumContext = EnumProvider.createEnumerationContextType(key);

            // create the context's EPR
            URL baseURL = ServiceHost.getBaseURL();
            // TODO: the "cagrid" part is configurable, so we need to read this// from somewhere
            String serviceURI = baseURL.toString() + "cagrid/" + WsEnumConstants.CAGRID_ENUMERATION_SERVICE_NAME;

            EndpointReferenceType epr = AddressingUtils.createEndpointReference(serviceURI, key);

            // Create the response container and return it
            container = new EnumerationResponseContainer();
            container.setContext(enumContext);
            container.setEPR(epr);
        } catch (Exception ex) {
            throw getTypedException( 
                new QueryProcessingException("Error creating enum resource: " + ex.getMessage(), ex), 
                new QueryProcessingExceptionType());
        }
        return container;
	}
}

