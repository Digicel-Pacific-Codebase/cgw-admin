package com.digicel.service.cgw.admin.web.action.msisdncreate;

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

import com.digicel.service.cgw.admin.exceptions.OperationNotPermittedException;
import com.digicel.service.cgw.admin.logic.msisdnranges.MsisdnRangeManager;
import com.digicel.service.cgw.admin.util.Constants;
import com.digicel.service.cgw.admin.web.action.AbstractProtectedAction;
import com.digicel.service.cgw.admin.web.forms.IndividualMsisdnForm;

/**
 * This class is in charge of retrieving the IndividualMsisdnForm and communicating 
 * with the Business Logic layer to add an individual MSISDN in the B/W list. In case 
 * the business layer provides a successful operation the method forwards to the list 
 * of ranges. In case of failure the method redirects to error.
 * 
 * @author CME
 *
 */
public class IndividualMsisdnCreateAction extends AbstractProtectedAction
{
   private static Logger log = LoggerFactory.getLogger(IndividualMsisdnCreateAction.class);

   @Override
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String currentUser)
      throws OperationNotPermittedException
   {
      log.trace("Enter Method executeAction params: {}", new Object[]{new Object[]{mapping, form, request, response, currentUser } });
      this.checkRole(request, Constants.RESOURCE_INDIVIDUAL_MSISDN_CREATE);
      ActionForward result = mapping.findForward("error");
      ActionErrors errors = new ActionErrors();

      IndividualMsisdnForm myForm = (IndividualMsisdnForm) form;
      String msisdn = myForm.getString("msisdn");

      if(StringUtils.isNotBlank(msisdn))
      {
         try
         {
            MsisdnRangeManager.getInstance().insertIndividualMsisdn(msisdn);
            result = mapping.findForward("success");
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
         errors.add("error.missingmsisdn", new ActionMessage("error.createindividualmsisdn.missingmsisdn"));
      }

      saveErrors(request, (ActionMessages) errors);

      log.trace("Return Method executeAction. Result: {}", result);
      return result;
   }

}
