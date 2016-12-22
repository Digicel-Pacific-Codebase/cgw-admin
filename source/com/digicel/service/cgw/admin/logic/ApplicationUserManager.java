package com.digicel.service.cgw.admin.logic;

import java.security.Principal;

import javax.portlet.PortletRequest;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.util.Constants;

/**
 * This class is in charge of the retrieval of the user connected and the access
 * authorization verification
 * 
 * @author CME
 * 
 */
public class ApplicationUserManager
{
   private static Logger log = LoggerFactory.getLogger(ApplicationUserManager.class);

   private static final ApplicationUserManager instance = new ApplicationUserManager();

   private ApplicationUserManager()
   {
   }

   public static ApplicationUserManager getInstance()
   {
      return instance;
   }

   /**
    * Retrieves the current user name based on the request.
    * 
    * @param request
    * @return
    */
   public String getCurrentUserName(ServletRequest request)
   {
      log.trace("Enter Method getCurrentUserName params: {}", request);

      String result;
      PortletRequest portletRequest = (PortletRequest) request.getAttribute(Constants.PORTLET_REQUEST_ATTRIB);
      Principal principal = portletRequest.getUserPrincipal();
      result = principal.getName();

      log.trace("Return Method getCurrentUserName. Result: {} ", result);
      return result;
   }

   /**
    * Checks if currently logged user has right for given resource.
    * 
    * @param request
    *           {@link HttpServletRequest} instance
    * @param resource
    *           resource name to check
    * @return <code>true</code> if user has right for resource,
    *         <code>false</code> otherwise.
    */
   public boolean hasRight(ServletRequest request, String resource)
   {
      log.trace("Enter Method hasRight params: {}", new Object[]{new Object[]{request, resource } });

      final boolean retVal;

      if((resource == null) || ("".equals(resource)))
      {
         retVal = false;
      }
      else
      {
         PortletRequest portletRequest = (PortletRequest) request.getAttribute(Constants.PORTLET_REQUEST_ATTRIB);
         retVal = portletRequest.isUserInRole(resource);
      }
      log.trace("Return Method hasRight. Result: {} ", retVal);
      return retVal;
   }

   /**
    * Checks if currently logged user has right for given resources.
    * 
    * @param request
    *           {@link HttpServletRequest} instance
    * @param resources
    *           resource names to check
    * @param all
    *           <code>true</code> - user has to have rights for ALL resources to
    *           return <code>true</code>, <code>false</code> - user needs to
    *           have right to any of resources to return <code>true</code>.
    * @return <code>true</code> if user has right for resources,
    *         <code>false</code> otherwise.
    */
   public boolean hasRight(ServletRequest request, String[] resources, boolean all)
   {
      log.trace("Enter Method hasRight params: {}", new Object[]{new Object[]{request, resources, all } });

      boolean result = false;
      PortletRequest portletRequest = (PortletRequest) request.getAttribute(Constants.PORTLET_REQUEST_ATTRIB);

      boolean hasRightForAll = true;
      for(int i = 0; i < resources.length; i++)
      {
         if(portletRequest.isUserInRole(resources[i]) && !all)
         {
            result = true;
            break;
         }

         if(!portletRequest.isUserInRole(resources[i]) && all)
         {
            hasRightForAll = false;
            break;
         }
      }
      if(result == false)
      {
         if(all)
         {
            result = hasRightForAll;
         }
         else
         {
            result = false;
         }
      }
      log.trace("Return Method hasRight. Result: {} ", result);
      return result;
   }

}