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
package gov.nih.nci.cagrid.introduce.test;


import gov.nih.nci.cagrid.introduce.IntroduceConstants;

import java.io.File;

public class PersistentTestCaseInfo extends TestCaseInfo {

	public String name = "IntroduceTestPersistentService";

	public String dir = "IntroduceTest";

	public String packageName = "org.test.persistent";

	public String namespaceDomain = "http://persistent.test.org/IntroduceTestPersistentService";

	public PersistentTestCaseInfo() {

	}

	public PersistentTestCaseInfo(String name, String dir, String packageName,
			String namespaceDomain) {
		this.name = name;
		this.dir = dir;
		this.packageName = packageName;
		this.namespaceDomain = namespaceDomain;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.introduce.TestCaseInfoI#getDir()
	 */
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.introduce.TestCaseInfoI#getName()
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.introduce.TestCaseInfoI#getNamespaceDomain()
	 */
	public String getNamespace() {
		return namespaceDomain;
	}

	public void setNamespaceDomain(String namespaceDomain) {
		this.namespaceDomain = namespaceDomain;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.introduce.TestCaseInfoI#getPackageName()
	 */
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageDir() {
		return getPackageName().replace('.',File.separatorChar);
	}

	public String getResourceFrameworkType() {
		return IntroduceConstants.INTRODUCE_MAIN_RESOURCE + "," +IntroduceConstants.INTRODUCE_SINGLETON_RESOURCE + "," + IntroduceConstants.INTRODUCE_PERSISTENT_RESOURCE + "," + IntroduceConstants.INTRODUCE_IDENTIFIABLE_RESOURCE;
	}

}
