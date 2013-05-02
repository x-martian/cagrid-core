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
package org.cagrid.websso.client.acegi;

import org.acegisecurity.GrantedAuthority;

public class DefaultGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	private String authority = null;

	public DefaultGrantedAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}
}
