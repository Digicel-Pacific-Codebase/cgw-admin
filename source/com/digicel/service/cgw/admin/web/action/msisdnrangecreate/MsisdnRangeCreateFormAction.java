package com.digicel.service.cgw.admin.web.action.msisdnrangecreate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.exceptions.OperationNotPermittedException;
import com.digicel.service.cgw.admin.util.Constants;
import com.digicel.service.cgw.admin.web.action.AbstractProtectedAction;

/**
 * This class is in charge of checking the role to perform the operation and 
 * forward the user depending on the validation result.
 * 
 * @author CME
 *
 */
public class MsisdnRangeCreateFormAction extends AbstractProtectedAction
{
   private static Logger log = LoggerFactory.getLogger(MsisdnRangeCreateFormAction.class);

   @Override
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String currentUser)
      throws OperationNotPermittedException
   {
      log.trace("Enter Method executeAction params: {}", new Object[]{new Object[]{mapping, form, request, response, currentUser } });
      this.checkRole(request, Constants.RESOURCE_MSISDN_RANGE_CREATE);
      ActionForward result = mapping.findForward("success");
      log.trace("Return Method executeAction. Result: {}", result);
      return result;
   }

}
