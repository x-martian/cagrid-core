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
package gov.nih.nci.cagrid.introduce.test.steps;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.introduce.test.TestCaseInfo;
import gov.nih.nci.cagrid.introduce.test.util.SourceUtils;

import java.io.File;


public class RemoveSimpleMethodImplStep extends BaseStep {
	private TestCaseInfo tci;
	private String methodName;


	public RemoveSimpleMethodImplStep(TestCaseInfo tci, String methodName, boolean build) throws Exception {
		super(tci.getDir(),build);
		this.tci = tci;
		this.methodName = methodName;
	}


	public void runStep() throws Throwable {
		System.out.println("Adding a simple methods implementation.");

		File inFileClient = new File(Utils.decodeUrl(this.getClass().getResource("/gold/simple/" + "SimpleMethodClientRemoval.java")));
		File outFileClient = new File(tci.getDir() + File.separator + "src" + File.separator + tci.getPackageDir() + File.separator + "client" + File.separator  + tci.getName() + "Client.java");
		
		SourceUtils.modifyImpl(inFileClient, outFileClient, "main");
			
		buildStep();
	}

}
