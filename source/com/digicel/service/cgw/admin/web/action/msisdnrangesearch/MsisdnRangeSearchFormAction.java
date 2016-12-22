package com.digicel.service.cgw.admin.web.action.msisdnrangesearch;

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
 * This class is in charge of the parameter retrieval on MSISDN range search.
 * 
 * @author CME
 * 
 */
public class MsisdnRangeSearchFormAction extends AbstractProtectedAction
{
   private static Logger log = LoggerFactory.getLogger(MsisdnRangeSearchFormAction.class);

   /**
    * Retrieves the msisdn parameter from the request and stores it in the
    * session.
    */
   @Override
   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String currentUser)
      throws OperationNotPermittedException
   {
      log.trace("Enter Method executeAction params: {}", new Object[]{new Object[]{mapping, form, request, response, currentUser } });
      this.checkRole(request, Constants.RESOURCE_MSISDN_RANGE_SEARCH);
      ActionForward result = null;

      String msisdn = request.getParameter(Constants.URL_MSISDN_RANGES_MSISDN);

      if(msisdn != null)
      {
         request.getSession().setAttribute(Constants.URL_MSISDN_RANGES_MSISDN, msisdn);
      }

      result = mapping.findForward("success");

      log.trace("Return Method executeAction. Result: {}", result);
      return result;
   }
}
