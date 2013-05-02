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
package gov.nih.nci.cagrid.data.ui.domain;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.common.portal.PortalLookAndFeel;
import gov.nih.nci.cagrid.common.portal.PortalUtils;
import gov.nih.nci.cagrid.data.DataServiceConstants;
import gov.nih.nci.cagrid.data.common.ExtensionDataManager;
import gov.nih.nci.cagrid.data.extension.ModelSourceType;
import gov.nih.nci.cagrid.introduce.beans.resource.ResourcePropertyType;
import gov.nih.nci.cagrid.introduce.common.CommonTools;
import gov.nih.nci.cagrid.introduce.common.FileFilters;
import gov.nih.nci.cagrid.introduce.common.ResourceManager;
import gov.nih.nci.cagrid.introduce.common.ServiceInformation;
import gov.nih.nci.cagrid.metadata.MetadataUtils;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import org.cagrid.grape.GridApplication;
import org.cagrid.grape.utils.CompositeErrorDialog;


/**
 * DomainModelAdvancedOptionsDialog Dialog to hide advanced options from the
 * user when selecting a Domain Model from the caDSR
 * 
 * @author David Ervin
 * 
 * @created Jun 14, 2007 10:14:20 AM
 * @version $Id: DomainModelAdvancedOptionsDialog.java,v 1.5 2009-01-29 19:23:56 dervin Exp $
 */
public class DomainModelAdvancedOptionsDialog extends JDialog {
    public static final String INFORMATION = 
        "Selection of 'No Domain Model' is for development and testing " +
        "purposes only, and should never be used for a production " +
        "data service.\n\n" +
        "A domain model may also be selected from the local file system.  " +
        "This option is intended to cut down on build time of the service " +
        "while developing it and saving multiple times, as it does not " +
        "connect out to the caDSR grid service.  A domain model should " +
        "be created once from the caDSR grid service, then copied out of " +
        "the service's etc directory for use in later development.  " +
        "Before a service is finalized and ready for deployment, the " +
        "caDSR should be used again to create a fresh domain model.\n" +
        "Please note that the domain model is an XML file generated by " +
        "specialized caGrid tooling, and is not derived from an XMI file " +
        "created by the caCORE process, and is not an export from the CDE " +
        "browser.  The domain model XML file must conform to the domain " +
        "model schema, which has the namespace " +
        "'gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata.dataservice'.  " +
        "This schema may be found in the caGrid metadata project.";
    
    private ServiceInformation serviceInfo = null;
    private ExtensionDataManager dataManager = null;

    private JCheckBox noDomainModelCheckBox = null;
    private JCheckBox fromFileCheckBox = null;
    private JPanel checkBoxPanel = null;
    private JLabel filenameLabel = null;
    private JTextField filenameTextField = null;
    private JButton browseButton = null;
    private JPanel mainPanel = null;
    private JTextArea informationTextArea = null;
    private JScrollPane infoScrollPane = null;
    private JPanel fromFilePanel = null;

    private JButton doneButton = null;
    
    
    public DomainModelAdvancedOptionsDialog(ServiceInformation info, ExtensionDataManager dataManager) {
        super(GridApplication.getContext().getApplication(), 
            "Advanced Domain Model Options", true);
        this.serviceInfo = info;
        this.dataManager = dataManager;
        // make the dialog ignore the close button, it's confusing
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        initialize();
    }


    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        loadFromExtensionData();
        this.setSize(new Dimension(550, 350));
        this.setContentPane(getMainPanel());
        GridApplication.getContext().centerDialog(this);
    }
    
    
    private void loadFromExtensionData() {
        // select radio buttons according to stored cadsr information
        try {
            if (dataManager.isNoDomainModel()) {
                getNoDomainModelCheckBox().setSelected(true);
            } else if (dataManager.isPreBuiltModel()) {
                getFromFileCheckBox().setSelected(true);
                ResourcePropertyType[] resourceProps = serviceInfo.getServices().getService(0)
                    .getResourcePropertiesList().getResourceProperty();
                for (ResourcePropertyType prop : resourceProps) {
                    if (prop.getQName().equals(DataServiceConstants.DOMAIN_MODEL_QNAME)) {
                        File dmFile = new File(serviceInfo.getBaseDirectory().getAbsolutePath() 
                            + File.separator + "etc" + File.separator + prop.getFileLocation());
                        getFilenameTextField().setText(dmFile.getCanonicalPath());
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            CompositeErrorDialog.showErrorDialog("Error loading state information", ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method initializes noDomainModelCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getNoDomainModelCheckBox() {
        if (noDomainModelCheckBox == null) {
            noDomainModelCheckBox = new JCheckBox();
            noDomainModelCheckBox.setText("Use No Domain Model");
            noDomainModelCheckBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    boolean noDomainSelected = getNoDomainModelCheckBox().isSelected();
                    getFromFileCheckBox().setEnabled(!noDomainSelected);
                    if (noDomainSelected) {
                        // can't do from file AND no domain model...
                        getFromFileCheckBox().setSelected(false);
                    }
                    // enable / disable the from file selection as needed
                    PortalUtils.setContainerEnabled(getFromFilePanel(), !noDomainSelected);
                    getBrowseButton().setEnabled(getFromFileCheckBox().isSelected());
                    // store the domain model source state
                    try {
                        ModelSourceType source = ModelSourceType.mms;
                        if (noDomainSelected) {
                            source = ModelSourceType.none;
                        }
                        if (getFromFileCheckBox().isSelected()) {
                            source = ModelSourceType.preBuilt;
                        }
                        dataManager.storeDomainModelSource(source);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        CompositeErrorDialog.showErrorDialog("Error storing domain model source", 
                            ex.getMessage(), ex);
                    }
                }
            });
        }
        return noDomainModelCheckBox;
    }
    
    
    /**
     * This method initializes fromFileCheckBox 
     *  
     * @return javax.swing.JCheckBox    
     */
    private JCheckBox getFromFileCheckBox() {
        if (fromFileCheckBox == null) {
            fromFileCheckBox = new JCheckBox();
            fromFileCheckBox.setText("Domain Model From File System");
            fromFileCheckBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    boolean fromFileSelected = getFromFileCheckBox().isSelected();
                    getNoDomainModelCheckBox().setEnabled(!fromFileSelected);
                    // can browse now
                    getBrowseButton().setEnabled(fromFileSelected);
                    if (fromFileSelected) {
                        // cant have NO domain model and be from the file system
                        getNoDomainModelCheckBox().setSelected(false);
                    }
                    // store the domain model source state
                    try {
                        ModelSourceType source = ModelSourceType.mms;
                        if (getFromFileCheckBox().isSelected()) {
                            source = ModelSourceType.preBuilt;
                        }
                        dataManager.storeDomainModelSource(source);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        CompositeErrorDialog.showErrorDialog("Error storing domain model source", 
                            ex.getMessage(), ex);
                    }
                }
            });
        }
        return fromFileCheckBox;
    }


    /**
     * This method initializes filenameLabel
     * 
     * @return javax.swing.JLabel
     */
    private JLabel getFilenameLabel() {
        if (filenameLabel == null) {
            filenameLabel = new JLabel();
            filenameLabel.setText("Filename:");
        }
        return filenameLabel;
    }


    /**
     * This method initializes filenameTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getFilenameTextField() {
        if (filenameTextField == null) {
            filenameTextField = new JTextField();
            filenameTextField.setEditable(false);
        }
        return filenameTextField;
    }


    /**
     * This method initializes browseButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBrowseButton() {
        if (browseButton == null) {
            browseButton = new JButton();
            browseButton.setText("Browse");
            browseButton.setEnabled(false); // only enabled by check box
            browseButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    browseForDomainModel();
                }
            });
        }
        return browseButton;
    }


    /**
     * This method initializes mainPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getMainPanel() {
        if (mainPanel == null) {
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.anchor = GridBagConstraints.EAST;
            gridBagConstraints8.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints8.gridy = 3;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 0;
            gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.gridy = 0;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = GridBagConstraints.BOTH;
            gridBagConstraints5.gridy = 2;
            gridBagConstraints5.weightx = 1.0;
            gridBagConstraints5.weighty = 1.0;
            gridBagConstraints5.insets = new Insets(6, 6, 6, 6);
            gridBagConstraints5.gridx = 0;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.insets = new Insets(6, 6, 6, 6);
            gridBagConstraints4.gridy = 1;
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.add(getFromFilePanel(), gridBagConstraints4);
            mainPanel.add(getInfoScrollPane(), gridBagConstraints5);
            mainPanel.add(getCheckBoxPanel(), gridBagConstraints6);
            mainPanel.add(getDoneButton(), gridBagConstraints8);
        }
        return mainPanel;
    }


    /**
     * This method initializes informationTextArea
     * 
     * @return javax.swing.JTextArea
     */
    private JTextArea getInformationTextArea() {
        if (informationTextArea == null) {
            informationTextArea = new JTextArea();
            informationTextArea.setWrapStyleWord(true);
            informationTextArea.setLineWrap(true);
            informationTextArea.setText(INFORMATION);
            informationTextArea.setCaretPosition(0);
        }
        return informationTextArea;
    }


    /**
     * This method initializes infoScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getInfoScrollPane() {
        if (infoScrollPane == null) {
            infoScrollPane = new JScrollPane();
            infoScrollPane.setBorder(BorderFactory.createTitledBorder(
                null, "Information", TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));
            infoScrollPane.setViewportView(getInformationTextArea());
        }
        return infoScrollPane;
    }


    /**
     * This method initializes fromFilePanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getFromFilePanel() {
        if (fromFilePanel == null) {
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 2;
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints2.gridy = 0;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints1.gridx = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridy = 0;
            fromFilePanel = new JPanel();
            fromFilePanel.setLayout(new GridBagLayout());
            fromFilePanel.setBorder(BorderFactory.createTitledBorder(
                null, "Model From Filesystem", TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));
            fromFilePanel.add(getFilenameLabel(), gridBagConstraints);
            fromFilePanel.add(getFilenameTextField(), gridBagConstraints1);
            fromFilePanel.add(getBrowseButton(), gridBagConstraints2);
        }
        return fromFilePanel;
    }


    /**
     * This method initializes checkBoxPanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getCheckBoxPanel() {
        if (checkBoxPanel == null) {
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 1;
            gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints7.gridy = 0;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints3.gridy = 0;
            checkBoxPanel = new JPanel();
            checkBoxPanel.setLayout(new GridBagLayout());
            checkBoxPanel.add(getNoDomainModelCheckBox(), gridBagConstraints3);
            checkBoxPanel.add(getFromFileCheckBox(), gridBagConstraints7);
        }
        return checkBoxPanel;
    }
    
    
    private void browseForDomainModel() {
        String selection = null;
        try {
            selection = ResourceManager.promptFile(null, FileFilters.XML_FILTER);
        } catch (Exception ex) {
            ex.printStackTrace();
            CompositeErrorDialog.showErrorDialog("Error selecting domain model", ex.getMessage(), ex);
        }
        if (selection != null) {
            File inFile = new File(selection);
            FileReader inFileReader = null;
            // verify it's a domain model
            try {
                // attempt to deserialzie the file
                inFileReader = new FileReader(inFile);
                MetadataUtils.deserializeDomainModel(inFileReader);
            } catch (Exception ex) {
                String[] message = {
                    "The selected file could not be read as a valid Domain Model.",
                    "Domain Models must conform the the domain model schema with the ",
                    "URI: " + DataServiceConstants.DOMAIN_MODEL_QNAME.getNamespaceURI()
                };
                GridApplication.getContext().showMessage(message);
                return;
            } finally {
                if (inFileReader != null) {
                    try {
                        inFileReader.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        // whatever, we tried
                    }
                }
            }
            
            // locate / create resource property
            ResourcePropertyType domainModelResourceProperty = null;
            ResourcePropertyType[] domainModelProps = CommonTools.getResourcePropertiesOfType(
                serviceInfo.getServices().getService(0), DataServiceConstants.DOMAIN_MODEL_QNAME);
            if (domainModelProps.length != 0) {
                domainModelResourceProperty = domainModelProps[0];
                // if old file exists, delete it
                File oldFile = new File(
                    serviceInfo.getBaseDirectory().getAbsolutePath() + 
                    File.separator + "etc" + File.separator +
                    domainModelResourceProperty.getFileLocation());
                // FIXME: this is bad... if user plays with domain model from file
                // but does NOT save the service, the resource property continues to
                // point to a file which no longer exists, and subsequent loads
                // of the service will fail.  So will deployment, actually...
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            } else {
                domainModelResourceProperty = new ResourcePropertyType();
                domainModelResourceProperty.setPopulateFromFile(true);
                domainModelResourceProperty.setRegister(true);
                domainModelResourceProperty.setQName(DataServiceConstants.DOMAIN_MODEL_QNAME);
            }
            
            // copy the new file in to the service's etc directory
            File outFile = new File(serviceInfo.getBaseDirectory().getAbsolutePath() +
                File.separator + "etc" + File.separator + inFile.getName());
            try {
                Utils.copyFile(inFile, outFile);
            } catch (IOException ex) {
                ex.printStackTrace();
                CompositeErrorDialog.showErrorDialog("Error copying selected domain model file in to service", 
                    ex.getMessage(), ex);
            }
            
            // set value of resource property
            domainModelResourceProperty.setFileLocation(outFile.getName());
            
            // possibly put the resource property in the service for the first time
            if (domainModelProps.length == 0) {
                CommonTools.addResourcePropety(
                    serviceInfo.getServices().getService(0), domainModelResourceProperty);
            }
            
            // change the GUI to reflect the new value
            getFilenameTextField().setText(inFile.getName());
        }
    }


    /**
     * This method initializes doneButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getDoneButton() {
        if (doneButton == null) {
            doneButton = new JButton();
            doneButton.setText("Done");
            doneButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return doneButton;
    }
}
