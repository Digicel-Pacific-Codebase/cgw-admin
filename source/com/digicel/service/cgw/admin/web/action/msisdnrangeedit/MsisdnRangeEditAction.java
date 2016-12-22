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

public class MsisdnRangeEditAction extends AbstractProtectedAction
{
   private static Logger log = LoggerFactory.getLogger(MsisdnRangeEditAction.class);

   @Override
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String currentUser)
      throws OperationNotPermittedException
   {
      log.trace("Enter Method executeAction params: {}", new Object[]{new Object[]{mapping, form, request, response, currentUser } });
      this.checkRole(request, Constants.RESOURCE_MSISDN_RANGE_EDIT);
      ActionForward result = mapping.findForward("error");
      ActionErrors errors = new ActionErrors();

      MsisdnRangeForm myForm = (MsisdnRangeForm) form;
      String id = myForm.getString("id");
      String from = myForm.getString("from");
      String to = myForm.getString("to");

      if(StringUtils.isNotBlank(id))
      {
         if(StringUtils.isNotBlank(from) && StringUtils.isNotBlank(to))
         {
            try
            {
               MsisdnRangeBean msisdnRange = new MsisdnRangeBean();
               msisdnRange.setId(Long.parseLong(id));
               msisdnRange.setFrom(Long.parseLong(from));
               msisdnRange.setTo(Long.parseLong(to));

               MsisdnRangeManager.getInstance().editMsisdnRange(msisdnRange);
               result = mapping.findForward("success");
            }
            catch(NumberFormatException ex)
            {
               log.error("Error calling executeAction. Method params: " + Arrays.toString(new Object[]{mapping, form, request, response, currentUser }), ex);
               errors.add("error.parserange", new ActionMessage("error.editmsisdnrange.parsedata"));
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
            errors.add("error.missingrange", new ActionMessage("error.editmsisdnrange.missingrange"));
         }
      }
      else
      {
         log.error("Error calling executeAction. Method params: " + Arrays.toString(new Object[]{mapping, form, request, response, currentUser }));
         errors.add("error.missingid", new ActionMessage("error.editmsisdnrange.missingid"));
      }

      saveErrors(request, (ActionMessages) errors);

      log.trace("Return Method executeAction. Result: {}", result);
      return result;
   }

}
