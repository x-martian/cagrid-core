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
package org.cagrid.data.sdkquery42.style.wizard.model;

import javax.swing.JPanel;

import org.cagrid.data.sdkquery42.style.wizard.config.DomainModelConfigurationStep;
import org.cagrid.data.sdkquery42.style.wizard.config.DomainModelConfigurationStep.DomainModelConfigurationSource;

/**
 * JPanel which is required to produce a caGrid Domain Model
 * 
 * @author David
 */
public abstract class DomainModelSourcePanel extends JPanel {
    
    private DomainModelSourceValidityListener validityListener = null;
    private DomainModelConfigurationStep configuration = null;
    
    public DomainModelSourcePanel(
        DomainModelSourceValidityListener validityListener, 
        DomainModelConfigurationStep configuration) {
        super();
        this.validityListener = validityListener;
        this.configuration = configuration;
    }
    
    
    public abstract DomainModelConfigurationSource getSourceType();
    
    
    public abstract String getName();
    
    
    public abstract void populateFromConfiguration();
    
    
    public abstract void revalidateModel();
    
    
    protected DomainModelConfigurationStep getConfiguration() {
        return this.configuration;
    }
    
    
    protected void setModelValidity(boolean valid) {
        validityListener.domainModelSourceValid(this, valid);
    }
}
