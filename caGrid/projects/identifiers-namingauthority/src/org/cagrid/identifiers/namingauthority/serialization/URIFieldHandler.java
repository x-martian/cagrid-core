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
package org.cagrid.identifiers.namingauthority.serialization;

import java.net.URI;
import java.net.URISyntaxException;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

public class URIFieldHandler extends GeneralizedFieldHandler {

	@Override
	public Object convertUponGet(Object uri) {
		if (uri == null) {
            return null;
        } else {
            return uri.toString();
        }
	}

	@Override
	public Object convertUponSet(Object string) {
		URI result = null;
        try {
            result = new URI((String) string);
        } catch (URISyntaxException e) {
        }

        return result;
	}

	@Override
	public Class getFieldType() {
		return URI.class;
	}

}
