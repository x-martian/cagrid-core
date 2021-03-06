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
package org.cagrid.fqp.test.remote;

import java.util.Vector;

import org.cagrid.fqp.test.remote.steps.NotificationClientSetupStep;

import gov.nih.nci.cagrid.testing.system.haste.Step;
import gov.nih.nci.cagrid.testing.system.haste.Story;


/**
 * NotificationClientSetupStory
 * Sets up the client side of things to support ws-notification listeners
 * 
 * @author David
 */
public class NotificationClientSetupStory extends Story {

    public String getName() {
        return "Notification Client Setup Story";
    }


    public String getDescription() {
        return "Sets up the client side of things to support notification listeners";
    }


    protected Vector<?> steps() {
        Vector<Step> steps = new Vector<Step>();
        steps.add(new NotificationClientSetupStep());
        return steps;
    }
}
