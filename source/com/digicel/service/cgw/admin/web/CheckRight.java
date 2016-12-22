package com.digicel.service.cgw.admin.web;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.logic.ApplicationUserManager;

/**
 * Class in charge of checking the user access from the pages.
 * 
 * @author CME
 * 
 */
public class CheckRight extends TagSupport
{

   private static final Logger log = LoggerFactory.getLogger(CheckRight.class);
   private static final long serialVersionUID = -3458532429285169457L;

   private String resource1;
   private String resource2;
   private String resource3;
   private String resource4;
   private String resource5;

   /**
    * @return the resource5
    */
   public String getResource5()
   {
      return resource5;
   }

   /**
    * @param resource5
    *           the resource5 to set
    */
   public void setResource5(String resource5)
   {
      this.resource5 = resource5;
   }

   /**
    * @return the resource4
    */
   public String getResource4()
   {
      return resource4;
   }

   /**
    * @param resource4
    *           the resource4 to set
    */
   public void setResource4(String resource4)
   {
      this.resource4 = resource4;
   }

   /**
    * @return the resource2
    */
   public String getResource2()
   {
      return resource2;
   }

   /**
    * @param resource2
    *           the resource2 to set
    */
   public void setResource2(String resource2)
   {
      this.resource2 = resource2;
   }

   /**
    * @return the resource3
    */
   public String getResource3()
   {
      return resource3;
   }

   /**
    * @param resource3
    *           the resource3 to set
    */
   public void setResource3(String resource3)
   {
      this.resource3 = resource3;
   }

   /**
    * @return the resource
    */
   public String getResource1()
   {
      return resource1;
   }

   /**
    * @param resource
    *           the resource to set
    */
   public void setResource1(String resource1)
   {
      this.resource1 = resource1;
   }

   public int doStartTag() throws JspException
   {
      log.trace("Enter Method doStartTag ");
      int result;
      final ApplicationUserManager manager = ApplicationUserManager.getInstance();
      final ServletRequest request = pageContext.getRequest();

      if(manager.hasRight(request, resource1) || manager.hasRight(request, resource2) || manager.hasRight(request, resource3) || manager.hasRight(request, resource4) || manager
         .hasRight(request, resource5))
      {
         result = EVAL_BODY_INCLUDE;
      }
      else
      {
         result = SKIP_BODY;
      }

      log.trace("Return Method doStartTag. Result: {} ", result);
      return result;
   }

}
