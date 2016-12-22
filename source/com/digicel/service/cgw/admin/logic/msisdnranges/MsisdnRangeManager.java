package com.digicel.service.cgw.admin.logic.msisdnranges;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digicel.service.cgw.admin.data.DAAccessFactory;
import com.digicel.service.cgw.admin.data.beans.MsisdnRangeBean;
import com.digicel.service.cgw.admin.exceptions.ServiceCgwAdminCriticalException;
import com.digicel.service.cgw.admin.web.tags.tables.PagingConfig;

/**
 * This class in charge of process the information received from the Action classes, 
 * validate it, access the database module and return a result if corresponds.
 * 
 * @author CME
 *
 */
public class MsisdnRangeManager
{
   private static Logger log = LoggerFactory.getLogger(MsisdnRangeManager.class);

   private static MsisdnRangeManager instance = new MsisdnRangeManager();

   private MsisdnRangeManager()
   {
   }

   public static MsisdnRangeManager getInstance()
   {
      return instance;
   }

   /**
    * Communicates with the DAAccess instance and retrieves the list of MSISDN ranges
    * based on a MSISDN
    * 
    * @param config
    * @param msisdn
    * @return
    * @throws ServiceCgwAdminCriticalException
    */
   public List<MsisdnRangeBean> getMsisdnRangesByMsisdn(PagingConfig config, String msisdn) throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method getMsisdnRangesByMsisdn params: {}", new Object[]{new Object[]{config, msisdn } });
      List<MsisdnRangeBean> result = null;

      try
      {
         result = DAAccessFactory.getDaAccess().getMsisdnRangesByMsisdn(config, Long.parseLong(msisdn));
      }
      catch(NumberFormatException e)
      {
         log.error("Error calling getMsisdnRangesByMsisdn. Method params: " + Arrays.toString(new Object[]{config, msisdn }), e);
         throw new ServiceCgwAdminCriticalException("Error getting the list of MSISDN ranges. Invalid msisdn: " + msisdn);
      }
      catch(SQLException e)
      {
         log.error("Error calling getMsisdnRangesByMsisdn. Method params: " + Arrays.toString(new Object[]{config, msisdn }), e);
         throw new ServiceCgwAdminCriticalException("Error getting the list of MSISDN ranges with msisdn: " + msisdn);
      }

      log.trace("Return Method getMsisdnRangesByMsisdn. Result: {}", result);
      return result;
   }

   /**
    * Communicates with the DAAccess instance and retrieves the count for the list of MSISDN ranges
    * based on a MSISDN
    * 
    * @param msisdn
    * @return
    * @throws ServiceCgwAdminCriticalException
    */
   public long getMsisdnRangesCountByMsisdn(String msisdn) throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method getMsisdnRangesCountByMsisdn params: {}", msisdn);
      long result = 0;

      try
      {
         result = DAAccessFactory.getDaAccess().getMsisdnRangeCountByMsisdn(Long.parseLong(msisdn));
      }
      catch(NumberFormatException e)
      {
         log.error("Error calling getMsisdnRangesCountByMsisdn. Method params: " + msisdn, e);
         throw new ServiceCgwAdminCriticalException("Error getting the count for the list of MSISDN ranges. Invalid msisdn: " + msisdn);
      }
      catch(SQLException e)
      {
         log.error("Error calling getMsisdnRangesCountByMsisdn. Method params: " + msisdn, e);
         throw new ServiceCgwAdminCriticalException("Error getting the count for the list of MSISDN ranges with msisdn: " + msisdn);
      }

      log.trace("Return Method getMsisdnRangesCountByMsisdn. Result: {}", result);
      return result;
   }

   /**
    * Communicates with the DAAccess instance and retrieves the list of MSISDN ranges
    * 
    * @param config
    * @return
    * @throws ServiceCgwAdminCriticalException
    */
   public List<MsisdnRangeBean> getAllMsisdnRanges(PagingConfig config) throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method getAllMsisdnRanges params: {}", config);
      List<MsisdnRangeBean> result = null;

      try
      {
         result = DAAccessFactory.getDaAccess().getAllMsisdnRanges(config);
      }
      catch(SQLException e)
      {
         log.error("Error calling getAllMsisdnRanges. Method params: " + config, e);
         throw new ServiceCgwAdminCriticalException("Error getting the list of MSISDN ranges.");
      }

      log.trace("Return Method getAllMsisdnRanges. Result: {}", result);
      return result;
   }

   /**
    * Communicates with the DAAccess instance and retrieves the count for the list of MSISDN ranges
    * 
    * @return
    * @throws ServiceCgwAdminCriticalException
    */
   public long getAllMsisdnRangesCount() throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method getAllMsisdnRangesCount.");
      long result = 0;

      try
      {
         result = DAAccessFactory.getDaAccess().getAllMsisdnRangesCount();
      }
      catch(NumberFormatException e)
      {
         log.error("Error calling getAllMsisdnRangesCount.", e);
         throw new ServiceCgwAdminCriticalException("Error getting the count for the list of MSISDN ranges.");
      }
      catch(SQLException e)
      {
         log.error("Error calling getAllMsisdnRangesCount.", e);
         throw new ServiceCgwAdminCriticalException("Error getting the count for the list of MSISDN ranges.");
      }

      log.trace("Return Method getAllMsisdnRangesCount. Result: {}", result);
      return result;
   }

   /**
    * It retrieves a MSISDN Range with the ID provided as parameter.
    * 
    * @param id
    * @return
    * @throws ServiceCgwAdminCriticalException
    */
   public MsisdnRangeBean getMsisdnRangeById(Long id) throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method getMsisdnRangeById params: {}", id);
      MsisdnRangeBean result = null;

      try
      {
         result = DAAccessFactory.getDaAccess().getMsisdnRangeById(id);
      }
      catch(SQLException e)
      {
         log.error("Error calling getMsisdnRangeById. Method params: " + id, e);
         throw new ServiceCgwAdminCriticalException("Error getting the MSISDN range with the id: " + id);
      }

      log.trace("Return Method getMsisdnRangeById. Result: {}", result);
      return result;
   }

   /**
    * This method communicates with the database layer to adds a MSISDN range to the B/W list
    * 
    * @param msisdnRange
    * @throws ServiceCgwAdminCriticalException
    */
   public void insertMsisdnRange(MsisdnRangeBean msisdnRange) throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method insertMsisdnRange params: {}", msisdnRange);

      try
      {
         DAAccessFactory.getDaAccess().insertMsisdnRange(msisdnRange);
      }
      catch(SQLException e)
      {
         log.error("Error calling insertMsisdnRange. Method params: " + msisdnRange, e);
         throw new ServiceCgwAdminCriticalException("Error creating the MSISDN range with the data: " + msisdnRange);
      }

      log.trace("Return Method insertMsisdnRange.");
   }

   /**
    * This method communicates with the database layer to edit an existing range of the B/W list.
    * 
    * @param msisdnRange
    * @throws ServiceCgwAdminCriticalException
    */
   public void editMsisdnRange(MsisdnRangeBean msisdnRange) throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method editMsisdnRange params: {}", msisdnRange);

      try
      {
         DAAccessFactory.getDaAccess().editMsisdnRange(msisdnRange);
      }
      catch(SQLException e)
      {
         log.error("Error calling editMsisdnRange. Method params: " + msisdnRange, e);
         throw new ServiceCgwAdminCriticalException("Error editing the MSISDN range with the data: " + msisdnRange);
      }

      log.trace("Return Method editMsisdnRange.");
   }

   /**
    * This method communicates with the database layer to remove a MSISDN range from the B/W list
    * 
    * @param id
    * @throws ServiceCgwAdminCriticalException
    */
   public void deleteMsisdnRange(long id) throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method deleteMsisdnRange params: {}", id);

      try
      {
         DAAccessFactory.getDaAccess().deleteMsisdnRange(id);
      }
      catch(SQLException e)
      {
         log.error("Error calling editMsisdnRange. Method params: " + id, e);
         throw new ServiceCgwAdminCriticalException("Error deleting the MSISDN range with id: " + id);
      }

      log.trace("Return Method deleteMsisdnRange.");
   }

   /**
    * This method creates a MsisdnRangeBean with the from and to attributes with the msisdn received 
    * and communicates with the database layer to add it to the B/W list.
    * 
    * @param msisdn
    * @throws ServiceCgwAdminCriticalException
    */
   public void insertIndividualMsisdn(String msisdn) throws ServiceCgwAdminCriticalException
   {
      log.trace("Enter Method insertIndividualMsisdn params: {}", msisdn);

      try
      {
         long msisdnValue = Long.parseLong(msisdn);

         MsisdnRangeBean msisdnRange = new MsisdnRangeBean();
         msisdnRange.setFrom(msisdnValue);
         msisdnRange.setTo(msisdnValue);

         DAAccessFactory.getDaAccess().insertMsisdnRange(msisdnRange);
      }
      catch(NumberFormatException e)
      {
         log.error("Error calling insertIndividualMsisdn. Method params: " + msisdn, e);
         throw new ServiceCgwAdminCriticalException("Error creating the MSISDN range with msisdn: " + msisdn);
      }
      catch(SQLException e)
      {
         log.error("Error calling insertIndividualMsisdn. Method params: " + msisdn, e);
         throw new ServiceCgwAdminCriticalException("Error creating the MSISDN range with msisdn: " + msisdn);
      }

      log.trace("Return Method insertIndividualMsisdn.");
   }

}
