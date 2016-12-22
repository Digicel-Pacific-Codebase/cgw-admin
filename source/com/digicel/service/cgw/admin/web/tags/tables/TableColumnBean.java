package com.digicel.service.cgw.admin.web.tags.tables;

/**
 * Model of the column for the abstract pageable table.
 * 
 * @author mazanik
 */
public class TableColumnBean
{

   private final String name;
   private final String physicalName;
   private final String width;
   private final int colspan;
   private final boolean sortable;

   public TableColumnBean(String name, String physicalName, String width, int colspan, boolean sortable)
   {
      this.name = name;
      this.physicalName = physicalName;
      this.width = width;
      this.colspan = colspan;
      this.sortable = sortable;
   }

   public String getName()
   {
      return this.name;
   }

   public String getWidth()
   {
      return this.width;
   }

   public String getPhysicalName()
   {
      return this.physicalName;
   }

   public boolean isSortable()
   {
      return this.sortable;
   }

   public int getColspan()
   {
      return this.colspan;
   }

}
