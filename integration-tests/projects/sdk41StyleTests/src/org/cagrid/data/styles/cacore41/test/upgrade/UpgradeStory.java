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
package org.cagrid.data.styles.cacore41.test.upgrade;

import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainer;
import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainerFactory;
import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainerType;
import gov.nih.nci.cagrid.testing.system.deployment.steps.DeployServiceStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.DestroyContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.StartContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.StopContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.UnpackContainerStep;
import gov.nih.nci.cagrid.testing.system.haste.Step;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.cagrid.data.styles.cacore41.test.steps.InvokeSDK41CQL2DataServiceStep;
import org.cagrid.data.styles.cacore41.test.steps.InvokeSDK41DataServiceStep;
import org.cagrid.data.test.creation.DataTestCaseInfo;
import org.cagrid.data.test.creation.DeleteOldServiceStep;
import org.cagrid.data.test.system.BaseSystemTest;
import org.cagrid.data.test.system.ResyncAndBuildStep;
import org.cagrid.data.test.upgrades.UnpackOldServiceStep;
import org.cagrid.data.test.upgrades.UpgradeIntroduceServiceStep;


public abstract class UpgradeStory extends BaseSystemTest {

    private DataTestCaseInfo testServiceInfo = null;
    private ServiceContainer container = null;


    public abstract DataTestCaseInfo getTestCaseInfo();


    public abstract String getServiceZipName();


    public boolean storySetUp() {
        boolean setupOk = true;
        testServiceInfo = getTestCaseInfo();
        try {
            container = ServiceContainerFactory.createContainer(ServiceContainerType.TOMCAT_CONTAINER);
        } catch (IOException ex) {
            setupOk = false;
            ex.printStackTrace();
            fail("Error setting up service container: " + ex.getMessage());
        }
        return setupOk;
    }


    protected Vector<?> steps() {
        Vector<Step> steps = new Vector<Step>();
        // steps to unpack and upgrade the old service
        steps.add(new DeleteOldServiceStep(testServiceInfo));
        steps.add(new UnpackOldServiceStep(getServiceZipName()));
        steps.add(new UpgradeIntroduceServiceStep(testServiceInfo.getDir()));
        steps.add(new ResyncAndBuildStep(testServiceInfo, getIntroduceBaseDir()));
        // deploy the service, check out the CQL 2 related operations and
        // metadata
        steps.add(new UnpackContainerStep(container));
        List<String> args = Arrays.asList(new String[]{"-Dno.deployment.validation=true",
                "-Dperform.index.service.registration=false"});
        steps.add(new DeployServiceStep(container, testServiceInfo.getDir(), args));
        steps.add(new StartContainerStep(container));
        steps.add(new InvokeSDK41DataServiceStep(container, testServiceInfo));
        steps.add(new InvokeSDK41CQL2DataServiceStep(container, testServiceInfo));
        return steps;
    }
    
    
    public void storyTearDown() {
        System.out.println(container.getProperties().getContainerDirectory().getAbsolutePath());
        try {
            if (container.isStarted()) {
                new StopContainerStep(container).runStep();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            if (container.isUnpacked()) {
               new DestroyContainerStep(container).runStep();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            new DeleteOldServiceStep(testServiceInfo).runStep();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
