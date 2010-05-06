package gov.nih.nci.cagrid.workflow.factory.client;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.common.security.ProxyUtil;
import gov.nih.nci.cagrid.workflow.factory.common.TavernaWorkflowServiceI;
import gov.nih.nci.cagrid.workflow.service.impl.client.TavernaWorkflowServiceImplClient;
import gov.nih.nci.cagrid.workflow.service.impl.common.TavernaWorkflowServiceImplConstantsBase;
import gov.nih.nci.cagrid.workflow.service.impl.stubs.types.CannotSetCredential;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.xml.namespace.QName;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.gaards.cds.client.ClientConstants;
import org.cagrid.gaards.cds.client.DelegationUserClient;
import org.cagrid.gaards.cds.common.IdentityDelegationPolicy;
import org.cagrid.gaards.cds.common.ProxyLifetime;
import org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference;
import org.cagrid.transfer.context.client.TransferServiceContextClient;
import org.cagrid.transfer.context.client.helper.TransferClientHelper;
import org.cagrid.transfer.context.stubs.types.TransferServiceContextReference;
import org.cagrid.transfer.descriptor.DataDescriptor;
import org.cagrid.transfer.descriptor.DataTransferDescriptor;
import org.cagrid.transfer.descriptor.Status;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;
import org.globus.wsrf.container.ContainerException;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;
import org.xml.sax.InputSource;

import workflowmanagementfactoryservice.StartInputType;
import workflowmanagementfactoryservice.WMSInputType;
import workflowmanagementfactoryservice.WMSOutputType;
import workflowmanagementfactoryservice.WSDLReferences;
import workflowmanagementfactoryservice.WorkflowOutputType;
import workflowmanagementfactoryservice.WorkflowPortType;
import workflowmanagementfactoryservice.WorkflowStatusType;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS
 * METHODS.
 * 
 * This client is generated automatically by Introduce to provide a clean
 * unwrapped API to the service.
 * 
 * On construction the class instance will contact the remote service and
 * retrieve it's security metadata description which it will use to configure
 * the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */

/**
 * Has helper methods along with the Introduce generated  GRID SERVICE ACCESS METHODS.
 * 
 * @author sulakhe
 * @version caGrid 1.4
 * @date 02/26/10
 */
public class TavernaWorkflowServiceClient extends
TavernaWorkflowServiceClientBase implements TavernaWorkflowServiceI {

	private EndpointReferenceType workflowEPR = null;

	public EndpointReferenceType getWorkflowEPR() {
		return this.workflowEPR;
	}

	public void setWorkflowEPR(EndpointReferenceType workflowEPR) {
		this.workflowEPR = workflowEPR;
	}

	public TavernaWorkflowServiceClient(String url)
	throws MalformedURIException, RemoteException {
		this(url, null);
	}

	public TavernaWorkflowServiceClient(String url, GlobusCredential proxy)
	throws MalformedURIException, RemoteException {
		super(url, proxy);
	}

	public TavernaWorkflowServiceClient(EndpointReferenceType epr)
	throws MalformedURIException, RemoteException {
		this(epr, null);
	}

	public TavernaWorkflowServiceClient(EndpointReferenceType epr,
			GlobusCredential proxy) throws MalformedURIException,
			RemoteException {
		super(epr, proxy);
	}

	public static void usage(){
		System.out.println(TavernaWorkflowServiceClient.class.getName() + " -url <service url>" 
				+ "\n -scuflDoc <path_to_file>" + "\n -inputDoc <path_to_input_file>" 
				+ "\n -ouputDoc <path_for_output_file(Optional)>");
	}

	/**
	 * First method to be invoked on the Workflow service that handles the workflow execution.
	 * Takes the workflow definition file (t2flow) as an input and creates a resource on the Context service.

	 * 
	 * @param url			Url to the WorkflowService
	 * @param scuflDoc		The workflow definition file (t2flow) created using Taverna 2.1.x
	 * @param workflowName	The name for the workflow execution.
	 * @param terminationTime User can give a termination time for the resource created for a workflow execution.
	 * @return EPR			The epr representing the resource created on the context service that hanldes the workflow execution.
	 * @throws MalformedURIException, RemoteException
	 */

	public static EndpointReferenceType setupWorkflow(String url, String scuflDoc, 
			String workflowName, Calendar terminationTime) throws MalformedURIException, RemoteException, Exception {
		
		TavernaWorkflowServiceClient client = new TavernaWorkflowServiceClient(url);
		WMSInputType input = createInput(scuflDoc, workflowName, null, terminationTime);
		WMSOutputType wMSOutputElement = client.createWorkflow(input);
		client.setWorkflowEPR(wMSOutputElement.getWorkflowEPR());
		return wMSOutputElement.getWorkflowEPR();

	}

	/**
	 * This method is to be used to set the delegated credential on the WorkflowService.
	 * Call this method before starting the workflow, and it will automatically use the delegated
	 * credentials when executing the workflow.
	 * 
	 * @param epr			Epr pointing the the WorkflowService resource ( on context service) that will handle a workflow execution.
	 * @param ref			reference to the delegated credential received from CDS.
	 * @return
	 * @throws CannotSetCredential, MalformedURIException
	 */

	public static void setDelegatedCredential(EndpointReferenceType epr, DelegatedCredentialReference ref, GlobusCredential credential) throws MalformedURIException, RemoteException, CannotSetCredential
	{
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(epr, credential);
		//This will force the client to send its credentials (to verify if it is the same credential
		// that has been delegated.
		//serviceClient.setAnonymousPrefered(false);
		serviceClient.setDelegatedCredential(ref);
	}

	public static void setDelegatedCredential(EndpointReferenceType epr, DelegatedCredentialReference ref) throws MalformedURIException, RemoteException, CannotSetCredential
	{
		setDelegatedCredential(epr, ref, null);
	}

	
	public static WorkflowStatusType startWorkflow(WorkflowPortType[] inputParams, EndpointReferenceType epr) throws MalformedURIException, RemoteException, Exception
	{		
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(epr);
		StartInputType startInputElement = null;
		if(!(inputParams == null))
		{
			if(inputParams.length > 0)
			{
				startInputElement = new StartInputType();
				startInputElement.setInputParams(inputParams);
			}			
		}
		WorkflowStatusType workflowStatusElement =  serviceClient.startWorkflow(startInputElement);
		return workflowStatusElement;

	}

	@Deprecated
	public static WorkflowStatusType startWorkflow(String[] inputString, EndpointReferenceType epr) throws MalformedURIException, RemoteException, Exception
	{
		
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(epr);
		StartInputType startInputElement = null;
		if(!(inputString == null))
		{
			if(inputString.length > 0)
			{
				startInputElement = new StartInputType();
				startInputElement.setInputArgs(inputString);
			}			
		}
		//startInputElement.setInputArgs(inputString);
		WorkflowStatusType workflowStatusElement =  serviceClient.start(startInputElement);

		return workflowStatusElement;

	}
	
	/**
	 * Returns the status of the worklow submited
	 * 
	 * @param epr					Epr pointing the the WorkflowService resource ( on context service) that will handle a workflow execution.
	 * @return WorkflowStatusType	Status of the workflow.
	 * @throws MalformedURIException, RemoteException
	 */

	public static WorkflowStatusType getStatus(EndpointReferenceType epr)
	throws MalformedURIException, RemoteException {
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(epr);
		WorkflowStatusType workflowStatusElement = serviceClient.getStatus();
		return workflowStatusElement;

	}

	/**
	 * Get the Output of the workflow execution. This method only returns the outputs on the output ports 
	 * of the workflow. This doesn't return any files. Use {@link #getOutputDataHelper(EndpointReferenceType)}getOutputDataHelper 
	 * to get files using caTransfer.
	 * 
	 * 
	 * @param epr					Epr pointing the the WorkflowService resource ( on context service) that will handle a workflow execution.
	 * @return WorkflowOutputType	The output type that holds outputs from the output ports (Currents only Strings).
	 * @throws MalformedURIException, RemoteException
	 */

	public static WorkflowOutputType getOutput(EndpointReferenceType epr)
	throws MalformedURIException, RemoteException {
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(epr);
		WorkflowOutputType workflowOutputElement = serviceClient.getWorkflowOutput();
		return workflowOutputElement;
	}

	/**
	 * This method allows a client to subscribe to the WORKFLOWSTATUSELEMENT resource property of an 
	 * executing workflow 
	 * 
	 * @param epr					Epr pointing the the WorkflowService resource ( on context service) that will handle a workflow execution.
	 * @param TimeInSeconds			Time interval after which the subscription expires. 
	 * @return
	 * @throws MalformedURIException, RemoteException, ContainerException, InterruptedException
	 */
	public static void subscribeRP(EndpointReferenceType epr, int TimeInSeconds) throws MalformedURIException, 
	RemoteException, ContainerException, InterruptedException
	{
		CountDownLatch doneSignal = new CountDownLatch(1);
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(epr, doneSignal, null);
		serviceClient.subscribe(TavernaWorkflowServiceImplConstantsBase.WORKFLOWSTATUSELEMENT);
		doneSignal.await(TimeInSeconds, TimeUnit.SECONDS);
	}

	//Method that returns an EPR from the Credential Delegation Service. It automatically picks the Clients
	// credentials from /tmp/x509... file and if CDS is able to authenticate with it, it then retuns an epr representing the CD

	public static DelegatedCredentialReference delegateCredential(String cdsURL, String delegateTo, String currentProxyFile) throws Exception {

		// The default credential of the user that is currently logged in.
		GlobusCredential credential = getLocalCredential(currentProxyFile);

		// Specifies how long the delegation service can delegate this credential to other parties.

		ProxyLifetime delegationLifetime = new ProxyLifetime();
		delegationLifetime.setHours(4);
		delegationLifetime.setMinutes(0);
		delegationLifetime.setSeconds(0);

		// Specifies the path length of the credential being delegate the minumum is 1.
		int delegationPathLength = 1;

		// Specifies the how long credentials issued to allowed parties will be valid for.
		ProxyLifetime issuedCredentialLifetime = new ProxyLifetime();
		issuedCredentialLifetime.setHours(4);
		issuedCredentialLifetime.setMinutes(0);
		issuedCredentialLifetime.setSeconds(0);

		// Specifies the path length of the credentials issued to allowed
		// parties. A path length of 0 means that the requesting party cannot further delegate the credential.
		int issuedCredentialPathLength = 0;

		// Specifies the key length of the delegated credential
		int keySize = ClientConstants.DEFAULT_KEY_SIZE;

		// The policy stating which parties will be allowed to obtain a
		// delegated credential. The CDS will only sissue credentials to parties listed in this policy.
		List<String> parties = new ArrayList<String>();
		//parties.add("/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=jdoe");
		parties.add(delegateTo);

		IdentityDelegationPolicy policy = org.cagrid.gaards.cds.common.Utils.createIdentityDelegationPolicy(parties);

		// Create an instance of the delegation client, specifies the CDS
		// Service URL and the credential to be delegated.
		DelegationUserClient client = new DelegationUserClient(cdsURL, credential);

		// Delegates the credential and returns a reference which can later
		// be used by allowed parties to obtain a credential.
		DelegatedCredentialReference ref = client.delegateCredential(delegationLifetime, delegationPathLength, policy, issuedCredentialLifetime, issuedCredentialPathLength, keySize);
		return ref;
	} 

	public static GlobusCredential getLocalCredential(String currentProxyFile) throws Exception 
	{
		GlobusCredential credential = null;
		if(currentProxyFile!=null)
		{
			credential = ProxyUtil.loadProxy(currentProxyFile);			
		}
		else
		{
			credential = ProxyUtil.getDefaultProxy();
		}
		if(credential == null)
		{
			throw new Exception("Unable to get the local credential. \nPlease creat a valid proxy in the default location \nor give a path to the proxy file.");
		}
		return credential;
	}

	public static TransferServiceContextReference putInputDataHelper(EndpointReferenceType epr, String location, GlobusCredential credential) throws Exception {

		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(epr);
		TransferServiceContextReference ref = serviceClient.putInputData(new File (location).getName());

		TransferServiceContextClient tclient1 = new TransferServiceContextClient(ref.getEndpointReference());

		//Client can't set the dataDescriptor :(
		//DataDescriptor dd = new DataDescriptor(null, "My Data");

		System.out.println("DD " + tclient1.getDataTransferDescriptor().getDataDescriptor().getName());

		BufferedInputStream isFile = null;
		File file = new File(location);
		long size = file.length();
		isFile = new BufferedInputStream(new FileInputStream(file));
		TransferClientHelper.putData(isFile, size, tclient1.getDataTransferDescriptor(), credential);

		// tell the resource that the data has been uploaded.
		tclient1.setStatus(Status.Staged);
		return ref;
	}
	
	public static TransferServiceContextReference putInputDataHelper(EndpointReferenceType epr, String location) throws Exception {
		return putInputDataHelper(epr, location, null);
	}
	
	public static File getOutputDataHelper(EndpointReferenceType epr, GlobusCredential credential, String saveDir) throws MalformedURIException, RemoteException, IOException, Exception {
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(epr);
		TransferServiceContextReference ref = serviceClient.getOutputData();
		TransferServiceContextClient tclient = new TransferServiceContextClient(ref.getEndpointReference());

		//File name of the output file set by the Service using DataDescriptor
		String fileName = tclient.getDataTransferDescriptor().getDataDescriptor().getName();
		File outputFile = new File(saveDir + File.separator + fileName);

		//Get the data from the caTransfer service context using the Helper class.
		InputStream stream = TransferClientHelper.getData(tclient.getDataTransferDescriptor(), credential);

		FileOutputStream fileoutputstream = new FileOutputStream(outputFile);
		int n;
		byte buf[]=new byte[1024];
		while ((n = stream.read(buf, 0, 1024)) > -1)
			fileoutputstream.write(buf, 0, n);

		fileoutputstream.close(); 
		return outputFile;
	}
	
	public static File getOutputDataHelper(EndpointReferenceType epr, String saveDir) throws MalformedURIException, RemoteException, IOException, Exception {
		return getOutputDataHelper(epr, null, saveDir);
	}
	public static void writeEprToFile(EndpointReferenceType epr,
			String workflowName) throws Exception {
		FileWriter writer = null;
		String curDir = System.getProperty("user.dir");
		System.out.println(curDir + "/" + workflowName + ".epr");

		try {
			writer = new FileWriter(curDir + "/" + workflowName + ".epr");
			QName qName = new QName("", "TWS_EPR");

			writer.write(ObjectSerializer.toString(epr, qName));

		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public static EndpointReferenceType readEprFromFile(String fileName)
	throws Exception {
		FileInputStream in = null;
		EndpointReferenceType ref = new EndpointReferenceType();
		try {
			in = new FileInputStream(fileName);
			ref = (EndpointReferenceType) ObjectDeserializer.deserialize(
					new InputSource(in), EndpointReferenceType.class);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return ref;
	}

	public static WMSInputType createInput(String scuflFile, String name, 
			EndpointReferenceType cdsEpr, Calendar terminationTime) throws Exception {
		
		WMSInputType input = new WMSInputType();
		String scuflProcess = Utils.fileToStringBuffer(new File(scuflFile)).toString();
		input.setScuflDoc(scuflProcess);
		input.setWorkflowName(name);
		if(cdsEpr != null){
			input.setDelegationEPR(cdsEpr);			
		}
		if(terminationTime !=null){
			input.setTerminationTime(terminationTime);
		}
		return input;
	}

  public workflowmanagementfactoryservice.WMSOutputType createWorkflow(workflowmanagementfactoryservice.WMSInputType wMSInputElement) throws RemoteException, gov.nih.nci.cagrid.workflow.factory.stubs.types.WorkflowException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createWorkflow");
    gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowRequest params = new gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowRequest();
    gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowRequestWMSInputElement wMSInputElementContainer = new gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowRequestWMSInputElement();
    wMSInputElementContainer.setWMSInputElement(wMSInputElement);
    params.setWMSInputElement(wMSInputElementContainer);
    gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowResponse boxedResult = portType.createWorkflow(params);
    return boxedResult.getWMSOutputElement();
    }
  }

  public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getMultipleResourceProperties");
    return portType.getMultipleResourceProperties(params);
    }
  }

  public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getResourceProperty");
    return portType.getResourceProperty(params);
    }
  }

  public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"queryResourceProperties");
    return portType.queryResourceProperties(params);
    }
  }

}
