package com.digicel.service.cgw.admin.data;

import java.io.Reader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.data.beans.MsisdnRangeBean;
import com.digicel.service.cgw.admin.web.tags.tables.PagingConfig;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * This class in in charge of the communication with the database.
 * 
 * @author CME
 * 
 */
public class DAAccess
{
   private static final Logger log = LoggerFactory.getLogger(DAAccess.class);
   private SqlMapClient sqlMap;

   /**
    * Begins transaction.
    * 
    * @throws SQLException
    */
   private void begin() throws SQLException
   {
      log.trace("Enter Method begin");
      sqlMap.startTransaction();
      log.trace("Return Method begin ");
   }

   /**
    * Commits transaction.
    * 
    * @throws SQLException
    */
   private void commit() throws SQLException
   {
      log.trace("Enter Method commit.");
      sqlMap.commitTransaction();
      sqlMap.endTransaction();
      log.trace("Return Method commit ");
   }

   /**
    * Rollbacks transaction.
    */
   private void rollback()
   {
      log.trace("Enter Method rollback .");
      try
      {
         sqlMap.endTransaction();
      }
      catch(SQLException e)
      {
         log.error("DB Error. Error calling rollback.", e);
      }
      log.trace("Return Method rollback ");
   }

   /**
    * Closes connection.
    */
   private void closeConnection()
   {
      log.trace("Enter Method closeConnection ");
      try
      {
         log.debug("going to close connection");
         sqlMap.endTransaction();
      }
      catch(SQLException e)
      {
         log.error("DB Error. Error calling closeConnection.", e);

      }
      log.trace("Return Method closeConnection ");
   }

   /**
    * Instantiates the SQL Map Client based on a configuration file.
    * 
    * @param ibatisResourceFile
    *           db connection configuration file
    * @throws Exception
    */
   public DAAccess(String ibatisResourceFile) throws Exception
   {
      log.trace("Enter Method DAAccess params: {}", ibatisResourceFile);

      Reader reader = Resources.getResourceAsReader(ibatisResourceFile);
      sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);

      log.trace("Return Method DAAccess.");
   }

   /**
    * Retrieves all the MSISND ranges.
    * 
    * @param config
    *           paging parameters
    * @return a list of all the MSISND ranges.
    * @throws SQLException
    */
   @SuppressWarnings("unchecked")
   public List<MsisdnRangeBean> getAllMsisdnRanges(PagingConfig config) throws SQLException
   {
      log.trace("Enter Method getAllMsisdnRanges params: {}", config);
      List<MsisdnRangeBean> result = null;

      try
      {
         result = (List<MsisdnRangeBean>) getList("getAllMsisdnRanges", config);
      }
      catch(SQLException ex)
      {
         log.error("Error calling getAllMsisdnRanges.", ex);
         throw ex;
      }

      log.trace("Return Method getAllMsisdnRanges. Result: {}", result);
      return result;
   }

   /**
    * Retrieves the MSISDN ranges based on the MSISDN
    * 
    * @param config
    *           paging parameters
    * @param msisdn
    *           of the user
    * @return a list of MSISDN ranges
    * @throws SQLException
    */
   @SuppressWarnings("unchecked")
   public List<MsisdnRangeBean> getMsisdnRangesByMsisdn(PagingConfig config, Long msisdn) throws SQLException
   {
      log.trace("Enter Method getMsisdnRangesByMsisdn params: {}", new Object[]{new Object[]{config, msisdn } });

      List<MsisdnRangeBean> result = null;

      try
      {
         config.setParam(msisdn);
         result = (List<MsisdnRangeBean>) getList("getMsisdnRangesByMsisdn", config);
      }
      catch(SQLException ex)
      {
         log.error("Error calling getMsisdnRangesByMsisdn. Method params: " + Arrays.toString(new Object[]{config, msisdn }), ex);
         throw ex;
      }

      log.trace("Return Method getMsisdnRangesByMsisdn. Result: {}", result);
      return result;
   }

   /**
    * Retrieves the MSISDN range for the received ID
    * 
    * @param id
    *           of the MSISDN range
    * @return a MSISDN ranges
    * @throws SQLException
    */
   public MsisdnRangeBean getMsisdnRangeById(Long id) throws SQLException
   {
      log.trace("Enter Method getMsisdnRangeById params: {}", id);

      MsisdnRangeBean result = null;

      try
      {
         result = (MsisdnRangeBean) get("getMsisdnRangeById", id);
      }
      catch(SQLException ex)
      {
         log.error("Error calling getMsisdnRangeById. Method params: " + id, ex);
         throw ex;
      }

      log.trace("Return Method getMsisdnRangeById. Result: {}", result);
      return result;
   }

   /**
    * Inserts a MSISDN range in the database
    * 
    * @param msisdnRange MSISDN range data
    * @throws SQLException
    */
   public void insertMsisdnRange(MsisdnRangeBean msisdnRange) throws SQLException
   {
      log.trace("Enter Method insertMsisdnRange params: {}", msisdnRange);

      begin();
      try
      {
         sqlMap.insert("insertMsisdnRange", msisdnRange);
         commit();
      }
      catch(SQLException ex)
      {
         log.error("Error calling insertMsisdnRange. Method params: " + msisdnRange, ex);
         rollback();
         throw ex;
      }
      finally
      {
         closeConnection();
      }

      log.trace("Return Method insertMsisdnRange");
   }

   /**
    * Edits a MSISND range
    * 
    * @param msisdnRange with the id of the record to modify and the changes
    * @throws SQLException
    */
   public void editMsisdnRange(MsisdnRangeBean msisdnRange) throws SQLException
   {
      log.trace("Enter Method editMsisdnRange params: {}", msisdnRange);

      begin();
      try
      {
         sqlMap.update("editMsisdnRange", msisdnRange);
         commit();
      }
      catch(SQLException ex)
      {
         log.error("Error calling editMsisdnRange. Method params: " + msisdnRange, ex);
         rollback();
         throw ex;
      }
      finally
      {
         closeConnection();
      }

      log.trace("Return Method editMsisdnRange");
   }

   /**
    * Deletes a MSISDN range
    * 
    * @param id of the MSISDN range to delete
    * @throws SQLException
    */
   public void deleteMsisdnRange(long id) throws SQLException
   {
      log.trace("Enter Method deleteMsisdnRange params: {}", id);

      begin();
      try
      {
         sqlMap.delete("deleteMsisdnRange", id);
         commit();
      }
      catch(SQLException ex)
      {
         log.error("Error calling deleteMsisdnRange. Method params: " + id, ex);
         rollback();
         throw ex;
      }
      finally
      {
         closeConnection();
      }

      log.trace("Return Method deleteMsisdnRange");
   }

   /**
    * returns count of MSISDN Ranges by MSISDN
    * 
    * @return count of MSISDN Ranges
    * @throws SQLException
    */
   public long getMsisdnRangeCountByMsisdn(long msisdn) throws SQLException
   {
      log.trace("Enter Method getMsisdnRangeCountByMsisdn params: {}", msisdn);
      Long result = 0l;

      try
      {
         result = ((Long) get("getMsisdnRangeCountByMsisdn", msisdn)).longValue();
      }
      catch(SQLException ex)
      {
         log.error("Error calling getMsisdnRangeCountByMsisdn. Method params: " + msisdn, ex);
         throw ex;
      }

      log.trace("Return Method getMsisdnRangeCountByMsisdn. Result: {}", result);
      return result;
   }

   /**
    * returns count of all the MSISND ranges
    * 
    * @return count of MSISDN ranges
    * @throws SQLException
    */
   public long getAllMsisdnRangesCount() throws SQLException
   {
      log.trace("Enter Method getAllMsisdnRangesCount");
      Long result = 0l;

      try
      {
         result = ((Long) sqlMap.queryForObject("getAllMsisdnRangesCount")).longValue();
      }
      catch(SQLException ex)
      {
         log.error("Error calling getAllMsisdnRangesCount.", ex);
         throw ex;
      }

      log.trace("Return Method getAllMsisdnRangesCount. Result: {}", result);
      return result;
   }

   /**
    * Gets all rows from DB into List object
    * 
    * @param sql
    *           - query id
    * @param parameters
    *           - passed parameters object
    * @return list
    */
   @SuppressWarnings({"rawtypes" })
   private List getList(String sql, Object parameters) throws SQLException
   {
      return sqlMap.queryForList(sql, parameters);
   }

   /**
    * Get one row from DB defined by id
    * 
    * @param sql
    *           - query id
    * @param parameters
    *           - object of parameters
    * @return Object
    */
   private Object get(String sql, Object parameters) throws SQLException
   {
      return sqlMap.queryForObject(sql, parameters);
   }

}
