package com.digicel.service.cgw.admin.exceptions;

/**
 * Exception thrown when the user access is forbidden for the action
 * 
 * @author CME
 * 
 */
public class OperationNotPermittedException extends Exception
{
   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 3157050065408679338L;

   /**
    * @param message
    * @param cause
    */
   public OperationNotPermittedException(String message, Throwable cause)
   {
      super(message, cause);
   }

   /**
    * @param message
    */
   public OperationNotPermittedException(String message)
   {
      super(message);
   }

   public OperationNotPermittedException()
   {
      super();
   }
}
