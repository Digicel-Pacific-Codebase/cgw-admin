package com.digicel.service.cgw.admin.web.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Form class which contains the elements data for the Edit and Insert Msisdn Range forms. 
 * The id, from and to attributes of the form corresponding to the MSISDN range are defined 
 * in the configuration file. It has validation methods to check if the range is correctly defined.
 * 
 * @author CME
 *
 */
public class MsisdnRangeForm extends BaseForm
{
   private static Logger log = LoggerFactory.getLogger(MsisdnRangeForm.class);
   private static final long serialVersionUID = 8700193786306923737L;

   public MsisdnRangeForm()
   {
      super("error.msisdnrange");
   }

   @Override
   public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
   {
      log.trace("Enter Method validate params: {}", new Object[]{new Object[]{mapping, request } });

      ActionErrors errors = new ActionErrors();

      validateMandatory("from", errors);
      validateMandatory("to", errors);

      validateMsisdnFromString("from", errors);
      validateMsisdnFromString("to", errors);

      validateValidMsisdnRangeFromStrings("from", "to", errors);

      log.trace("Return Method validate. Result: {}", errors);
      return errors;
   }

}
