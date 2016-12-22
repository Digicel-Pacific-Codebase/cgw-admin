package com.digicel.service.cgw.admin.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.data.DAAccess;
import com.digicel.service.cgw.admin.data.DAAccessFactory;
import com.digicel.service.cgw.admin.util.Constants;

/**
 * Performs actions when the server starts and ends
 * 
 * @author CME
 * 
 */
public class ServletListener implements ServletContextListener
{
   private static final Logger log = LoggerFactory.getLogger(ServletListener.class);

   /**
    * Instantiates the database access.
    */
   public void contextInitialized(ServletContextEvent arg0)
   {
      // Call the configuration singleton so that it can populate cache

      DAAccess access;
      try
      {
         access = new DAAccess(Constants.DB_CONFIG_FILE);
         DAAccessFactory.setDaAccess(access);
      }
      catch(Exception e)
      {
         log.error("Error calling contextInitialized.", e);
      }
   }

   public void contextDestroyed(ServletContextEvent arg0)
   {
      // This will be called when Tomcat closes.

      // TODO something here

   }
}
