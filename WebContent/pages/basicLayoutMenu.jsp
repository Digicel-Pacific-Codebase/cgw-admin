<%@ taglib
	uri="http://portals.apache.org/bridges/struts/tags-portlet-html"
	prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="com.digicel.service.cgw.admin.util.Constants"%>
<%@ taglib uri="/WEB-INF/cgw-admin-tags.tld" prefix="cgw-admin"%>
<div class="portlet-menu" align="center">
	<table cellpadding="0" cellspacing="0" style="text-align: left;">

		<cgw-admin:checkRight resource1="<%=Constants.RESOURCE_MSISDN_RANGE_CREATE%>">
			<tr>
				<td>
					<div class="portlet-menu-item">
						<html:link action="msisdnRangeCreateForm.do" actionURL="true">
							<bean:message key="menu.msisdnRangeCreate" />
						</html:link>
					</div>
				</td>
			</tr>
		</cgw-admin:checkRight>
		
		<cgw-admin:checkRight resource1="<%=Constants.RESOURCE_INDIVIDUAL_MSISDN_CREATE%>">
			<tr>
				<td>
					<div class="portlet-menu-item">
						<html:link action="individualMsisdnCreateForm.do" actionURL="true">
							<bean:message key="menu.individualMsisdnCreate" />
						</html:link>
					</div>
				</td>
			</tr>
		</cgw-admin:checkRight>
		
		<cgw-admin:checkRight resource1="<%=Constants.RESOURCE_MSISDN_RANGE_SEARCH%>">
			<tr>
				<td>
					<div class="portlet-menu-item">
						<html:link action="msisdnRangeSearchForm.do" actionURL="true">
							<bean:message key="menu.msisdnRangeSearch" />
						</html:link>
					</div>
				</td>
			</tr>
		</cgw-admin:checkRight>

	</table>
</div>

<div id="position_fix" class="portlet-content">
	<center>
		<tiles:insert attribute="content" />
	</center>
</div>
