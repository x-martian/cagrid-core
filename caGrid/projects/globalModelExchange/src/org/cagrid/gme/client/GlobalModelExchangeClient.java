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
package org.cagrid.gme.client;

import gov.nih.nci.cagrid.common.SchemaValidationException;
import gov.nih.nci.cagrid.common.SchemaValidator;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.common.XMLUtilities;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.gme.common.FilesystemCacher;
import org.cagrid.gme.common.GlobalModelExchangeI;
import org.cagrid.gme.domain.XMLSchemaBundle;
import org.cagrid.gme.domain.XMLSchemaNamespace;
import org.cagrid.gme.stubs.types.NoSuchNamespaceExistsFault;
import org.globus.gsi.GlobusCredential;
import org.jdom.Document;
import org.jdom.Namespace;


/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS
 * METHODS. This client is generated automatically by Introduce to provide a
 * clean unwrapped API to the service. On construction the class instance will
 * contact the remote service and retrieve it's security metadata description
 * which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class GlobalModelExchangeClient extends GlobalModelExchangeClientBase implements GlobalModelExchangeI {

    public GlobalModelExchangeClient(String url) throws MalformedURIException, RemoteException {
        this(url, null);
    }


    public GlobalModelExchangeClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(url, proxy);
    }


    public GlobalModelExchangeClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr, null);
    }


    public GlobalModelExchangeClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException,
        RemoteException {
        super(epr, proxy);
    }


    public static void usage() {
        System.out.println(GlobalModelExchangeClient.class.getName() + " -url <service url>");
    }


    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 2)) {
                if (args[0].equals("-url")) {
                    GlobalModelExchangeClient client = new GlobalModelExchangeClient(args[1]);
                    // place client calls here if you want to use this main as a
                    // test....
                } else {
                    usage();
                    System.exit(1);
                }
            } else {
                usage();
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    public void validateXML(String xml) throws SchemaValidationException, IOException, URISyntaxException {
        Document doc = null;
        try {
            doc = XMLUtilities.stringToDocument(xml);
        } catch (Exception e) {
            throw new IOException("Error converting XML string to Document:" + e.getMessage());
        }
        validateXMLDocument(doc);
    }


    public void validateXMLDocument(Document doc) throws SchemaValidationException, URISyntaxException, IOException {
        // extract the document's namespace
        Namespace docNS = doc.getRootElement().getNamespace();
        XMLSchemaNamespace schemaNS = new XMLSchemaNamespace(docNS.getURI());

        // create temporary working area
        File workDir = File.createTempFile("gmetempdir", "");
        workDir.delete();
        workDir.mkdir();
        workDir.deleteOnExit();

        try {
            // ask the GME to write all the needed XSDs for that namespace to
            // the filesystem
            Map<XMLSchemaNamespace, File> cachedSchemas = cacheSchemas(schemaNS, workDir);

            // find the file of the root schema
            File rootXSDLocation = cachedSchemas.get(schemaNS);

            // leverage the core schema validator to do the work with the cached
            // XSDs
            SchemaValidator.validate(rootXSDLocation.getAbsolutePath(), XMLUtilities.documentToString(doc));
        } finally {
            Utils.deleteDir(workDir);
        }
    }


    public void validateXMLFile(File xmlFile) throws IOException, SchemaValidationException, URISyntaxException,
        NoSuchNamespaceExistsFault {

        Document doc = null;
        try {
            doc = XMLUtilities.fileNameToDocument(xmlFile.getAbsolutePath());
        } catch (Exception e) {
            throw new IOException("Error converting XML File to Document:" + e.getMessage());
        }
        validateXMLDocument(doc);
    }


    public Map<XMLSchemaNamespace, File> cacheSchemas(XMLSchemaNamespace targetNamespace, File directory)
        throws NoSuchNamespaceExistsFault, IOException, RemoteException {
        XMLSchemaBundle bundle = getXMLSchemaAndDependencies(targetNamespace);
        FilesystemCacher cacher = new FilesystemCacher(bundle, directory);
        Map<URI, File> cachedSchemas = cacher.cacheSchemas();

        Map<XMLSchemaNamespace, File> result = new HashMap<XMLSchemaNamespace, File>();
        for (URI uri : cachedSchemas.keySet()) {
            result.put(new XMLSchemaNamespace(uri), cachedSchemas.get(uri));
        }
        return result;
    }


    public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata()
        throws RemoteException {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getServiceSecurityMetadata");
            gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest params = new gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest();
            gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse boxedResult = portType
                .getServiceSecurityMetadata(params);
            return boxedResult.getServiceSecurityMetadata();
        }
    }


    public void publishXMLSchemas(org.cagrid.gme.domain.XMLSchema[] schemas) throws RemoteException,
        org.cagrid.gme.stubs.types.InvalidSchemaSubmissionFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "publishXMLSchemas");
            org.cagrid.gme.stubs.PublishXMLSchemasRequest params = new org.cagrid.gme.stubs.PublishXMLSchemasRequest();
            org.cagrid.gme.stubs.PublishXMLSchemasRequestSchemas schemasContainer = new org.cagrid.gme.stubs.PublishXMLSchemasRequestSchemas();
            schemasContainer.setXMLSchema(schemas);
            params.setSchemas(schemasContainer);
            org.cagrid.gme.stubs.PublishXMLSchemasResponse boxedResult = portType.publishXMLSchemas(params);
        }
    }


    public org.cagrid.gme.domain.XMLSchema getXMLSchema(org.cagrid.gme.domain.XMLSchemaNamespace targetNamespace)
        throws RemoteException, org.cagrid.gme.stubs.types.NoSuchNamespaceExistsFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getXMLSchema");
            org.cagrid.gme.stubs.GetXMLSchemaRequest params = new org.cagrid.gme.stubs.GetXMLSchemaRequest();
            org.cagrid.gme.stubs.GetXMLSchemaRequestTargetNamespace targetNamespaceContainer = new org.cagrid.gme.stubs.GetXMLSchemaRequestTargetNamespace();
            targetNamespaceContainer.setXMLSchemaNamespace(targetNamespace);
            params.setTargetNamespace(targetNamespaceContainer);
            org.cagrid.gme.stubs.GetXMLSchemaResponse boxedResult = portType.getXMLSchema(params);
            return boxedResult.getXMLSchema();
        }
    }


    public org.cagrid.gme.domain.XMLSchemaNamespace[] getXMLSchemaNamespaces() throws RemoteException {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getXMLSchemaNamespaces");
            org.cagrid.gme.stubs.GetXMLSchemaNamespacesRequest params = new org.cagrid.gme.stubs.GetXMLSchemaNamespacesRequest();
            org.cagrid.gme.stubs.GetXMLSchemaNamespacesResponse boxedResult = portType.getXMLSchemaNamespaces(params);
            return boxedResult.getXMLSchemaNamespace();
        }
    }


    public org.cagrid.gme.domain.XMLSchemaBundle getXMLSchemaAndDependencies(
        org.cagrid.gme.domain.XMLSchemaNamespace targetNamespace) throws RemoteException,
        org.cagrid.gme.stubs.types.NoSuchNamespaceExistsFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getXMLSchemaAndDependencies");
            org.cagrid.gme.stubs.GetXMLSchemaAndDependenciesRequest params = new org.cagrid.gme.stubs.GetXMLSchemaAndDependenciesRequest();
            org.cagrid.gme.stubs.GetXMLSchemaAndDependenciesRequestTargetNamespace targetNamespaceContainer = new org.cagrid.gme.stubs.GetXMLSchemaAndDependenciesRequestTargetNamespace();
            targetNamespaceContainer.setXMLSchemaNamespace(targetNamespace);
            params.setTargetNamespace(targetNamespaceContainer);
            org.cagrid.gme.stubs.GetXMLSchemaAndDependenciesResponse boxedResult = portType
                .getXMLSchemaAndDependencies(params);
            return boxedResult.getXMLSchemaBundle();
        }
    }


    public void deleteXMLSchemas(org.cagrid.gme.domain.XMLSchemaNamespace[] targetNamespaces) throws RemoteException,
        org.cagrid.gme.stubs.types.NoSuchNamespaceExistsFault, org.cagrid.gme.stubs.types.UnableToDeleteSchemaFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "deleteXMLSchemas");
            org.cagrid.gme.stubs.DeleteXMLSchemasRequest params = new org.cagrid.gme.stubs.DeleteXMLSchemasRequest();
            org.cagrid.gme.stubs.DeleteXMLSchemasRequestTargetNamespaces targetNamespacesContainer = new org.cagrid.gme.stubs.DeleteXMLSchemasRequestTargetNamespaces();
            targetNamespacesContainer.setXMLSchemaNamespace(targetNamespaces);
            params.setTargetNamespaces(targetNamespacesContainer);
            org.cagrid.gme.stubs.DeleteXMLSchemasResponse boxedResult = portType.deleteXMLSchemas(params);
        }
    }


    public org.cagrid.gme.domain.XMLSchemaNamespace[] getImportedXMLSchemaNamespaces(
        org.cagrid.gme.domain.XMLSchemaNamespace targetNamespace) throws RemoteException,
        org.cagrid.gme.stubs.types.NoSuchNamespaceExistsFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getImportedXMLSchemaNamespaces");
            org.cagrid.gme.stubs.GetImportedXMLSchemaNamespacesRequest params = new org.cagrid.gme.stubs.GetImportedXMLSchemaNamespacesRequest();
            org.cagrid.gme.stubs.GetImportedXMLSchemaNamespacesRequestTargetNamespace targetNamespaceContainer = new org.cagrid.gme.stubs.GetImportedXMLSchemaNamespacesRequestTargetNamespace();
            targetNamespaceContainer.setXMLSchemaNamespace(targetNamespace);
            params.setTargetNamespace(targetNamespaceContainer);
            org.cagrid.gme.stubs.GetImportedXMLSchemaNamespacesResponse boxedResult = portType
                .getImportedXMLSchemaNamespaces(params);
            return boxedResult.getXMLSchemaNamespace();
        }
    }


    public org.cagrid.gme.domain.XMLSchemaNamespace[] getImportingXMLSchemaNamespaces(
        org.cagrid.gme.domain.XMLSchemaNamespace targetNamespace) throws RemoteException,
        org.cagrid.gme.stubs.types.NoSuchNamespaceExistsFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getImportingXMLSchemaNamespaces");
            org.cagrid.gme.stubs.GetImportingXMLSchemaNamespacesRequest params = new org.cagrid.gme.stubs.GetImportingXMLSchemaNamespacesRequest();
            org.cagrid.gme.stubs.GetImportingXMLSchemaNamespacesRequestTargetNamespace targetNamespaceContainer = new org.cagrid.gme.stubs.GetImportingXMLSchemaNamespacesRequestTargetNamespace();
            targetNamespaceContainer.setXMLSchemaNamespace(targetNamespace);
            params.setTargetNamespace(targetNamespaceContainer);
            org.cagrid.gme.stubs.GetImportingXMLSchemaNamespacesResponse boxedResult = portType
                .getImportingXMLSchemaNamespaces(params);
            return boxedResult.getXMLSchemaNamespace();
        }
    }


    public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
        org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getMultipleResourceProperties");
            return portType.getMultipleResourceProperties(params);
        }
    }


    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params)
        throws RemoteException {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getResourceProperty");
            return portType.getResourceProperty(params);
        }
    }


    public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(
        org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "queryResourceProperties");
            return portType.queryResourceProperties(params);
        }
    }

}
