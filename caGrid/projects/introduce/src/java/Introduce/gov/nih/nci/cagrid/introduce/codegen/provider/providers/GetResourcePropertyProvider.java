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
package gov.nih.nci.cagrid.introduce.codegen.provider.providers;

import javax.xml.namespace.QName;

import gov.nih.nci.cagrid.introduce.beans.method.MethodType;
import gov.nih.nci.cagrid.introduce.beans.method.MethodTypeImportInformation;
import gov.nih.nci.cagrid.introduce.beans.method.MethodTypeOutput;
import gov.nih.nci.cagrid.introduce.beans.method.MethodTypeProviderInformation;
import gov.nih.nci.cagrid.introduce.beans.security.MethodAuthorization;
import gov.nih.nci.cagrid.introduce.beans.security.MethodSecurity;
import gov.nih.nci.cagrid.introduce.beans.security.NoAuthorization;
import gov.nih.nci.cagrid.introduce.beans.security.SecuritySetting;
import gov.nih.nci.cagrid.introduce.beans.service.ServiceType;
import gov.nih.nci.cagrid.introduce.common.CommonTools;
import gov.nih.nci.cagrid.introduce.common.ServiceInformation;

public class GetResourcePropertyProvider implements Provider {

    public void addResourceProvider(ServiceType service, ServiceInformation info) throws ProviderException {
        MethodType rpMethod = new MethodType();
        rpMethod.setName("GetResourceProperty");
        rpMethod.setOutput(new MethodTypeOutput());
        rpMethod.getOutput().setIsArray(false);
        rpMethod.getOutput().setQName(new QName("", "void"));

        MethodTypeImportInformation ii = new MethodTypeImportInformation();
        ii.setFromIntroduce(Boolean.FALSE);
        ii.setInputMessage(new QName(
            "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.wsdl",
            "GetResourcePropertyRequest"));
        ii.setOutputMessage(new QName(
            "http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.wsdl",
            "GetResourcePropertyResponse"));
        ii.setNamespace("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.wsdl");
        ii.setPackageName("org.oasis.wsrf.properties");
        ii.setPortTypeName("GetResourceProperty");
        ii.setWsdlFile("../wsrf/properties/WS-ResourceProperties.wsdl");
        rpMethod.setImportInformation(ii);
        rpMethod.setIsImported(true);

        MethodTypeProviderInformation pi = new MethodTypeProviderInformation();
        pi.setProviderClass("GetRPProvider");
        rpMethod.setProviderInformation(pi);
        rpMethod.setIsProvided(true);
        

        MethodSecurity sec = new MethodSecurity();
        sec.setSecuritySetting(SecuritySetting.None);
        MethodAuthorization auth = new MethodAuthorization();
        auth.setNoAuthorization(new NoAuthorization());
        
        rpMethod.setMethodSecurity(sec);


        CommonTools.addMethod(service, rpMethod);

    }


    public void removeResourceProvider(ServiceType service, ServiceInformation info) throws ProviderException {
        CommonTools.removeMethod(service.getMethods(), CommonTools.getMethod(service.getMethods(),
        "GetResourceProperty"));

    }

}
