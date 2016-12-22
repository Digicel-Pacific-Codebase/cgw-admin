package com.digicel.service.cgw.admin.util;

/**
 * Fixed values used in the application.
 * 
 * @author CME
 * 
 */
public class Constants
{

   public static final String DB_CONFIG_FILE = "cgwAdminMapConfig.xml";
   public static final String RESOURCES_BUNDLE = "WebMessageResources";

   // portlet session attribs
   public static final String PORTLET_REQUEST_ATTRIB = "javax.portlet.request";
   public static final String PORTLET_RESPONSE_ATTRIB = "javax.portlet.response";

   // user rights
   public static final String RESOURCE_MSISDN_RANGE_SEARCH = "CGWADMIN::MSISDN_RANGE_SEARCH";
   public static final String RESOURCE_MSISDN_RANGE_CREATE = "CGWADMIN::MSISDN_RANGE_CREATE";
   public static final String RESOURCE_MSISDN_RANGE_EDIT = "CGWADMIN::MSISDN_RANGE_EDIT";
   public static final String RESOURCE_MSISDN_RANGE_DELETE = "CGWADMIN::MSISDN_RANGE_DELETE";
   public static final String RESOURCE_INDIVIDUAL_MSISDN_CREATE = "CGWADMIN::MSISDN_INDIVIDUAL_MSISDN_CREATE";

   public static final String URL_MSISDN_RANGES_MSISDN = "msisdn";
   public static final String URL_MSISDN_RANGE_ID = "id";

   // session tables
   public static final String SESSION_TABLE_MSISDN_RANGES_VIEW = "DIGICEL.SERVICE.CGW.ADMIN.TABLE_MSISDN_RANGES_VIEW";

   public static final int MSISDN_MIN_LENGTH = 7;
   public static final int MSISDN_MAX_LENGTH = 13;

}
