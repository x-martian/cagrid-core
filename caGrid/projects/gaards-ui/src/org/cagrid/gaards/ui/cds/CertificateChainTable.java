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
package org.cagrid.gaards.ui.cds;

import gov.nih.nci.cagrid.common.FaultUtil;
import gov.nih.nci.cagrid.common.Runner;

import java.security.cert.X509Certificate;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.gaards.cds.common.CertificateChain;
import org.cagrid.gaards.cds.common.Utils;
import org.cagrid.gaards.ui.common.CertificateInformationComponent;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.table.GrapeBaseTable;
import org.cagrid.grape.utils.ErrorDialog;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings </A>
 */
public class CertificateChainTable extends GrapeBaseTable {
    private static Log log = LogFactory.getLog(CertificateChainTable.class);

    private static final long serialVersionUID = 1L;

    public final static String certificate = "certificate";

    public final static String CERTIFICATE_SUBJECT = "Subject";


    public CertificateChainTable() {
        super(createTableModel());
        TableColumn c = this.getColumn(certificate);
        c.setMaxWidth(0);
        c.setMinWidth(0);
        c.setPreferredWidth(0);
        c.setResizable(false);

        c = this.getColumn(CERTIFICATE_SUBJECT);
        this.clearTable();
    }


    public static DefaultTableModel createTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(certificate);
        model.addColumn(CERTIFICATE_SUBJECT);
        return model;
    }


    public void setCertificateChain(final CertificateChain chain) throws Exception {
        clearTable();
        if (chain != null) {
            X509Certificate[] certs = Utils.toCertificateArray(chain);
            if (certs != null) {
                for (int i = 0; i < certs.length; i++) {
                    Vector<Object> v = new Vector<Object>();
                    v.add(certs[i]);
                    v.add(certs[i].getSubjectDN().toString());
                    addRow(v);
                }
            }
        }
    }


    public synchronized X509Certificate getSelectedRecord() throws Exception {
        int row = getSelectedRow();
        if ((row >= 0) && (row < getRowCount())) {
            return (X509Certificate) getValueAt(row, 0);
        } else {
            throw new Exception("Please select a certificate!!!");
        }
    }


    public void doubleClick() {
        Runner runner = new Runner() {
            public void execute() {
                try {
                    GridApplication.getContext().addApplicationComponent(
                        new CertificateInformationComponent(getSelectedRecord()));
                } catch (Exception e) {
                    ErrorDialog.showError(e);
                    FaultUtil.logFault(log, e);
                }
            }
        };
        try {
            GridApplication.getContext().executeInBackground(runner);
        } catch (Exception t) {
            t.getMessage();
        }
    }


    public void singleClick() throws Exception {
        // nothing to do here
    }
}
