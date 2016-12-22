package com.digicel.service.cgw.admin.data;

/**
 * Factory class that provides/sets the DAAccess instance
 * 
 * @author CME
 * 
 */
public class DAAccessFactory
{
   private static DAAccess daAccess;

   public static DAAccess getDaAccess()
   {
      return daAccess;
   }

   public static void setDaAccess(DAAccess daAccess)
   {
      DAAccessFactory.daAccess = daAccess;
   }

}
