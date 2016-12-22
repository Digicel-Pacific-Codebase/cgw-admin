package com.digicel.service.cgw.admin.util;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.jetspeed.PortalReservedParameters;
import org.apache.jetspeed.request.RequestContext;
import org.apache.portals.bridges.struts.StrutsPortlet;
import org.apache.portals.bridges.struts.StrutsPortletURL;
import org.apache.portals.bridges.struts.config.PortletURLTypes;
import org.apache.portals.bridges.struts.config.StrutsPortletConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class in charge of the URL management.
 * 
 * @author CME
 *
 */
public class UrlUtil
{

   private static final Logger log = LoggerFactory.getLogger(UrlUtil.class);

   private static final UrlUtil instance = new UrlUtil();

   /**
    * Returns {@link UrlUtil} instance.
    * 
    * @return
    */
   public static UrlUtil getInstance()
   {
      log.trace("Enter Method getInstance.");
      log.trace("Return Method getInstance. Result: {}", instance);
      return instance;
   }

   private UrlUtil()
   {
   }

   /**
    * Returns resolved action URL.
    * 
    * @param pageContext
    *           page context
    * @param url
    *           URL to resolve
    * @return resolved action URL.
    */
   public String getActionUrl(PageContext pageContext, String url)
   {
      log.trace("Enter Method getActionUrl params: {}", new Object[]{new Object[]{pageContext, url } });
      String result = getURL(pageContext, url, PortletURLTypes.URLType.ACTION);
      log.trace("Return Method getActionUrl. Result: {}", result);
      return result;
   }

   /**
    * Returns resolved render URL.
    * 
    * @param pageContext
    *           page context
    * @param url
    *           URL to resolve
    * @return resolved render URL.
    */
   public String getRenderUrl(PageContext pageContext, String url)
   {
      log.trace("Enter Method getRenderUrl params: {}", new Object[]{new Object[]{pageContext, url } });
      String result = getURL(pageContext, url, PortletURLTypes.URLType.RENDER);
      log.trace("Return Method getRenderUrl. Result: {}", result);
      return result;
   }

   /**
    * Returns resolved resource URL.
    * 
    * @param pageContext
    *           page context
    * @param url
    *           URL to resolve
    * @return resolved resource URL.
    */
   public String getResourceUrl(PageContext pageContext, String url)
   {
      log.trace("Enter Method getResourceUrl params: {}", new Object[]{new Object[]{pageContext, url } });
      String result = getURL(pageContext, url, PortletURLTypes.URLType.RESOURCE);
      log.trace("Return Method getResourceUrl. Result: {}", result);
      return result;
   }

   /**
    * Returns resolved URL. URL type will be determined based on
    * struts-portlet-config.xml file.
    * 
    * @param pageContext
    *           page context
    * @param url
    *           URL to resolve
    * @return resolved URL. URL type will be determined based on
    *         struts-portlet-config.xml file.
    */
   public String getUrl(PageContext pageContext, String url)
   {
      log.trace("Enter Method getUrl params: {}", new Object[]{new Object[]{pageContext, url } });
      String result = getURL(pageContext, url, null);
      log.trace("Return Method getUrl. Result: {}", result);
      return result;
   }

   /**
    * Resolves a, possibly relative, url to a context relative one for use
    * within a Portlet context.
    * <p>
    * The url parameter may contain relative (../) elements.
    * </p>
    * 
    * @param pageContext
    *           the JSP pageContext
    * @param url
    *           the url to resolve
    * @return the resolved url
    */
   private String getContextRelativeURL(PageContext pageContext, String url, boolean addContextPath)
   {
      log.trace("Enter Method getContextRelativeURL params: {}", new Object[]{new Object[]{pageContext, url, addContextPath } });

      if(!url.startsWith("/"))
      {
         String newUrl = url;
         String currentPath = (String) pageContext.getRequest().getAttribute(StrutsPortlet.PAGE_URL);
         if(addContextPath)
         {
            currentPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath() + currentPath;
         }
         if(addContextPath || currentPath.length() > 1)// keep "/"
         {
            currentPath = currentPath.substring(0, currentPath.lastIndexOf('/'));
         }
         if(currentPath.length() == 0)
         {
            currentPath = "/";
         }
         while(currentPath.length() > 0)
         {
            if(newUrl.startsWith("../"))
            {
               currentPath = currentPath.substring(0, currentPath.lastIndexOf('/'));
               newUrl = newUrl.substring(3);
            }
            else
            {
               break;
            }
         }
         if(currentPath.length() > 1)
         {
            url = currentPath + "/" + newUrl;
         }
         else
         {
            url = "/" + newUrl;
         }
      }

      log.trace("Return Method getContextRelativeURL. Result: {}", url);
      return url;
   }

   /**
    * Creates an action or render PortletURL, or a ResourceURL.
    * <p>
    * The url parameter is first
    * {@link #getContextRelativeURL(PageContext, String) resolved} to an context
    * relative url.<br/>
    * Then, a prefixed contextPath is removed from the resulting url.<br/>
    * If the type parameter is specified (not null), the type of url created is
    * based on its value.<br/>
    * Otherwise, {@link PortletURLTypes#getType(String)} is used to determine
    * which type of url must be created.
    * </p>
    * 
    * @param pageContext
    *           the JSP pageContext
    * @param url
    *           the url to resolve
    * @param type
    *           indicated which type of url must be created
    * @return an action or render PortletURL, or a ResourceURL
    */
   private String getURL(PageContext pageContext, String url, PortletURLTypes.URLType type)
   {
      log.trace("Enter Method getURL params: {}", new Object[]{new Object[]{pageContext, url, type } });
      String result = null;

      url = getContextRelativeURL(pageContext, url, false);
      String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
      if(url.startsWith(contextPath + "/"))
      {
         url = url.substring(contextPath.length());
      }

      if(type == null)
      {
         StrutsPortletConfig strutsPortletConfig = (StrutsPortletConfig) pageContext.getAttribute(StrutsPortlet.STRUTS_PORTLET_CONFIG, PageContext.APPLICATION_SCOPE);
         type = strutsPortletConfig.getPortletURLTypes().getType(url);
      }

      if(type.equals(PortletURLTypes.URLType.ACTION))
      {
         result = StrutsPortletURL.createActionURL(pageContext.getRequest(), url).toString();
      }
      else
         if(type.equals(PortletURLTypes.URLType.RENDER))
         {
            result = StrutsPortletURL.createRenderURL(pageContext.getRequest(), url).toString();
         }
         else
         // type.equals(PortletURLTypes.URLType.RESOURCE)
         {
            result = contextPath + "/" + url;
            if(url.startsWith("/"))
            {
               result = contextPath + url;
            }
         }

      log.trace("Return Method getURL. Result: {}", result);
      return result;
   }

   public String getAbsoluteUrl(PortletRequest renderRequest, String relativePath)
   {
      log.trace("Enter Method getAbsoluteUrl params: {}", new Object[]{new Object[]{renderRequest, relativePath } });
      String result = null;

      RequestContext requestContext = (RequestContext) renderRequest.getAttribute(PortalReservedParameters.REQUEST_CONTEXT_ATTRIBUTE);
      HttpServletRequest request = requestContext.getRequest();
      StringBuffer path = new StringBuffer();
      if(!requestContext.getPortalURL().isRelativeOnly())
      {
         path.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort());
      }

      result = requestContext.getResponse().encodeURL(path.append(request.getContextPath()).append(request.getServletPath()).append(relativePath).toString());

      log.trace("Return Method getAbsoluteUrl. Result: {}", result);
      return result;
   }
}
