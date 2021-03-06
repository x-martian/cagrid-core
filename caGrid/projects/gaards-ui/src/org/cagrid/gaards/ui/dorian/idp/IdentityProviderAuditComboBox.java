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
package org.cagrid.gaards.ui.dorian.idp;

import gov.nih.nci.cagrid.common.FaultUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.gaards.dorian.idp.IdentityProviderAudit;


/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 */
public class IdentityProviderAuditComboBox extends JComboBox {
	private static Log log = LogFactory.getLog(IdentityProviderAuditComboBox.class);
	
    private static final long serialVersionUID = 1L;

    public static String ANY = "Any";

    private List<IdentityProviderAudit> list;


    public IdentityProviderAuditComboBox() {
        list = new ArrayList<IdentityProviderAudit>();

        Field[] fields = IdentityProviderAudit.class.getFields();

        for (int i = 0; i < fields.length; i++) {
            if (IdentityProviderAudit.class.isAssignableFrom(fields[i].getType())) {
                try {
                    IdentityProviderAudit o = (IdentityProviderAudit) fields[i].get(null);
                    list.add(o);
                } catch (Exception e) {
                    FaultUtil.logFault(log, e);
                }
            }
        }
        this.addItem(ANY);
        for (int i = 0; i < list.size(); i++) {
            this.addItem(list.get(i));
        }
        this.setSelectedItem(ANY);
    }


    public void setToAny() {
        setSelectedItem(ANY);
    }


    public IdentityProviderAudit getSelectedAuditType() {
        if (getSelectedItem().getClass().isAssignableFrom(IdentityProviderAudit.class)) {
            return (IdentityProviderAudit) getSelectedItem();
        } else {
            return null;
        }
    }
}
