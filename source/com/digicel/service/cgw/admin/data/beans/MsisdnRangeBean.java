package com.digicel.service.cgw.admin.data.beans;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * This class represents a range of MSISDN for a white or blacklist. It contains 
 * two long attributes to define the lower and upper sides of the range. In case 
 * of unique numbers the database uses a range of one number to store it in the 
 * same table. It worth to say the class also has an id attribute to match it with 
 * the id column in the database table.
 * 
 * @author CME
 *
 */
public class MsisdnRangeBean
{
   private long id;
   private long from;
   private long to;

   /**
    * @return the id
    */
   public long getId()
   {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(long id)
   {
      this.id = id;
   }

   /**
    * @return the from
    */
   public long getFrom()
   {
      return from;
   }

   /**
    * @param from the from to set
    */
   public void setFrom(long from)
   {
      this.from = from;
   }

   /**
    * @return the to
    */
   public long getTo()
   {
      return to;
   }

   /**
    * @param to the to to set
    */
   public void setTo(long to)
   {
      this.to = to;
   }

   @Override
   public String toString()
   {
      return ReflectionToStringBuilder.toString(this);
   }
}
