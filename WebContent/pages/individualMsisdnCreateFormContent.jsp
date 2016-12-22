<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="http://portals.apache.org/bridges/struts/tags-portlet-html" prefix="html" %>

<p/><html:img src="images/createIndividualMSISDN.png" altKey="title.individualmsisdn.create" /><br />

<p />
<html:errors/>
<p />

<html:form action="individualMsisdnCreate.do">

<table cellpadding="5" cellspacing="0" id="detailsTable">
	
	<tr>
	  <td class="portlet-form-field-label"><bean:message key="createIndividualMsisdn.label.msisdn" /></td>
	  <td class="portlet-form-input-field"><html:text property="msisdn" styleClass="portlet-form-field" style="width:150px"/></td>
	</tr>
	<tr>
	  <td colspan="2" align="center">
	    <button type="submit" class="portlet-form-button">
			<bean:message key="createIndividualMsisdn.button.submit"/>
		</button>
	  </td>
	</tr>
</table>

<p />

</html:form>