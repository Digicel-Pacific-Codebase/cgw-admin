package com.digicel.service.cgw.admin.web.forms;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.util.Constants;

/**
 * Superclass of the different forms. It provides validation methods the the MSISDNs
 * 
 * @author CME
 *
 */
public class BaseForm extends DynaActionForm
{

   private static Logger log = LoggerFactory.getLogger(BaseForm.class);
   private static final long serialVersionUID = 2524158373517079431L;

   protected String errorKeyPrefix;
   protected String emptySuffix;
   protected String msisdnValueSuffix;
   protected String rangeValueSuffix;
   protected String numberValueSuffix;

   public BaseForm(String errorKeyPrefix, String emptySuffix, String msisdnValueSuffix, String rangeValueSuffix, String numberValueSuffix)
   {
      super();
      this.errorKeyPrefix = errorKeyPrefix;
      this.emptySuffix = emptySuffix;
      this.msisdnValueSuffix = msisdnValueSuffix;
      this.rangeValueSuffix = rangeValueSuffix;
      this.numberValueSuffix = numberValueSuffix;
   }

   public BaseForm(String errorKeyPrefix)
   {
      this(errorKeyPrefix, "empty", "msisdn", "range", "value");
   }

   private boolean isEmpty(String property)
   {
      log.trace("Enter Method isEmpty params: {}", property);
      Object value = get(property);
      boolean result = (value == null || "".equals(value.toString().trim()));
      log.trace("Return Method isEmpty. Result: {}", result);
      return result;
   }

   /**
    * checks if value is empty
    * 
    * @param property
    * @param errors
    */
   protected boolean validateMandatory(String property, ActionErrors errors)
   {
      log.trace("Enter Method validateMandatory params: {}", new Object[]{new Object[]{property, errors } });
      boolean result;
      if(isEmpty(property))
      {
         errors.add(property, new ActionMessage(errorKeyPrefix + "." + property + "." + emptySuffix));
         result = false;
      }
      else
      {
         result = true;
      }
      log.trace("Return Method validateMandatory. Result: {} ", result);
      return result;
   }

   /**
    * checks if numeric value (from String property) is a valid MSISDN (it is number with given min and max length) Checks only non-empty values
    * @param property
    * @param errors
    */
   protected boolean validateMsisdnFromString(String property, ActionErrors errors)
   {
      log.trace("Enter Method validateMsisdnFromString params: {}", new Object[]{new Object[]{property, errors } });

      boolean result = true;
      if(!this.isEmpty(property))
      {
         String value = this.getString(property);
         try
         {
            Long.parseLong(value);

            if(value.length() < Constants.MSISDN_MIN_LENGTH || value.length() > Constants.MSISDN_MAX_LENGTH)
            {
               errors.add(property, new ActionMessage(this.errorKeyPrefix + "." + property + "." + this.msisdnValueSuffix, value));
               result = false;
            }
         }
         catch(NumberFormatException e)
         {
            errors.add(property, new ActionMessage(this.errorKeyPrefix + "." + property + "." + this.numberValueSuffix, value));
            result = false;
         }
      }

      log.trace("Return Method validateMsisdnFromString. Result: {}", result);
      return result;
   }

   /**
    * checks if both numeric values (from String properties) are a valid range of MSISDN. Checks only non-empty values
    * 
    * @param property1
    * @param property2
    * @param errors
    */
   protected boolean validateValidMsisdnRangeFromStrings(String property1, String property2, ActionErrors errors)
   {
      log.trace("Enter Method validateValidMsisdnRangeFromStrings params: {}", new Object[]{new Object[]{property1, property2, errors } });

      boolean result = true;
      if(!this.isEmpty(property1) && !this.isEmpty(property2))
      {
         String value1 = this.getString(property1);
         String value2 = this.getString(property2);
         try
         {
            if(Long.parseLong(value1) > Long.parseLong(value2))
            {
               errors.add(property1 + property2, new ActionMessage(this.errorKeyPrefix + "." + property1 + "." + this.rangeValueSuffix, value1));
               result = false;
            }
         }
         catch(NumberFormatException e)
         {
            result = false;
         }
      }

      log.trace("Return Method validateValidMsisdnRangeFromStrings. Result: {}", result);
      return result;
   }

   /**
    * Checks if numeric value (from String property) is a number. Checks only non-empty values
    * 
    * @param property
    * @param minValue
    *           min value to check.
    * @param errors
    */
   protected boolean validateNumberFromString(String property, ActionErrors errors)
   {
      log.trace("Enter Method validateNumberFromString params: {}", new Object[]{new Object[]{property, errors } });

      boolean result = true;
      if(!isEmpty(property))
      {
         String value = getString(property);
         try
         {
            Long.parseLong(value);
         }
         catch(NumberFormatException e)
         {
            errors.add(property, new ActionMessage(errorKeyPrefix + "." + property + "." + numberValueSuffix, value));
            result = false;
         }
      }

      log.trace("Return Method validateNumberFromString. Result: {} ", result);
      return result;
   }

}
