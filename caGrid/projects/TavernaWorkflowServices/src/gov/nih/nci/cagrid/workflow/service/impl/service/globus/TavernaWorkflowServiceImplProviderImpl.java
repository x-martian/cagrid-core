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
package gov.nih.nci.cagrid.workflow.service.impl.service.globus;

import gov.nih.nci.cagrid.workflow.service.impl.service.TavernaWorkflowServiceImplImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the TavernaWorkflowServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class TavernaWorkflowServiceImplProviderImpl{
	
	TavernaWorkflowServiceImplImpl impl;
	
	public TavernaWorkflowServiceImplProviderImpl() throws RemoteException {
		impl = new TavernaWorkflowServiceImplImpl();
	}
	

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.CancelResponse cancel(gov.nih.nci.cagrid.workflow.service.impl.stubs.CancelRequest params) throws RemoteException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.CannotCancelWorkflowFault {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.CancelResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.CancelResponse();
    impl.cancel();
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.GetDetailedStatusResponse getDetailedStatus(gov.nih.nci.cagrid.workflow.service.impl.stubs.GetDetailedStatusRequest params) throws RemoteException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.WorkflowException {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.GetDetailedStatusResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.GetDetailedStatusResponse();
    boxedResult.setDetailedStatusOutputElement(impl.getDetailedStatus());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.GetStatusResponse getStatus(gov.nih.nci.cagrid.workflow.service.impl.stubs.GetStatusRequest params) throws RemoteException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.WorkflowException {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.GetStatusResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.GetStatusResponse();
    boxedResult.setWorkflowStatusElement(impl.getStatus());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.GetWorkflowOutputResponse getWorkflowOutput(gov.nih.nci.cagrid.workflow.service.impl.stubs.GetWorkflowOutputRequest params) throws RemoteException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.WorkflowException {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.GetWorkflowOutputResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.GetWorkflowOutputResponse();
    boxedResult.setWorkflowOutputElement(impl.getWorkflowOutput());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.PauseResponse pause(gov.nih.nci.cagrid.workflow.service.impl.stubs.PauseRequest params) throws RemoteException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.WorkflowException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.CannotPauseWorkflowFault {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.PauseResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.PauseResponse();
    boxedResult.setWorkflowStatusElement(impl.pause());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.ResumeResponse resume(gov.nih.nci.cagrid.workflow.service.impl.stubs.ResumeRequest params) throws RemoteException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.WorkflowException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.CannotResumeWorkflowFault {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.ResumeResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.ResumeResponse();
    boxedResult.setWorkflowStatusElement(impl.resume());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.StartResponse start(gov.nih.nci.cagrid.workflow.service.impl.stubs.StartRequest params) throws RemoteException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.CannotStartWorkflowFault {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.StartResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.StartResponse();
    boxedResult.setWorkflowStatusElement(impl.start(params.getStartInputElement().getStartInputElement()));
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.SetDelegatedCredentialResponse setDelegatedCredential(gov.nih.nci.cagrid.workflow.service.impl.stubs.SetDelegatedCredentialRequest params) throws RemoteException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.CannotSetCredential {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.SetDelegatedCredentialResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.SetDelegatedCredentialResponse();
    impl.setDelegatedCredential(params.getDelegatedCredentialReference().getDelegatedCredentialReference());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.PutInputDataResponse putInputData(gov.nih.nci.cagrid.workflow.service.impl.stubs.PutInputDataRequest params) throws RemoteException {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.PutInputDataResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.PutInputDataResponse();
    boxedResult.setTransferServiceContextReference(impl.putInputData(params.getFilename()));
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.GetOutputDataResponse getOutputData(gov.nih.nci.cagrid.workflow.service.impl.stubs.GetOutputDataRequest params) throws RemoteException {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.GetOutputDataResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.GetOutputDataResponse();
    boxedResult.setTransferServiceContextReference(impl.getOutputData());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.workflow.service.impl.stubs.StartWorkflowResponse startWorkflow(gov.nih.nci.cagrid.workflow.service.impl.stubs.StartWorkflowRequest params) throws RemoteException, gov.nih.nci.cagrid.workflow.service.impl.stubs.types.CannotStartWorkflowFault {
    gov.nih.nci.cagrid.workflow.service.impl.stubs.StartWorkflowResponse boxedResult = new gov.nih.nci.cagrid.workflow.service.impl.stubs.StartWorkflowResponse();
    boxedResult.setWorkflowStatusElement(impl.startWorkflow(params.getStartInputElement().getStartInputElement()));
    return boxedResult;
  }

}
