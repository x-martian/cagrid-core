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
package org.test.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.axis.utils.ClassUtils;

import org.globus.gsi.GlobusCredential;

import org.test.stubs.IntroduceTestServicePortType;
import org.test.stubs.service.IntroduceTestServiceAddressingLocator;
import org.test.common.IntroduceTestServiceI;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT.
 *
 * This class is not thread safe.  A new instance should be created for any threads using this class.
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.0
 */
public class IntroduceTestServiceClient extends ServiceSecurityClient implements IntroduceTestServiceI {	
	protected IntroduceTestServicePortType portType;
	private Object portTypeMutex;

	public IntroduceTestServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public IntroduceTestServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	   	initialize();
	}
	
	public IntroduceTestServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public IntroduceTestServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
		initialize();
	}
	
	private void initialize() throws RemoteException {
	    this.portTypeMutex = new Object();
		this.portType = createPortType();
	}

	private IntroduceTestServicePortType createPortType() throws RemoteException {

		IntroduceTestServiceAddressingLocator locator = new IntroduceTestServiceAddressingLocator();
		// attempt to load our context sensitive wsdd file
		InputStream resourceAsStream = ClassUtils.getResourceAsStream(getClass(), "client-config.wsdd");
		if (resourceAsStream != null) {
			// we found it, so tell axis to configure an engine to use it
			EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
			// set the engine of the locator
			locator.setEngine(new AxisClient(engineConfig));
		}
		IntroduceTestServicePortType port = null;
		try {
			port = locator.getIntroduceTestServicePortTypePort(getEndpointReference());
		} catch (Exception e) {
			throw new RemoteException("Unable to locate portType:" + e.getMessage(), e);
		}

		return port;
	}

	public static void usage(){
		System.out.println(IntroduceTestServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client to connect to: " + args[1]);
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			   IntroduceTestServiceClient client = new IntroduceTestServiceClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
			  System.out.println(client.newMethod("I am calling new method"));
			} else {
				usage();
				System.exit(1);
			}
		} else {
			usage();
			System.exit(1);
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

    public java.lang.String newMethod(java.lang.String foo) throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"newMethod");
        org.test.stubs.NewMethodRequest params = new org.test.stubs.NewMethodRequest();
        params.setFoo(foo);
        org.test.stubs.NewMethodResponse boxedResult = portType.newMethod(params);
        return boxedResult.getResponse();
      }
    }
    public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"getServiceSecurityMetadata");
        gov.nih.nci.cagrid.introduce.security.GetServiceSecurityMetadataRequest params = new gov.nih.nci.cagrid.introduce.security.GetServiceSecurityMetadataRequest();
        gov.nih.nci.cagrid.introduce.security.GetServiceSecurityMetadataResponse boxedResult = portType.getServiceSecurityMetadata(params);
        return boxedResult.getServiceSecurityMetadata();
      }
    }

}
