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
package gov.nih.nci.cagrid.gridgrouper.service.globus;


import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.MessageContext;

import gov.nih.nci.cagrid.introduce.servicetools.security.AuthorizationExtension;
import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;
import org.globus.wsrf.impl.security.authorization.exceptions.CloseException;
import org.globus.wsrf.impl.security.authorization.exceptions.InitializeException;
import org.globus.wsrf.impl.security.authorization.exceptions.InvalidPolicyException;
import org.globus.wsrf.security.authorization.PDP;
import org.globus.wsrf.security.authorization.PDPConfig;
import org.globus.wsrf.config.ContainerConfig;
import org.w3c.dom.Node;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This is a PDP for use with the globus authorization callout.
 * This class will have a authorize method for each method on this grid service.
 * The method is responsible for making any authorization callouts required to satisfy the 
 * authorization requirements placed on each method call.  Each method will either return
 * upon a successful authorization or will throw an exception upon a failed authorization.
 * 
 * @created by Introduce Toolkit version 1.4
 * 
 */
public class GridGrouperAuthorization implements PDP {

	public static final String SERVICE_NAMESPACE = "http://cagrid.nci.nih.gov/GridGrouper";
	
	Map authorizationClassMap = new HashMap();
	
	
	public GridGrouperAuthorization() {
	}
	
	protected String getServiceNamespace(){
		return SERVICE_NAMESPACE;
	}
	
	public static String getCallerIdentity() {
		String caller = org.globus.wsrf.security.SecurityManager.getManager().getCaller();
		if ((caller == null) || (caller.equals("<anonymous>"))) {
			return null;
		} else {
			return caller;
		}
	}
					
	public void authorizeGetStem(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetChildStems(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetParentStem(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeUpdateStem(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetSubjectsWithStemPrivilege(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetStemPrivileges(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeHasStemPrivilege(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGrantStemPrivilege(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeRevokeStemPrivilege(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeAddChildStem(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeDeleteStem(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetGroup(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetChildGroups(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeAddChildGroup(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeDeleteGroup(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeUpdateGroup(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeAddMember(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetMembers(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeIsMemberOf(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetMemberships(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeDeleteMember(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeAddCompositeMember(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeDeleteCompositeMember(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGrantGroupPrivilege(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeRevokeGroupPrivilege(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetSubjectsWithGroupPrivilege(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetGroupPrivileges(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeHasGroupPrivilege(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeIsMember(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetMember(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetMembersGroups(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetServiceSecurityMetadata(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetMultipleResourceProperties(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetResourceProperty(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeQueryResourceProperties(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeAddMembershipRequest(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeUpdateMembershipRequest(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetMembershipRequests(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeEnableMembershipRequests(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeDisableMembershipRequests(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeIsMembershipRequestEnabled(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   
	
	public boolean isPermitted(Subject peerSubject, MessageContext context, QName operation)
		throws AuthorizationException {
		
		if(!operation.getNamespaceURI().equals(getServiceNamespace())){
		  return false;
		}
		if(operation.getLocalPart().equals("getStem")){
			authorizeGetStem(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getChildStems")){
			authorizeGetChildStems(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getParentStem")){
			authorizeGetParentStem(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("updateStem")){
			authorizeUpdateStem(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getSubjectsWithStemPrivilege")){
			authorizeGetSubjectsWithStemPrivilege(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getStemPrivileges")){
			authorizeGetStemPrivileges(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("hasStemPrivilege")){
			authorizeHasStemPrivilege(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("grantStemPrivilege")){
			authorizeGrantStemPrivilege(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("revokeStemPrivilege")){
			authorizeRevokeStemPrivilege(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("addChildStem")){
			authorizeAddChildStem(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("deleteStem")){
			authorizeDeleteStem(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getGroup")){
			authorizeGetGroup(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getChildGroups")){
			authorizeGetChildGroups(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("addChildGroup")){
			authorizeAddChildGroup(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("deleteGroup")){
			authorizeDeleteGroup(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("updateGroup")){
			authorizeUpdateGroup(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("addMember")){
			authorizeAddMember(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getMembers")){
			authorizeGetMembers(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("isMemberOf")){
			authorizeIsMemberOf(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getMemberships")){
			authorizeGetMemberships(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("deleteMember")){
			authorizeDeleteMember(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("addCompositeMember")){
			authorizeAddCompositeMember(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("deleteCompositeMember")){
			authorizeDeleteCompositeMember(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("grantGroupPrivilege")){
			authorizeGrantGroupPrivilege(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("revokeGroupPrivilege")){
			authorizeRevokeGroupPrivilege(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getSubjectsWithGroupPrivilege")){
			authorizeGetSubjectsWithGroupPrivilege(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getGroupPrivileges")){
			authorizeGetGroupPrivileges(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("hasGroupPrivilege")){
			authorizeHasGroupPrivilege(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("isMember")){
			authorizeIsMember(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getMember")){
			authorizeGetMember(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getMembersGroups")){
			authorizeGetMembersGroups(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getServiceSecurityMetadata")){
			authorizeGetServiceSecurityMetadata(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getMultipleResourceProperties")){
			authorizeGetMultipleResourceProperties(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getResourceProperty")){
			authorizeGetResourceProperty(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("queryResourceProperties")){
			authorizeQueryResourceProperties(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("addMembershipRequest")){
			authorizeAddMembershipRequest(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("updateMembershipRequest")){
			authorizeUpdateMembershipRequest(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getMembershipRequests")){
			authorizeGetMembershipRequests(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("enableMembershipRequests")){
			authorizeEnableMembershipRequests(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("disableMembershipRequests")){
			authorizeDisableMembershipRequests(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("isMembershipRequestEnabled")){
			authorizeIsMembershipRequestEnabled(peerSubject, context, operation);
			return true;
		} 		
		return false;
	}
	

	public Node getPolicy(Node query) throws InvalidPolicyException {
		return null;
	}


	public String[] getPolicyNames() {
		return null;
	}


	public Node setPolicy(Node policy) throws InvalidPolicyException {
		return null;
	}


	public void close() throws CloseException {


	}


	public void initialize(PDPConfig config, String name, String id) throws InitializeException {
    	try{
    		String serviceName = (String)config.getProperty(name, "serviceName");
    	    String etcPath = ContainerConfig.getBaseDirectory() + File.separator + (String)config.getProperty(name, "etcDirectoryPath");

    	
    	} catch (Exception e){
        	throw new InitializeException(e.getMessage(),e);
		}
	}
	
	
}
