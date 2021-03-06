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
package gov.nih.nci.cagrid.fqp.client;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.dcql.DCQLQuery;
import gov.nih.nci.cagrid.fqp.results.client.FederatedQueryResultsClient;

import java.io.InputStream;

import org.cagrid.transfer.context.client.TransferServiceContextClient;
import org.cagrid.transfer.context.client.helper.TransferClientHelper;
import org.cagrid.transfer.context.stubs.types.TransferServiceContextReference;
import org.globus.gsi.GlobusCredential;


public class FederatedQueryProcessorTransferClient {

    public static void usage() {
        System.out.println(FederatedQueryProcessorTransferClient.class.getName()
            + " -Durl <service url> -Dcql <DCQL file> -dcredential <caGrid Proxy>");
    }


    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 4)) {
                if (args[0].equals("-url")) {
                    String url = args[1];

                    if (!args[2].equals("-dcql")) {
                        usage();
                        System.exit(1);
                    }
                    String dcqlFile = args[3];

                    FederatedQueryProcessorClient client;
                    GlobusCredential cred = null;
                    if (url.startsWith("https")) {
                        cred = GlobusCredential.getDefaultCredential();
                        if (cred != null) {
                            System.out.println("Identity =" + cred.getIdentity());
                        }

                        client = new FederatedQueryProcessorClient(url, cred);
                    } else {
                        client = new FederatedQueryProcessorClient(url);
                    }

                    DCQLQuery dcql = Utils.deserializeDocument(dcqlFile, DCQLQuery.class);

                    System.out.println("Starting query");

                    FederatedQueryResultsClient resultsClient = client.executeAsynchronously(dcql);
                    // TODO: subscribe to processing status resource property
                    while (!resultsClient.isProcessingComplete()) {
                        Thread.sleep(1000);
                        System.out.print(".");
                    }

                    System.out.println();
                    System.out.println("Transfering...");
                    TransferServiceContextReference transferRef = resultsClient.transfer();

                    InputStream transferStream = null;
                    if (url.startsWith("https")) {
                        TransferServiceContextClient transferClient = new TransferServiceContextClient(transferRef
                            .getEndpointReference(), cred);
                        transferStream = TransferClientHelper.getData(transferClient.getDataTransferDescriptor(), cred);
                    } else {
                        TransferServiceContextClient transferClient = new TransferServiceContextClient(transferRef
                            .getEndpointReference());
                        transferStream = TransferClientHelper.getData(transferClient.getDataTransferDescriptor());
                    }

                    StringBuffer text = Utils.inputStreamToStringBuffer(transferStream);
                    System.out.println("Text from transfer:");
                    System.out.println(text);
                    System.out.println("Done");

                } else {
                    usage();
                    System.exit(1);
                }
            } else {
                usage();
                System.exit(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
