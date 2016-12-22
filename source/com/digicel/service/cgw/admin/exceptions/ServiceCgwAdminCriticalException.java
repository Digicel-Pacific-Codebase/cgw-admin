package com.digicel.service.cgw.admin.exceptions;

/**
 * CGW Admin custom exception
 * 
 * @author CME
 *
 */
public class ServiceCgwAdminCriticalException extends Exception
{

   private static final long serialVersionUID = 7587672507738730973L;

   public ServiceCgwAdminCriticalException()
   {
      super();
   }

   public ServiceCgwAdminCriticalException(String message)
   {
      super(message);
   }

}
