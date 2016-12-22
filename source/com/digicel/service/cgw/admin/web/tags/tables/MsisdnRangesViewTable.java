package com.digicel.service.cgw.admin.web.tags.tables;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.data.beans.MsisdnRangeBean;
import com.digicel.service.cgw.admin.exceptions.ServiceCgwAdminCriticalException;
import com.digicel.service.cgw.admin.logic.ApplicationUserManager;
import com.digicel.service.cgw.admin.logic.msisdnranges.MsisdnRangeManager;
import com.digicel.service.cgw.admin.util.Constants;

/**
 * Table view for MSISDN ranges.
 * 
 * @author CME
 *
 */
public class MsisdnRangesViewTable extends AbstractPageableTable<MsisdnRangeBean>
{
   private static final long serialVersionUID = 9045793514559401859L;

   private static Logger log = LoggerFactory.getLogger(MsisdnRangesViewTable.class);

   private static final MsisdnRangeManager msisdnRangeManager = MsisdnRangeManager.getInstance();

   protected String msisdn;

   protected boolean init()
   {
      log.trace("Enter Method init.");
      boolean result = true;

      defaultOrderColumn = "from_msisdn";

      msisdn = (String) pageContext.getSession().getAttribute(Constants.URL_MSISDN_RANGES_MSISDN);

      log.trace("Return Method init. Result: {}", result);
      return result;
   }

   protected long getData(PagingConfig config, List<MsisdnRangeBean> data) throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method getData params: {}", new Object[]{new Object[]{config, data } });

      long count = 0;

      try
      {
         if(!StringUtils.isEmpty(msisdn))
         {
            data.addAll(msisdnRangeManager.getMsisdnRangesByMsisdn(config, this.msisdn));
            count = msisdnRangeManager.getMsisdnRangesCountByMsisdn(this.msisdn);
         }
         else
         {
            data.addAll(msisdnRangeManager.getAllMsisdnRanges(config));
            count = msisdnRangeManager.getAllMsisdnRangesCount();
         }
      }
      catch(ServiceCgwAdminCriticalException e)
      {
         log.error("Error calling getData. Method params: " + Arrays.toString(new Object[]{config, data }), e);
         throw e;
      }

      log.trace("Return Method getData. Result: {}", count);
      return count;
   }

   protected List<TableColumnBean> getColumns()
   {
      log.trace("Enter Method getColumns.");

      Vector<TableColumnBean> cols = new Vector<TableColumnBean>(5);

      cols.add(new TableColumnBean(getResourceString("msisdnRangeView.label.from"), "from_msisdn", "100px", 1, true));
      cols.add(new TableColumnBean(getResourceString("msisdnRangeView.label.to"), "to_msisdn", "100px", 1, true));

      if(ApplicationUserManager.getInstance().hasRight(pageContext.getRequest(), Constants.RESOURCE_MSISDN_RANGE_DELETE) || ApplicationUserManager.getInstance().hasRight(
         pageContext.getRequest(), Constants.RESOURCE_MSISDN_RANGE_EDIT))
      {
         cols.add(new TableColumnBean(getResourceString("msisdnRangeView.label.action"), "", "", 1, false));
      }
      else
      {
         cols.add(new TableColumnBean("&nbsp", "", "", 1, false));
      }

      log.trace("Return Method getColumns. Result: {}", cols);
      return cols;
   }

   @Override
   protected String getRow(MsisdnRangeBean entry)
   {
      log.trace("Enter Method getRow params: {}", entry);
      String result;

      StringBuffer output = new StringBuffer();

      try
      {
         output.append("<tr><td class=\"row\">").append(entry.getFrom()).append("</td><td class=\"row\">").append(entry.getTo())
            .append("</td><td class=\"row\"><div class=\"tableActions\">");

         boolean prependSeparator = false;

         if(ApplicationUserManager.getInstance().hasRight(pageContext.getRequest(), Constants.RESOURCE_MSISDN_RANGE_EDIT))
         {
            String editActionUrl = urlUtil.getRenderUrl(pageContext, "msisdnRangeEditForm.do?" + Constants.URL_MSISDN_RANGE_ID + "=" + entry.getId());

            output.append("<a href='").append(editActionUrl).append("'>").append(getResourceString("msisdnRangeView.action.edit")).append("</a>");

            prependSeparator = true;
         }
         if(ApplicationUserManager.getInstance().hasRight(pageContext.getRequest(), Constants.RESOURCE_MSISDN_RANGE_DELETE))
         {
            if(prependSeparator)
            {
               output.append("&nbsp;|&nbsp;");
            }

            String deleteActionUrl = urlUtil.getRenderUrl(pageContext, "msisdnRangeDelete.do?" + Constants.URL_MSISDN_RANGE_ID + "=" + entry.getId());

            output.append("<a href=\"javascript:void(0)\" onClick=\"if(confirm('").append(getResourceString("msisdnRangeView.action.delete.question")).append("')) this.href='")
               .append(deleteActionUrl).append("';\">").append(getResourceString("msisdnRangeView.action.delete")).append("</a>");
         }

         output.append("</div></td></tr>");
      }
      catch(Throwable e)
      {
         log.error("Exception:", e);
      }

      result = output.toString();

      log.trace("Return Method getRow. Result: {}", result);
      return result;
   }

   @Override
   protected String getAction()
   {
      log.trace("Enter Method getAction.");

      final String retVal;

      retVal = "msisdnRangeSearchForm.do?" + Constants.URL_MSISDN_RANGES_MSISDN + "=" + msisdn + "&";

      log.trace("Return Method getAction. Result: {}", retVal);
      return retVal;
   }

   @Override
   protected String getNoRecordsKey()
   {
      log.trace("Enter Method getNoRecordsKey.");
      String result = "msisdnRangeView.errors.noRecords";

      log.trace("Return Method getNoRecordsKey. Result: {}", result);
      return result;
   }
}