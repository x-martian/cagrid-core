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
package gov.nih.nci.cagrid.data.style;

import gov.nih.nci.cagrid.introduce.beans.extension.ServiceExtensionDescriptionType;
import gov.nih.nci.cagrid.introduce.common.ServiceInformation;

/** 
 *  StyleCreationPostProcessor
 *  TODO:DOCUMENT ME
 * 
 * @author David Ervin
 * 
 * @created Jul 10, 2007 10:58:44 AM
 * @version $Id: StyleCreationPostProcessor.java,v 1.1 2007-07-12 17:20:52 dervin Exp $ 
 */
public interface StyleCreationPostProcessor {

    public void creationPostProcessStyle(ServiceExtensionDescriptionType desc, ServiceInformation serviceInfo) throws Exception;
}
