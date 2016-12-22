package com.digicel.service.cgw.admin.web.tags.tables;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.util.Constants;

/**
 * Base class for table view. There are abstract methods, which have to be
 * implemented in child classes: <br />
 * init() - its purpose is to check initial conditions for rendering the tag.
 * E.g. if user entered valid data into form. It's return value is boolean. When
 * <code>false</code> is returned, tag will not be rendered. <br />
 * getData() - returns data to be displayed by the tag. <br />
 * getHeader() - returns header of the table in HTML. It should be encapsulated
 * in \
 * <tr\>
 * \
 * </tr\>
 * tags. <br />
 * getRow(Object entry) - returns one row of the table (for given entry) in
 * HTML. It should be encapsulated in \
 * <tr\>
 * \
 * </tr\>
 * tags. <br />
 * getNoRecordsKey() - returns key to search message resources when no record is
 * displayed.
 * 
 * @author halanova
 * 
 */
public abstract class AbstractTable<T> extends TagSupport
{

   private static final long serialVersionUID = 6357061162671287416L;

   protected static Logger log = LoggerFactory.getLogger(AbstractTable.class);

   private String width;

   protected abstract boolean init();

   protected abstract List<T> getData();

   protected abstract String getHeader();

   protected abstract String getRow(T entry);

   protected abstract String getNoRecordsString();

   public String getWidth()
   {
      return width;
   }

   public void setWidth(String width)
   {
      this.width = width;
   }

   private String getHtml(List<T> data)
   {

      StringBuffer buffer = new StringBuffer();

      buffer.append("\n<table cellpadding=\"2\" cellspacing=\"0\"" + (width != null ? " width=\"" + width + "\"" : "") + ">\n");
      buffer.append(getHeader());

      Iterator<T> dataIterator = data.iterator();
      while(dataIterator.hasNext())
      {
         buffer.append(getRow(dataIterator.next()));
      }

      buffer.append("</table>");

      return buffer.toString();
   }

   /**
    * generate tag
    */
   public int doStartTag()
   {
      if(!init()) return SKIP_BODY;

      try
      {
         // load data
         List<T> data = getData();

         if(data == null || data.isEmpty())
         {
            pageContext.getOut().print(getNoRecordsString());
         }
         else
         {
            pageContext.getOut().print(getHtml(data));
         }
      }
      catch(Exception e)
      {
         log.error("Exception: ", e);
      }

      return SKIP_BODY;
   }

   protected String getResourceString(String key)
   {
      MessageResources mres = MessageResources.getMessageResources(Constants.RESOURCES_BUNDLE);
      return mres.getMessage((Locale) pageContext.getSession().getAttribute(Globals.LOCALE_KEY), key);
   }
}