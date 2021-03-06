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
package org.cagrid.gaards.cds.delegated.service.globus;

import java.rmi.RemoteException;

import org.cagrid.gaards.cds.delegated.service.DelegatedCredentialImpl;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the CredentialDelegationServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public class DelegatedCredentialProviderImpl{
	
	DelegatedCredentialImpl impl;
	
	public DelegatedCredentialProviderImpl() throws RemoteException {
		impl = new DelegatedCredentialImpl();
	}
	

    public org.cagrid.gaards.cds.delegated.stubs.GetDelegatedCredentialResponse getDelegatedCredential(org.cagrid.gaards.cds.delegated.stubs.GetDelegatedCredentialRequest params) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.DelegationFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    org.cagrid.gaards.cds.delegated.stubs.GetDelegatedCredentialResponse boxedResult = new org.cagrid.gaards.cds.delegated.stubs.GetDelegatedCredentialResponse();
    boxedResult.setCertificateChain(impl.getDelegatedCredential(params.getPublicKey().getPublicKey()));
    return boxedResult;
  }

}
