<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="http://portals.apache.org/bridges/struts/tags-portlet-html" prefix="html" %>

<!-- Checks the ID property of the form. It's empty in case of creation. -->
<logic:empty name="MsisdnRangeForm" property="id" scope="request">
	<p/><html:img src="images/createMSISDNrange.png" altKey="title.msisdnrange.create" /><br />
	<bean:define id="formAction" value="msisdnRangeCreate.do" toScope="request" />
</logic:empty>
<logic:notEmpty name="MsisdnRangeForm" property="id" scope="request">
	<p/><html:img src="images/editMSISDNrange.png" altKey="title.msisdnrange.edit" /><br />
	<bean:define id="formAction" value="msisdnRangeEdit.do" toScope="request" />
</logic:notEmpty>

<p />
<html:errors/>
<p />

<html:form action='<%=(String) request.getAttribute("formAction")%>'>
	<html:hidden property="id" />
	<table cellpadding="5" cellspacing="0" id="detailsTable">
		
		<tr>
		  <td class="portlet-form-field-label"><bean:message key="manageMsisdnRange.label.from" /></td>
		  <td class="portlet-form-input-field"><html:text property="from" styleClass="portlet-form-field" style="width:150px"/></td>
		</tr>
		<tr>
		  <td class="portlet-form-field-label"><bean:message key="manageMsisdnRange.label.to" /></td>
		  <td class="portlet-form-input-field"><html:text property="to" styleClass="portlet-form-field" style="width:150px" /></td>
		</tr>
		<tr>
		  <td colspan="2" align="center">
		    <button type="submit" class="portlet-form-button">
				<bean:message key="manageMsisdnRange.button.submit"/>
			</button>
		  </td>
		</tr>
	</table>
	
	<p />

</html:form>

