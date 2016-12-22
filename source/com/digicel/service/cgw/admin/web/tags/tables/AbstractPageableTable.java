package com.digicel.service.cgw.admin.web.tags.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.exceptions.ServiceCgwAdminCriticalException;
import com.digicel.service.cgw.admin.util.Constants;
import com.digicel.service.cgw.admin.util.UrlUtil;

/**
 * Base class for table views used in web interface and it provides pageable
 * mechanism. It also supports generics. There are abstract methods, which have
 * to be implemented in child classes: <br />
 * init() - its purpose is to check initial conditions for rendering the tag.
 * E.g. if user entered valid data into form. It's return value is boolean. When
 * <code>false</code> is returned, tag will not be rendered. <br />
 * getTotalNumberOfRecords() - returns total number of records to correctly
 * adjust paging. <br />
 * getAction() - returns name of action. It should end with '&' or '?' character
 * - as paging information will be appended to the end. <br />
 * getData(PagingConfig config) - returns data to be displayed by the tag.
 * Paging configuration is supplied as a parameter of the method, it should be
 * used to retrieve correct records from DB. <br />
 * getHeader() - returns header of the table in HTML. It should be encapsulated
 * in \
 * <tr\>
 * \
 * </tr\>
 * tags. <br />
 * getRow(T entry) - returns one row of the table (for given entry) in HTML. It
 * should be encapsulated in \
 * <tr\>
 * \
 * </tr\>
 * tags. <br />
 * getNoRecordsKey() - returns key to search message resources when no record is
 * displayed.
 * 
 * @author hansut
 */
public abstract class AbstractPageableTable<T> extends TagSupport
{

   protected enum NavigatorPosition {
      TOP, BOTTOM, BOTH
   };

   private static final long serialVersionUID = 8196896315217614174L;

   private static final String PAGING_FROM = "from";
   private static final String PAGING_TO = "to";
   private static final String ORDER_DIRECTION = "odir";
   private static final String ORDER_COLUMN = "ocol";
   protected static final String SORT_DIRECTION_ASC = "asc";
   protected static final String SORT_DIRECTION_DESC = "desc";
   private static final String SORT_ORDER_DEFAULT = "1";
   private static final String SORT_DIRECTION_DEFAULT = SORT_DIRECTION_ASC;
   private static final long ROWS_PER_PAGE_DEFAULT = 20;

   protected static final Logger log = LoggerFactory.getLogger(AbstractPageableTable.class);
   private static final MessageResources mres = MessageResources.getMessageResources(Constants.RESOURCES_BUNDLE);

   private List<TableColumnBean> columnList;
   private String configSessionKey;
   private String tableId;
   protected String defaultOrderDirection = SORT_DIRECTION_DEFAULT;
   protected String defaultOrderColumn = SORT_ORDER_DEFAULT;
   protected final long rowsPerPage = ROWS_PER_PAGE_DEFAULT;
   protected String width;
   private Locale locale;

   protected final UrlUtil urlUtil = UrlUtil.getInstance();

   protected abstract boolean init();

   protected abstract String getAction();

   protected abstract long getData(PagingConfig config, List<T> data) throws ServiceCgwAdminCriticalException;

   protected abstract List<TableColumnBean> getColumns();

   protected abstract String getRow(T entry);

   /**
    * @return Resource bundle value from returned key
    */
   protected abstract String getNoRecordsKey();

   protected String getResourceString(String key)
   {
      log.trace("Enter Method getResourceString params: {}", key);
      String result = mres.getMessage(locale, key);

      log.trace("Return Method getResourceString. Result: {}", result);
      return result;
   }

   protected String getResourceString(String key, String param1)
   {
      log.trace("Enter Method getResourceString params: {}", new Object[]{new Object[]{key, param1 } });
      String result = mres.getMessage(locale, key, param1);

      log.trace("Return Method getResourceString. Result: {}", result);
      return result;
   }

   protected String getResourceString(String key, String param1, String param2)
   {
      log.trace("Enter Method getResourceString params: {}", new Object[]{new Object[]{key, param1, param2 } });
      String result = mres.getMessage(locale, key, param1, param2);

      log.trace("Return Method getResourceString. Result: {}", result);
      return result;
   }

   protected String getFooter()
   {
      return null;
   }

   public String getConfigSessionKey()
   {
      return configSessionKey;
   }

   public void setConfigSessionKey(String configSessionKey)
   {
      this.configSessionKey = configSessionKey;
   }

   private String getTableId()
   {
      return this.tableId;
   }

   public void setTableId(String tableId)
   {
      this.tableId = tableId;
   }

   public String getWidth()
   {
      return width;
   }

   public void setWidth(String width)
   {
      this.width = width;
   }

   protected String generateLinkHtml(PagingConfig config, long rowFrom, long rowTo, String name)
   {
      log.trace("Enter Method generateLinkHtml params: {}", new Object[]{new Object[]{config, rowFrom, rowTo, name } });
      String result = null;

      StringBuffer link = new StringBuffer();

      link.append(config.getActionWithAsterisk());
      link.append(PAGING_TO);
      link.append("=");
      link.append(rowTo);
      link.append("&");
      link.append(PAGING_FROM);
      link.append("=");
      link.append(rowFrom);
      link.append("&");
      link.append(ORDER_COLUMN);
      link.append("=");
      link.append(config.getOrderColumn());
      link.append("&");
      link.append(ORDER_DIRECTION);
      link.append("=");
      link.append(config.getOrderDirection());

      String actionUrl = urlUtil.getRenderUrl(pageContext, link.toString());

      result = "<a href=\"" + actionUrl + "\">" + name + "</a>";

      log.trace("Return Method generateLinkHtml. Result: {}", result);
      return result;
   }

   protected String generateSortLink(String colName, PagingConfig config, String columnPhysicalName)
   {
      log.trace("Enter Method generateSortLink params: {}", new Object[]{new Object[]{colName, config, columnPhysicalName } });
      String result = null;

      StringBuffer href = new StringBuffer();

      href.append(config.getActionWithAsterisk());
      href.append(ORDER_COLUMN);
      href.append("=");
      href.append(columnPhysicalName);
      href.append("&");
      href.append(ORDER_DIRECTION);
      href.append("=");

      if(columnPhysicalName.equals(config.getOrderColumn()) && config.getOrderDirection().equals(SORT_DIRECTION_DESC))
      {
         href.append(SORT_DIRECTION_ASC);
      }
      else
      {
         href.append(SORT_DIRECTION_DESC);
      }

      String actionUrl = urlUtil.getRenderUrl(pageContext, href.toString());

      String link = "<a href=\"" + actionUrl + "\">" + colName;
      if(columnPhysicalName.equals(config.getOrderColumn()))
      {
         // this is the column we do order
         if(config.getOrderDirection().equals(SORT_DIRECTION_DESC))
         {
            link = link + getIcon("query.desc.img", "query.icon");
         }
         else
         {
            link = link + getIcon("query.asc.img", "query.icon");
         }
      }

      result = link + "</a>";

      log.trace("Return Method generateSortLink. Result: {}", result);
      return result;
   }

   protected String generateNavigator(PagingConfig config, long rowsPerPage)
   {
      log.trace("Enter Method generateNavigator params: {}", new Object[]{new Object[]{config, rowsPerPage } });
      String result = null;

      StringBuffer buffer = new StringBuffer();

      buffer.append("\n<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\" align=\"center\"><tr><td class=\"portlet-section-footer\" >");
      buffer.append(getResourceString("table.displayingRows").replaceAll("\\{0\\}", String.valueOf(config.getRecordFrom()))
         .replaceAll("\\{1\\}", String.valueOf(config.getRecordTo())).replaceAll("\\{2\\}", String.valueOf(config.getTotalRecords())));

      buffer.append("&nbsp;&nbsp;</td><td class=\"portlet-section-footer\">");

      if(config.getRecordFrom() > 1)
         buffer.append(generateLinkHtml(config, 1, rowsPerPage, getIcon("table.first.img", "table.icon")));
      else
         buffer.append(getResourceString("table.firstDisabled"));

      buffer.append("</td><td class=\"portlet-section-footer\">");

      if(config.getRecordFrom() > 1)
         buffer.append(generateLinkHtml(config, Math.max(0, config.getRecordFrom() - rowsPerPage), config.getRecordFrom() - 1, getIcon("table.prev.img", "table.icon")));
      else
         buffer.append(getResourceString("table.prevDisabled"));

      buffer.append("</td><td class=\"portlet-section-footer\">");

      if(config.getRecordTo() < config.getTotalRecords())
         buffer.append(generateLinkHtml(config, config.getRecordTo() + 1, Math.min(config.getTotalRecords(), config.getRecordTo() + rowsPerPage),
            getIcon("table.next.img", "table.icon")));
      else
         buffer.append(getResourceString("table.nextDisabled"));

      buffer.append("</td><td class=\"portlet-section-footer\">");

      if(config.getRecordTo() < config.getTotalRecords())
      {
         long firstRecord = ((config.getTotalRecords() / rowsPerPage) * rowsPerPage) + 1 - ((config.getTotalRecords() % rowsPerPage == 0) ? rowsPerPage : 0);
         buffer.append(generateLinkHtml(config, firstRecord, config.getTotalRecords(), getIcon("table.last.img", "table.icon")));
      }
      else
         buffer.append(getResourceString("table.lastDisabled"));

      buffer.append("</td></tr></table>");

      result = buffer.toString();
      log.trace("Return Method generateNavigator. Result: {}", result);
      return result;
   }

   protected String getHeader(PagingConfig config)
   {
      log.trace("Enter Method getHeader params: {}", config);
      String result = null;

      StringBuffer buffer = new StringBuffer();

      columnList = getColumns();

      buffer.append("<tr>");
      for(TableColumnBean col : columnList)
      {
         buffer.append("<td class=\"portlet-section-header\"");

         if(col.getWidth() != null && !"".equals(col.getWidth().trim())) buffer.append(" width=\"").append(col.getWidth()).append("\"");

         if(col.getColspan() > 1) buffer.append("\" colspan=\"").append(col.getColspan()).append("\"");

         buffer.append(">");

         if(col.isSortable())
            buffer.append(generateSortLink(col.getName(), config, col.getPhysicalName()));
         else
            buffer.append(col.getName());

         buffer.append("</td>");
      }
      buffer.append("</tr>");

      result = buffer.toString();

      log.trace("Return Method getHeader. Result: {}", result);
      return result;
   }

   protected String getHtml(PagingConfig config, long rowsPerPage, List<T> data, String tableId)
   {
      log.trace("Enter Method getHtml params: {}", new Object[]{new Object[]{config, rowsPerPage, data, tableId } });
      String result = null;
      
      StringBuffer buffer = new StringBuffer();
      String navigator = null;

      buffer.append(getImgTitle());

      if(config != null && config.getTotalRecords() > rowsPerPage)
      {
         navigator = generateNavigator(config, rowsPerPage);
      }

      // if there is no user or paging config in the request, don't use paging
      if((navigator != null) && (NavigatorPosition.TOP.equals(this.getNavigatorPosition()) || (NavigatorPosition.BOTH.equals(this.getNavigatorPosition()))))
      {
         buffer.append(navigator);
         buffer.append("<p />");
      }

      buffer.append("\n<table id=\"").append(tableId).append("\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" align=\"center\"");

      if(width != null && !"".equals(width)) buffer.append(" width=\"").append(width).append("\"");

      buffer.append(">");

      buffer.append(getHeader(config));

      for(T record : data)
         buffer.append(getRow(record));

      buffer.append("\n</table>");

      // if there is no user or paging config in the request, don't use paging
      if((navigator != null) && (NavigatorPosition.BOTTOM.equals(this.getNavigatorPosition()) || (NavigatorPosition.BOTH.equals(this.getNavigatorPosition()))))
      {
         buffer.append("<p />");
         buffer.append(navigator);
      }

      String footer = getFooter();
      if(footer != null) buffer.append(footer);

      result = buffer.toString();

      log.trace("Return Method getHtml. Result: {}", result);
      return result;
   }

   protected String getImgTitle()
   {
      return "";
   }
   
   /**
    * @param message
    * @return
    */
   private String getErrorLabel(String message)
   {
      log.trace("Enter Method getErrorLabel params: {}", message);
      String result = "";
      StringBuilder sb = new StringBuilder();
      
      if(!StringUtils.isEmpty(message))
      {
         sb.append("<label>").append(message).append("</label>");
      }
      
      result = sb.toString();
      
      log.trace("Return Method getErrorLabel. Result: {}", result);
      return result;
   }

   /**
    * generate tag
    */
   @Override
   public int doStartTag()
   {
      log.trace("Enter Method doStartTag.");
      int result = SKIP_BODY;

      if(!init())
      {
         log.trace("Return Method doStartTag. Result: {}", result);
         return result;
      }

      locale = pageContext.getRequest().getLocale();

      try
      {

         String sRowFrom = "";
         String sRowTo = "";
         String orderColumn = null;
         String orderDirection = null;
         long rowFrom = Long.MIN_VALUE;
         long rowTo = Long.MIN_VALUE;

         // process request parameters
         sRowFrom = pageContext.getRequest().getParameter(PAGING_FROM);
         sRowTo = pageContext.getRequest().getParameter(PAGING_TO);
         orderColumn = pageContext.getRequest().getParameter(ORDER_COLUMN);
         orderDirection = pageContext.getRequest().getParameter(ORDER_DIRECTION);

         // get paging config from da session
         HttpSession session = pageContext.getSession();
         PagingConfig sessionConfig = (PagingConfig) session.getAttribute(configSessionKey);
         if(sessionConfig == null)
         {
            sessionConfig = new PagingConfig();
         }

         try
         {
            rowFrom = Long.parseLong(sRowFrom);
         }
         catch(Exception e)
         {
            rowFrom = sessionConfig.getRecordFrom();
         }

         try
         {
            rowTo = Long.parseLong(sRowTo);
         }
         catch(Exception e)
         {
            rowTo = sessionConfig.getRecordTo();
         }

         // initial paging configuration
         if(rowFrom < 1) rowFrom = 1;

         if(rowTo < 1 || rowTo < rowFrom) rowTo = rowFrom + rowsPerPage - 1;

         // check order direction
         if(orderDirection == null || (!orderDirection.equals(SORT_DIRECTION_ASC) && !orderDirection.equals(SORT_DIRECTION_DESC)))
         {
            orderDirection = sessionConfig.getOrderDirection();
         }
         if(orderDirection == null || (!orderDirection.equals(SORT_DIRECTION_ASC) && !orderDirection.equals(SORT_DIRECTION_DESC)))
         {
            orderDirection = defaultOrderDirection;
         }

         // check order column
         if(orderColumn == null)
         {
            if(sessionConfig.getOrderColumn() != null && sessionConfig.getOrderColumn().trim().length() > 0)
            {
               orderColumn = sessionConfig.getOrderColumn();
            }
            else
            {
               orderColumn = defaultOrderColumn;
            }
         }

         // prepare parameter for transactions request
         PagingConfig pConfig = new PagingConfig();
         pConfig.setRecordFrom(rowFrom);
         pConfig.setRecordTo(rowTo);
         pConfig.setActionWithAsterisk(getAction());
         pConfig.setOrderColumn(orderColumn);
         pConfig.setOrderDirection(orderDirection);
         pConfig.setFilter(sessionConfig.getFilter());

         // load data
         List<T> data = new ArrayList<T>();
         
         long totalNumber = 0l;
         
         try
         {
            totalNumber = getData(new PagingConfig(pConfig, sessionConfig.getParam(), sessionConfig.getWhereClause()), data);
            
            if(totalNumber > 0)
            {
               pConfig.setTotalRecords(totalNumber);
               pageContext.getOut().print(getHtml(pConfig, rowsPerPage, data, getTableId()));
            }
            else
            {
               pageContext.getOut().print(getErrorLabel(getResourceString(this.getNoRecordsKey())));
            }
         }
         catch(ServiceCgwAdminCriticalException e)
         {
            pageContext.getOut().print(getErrorLabel(e.getMessage()));
         }

      }
      catch(Exception e)
      {
         log.error("Exception: ", e);
      }

      log.trace("Return Method doStartTag. Result: {}", result);
      return result;
   }

   /**
    * Returns icon HTML code.
    * 
    * @param imageKey
    *           key to resources with image URL
    * @param iconKey
    *           key to reesources with HTML code for icon. Need to contain one
    *           space for image URL.
    * @return
    */
   protected String getIcon(String imageKey, String iconKey)
   {
      log.trace("Enter Method getIcon params: {}", new Object[]{new Object[]{imageKey, iconKey } });
      String result = null;

      String imageUrl = urlUtil.getResourceUrl(pageContext, getResourceString(imageKey));

      result = getResourceString(iconKey).replaceAll("\\{0\\}", imageUrl);
      return result;
   }

   /**
    * Returns icon HTML code.
    * 
    * @param iconKey
    *           key to reesources with HTML code for icon. Need to contain one
    *           space for image URL. imageUrl will be also taken from resources.
    *           Key will be computed as iconKey + ".img".
    * @return
    */
   protected String getIcon(String iconKey)
   {
      log.trace("Enter Method getIcon params: {}", iconKey);
      String result = getIcon(iconKey + ".img", iconKey);
      log.trace("Return Method getIcon. Result: {}", result);
      return result;
   }

   protected NavigatorPosition getNavigatorPosition()
   {
      return NavigatorPosition.BOTH;
   }

}
