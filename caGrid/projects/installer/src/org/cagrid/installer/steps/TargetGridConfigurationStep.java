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
/**
 * 
 */
package org.cagrid.installer.steps;

import java.io.File;
import java.io.FileFilter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.cagrid.installer.steps.options.ListPropertyConfigurationOption;
import org.cagrid.installer.steps.options.ListPropertyConfigurationOption.LabelValuePair;
import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.WizardModel;
import org.w3c.dom.Document;


/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 */
public class TargetGridConfigurationStep extends PropertyConfigurationStep {

    boolean prepared = false;


    public TargetGridConfigurationStep(String name, String description) {
        super(name, description);
    }


    @Override
    public void applyState() throws InvalidStateException {
        super.applyState();
    }


    @Override
    public void init(WizardModel m) {
        super.init(m);
    }


    @Override
    public void prepare() {
        if (!prepared) {
            File caGridTargetGridDir = new File(this.model.getProperty(Constants.CAGRID_HOME) + File.separator
                + "repository" + File.separator + "caGrid" + File.separator + "target_grid");
            FileFilter filter = new FileFilter() {

                public boolean accept(File pathname) {
                    return pathname.getName().startsWith("ivy") && pathname.getName().endsWith(".xml");
                }
            };

            try {
                File[] files = caGridTargetGridDir.listFiles(filter);
                LabelValuePair[] targetGridPairs = new LabelValuePair[files.length + 1];
                for (int i = 0; i < files.length; i++) {
                    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

                    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                    Document doc = docBuilder.parse(files[i]);
                    doc.getDocumentElement().normalize();

                    XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
                    
                    XPath revXPath = factory.newXPath();
                    XPathExpression revisionExp = revXPath.compile("/ivy-module/info/@revision");
                    String targetGrid = revisionExp.evaluate(doc);

                    XPath descXPath = factory.newXPath();
                    XPathExpression descExp = descXPath.compile("/ivy-module/info/description/text()");
                    String targetDescription = descExp.evaluate(doc);
   
                    targetGridPairs[i] = new LabelValuePair(targetDescription, targetGrid);

                }

                targetGridPairs[files.length] = new LabelValuePair("No Target Grid", Constants.NO_TARGET_GRID);
                addListOption(new ListPropertyConfigurationOption(Constants.TARGET_GRID, model
                    .getMessage("target.grid"), targetGridPairs, true));
            } catch (Exception e) {
                e.printStackTrace();
            }
            prepared = true;
        }

        super.prepare();
    }
   

} // @jve:decl-index=0:visual-constraint="10,10"
