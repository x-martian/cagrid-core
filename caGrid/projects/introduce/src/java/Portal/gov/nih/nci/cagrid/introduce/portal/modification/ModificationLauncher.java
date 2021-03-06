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
package gov.nih.nci.cagrid.introduce.portal.modification;

import gov.nih.nci.cagrid.introduce.common.ResourceManager;

import java.io.File;

import org.apache.log4j.Logger;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.model.RenderOptions;


public class ModificationLauncher {
    
    private static final Logger logger = Logger.getLogger(ModificationLauncher.class);

    public File methodsDirectory = null;


    public ModificationLauncher() {
        try {
            chooseService();
        } catch (Exception e) {
            logger.error(e);
        }
        if (this.methodsDirectory != null) {
            try {
                ModificationViewer viewer = new ModificationViewer(this.methodsDirectory);
                RenderOptions ro = new RenderOptions();
                GridApplication.getContext().addApplicationComponent(viewer, null, ro);
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }


    private void chooseService() throws Exception {
        String dir = ResourceManager.promptDir(null);
        if (dir != null) {
            this.methodsDirectory = new File(dir);
        }
    }

}
