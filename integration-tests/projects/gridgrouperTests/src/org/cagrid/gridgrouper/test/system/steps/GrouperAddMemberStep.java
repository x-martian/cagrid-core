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
package org.cagrid.gridgrouper.test.system.steps;

import gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouperClient;
import gov.nih.nci.cagrid.testing.system.haste.Step;

public class GrouperAddMemberStep extends Step {
	private String endpoint;
	private String group;
	private String subject;
	private boolean shouldFail = false;

	public GrouperAddMemberStep(String group, String subject, String endpoint) {
		this(group, subject, false, endpoint);
	}

	public GrouperAddMemberStep(String group, String subject, boolean shouldFail, String endpoint) {
		super();

		this.endpoint = endpoint;
		this.group = group;
		this.subject = subject;
		this.shouldFail = shouldFail;
	}

	@Override
	public void runStep() throws Exception {
		GridGrouperClient grouper = new GridGrouperClient(this.endpoint);
		grouper.setAnonymousPrefered(false);
		// add member
		try {
			grouper.addMember(new GroupIdentifier(null, this.group), this.subject);
			if (this.shouldFail) {
				fail("addMember should fail");
			}
		} catch (Exception e) {
			if (!this.shouldFail) {
				throw e;
			}
		}
	}

}
