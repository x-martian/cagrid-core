<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
			<form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
			    <form:errors path="*" cssClass="errors" id="status" element="div" />
	            <fieldset>
	                <legend>CAS Login Form</legend>
	                <div class="box" id="login">
	                <!-- <spring:message code="screen.welcome.welcome" /> -->
	                    <h2><spring:message code="screen.welcome.instructions" /></h2>
	                    <div class="row">
	                        <label for="username"><spring:message code="screen.welcome.label.netid" /></label>
							<c:if test="${not empty sessionScope.openIdLocalId}">
							<strong>${sessionScope.openIdLocalId}</strong>
							<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
							</c:if>
							
							<c:if test="${empty sessionScope.openIdLocalId}">
							<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
							<form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
							</c:if>
	                    </div>
	                    <div class="row">
	                        <label for="password"><spring:message code="screen.welcome.label.password" /></label>
							<%--
							NOTE: Certain browsers will offer the option of caching passwords for a user.  There is a non-standard attribute,
							"autocomplete" that when set to "off" will tell certain browsers not to prompt to cache credentials.  For more
							information, see the following web page:
							http://www.geocities.com/technofundo/tech/web/ie_autocomplete.html
							--%>
							<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
							<form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" />
	                    </div>
	                    <div class="row">
	                        <label for="authenticationServiceURL"><spring:message code="screen.welcome.label.authentication.service" /></label>
							<spring:message code="screen.welcome.label.authentication.service.accesskey" var="authenticationServiceAccessKey" />
							<form:select cssClass="required" cssErrorClass="error" id="authenticationServiceURL" tabindex="3" path="authenticationServiceURL"  accesskey="${authenticationServiceAccessKey}" htmlEscape="true" >
								<form:option value="-" label="-- Please Select"/>
            					<form:options items="${authenticationServiceInformationList}" itemValue="authenticationServiceURL" itemLabel="authenticationServiceName"/>
        					</form:select>
	                    </div>
	                    <div class="row check">
	                        <input id="warn" name="warn" value="true" tabindex="4" accesskey="<spring:message code="screen.welcome.label.warn.accesskey" />" type="checkbox" />
	                        <label for="warn"><spring:message code="screen.welcome.label.warn" /></label>
	                    </div>
	                    <div class="row btn-row">
							<input type="hidden" name="lt" value="${flowExecutionKey}" />
							<input type="hidden" name="_eventId" value="submit" />

	                        <input class="btn-submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="5" type="submit" />
	                        <input class="btn-reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="6" type="reset" />
	                    </div>
	                </div>
	            </fieldset>
               
	            <div id="sidebar">
	                <p><spring:message code="screen.welcome.security" /></p>
	                <div id="list-languages">
						<c:set var="query" value="<%=request.getQueryString() == null ? "" : request.getQueryString().replaceAll("&locale=([A-Za-z][A-Za-z]_)?[A-Za-z][A-Za-z]|^locale=([A-Za-z][A-Za-z]_)?[A-Za-z][A-Za-z]", "")%>" />
						<c:set var="loginUrl" value="login?${query}${not empty query ? '&' : ''}locale=" />
	                    <h3>Languages:</h3>
						<ul>
							<li class="first"><a href="login?${query}${not empty query ? '&' : ''}locale=en">English</a></li>
						</ul>
	                </div>
	            </div>
        	</form:form>
<jsp:directive.include file="includes/bottom.jsp" />
