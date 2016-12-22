package com.digicel.service.cgw.admin.web.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Form class which contains the elements data for the Insert Individual Msisdn form. 
 * The msisdn attribute corresponding to the individual number is defined in the configuration 
 * file. It has validation methods to check if the MSISDN is valid and correctly defined.
 * 
 * @author CME
 *
 */
public class IndividualMsisdnForm extends BaseForm
{
   private static Logger log = LoggerFactory.getLogger(IndividualMsisdnForm.class);
   private static final long serialVersionUID = -7837201696260418037L;

   public IndividualMsisdnForm()
   {
      super("error.individualMsisdn");
   }

   @Override
   public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
   {
      log.trace("Enter Method validate params: {}", new Object[]{new Object[]{mapping, request } });

      ActionErrors errors = new ActionErrors();

      validateMandatory("msisdn", errors);
      validateMsisdnFromString("msisdn", errors);

      log.trace("Return Method validate. Result: {}", errors);
      return errors;
   }
}
