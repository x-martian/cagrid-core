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
package org.cagrid.gaards.ui.dorian;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.dorian.client.IFSUserClient;

import java.util.List;

import org.cagrid.gaards.dorian.client.GridAdministrationClient;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.client.LocalAdministrationClient;
import org.cagrid.gaards.dorian.client.LocalUserClient;
import org.cagrid.gaards.dorian.policy.AccountInformationModificationPolicy;
import org.cagrid.gaards.dorian.policy.DorianPolicy;
import org.cagrid.gaards.ui.common.ServiceHandle;
import org.cagrid.grape.configuration.ServiceDescriptor;
import org.globus.gsi.GlobusCredential;
import org.globus.wsrf.impl.security.authorization.IdentityAuthorization;


public class DorianHandle extends ServiceHandle {

    private List<AuthenticationServiceHandle> authenticationServices;
    private DorianPolicy policy;


    public DorianHandle(ServiceDescriptor des) throws Exception {
        super(des);
        GridUserClient client = new GridUserClient(des.getServiceURL());
        this.policy = client.getPolicy();
    }


    public DorianPolicy getPolicy() {
        return policy;
    }


    public GridAdministrationClient getAdminClient(GlobusCredential credential) throws Exception {
        GridAdministrationClient client = new GridAdministrationClient(getServiceDescriptor().getServiceURL(),
            credential);
        if (Utils.clean(getServiceDescriptor().getServiceIdentity()) != null) {
            IdentityAuthorization auth = new IdentityAuthorization(getServiceDescriptor().getServiceIdentity());
            client.setAuthorization(auth);
        }
        client.setPolicy(policy);
        return client;
    }


    public GridUserClient getUserClient(GlobusCredential credential) throws Exception {
        GridUserClient client = null;
        if (credential == null) {
            client = new GridUserClient(getServiceDescriptor().getServiceURL(), credential, true);
        } else {
            client = new GridUserClient(getServiceDescriptor().getServiceURL(), credential, false);
        }
        if (Utils.clean(getServiceDescriptor().getServiceIdentity()) != null) {
            IdentityAuthorization auth = new IdentityAuthorization(getServiceDescriptor().getServiceIdentity());
            client.setAuthorization(auth);
        }
        client.setPolicy(policy);
        return client;
    }


    public GridUserClient getUserClient() throws Exception {
        GridUserClient client = new GridUserClient(getServiceDescriptor().getServiceURL());
        if (Utils.clean(getServiceDescriptor().getServiceIdentity()) != null) {
            IdentityAuthorization auth = new IdentityAuthorization(getServiceDescriptor().getServiceIdentity());
            client.setAuthorization(auth);
        }
        client.setPolicy(policy);
        return client;
    }


    public LocalAdministrationClient getLocalAdminClient(GlobusCredential credential) throws Exception {
        LocalAdministrationClient client = new LocalAdministrationClient(getServiceDescriptor().getServiceURL(),
            credential);
        if (Utils.clean(getServiceDescriptor().getServiceIdentity()) != null) {
            IdentityAuthorization auth = new IdentityAuthorization(getServiceDescriptor().getServiceIdentity());
            client.setAuthorization(auth);
        }
        client.setPolicy(policy);
        return client;
    }


    public IFSUserClient getOldUserClient() throws Exception {
        IFSUserClient client = new IFSUserClient(getServiceDescriptor().getServiceURL());
        if (Utils.clean(getServiceDescriptor().getServiceIdentity()) != null) {
            IdentityAuthorization auth = new IdentityAuthorization(getServiceDescriptor().getServiceIdentity());
            client.setAuthorization(auth);
        }

        return client;
    }


    public LocalUserClient getLocalUserClient() throws Exception {
        LocalUserClient client = new LocalUserClient(getServiceDescriptor().getServiceURL());
        if (Utils.clean(getServiceDescriptor().getServiceIdentity()) != null) {
            IdentityAuthorization auth = new IdentityAuthorization(getServiceDescriptor().getServiceIdentity());
            client.setAuthorization(auth);
        }
        client.setPolicy(policy);
        return client;
    }


    public String getServiceVersion() throws Exception {
        return getLocalUserClient().getServiceVersion();
    }


    public void setAuthenticationServices(List<AuthenticationServiceHandle> authenticationServices) {
        this.authenticationServices = authenticationServices;
    }


    public List<AuthenticationServiceHandle> getAuthenticationServices() {
        return authenticationServices;
    }


    public boolean localAccountModification() {
        if (policy != null) {
            if (policy.getIdentityProviderPolicy() != null) {
                if ((policy.getIdentityProviderPolicy().getAccountInformationModificationPolicy() != null)
                    && (policy.getIdentityProviderPolicy().getAccountInformationModificationPolicy()
                        .equals(AccountInformationModificationPolicy.User))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
