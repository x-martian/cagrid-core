package gov.nih.nci.cagrid.gridgrouper.grouper;

import gov.nih.nci.cagrid.gridgrouper.bean.MembershipRequestStatus;

import java.util.Set;

public interface MembershipRequestI {

	public String getRequestorId();
	
	public MemberI getReviewer();
	
	public MembershipRequestStatus getStatus();

	public Set<?> getHistory();

}
