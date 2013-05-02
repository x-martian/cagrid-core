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
package org.cagrid.mms.client;

import gov.nih.nci.cagrid.metadata.dataservice.DomainModel;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.mms.common.MetadataModelServiceI;
import org.cagrid.mms.domain.ModelSourceMetadata;
import org.cagrid.mms.domain.UMLProjectIdentifer;
import org.globus.gsi.GlobusCredential;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS
 * METHODS. This client is generated automatically by Introduce to provide a
 * clean unwrapped API to the service. On construction the class instance will
 * contact the remote service and retrieve it's security metadata description
 * which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class MetadataModelServiceClient extends MetadataModelServiceClientBase implements MetadataModelServiceI {

    public MetadataModelServiceClient(String url) throws MalformedURIException, RemoteException {
        this(url, null);
    }

    public MetadataModelServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(url, proxy);
    }

    public MetadataModelServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr, null);
    }

    public MetadataModelServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException,
        RemoteException {
        super(epr, proxy);
    }

    public static void usage() {
        System.out.println(MetadataModelServiceClient.class.getName() + " -url <service url>");
    }

    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 2)) {
                if (args[0].equals("-url")) {
                    MetadataModelServiceClient client = new MetadataModelServiceClient(args[1]);
                    // place client calls here if you want to use this main as a
                    // test....
                    ModelSourceMetadata md = client.getModelSourceMetadata();

                    UMLProjectIdentifer umlProjectIdentifer = new UMLProjectIdentifer();
                    umlProjectIdentifer.setIdentifier("caTIES 1.0");
                    umlProjectIdentifer.setVersion("1");
                    
                    DomainModel dm = client.generateDomainModelForProject(umlProjectIdentifer);

                    System.out.println(dm.getProjectDescription());
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

  public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getMultipleResourceProperties");
    return portType.getMultipleResourceProperties(params);
    }
  }

  public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getResourceProperty");
    return portType.getResourceProperty(params);
    }
  }

  public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"queryResourceProperties");
    return portType.queryResourceProperties(params);
    }
  }

  public gov.nih.nci.cagrid.metadata.dataservice.DomainModel generateDomainModelForProject(org.cagrid.mms.domain.UMLProjectIdentifer umlProjectIdentifer) throws RemoteException, org.cagrid.mms.stubs.types.InvalidUMLProjectIndentifier {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"generateDomainModelForProject");
    org.cagrid.mms.stubs.GenerateDomainModelForProjectRequest params = new org.cagrid.mms.stubs.GenerateDomainModelForProjectRequest();
    org.cagrid.mms.stubs.GenerateDomainModelForProjectRequestUmlProjectIdentifer umlProjectIdentiferContainer = new org.cagrid.mms.stubs.GenerateDomainModelForProjectRequestUmlProjectIdentifer();
    umlProjectIdentiferContainer.setUMLProjectIdentifer(umlProjectIdentifer);
    params.setUmlProjectIdentifer(umlProjectIdentiferContainer);
    org.cagrid.mms.stubs.GenerateDomainModelForProjectResponse boxedResult = portType.generateDomainModelForProject(params);
    return boxedResult.getDomainModel();
    }
  }

  public gov.nih.nci.cagrid.metadata.dataservice.DomainModel generateDomainModelForPackages(org.cagrid.mms.domain.UMLProjectIdentifer umlProjectIdentifer,java.lang.String[] packageNames) throws RemoteException, org.cagrid.mms.stubs.types.InvalidUMLProjectIndentifier {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"generateDomainModelForPackages");
    org.cagrid.mms.stubs.GenerateDomainModelForPackagesRequest params = new org.cagrid.mms.stubs.GenerateDomainModelForPackagesRequest();
    org.cagrid.mms.stubs.GenerateDomainModelForPackagesRequestUmlProjectIdentifer umlProjectIdentiferContainer = new org.cagrid.mms.stubs.GenerateDomainModelForPackagesRequestUmlProjectIdentifer();
    umlProjectIdentiferContainer.setUMLProjectIdentifer(umlProjectIdentifer);
    params.setUmlProjectIdentifer(umlProjectIdentiferContainer);
    params.setPackageNames(packageNames);
    org.cagrid.mms.stubs.GenerateDomainModelForPackagesResponse boxedResult = portType.generateDomainModelForPackages(params);
    return boxedResult.getDomainModel();
    }
  }

  public gov.nih.nci.cagrid.metadata.dataservice.DomainModel generateDomainModelForClasses(org.cagrid.mms.domain.UMLProjectIdentifer umlProjectIdentifer,java.lang.String[] fullyQualifiedClassNames) throws RemoteException, org.cagrid.mms.stubs.types.InvalidUMLProjectIndentifier {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"generateDomainModelForClasses");
    org.cagrid.mms.stubs.GenerateDomainModelForClassesRequest params = new org.cagrid.mms.stubs.GenerateDomainModelForClassesRequest();
    org.cagrid.mms.stubs.GenerateDomainModelForClassesRequestUmlProjectIdentifer umlProjectIdentiferContainer = new org.cagrid.mms.stubs.GenerateDomainModelForClassesRequestUmlProjectIdentifer();
    umlProjectIdentiferContainer.setUMLProjectIdentifer(umlProjectIdentifer);
    params.setUmlProjectIdentifer(umlProjectIdentiferContainer);
    params.setFullyQualifiedClassNames(fullyQualifiedClassNames);
    org.cagrid.mms.stubs.GenerateDomainModelForClassesResponse boxedResult = portType.generateDomainModelForClasses(params);
    return boxedResult.getDomainModel();
    }
  }

  public gov.nih.nci.cagrid.metadata.dataservice.DomainModel generateDomainModelForClassesWithExcludes(org.cagrid.mms.domain.UMLProjectIdentifer umlProjectIdentifer,java.lang.String[] fullyQualifiedClassNames,org.cagrid.mms.domain.UMLAssociationExclude[] umlAssociationExclude) throws RemoteException, org.cagrid.mms.stubs.types.InvalidUMLProjectIndentifier {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"generateDomainModelForClassesWithExcludes");
    org.cagrid.mms.stubs.GenerateDomainModelForClassesWithExcludesRequest params = new org.cagrid.mms.stubs.GenerateDomainModelForClassesWithExcludesRequest();
    org.cagrid.mms.stubs.GenerateDomainModelForClassesWithExcludesRequestUmlProjectIdentifer umlProjectIdentiferContainer = new org.cagrid.mms.stubs.GenerateDomainModelForClassesWithExcludesRequestUmlProjectIdentifer();
    umlProjectIdentiferContainer.setUMLProjectIdentifer(umlProjectIdentifer);
    params.setUmlProjectIdentifer(umlProjectIdentiferContainer);
    params.setFullyQualifiedClassNames(fullyQualifiedClassNames);
    org.cagrid.mms.stubs.GenerateDomainModelForClassesWithExcludesRequestUmlAssociationExclude umlAssociationExcludeContainer = new org.cagrid.mms.stubs.GenerateDomainModelForClassesWithExcludesRequestUmlAssociationExclude();
    umlAssociationExcludeContainer.setUMLAssociationExclude(umlAssociationExclude);
    params.setUmlAssociationExclude(umlAssociationExcludeContainer);
    org.cagrid.mms.stubs.GenerateDomainModelForClassesWithExcludesResponse boxedResult = portType.generateDomainModelForClassesWithExcludes(params);
    return boxedResult.getDomainModel();
    }
  }

  public gov.nih.nci.cagrid.metadata.ServiceMetadata annotateServiceMetadata(gov.nih.nci.cagrid.metadata.ServiceMetadata serviceMetadata,org.cagrid.mms.domain.NamespaceToProjectMapping[] namespaceToProjectMappings) throws RemoteException, org.cagrid.mms.stubs.types.InvalidUMLProjectIndentifier {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"annotateServiceMetadata");
    org.cagrid.mms.stubs.AnnotateServiceMetadataRequest params = new org.cagrid.mms.stubs.AnnotateServiceMetadataRequest();
    org.cagrid.mms.stubs.AnnotateServiceMetadataRequestServiceMetadata serviceMetadataContainer = new org.cagrid.mms.stubs.AnnotateServiceMetadataRequestServiceMetadata();
    serviceMetadataContainer.setServiceMetadata(serviceMetadata);
    params.setServiceMetadata(serviceMetadataContainer);
    org.cagrid.mms.stubs.AnnotateServiceMetadataRequestNamespaceToProjectMappings namespaceToProjectMappingsContainer = new org.cagrid.mms.stubs.AnnotateServiceMetadataRequestNamespaceToProjectMappings();
    namespaceToProjectMappingsContainer.setNamespaceToProjectMapping(namespaceToProjectMappings);
    params.setNamespaceToProjectMappings(namespaceToProjectMappingsContainer);
    org.cagrid.mms.stubs.AnnotateServiceMetadataResponse boxedResult = portType.annotateServiceMetadata(params);
    return boxedResult.getServiceMetadata();
    }
  }

  public org.cagrid.mms.domain.ModelSourceMetadata getModelSourceMetadata() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getModelSourceMetadata");
    org.cagrid.mms.stubs.GetModelSourceMetadataRequest params = new org.cagrid.mms.stubs.GetModelSourceMetadataRequest();
    org.cagrid.mms.stubs.GetModelSourceMetadataResponse boxedResult = portType.getModelSourceMetadata(params);
    return boxedResult.getModelSourceMetadata();
    }
  }

}
