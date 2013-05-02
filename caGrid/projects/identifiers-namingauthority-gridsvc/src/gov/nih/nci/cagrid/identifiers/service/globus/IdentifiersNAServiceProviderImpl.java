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
package gov.nih.nci.cagrid.identifiers.service.globus;

import gov.nih.nci.cagrid.identifiers.service.IdentifiersNAServiceImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the IdentifiersNAServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class IdentifiersNAServiceProviderImpl{
	
	IdentifiersNAServiceImpl impl;
	
	public IdentifiersNAServiceProviderImpl() throws RemoteException {
		impl = new IdentifiersNAServiceImpl();
	}
	

    public gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierResponse createIdentifier(gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequest params) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierResponse boxedResult = new gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierResponse();
    boxedResult.setIdentifier(impl.createIdentifier(params.getIdentifierData().getIdentifierData()));
    return boxedResult;
  }

    public gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierResponse resolveIdentifier(gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierRequest params) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault {
    gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierResponse boxedResult = new gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierResponse();
    boxedResult.setIdentifierData(impl.resolveIdentifier(params.getIdentifier().getIdentifier()));
    return boxedResult;
  }

    public gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysResponse deleteKeys(gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysRequest params) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysResponse boxedResult = new gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysResponse();
    impl.deleteKeys(params.getIdentifier().getIdentifier(),params.getKeyNames().getKeyName());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.identifiers.stubs.CreateKeysResponse createKeys(gov.nih.nci.cagrid.identifiers.stubs.CreateKeysRequest params) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    gov.nih.nci.cagrid.identifiers.stubs.CreateKeysResponse boxedResult = new gov.nih.nci.cagrid.identifiers.stubs.CreateKeysResponse();
    impl.createKeys(params.getIdentifier().getIdentifier(),params.getIdentifierData().getIdentifierData());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesResponse replaceKeyValues(gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesRequest params) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesResponse boxedResult = new gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesResponse();
    impl.replaceKeyValues(params.getIdentifier().getIdentifier(),params.getIdentifierValues().getIdentifierValues());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.identifiers.stubs.GetKeyNamesResponse getKeyNames(gov.nih.nci.cagrid.identifiers.stubs.GetKeyNamesRequest params) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault {
    gov.nih.nci.cagrid.identifiers.stubs.GetKeyNamesResponse boxedResult = new gov.nih.nci.cagrid.identifiers.stubs.GetKeyNamesResponse();
    boxedResult.setKeyName(impl.getKeyNames(params.getIdentifier().getIdentifier()));
    return boxedResult;
  }

    public gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataResponse getKeyData(gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataRequest params) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataResponse boxedResult = new gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataResponse();
    boxedResult.setKeyNameData(impl.getKeyData(params.getIdentifier().getIdentifier(),params.getKeyName().getKeyName()));
    return boxedResult;
  }

}
