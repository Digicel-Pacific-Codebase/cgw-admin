<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://portals.apache.org/bridges/struts/tags-portlet-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/cgw-admin-tags.tld" prefix="cgw-admin" %>
<%@ page import="com.digicel.service.cgw.admin.util.Constants"%>

<%
	final String msisdn = (String) pageContext.getSession().getAttribute(Constants.URL_MSISDN_RANGES_MSISDN);
	
%>
				 
<p />


<html:errors/>
<p />
<html:form action="msisdnRangeSearchForm.do" method="POST">

	<p/><html:img src="images/searchMSISDNranges.png" altKey="title.msisdnrange.search" /><br />
	<table cellpadding="5" cellspacing="0" >
		<tr>
			<br>
		</tr>
		
		<tr>
		  <td><bean:message key="msisdnRangeView.label.msisdn" /></td>
		  <td><html:text property="msisdn" styleClass="portlet-form-field" style="width:150px;" value="<%=msisdn%>" />&nbsp;
		  	  <button type="submit" class="portlet-form-button" property="searchMsisdnRangeSubmit">
		    	<bean:message key="msisdnRangeView.button.search" />
			  </button>
		  </td>
		</tr>
	</table>

	<br />

</html:form> 


		<cgw-admin:msisdnRangesTable tableId="list_table" configSessionKey="<%=Constants.SESSION_TABLE_MSISDN_RANGES_VIEW %>" />

