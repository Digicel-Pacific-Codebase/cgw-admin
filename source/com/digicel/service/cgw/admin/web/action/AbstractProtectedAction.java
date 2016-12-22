package com.digicel.service.cgw.admin.web.action;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.exceptions.OperationNotPermittedException;
import com.digicel.service.cgw.admin.logic.ApplicationUserManager;
import com.digicel.service.cgw.admin.util.Constants;
import com.digicel.service.cgw.admin.web.tags.tables.PagingConfig;

/**
 * This class provides different methods for the access verification to their
 * subclasses
 * 
 * @author CME
 * 
 */
public abstract class AbstractProtectedAction extends Action
{

   private static Logger log = LoggerFactory.getLogger(AbstractProtectedAction.class);

   /**
    * Checks if the user is logged in
    */
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws OperationNotPermittedException
   {
      log.trace("Enter Method execute params: {}", new Object[]{new Object[]{mapping, form, request, response } });
      String currentUser = ApplicationUserManager.getInstance().getCurrentUserName(request);
      ActionForward result = executeAction(mapping, form, request, response, currentUser);
      log.trace("Return Method execute. Result: {} ", result);
      return result;
   }

   protected void checkRole(HttpServletRequest request, String resource) throws OperationNotPermittedException
   {
      log.trace("Enter Method checkRole params: {}", new Object[]{new Object[]{request, resource } });
      if(ApplicationUserManager.getInstance().hasRight(request, resource))
      {
         log.trace("Return Method checkRole. ");
         return;
      }
      PortletRequest portletRequest = (PortletRequest) request.getAttribute(Constants.PORTLET_REQUEST_ATTRIB);
      throw new OperationNotPermittedException(resource + " not permitted for " + portletRequest.getRemoteUser());
   }

   protected void checkRole(HttpServletRequest request, String[] resources) throws OperationNotPermittedException
   {
      log.trace("Enter Method checkRole params: {}", new Object[]{new Object[]{request, resources } });
      if(ApplicationUserManager.getInstance().hasRight(request, resources, false))
      {
         log.trace("Return Method checkRole ");
         return;
      }
      PortletRequest portletRequest = (PortletRequest) request.getAttribute(Constants.PORTLET_REQUEST_ATTRIB);

      throw new OperationNotPermittedException("One of " + resources + " not permitted for " + portletRequest.getRemoteUser());
   }

   /**
    * resets paging configuration in the session uder given attribute
    * 
    * @param request
    * @param attribute
    */
   protected void resetPagingConfig(HttpServletRequest request, String attribute)
   {
      PagingConfig config = (PagingConfig) request.getSession().getAttribute(attribute);
      if(config != null)
      {
         config.setRecordFrom(0);
         config.setRecordTo(0);
      }
   }

   public abstract ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String currentUser)
      throws OperationNotPermittedException;

}
