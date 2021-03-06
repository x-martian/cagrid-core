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
package org.cagrid.transfer.context.client;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.axis.gsi.GSIConstants;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.net.GSIHttpURLConnection;
import org.ietf.jgss.GSSCredential;

import org.cagrid.transfer.context.stubs.TransferServiceContextPortType;
import org.cagrid.transfer.context.stubs.service.TransferServiceContextServiceAddressingLocator;
import org.cagrid.transfer.context.client.helper.TransferClientHelper;
import org.cagrid.transfer.context.common.TransferServiceContextI;
import org.cagrid.transfer.descriptor.DataTransferDescriptor;

import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class TransferServiceContextClient extends TransferServiceContextClientBase implements TransferServiceContextI {	

	public TransferServiceContextClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public TransferServiceContextClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public TransferServiceContextClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public TransferServiceContextClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(TransferServiceContextClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  TransferServiceContextClient client = new TransferServiceContextClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
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
	
    /**
     * Returns a handle to the input stream of the socket which is returning the
     * data referred to by the descriptor. This method can make an https
     * connection if desired using the credentials exist in this client.
     * 
     * @return
     * @throws Exception
     */
    public InputStream getData() throws Exception {
        return TransferClientHelper.getData(getDataTransferDescriptor(), getProxy());
    }

    /**
     * Reads from the input stream to put the data to the server. Be sure to close the stream when
     * done writing the data. This method can use http and https if the
     * credentials exist in this client.  This is a blocking call. Will return with the entire data has been transmitted.
     * 
     * @param is                input stream providing the data
     * @param contentLength     number of bytes in the input stream to be read
     * @return
     * @throws Exception
     */
    public void putData(InputStream is, long contentLength) throws Exception {
        TransferClientHelper.putData(is, contentLength, getDataTransferDescriptor(), getProxy());
    }

  public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"destroy");
    return portType.destroy(params);
    }
  }

  public org.oasis.wsrf.lifetime.SetTerminationTimeResponse setTerminationTime(org.oasis.wsrf.lifetime.SetTerminationTime params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"setTerminationTime");
    return portType.setTerminationTime(params);
    }
  }

  public org.cagrid.transfer.descriptor.DataTransferDescriptor getDataTransferDescriptor() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getDataTransferDescriptor");
    org.cagrid.transfer.context.stubs.GetDataTransferDescriptorRequest params = new org.cagrid.transfer.context.stubs.GetDataTransferDescriptorRequest();
    org.cagrid.transfer.context.stubs.GetDataTransferDescriptorResponse boxedResult = portType.getDataTransferDescriptor(params);
    return boxedResult.getDataTransferDescriptor();
    }
  }

  public org.oasis.wsn.SubscribeResponse subscribe(org.oasis.wsn.Subscribe params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"subscribe");
    return portType.subscribe(params);
    }
  }

  public org.cagrid.transfer.descriptor.Status getStatus() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getStatus");
    org.cagrid.transfer.context.stubs.GetStatusRequest params = new org.cagrid.transfer.context.stubs.GetStatusRequest();
    org.cagrid.transfer.context.stubs.GetStatusResponse boxedResult = portType.getStatus(params);
    return boxedResult.getStatus();
    }
  }

  public void setStatus(org.cagrid.transfer.descriptor.Status status) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"setStatus");
    org.cagrid.transfer.context.stubs.SetStatusRequest params = new org.cagrid.transfer.context.stubs.SetStatusRequest();
    org.cagrid.transfer.context.stubs.SetStatusRequestStatus statusContainer = new org.cagrid.transfer.context.stubs.SetStatusRequestStatus();
    statusContainer.setStatus(status);
    params.setStatus(statusContainer);
    org.cagrid.transfer.context.stubs.SetStatusResponse boxedResult = portType.setStatus(params);
    }
  }

}
