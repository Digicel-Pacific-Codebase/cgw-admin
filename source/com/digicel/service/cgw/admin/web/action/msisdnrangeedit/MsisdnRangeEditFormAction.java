package com.digicel.service.cgw.admin.web.action.msisdnrangeedit;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.data.beans.MsisdnRangeBean;
import com.digicel.service.cgw.admin.exceptions.OperationNotPermittedException;
import com.digicel.service.cgw.admin.logic.msisdnranges.MsisdnRangeManager;
import com.digicel.service.cgw.admin.util.Constants;
import com.digicel.service.cgw.admin.web.action.AbstractProtectedAction;
import com.digicel.service.cgw.admin.web.forms.MsisdnRangeForm;

/**
 * This class is in charge of checking the role to perform the operation, retrieve the id of 
 * the range to edit, communicate with the Business Logic layer to obtain the data of the range, 
 * fill the MsisdnRangeForm with the MsisdnRangeBean data and forward the user depending on the 
 * validation and Business Logic layer result.
 * 
 * @author CME
 *
 */
public class MsisdnRangeEditFormAction extends AbstractProtectedAction
{
   private static Logger log = LoggerFactory.getLogger(MsisdnRangeEditFormAction.class);

   @Override
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String currentUser)
      throws OperationNotPermittedException
   {
      log.trace("Enter Method executeAction params: {}", new Object[]{new Object[]{mapping, form, request, response, currentUser } });
      this.checkRole(request, Constants.RESOURCE_MSISDN_RANGE_EDIT);
      ActionForward result = null;
      ActionErrors errors = new ActionErrors();

      String id = (String) request.getParameter(Constants.URL_MSISDN_RANGE_ID);

      if(StringUtils.isNotBlank(id))
      {
         try
         {
            MsisdnRangeBean rangeBean = MsisdnRangeManager.getInstance().getMsisdnRangeById(Long.parseLong(id));

            MsisdnRangeForm myForm = (MsisdnRangeForm) form;
            myForm.set("id", String.valueOf(rangeBean.getId()));
            myForm.set("from", String.valueOf(rangeBean.getFrom()));
            myForm.set("to", String.valueOf(rangeBean.getTo()));
         }
         catch(Exception ex)
         {
            log.error("Error calling executeAction. Method params: " + Arrays.toString(new Object[]{mapping, form, request, response, currentUser }), ex);
            errors.add("error.db", new ActionMessage("error.general.database"));
         }

      }
      else
      {
         log.error("Error calling executeAction. Method params: " + Arrays.toString(new Object[]{mapping, form, request, response, currentUser }));
         errors.add("error.missingid", new ActionMessage("error.editmsisdnrange.missingid"));
      }

      saveErrors(request, (ActionMessages) errors);
      result = mapping.findForward("success");

      log.trace("Return Method executeAction. Result: {}", result);
      return result;
   }

}
