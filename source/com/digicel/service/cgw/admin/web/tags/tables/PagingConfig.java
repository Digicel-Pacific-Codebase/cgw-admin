package com.digicel.service.cgw.admin.web.tags.tables;

import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Contains the different elements involved in the table pagination.
 * 
 * @author CME
 * 
 */
public class PagingConfig
{

   private long recordFrom;
   private long recordTo;
   private long totalRecords;
   private String actionWithAsterisk;
   private String orderColumn;
   private String orderDirection;
   private long param; // parameter to selects
   private String paramString; // String parameter
   private String whereClause; // WHERE clause for selects
   private Map<String, Object> filter;

   public PagingConfig()
   {
   }

   public PagingConfig(PagingConfig config)
   {
      recordFrom = config.getRecordFrom();
      recordTo = config.getRecordTo();
      totalRecords = config.getTotalRecords();
      actionWithAsterisk = config.getActionWithAsterisk();
      orderColumn = config.getOrderColumn();
      orderDirection = config.getOrderDirection();
      filter = config.getFilter();
   }

   public PagingConfig(PagingConfig config, long param)
   {
      recordFrom = config.getRecordFrom();
      recordTo = config.getRecordTo();
      totalRecords = config.getTotalRecords();
      actionWithAsterisk = config.getActionWithAsterisk();
      orderColumn = config.getOrderColumn();
      orderDirection = config.getOrderDirection();
      filter = config.getFilter();
      this.param = param;
   }

   public PagingConfig(PagingConfig config, long param, String whereClause)
   {
      recordFrom = config.getRecordFrom();
      recordTo = config.getRecordTo();
      totalRecords = config.getTotalRecords();
      actionWithAsterisk = config.getActionWithAsterisk();
      orderColumn = config.getOrderColumn();
      orderDirection = config.getOrderDirection();
      filter = config.getFilter();
      this.param = param;
      this.whereClause = whereClause;
   }

   /**
    * @return Returns the action.
    */
   public String getActionWithAsterisk()
   {
      return actionWithAsterisk;
   }

   /**
    * @param action
    *           The action to set.
    */
   public void setActionWithAsterisk(String action)
   {
      this.actionWithAsterisk = action;
   }

   /**
    * @return Returns the recordFrom.
    */
   public long getRecordFrom()
   {
      return recordFrom;
   }

   /**
    * @param recordFrom
    *           The recordFrom to set.
    */
   public void setRecordFrom(long recordFrom)
   {
      this.recordFrom = recordFrom;
   }

   /**
    * @return Returns the recordTo.
    */
   public long getRecordTo()
   {
      return recordTo;
   }

   /**
    * @param recordTo
    *           The recordTo to set.
    */
   public void setRecordTo(long recordTo)
   {
      this.recordTo = recordTo;
   }

   /**
    * @return Returns the totalRecords.
    */
   public long getTotalRecords()
   {
      return totalRecords;
   }

   /**
    * @param totalRecords
    *           The totalRecords to set.
    */
   public void setTotalRecords(long totalRecords)
   {
      this.totalRecords = totalRecords;
   }

   /**
    * @return the orderColumn
    */
   public String getOrderColumn()
   {
      return orderColumn;
   }

   /**
    * @param orderColumn
    *           the orderColumn to set
    */
   public void setOrderColumn(String orderColumn)
   {
      this.orderColumn = orderColumn;
   }

   /**
    * @return the orderDirection
    */
   public String getOrderDirection()
   {
      return orderDirection;
   }

   /**
    * @param orderDirection
    *           the orderDirection to set
    */
   public void setOrderDirection(String orderDirection)
   {
      this.orderDirection = orderDirection;
   }

   public long getParam()
   {
      return param;
   }

   public void setParam(long param)
   {
      this.param = param;
   }

   public String getWhereClause()
   {
      return whereClause;
   }

   public void setWhereClause(String whereClause)
   {
      this.whereClause = whereClause;
   }

   public String getParamString()
   {
      return paramString;
   }

   public void setParamString(String paramString)
   {
      this.paramString = paramString;
   }

   public Map<String, Object> getFilter()
   {
      return filter;
   }

   public void setFilter(Map<String, Object> filter)
   {
      this.filter = filter;
   }

   @Override
   public String toString()
   {
      return ReflectionToStringBuilder.toString(this);
   }
}
