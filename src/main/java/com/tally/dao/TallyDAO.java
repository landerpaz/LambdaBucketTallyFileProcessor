package com.tally.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

import com.tally.dto.TallyInputDTO;
import com.tally.vo.DayBookMasterVO;
import com.tally.vo.InventoryEntryVO;
import com.tally.vo.LedgerEntryVO;
import com.tally.vo.ProductionSummaryVO;
import com.tally.vo.Receipt;
import com.tally.vo.Sales;
import com.tally.vo.SalesOrder;
import com.tally.vo.SalesSummaryVO;
import com.tally.vo.StockBatchUDF;
import com.tally.vo.StockDetail;
import com.tally.vo.StockMaster;
import com.tally.util.Constants;
import com.tally.util.TallyRequestContext;
import com.tally.util.Utility;

public class TallyDAO implements BaseDAO {
	
	//private final Logger LOG = LoggerFactory.getLogger(TallyDayBookBC.class);
	
	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement = null;
	
	public int getNextValueForReportId() {
		
		int nextVal = 0;
		
		try {
			
			connection = DatabaseManager.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(Constants.DB_GET_TALLY_SUMMARY_REPORT_ID_NEXTVAL);
			resultSet = preparedStatement.executeQuery();
		
			while(resultSet.next()) {
				
				nextVal = resultSet.getInt(Constants.REPORT_ID);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in getting products from DB...");
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return nextVal + 1;
	}
	
	//public Response addTallySummary(TallyRequestContext context) {
	public void addTallySummary(TallyRequestContext context) {
		
		/*Response response = new Response();
		response.setStatus(Constants.RESPONSE_STATUS_SUCCESS);
		response.setStatusMessage(Constants.RESPONSE_MESSAGE_PRODUCT_ADD_SUCCESS);*/
		
		try {
			
			int batchSize = 100;
			int count = 0;
			
			connection = DatabaseManager.getInstance().getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(Constants.DB_ADD_TALLY_SUMMARY);
			
			int parameterIndex = 1;
			for(int index=0; index<context.getKeys().size(); index++) {
				preparedStatement.setInt(parameterIndex++, context.getReportId());
				preparedStatement.setString(parameterIndex++, context.getReportName());
				preparedStatement.setString(parameterIndex++, context.getKeys().get(index));
				preparedStatement.setString(parameterIndex++, context.getValues1().get(index));
				preparedStatement.setString(parameterIndex++, context.getValues2().get(index));
				preparedStatement.setDate(parameterIndex++, Utility.getCurrentdate());
				preparedStatement.setBoolean(parameterIndex++, context.isCheckFlag());
				preparedStatement.addBatch();
				
				parameterIndex = 1;
				count++;
				
				if(count >= batchSize) {
					preparedStatement.executeBatch();
					connection.commit();
					count = 0;
				}
				
			}
			
			if(count > 0) {
				preparedStatement.executeBatch();
				connection.commit();
			}
			
		} catch (Exception e) {
			
			try {
				if(null != connection) {
					connection.rollback();
				}
			} catch (SQLException sqlException) {
				// TODO: handle exception
				System.out.println("Error in connection rollback...");
				sqlException.printStackTrace();
			}
			
			// TODO: handle exception
			System.out.println("Error in adding products in DB...");
			e.printStackTrace();
			
			/*response.setStatus(Constants.RESPONSE_STATUS_FAILED);
			response.setStatusMessage(Constants.RESPONSE_MESSAGE_PRODUCT_ADD_FAILED);*/
		} finally {
			closeResources();
		}
		
		//return response;
	}
		
	//public void addTallyDayBook(TallyInputDTO tallyInputDTO) throws Exception {
	public void addTallyDayBook(TallyInputDTO tallyInputDTO) {
		
		/*Response response = new Response();
		response.setStatus(Constants.RESPONSE_STATUS_SUCCESS);
		response.setStatusMessage(Constants.RESPONSE_MESSAGE_PRODUCT_ADD_SUCCESS);*/
		
		PreparedStatement ledgerPreparedStatement = null;
		PreparedStatement inventoryPreparedStatement = null;
		String voucherKey = null;
		
		try {
			
			connection = DatabaseManager.getInstance().getConnection();
			connection.setAutoCommit(false);
			
			for(DayBookMasterVO dayBookMasterVO : tallyInputDTO.getDayBookMasterVOs()) {
				
				voucherKey = dayBookMasterVO.getVoucherKey();
				System.out.println("Day book VK : " + voucherKey);
			
				/*	int parameterIndex = 1;
				
				//delete data from table
				preparedStatement = connection.prepareStatement(Constants.DB_DELETE_DAYBOOK_LEDGER);
				preparedStatement.setString(parameterIndex, dayBookMasterVO.getVoucherKey());
				preparedStatement.execute();
				preparedStatement = connection.prepareStatement(Constants.DB_DELETE_DAYBOOK_INVENTORY);
				preparedStatement.setString(parameterIndex, dayBookMasterVO.getVoucherKey());
				preparedStatement.execute();
				preparedStatement = connection.prepareStatement(Constants.DB_DELETE_DAYBOOK_MASTER);
				preparedStatement.setString(parameterIndex, dayBookMasterVO.getVoucherKey());
				preparedStatement.execute();
	*/			
				//insert data into table
				preparedStatement = connection.prepareStatement(Constants.DB_ADD_DAYBOOK_MASTER);
				ledgerPreparedStatement = connection.prepareStatement(Constants.DB_ADD_DAYBOOK_LEDGER);
				inventoryPreparedStatement = connection.prepareStatement(Constants.DB_ADD_DAYBOOK_INVENTORY);
				
				int parameterIndex = 1;
				preparedStatement.setString(parameterIndex++, dayBookMasterVO.getVoucherKey());
				preparedStatement.setString(parameterIndex++, dayBookMasterVO.getVoucherType());
				preparedStatement.setString(parameterIndex++, dayBookMasterVO.getVoucherDate());
				preparedStatement.setString(parameterIndex++, dayBookMasterVO.getVoucherNumber());
				preparedStatement.setString(parameterIndex++, dayBookMasterVO.getPartyLedgerName());
				preparedStatement.setString(parameterIndex++, dayBookMasterVO.getEffectiveDate());
				preparedStatement.setString(parameterIndex++, dayBookMasterVO.getMasterId());
				preparedStatement.setBoolean(parameterIndex++, false);
				preparedStatement.setDate(parameterIndex++, Utility.getCurrentdate());
				preparedStatement.setString(parameterIndex++, null);
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
				
				//preparedStatement.executeUpdate();
				
				
				try {
					preparedStatement.executeUpdate();
					
					System.out.println("Day book VK : " + voucherKey + " Inserted");
				} catch (Exception e) {
					if(null != e && null != e.getMessage() && e.getMessage().contains("Duplicate")) {
						System.out.println("Record is already available in Day book master table");
					} else {
						e.printStackTrace();
						//throw new RuntimeException(e);
					}
					
					continue;
				}
				
				//LOG.info(LOG_BASE_FORMAT, tallyInputDTO.getTrackingID(), "addTallyDayBook, data inserted in DB DAYBOOK_MASTER for Party : " + dayBookMasterVO.getPartyLedgerName() + " , Ledger type : " + dayBookMasterVO.getVoucherType());
				
				//insert data in DAYBOOK_LEDGER
				double amount = 0.0;
				for(LedgerEntryVO ledgerEntryVO : dayBookMasterVO.getLedgerEntryVOs()) {
					parameterIndex = 1;
					
					/*if(dayBookMasterVO.getVoucherType().equalsIgnoreCase("Delivery Note GST")) {
						System.out.println("Ledger Name : " + ledgerEntryVO.getLedgerName());
					}*/
					
					ledgerPreparedStatement.setString(parameterIndex++, ledgerEntryVO.getLedgerName());
					if(null != ledgerEntryVO.getAmount() && ledgerEntryVO.getAmount().trim().length() > 0) {
						amount = Math.abs(Double.parseDouble(ledgerEntryVO.getAmount()));
					} 
					ledgerPreparedStatement.setDouble(parameterIndex++, amount);
					amount = 0.0;
					ledgerPreparedStatement.setString(parameterIndex++, dayBookMasterVO.getVoucherKey());
					ledgerPreparedStatement.setDate(parameterIndex++, Utility.getCurrentdate());
					ledgerPreparedStatement.setString(parameterIndex++, null);
					ledgerPreparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
					ledgerPreparedStatement.executeUpdate();
				}
				
				//LOG.info(LOG_BASE_FORMAT, tallyInputDTO.getTrackingID(), "addTallyDayBook, data inserted in DB DAYBOOK_LEDGER for Party : " + dayBookMasterVO.getPartyLedgerName() + " , Ledger type : " + dayBookMasterVO.getVoucherType());
						
				//insert data in DAYBOOK_INVENTORY
				for(InventoryEntryVO inventoryEntryVO : dayBookMasterVO.getInventoryEntryVOs()) {
					parameterIndex = 1;
					inventoryPreparedStatement.setString(parameterIndex++, inventoryEntryVO.getStockItemName());
					if(null == inventoryEntryVO.getAmount() || inventoryEntryVO.getAmount().trim().length() < 1) {
						inventoryPreparedStatement.setString(parameterIndex++, "0");
					} else {
						inventoryPreparedStatement.setString(parameterIndex++, inventoryEntryVO.getAmount());
					}
					inventoryPreparedStatement.setString(parameterIndex++, inventoryEntryVO.getRate());
					inventoryPreparedStatement.setString(parameterIndex++, inventoryEntryVO.getBilledQuantity());
					inventoryPreparedStatement.setString(parameterIndex++, dayBookMasterVO.getVoucherKey());
					inventoryPreparedStatement.setDate(parameterIndex++, Utility.getCurrentdate());
					inventoryPreparedStatement.setString(parameterIndex++, null);
					inventoryPreparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
					inventoryPreparedStatement.executeUpdate();
				}
			
				//LOG.info(LOG_BASE_FORMAT, tallyInputDTO.getTrackingID(), "addTallyDayBook, data inserted in DB DAYBOOK_INVENTORY for Party : " + dayBookMasterVO.getPartyLedgerName() + " , Ledger type : " + dayBookMasterVO.getVoucherType());
			}
			
			connection.commit();
			
		} catch (Exception e) {
			
			if(null != connection) {
				try{
					connection.rollback();
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}
			}
			
			/*if(null != e && null != e.getMessage() && e.getMessage().contains("Duplicate")) {
				//LOG.warn(LOG_BASE_FORMAT, tallyInputDTO.getTrackingID(), "Record is already available");
				System.out.println("Record is already available for vc : " + voucherKey);
			} else {
				e.printStackTrace();
				//throw new RuntimeException(e);
			}*/
			
			//response.setStatus(Constants.RESPONSE_STATUS_FAILED);
			//response.setStatusMessage(Constants.RESPONSE_MESSAGE_PRODUCT_ADD_FAILED);
		} finally {
			
			try {
			if(null != ledgerPreparedStatement) { ledgerPreparedStatement.close(); }
			if(null != inventoryPreparedStatement) { inventoryPreparedStatement.close(); }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			closeResources();
		}
		
		//return response;
	}
	
	public void addTallyStock(TallyInputDTO tallyInputDTO) {
		
		/*Response response = new Response();
		response.setStatus(Constants.RESPONSE_STATUS_SUCCESS);
		response.setStatusMessage(Constants.RESPONSE_MESSAGE_PRODUCT_ADD_SUCCESS);*/
		
		PreparedStatement stockDetailPreparedStatement = null;
		PreparedStatement batchUDFPreparedStatement = null;
		
		try {
			
			connection = DatabaseManager.getInstance().getConnection();
			connection.setAutoCommit(false);
			
			for(StockMaster stockMaster : tallyInputDTO.getStockMasters()) {
			
				/*	int parameterIndex = 1;
				
				//delete data from table
				preparedStatement = connection.prepareStatement(Constants.DB_DELETE_DAYBOOK_LEDGER);
				preparedStatement.setString(parameterIndex, dayBookMasterVO.getVoucherKey());
				preparedStatement.execute();
				preparedStatement = connection.prepareStatement(Constants.DB_DELETE_DAYBOOK_INVENTORY);
				preparedStatement.setString(parameterIndex, dayBookMasterVO.getVoucherKey());
				preparedStatement.execute();
				preparedStatement = connection.prepareStatement(Constants.DB_DELETE_DAYBOOK_MASTER);
				preparedStatement.setString(parameterIndex, dayBookMasterVO.getVoucherKey());
				preparedStatement.execute();
				 */
				
				//insert data into table
				preparedStatement = connection.prepareStatement(Constants.DB_ADD_STOCK);
				stockDetailPreparedStatement = connection.prepareStatement(Constants.DB_ADD_STOCK_ITEM);
				batchUDFPreparedStatement = connection.prepareStatement(Constants.DB_ADD_STOCK_ITEM_DETAIL);
				
				int parameterIndex = 1;
				preparedStatement.setString(parameterIndex++, stockMaster.getVoucherType());
				//preparedStatement.setString(parameterIndex++, stockMaster.getAction());
				//preparedStatement.setString(parameterIndex++, stockMaster.getVoucherDate()); //DATE_ALT
				preparedStatement.setString(parameterIndex++, stockMaster.getVoucherDate()); //DATE_ENT
				//preparedStatement.setString(parameterIndex++, stockMaster.getVoucherTypeName());
				preparedStatement.setString(parameterIndex++, stockMaster.getVoucherNumber());
				preparedStatement.setString(parameterIndex++, stockMaster.getVoucherKey());
				preparedStatement.setString(parameterIndex++, stockMaster.getVoucherEffectiveDate());
				//preparedStatement.setString(parameterIndex++, stockMaster.getPersistedView());
				//preparedStatement.setString(parameterIndex++, stockMaster.getAlterId());
				preparedStatement.setString(parameterIndex++, stockMaster.getMasterId());
				preparedStatement.setString(parameterIndex++, stockMaster.getOprDate());
				
				preparedStatement.setString(parameterIndex++, stockMaster.getRealWeight());
				preparedStatement.setString(parameterIndex++, stockMaster.getStartTime());
				preparedStatement.setString(parameterIndex++, stockMaster.getRewindStart());
				preparedStatement.setString(parameterIndex++, stockMaster.getRewindEnd());
				preparedStatement.setString(parameterIndex++, stockMaster.getOperatedBy());
				preparedStatement.setString(parameterIndex++, stockMaster.getForeman1());
				preparedStatement.setString(parameterIndex++, stockMaster.getForeman2());
				preparedStatement.setDate(parameterIndex++, Utility.getCurrentdate());
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
				
				try {
					preparedStatement.executeUpdate();
				}  catch (Exception e) {
					// TODO: handle exception
					if(null != e && null != e.getMessage() && e.getMessage().contains("Duplicate")) {
						System.out.println("Record is already available in Stock master");
					} else {
						e.printStackTrace();
						//throw new RuntimeException(e);
					}
					
					continue;
				}
				
				//LOG.info(LOG_BASE_FORMAT, tallyInputDTO.getTrackingID(), "addTallyDayBook, data inserted in DB DAYBOOK_MASTER for Party : " + dayBookMasterVO.getPartyLedgerName() + " , Ledger type : " + dayBookMasterVO.getVoucherType());
				
				//insert data in DAYBOOK_LEDGER
				double amount = 0.0;
				List<StockDetail> stockDetails = stockMaster.getStockDetails();
				
				if(null == stockDetails) continue;
				
				for(StockDetail stockDetail : stockDetails) {
					parameterIndex = 1;
					
					/*if(dayBookMasterVO.getVoucherType().equalsIgnoreCase("Delivery Note GST")) {
						System.out.println("Ledger Name : " + ledgerEntryVO.getLedgerName());
					}*/
					//System.out.println("stockDetail.getStockDetailsId() : " + stockDetail.getStockDetailsId());
					stockDetailPreparedStatement.setString(parameterIndex++, stockDetail.getStockDetailsId());
					stockDetailPreparedStatement.setString(parameterIndex++, stockDetail.getStockItemName());
					stockDetailPreparedStatement.setString(parameterIndex++, stockDetail.getRate());
							
					amount = 0.0;
					if(null != stockDetail.getAmount() && stockDetail.getAmount().trim().length() > 0) {
						amount = Math.abs(Double.parseDouble(stockDetail.getAmount()));
					} 
					stockDetailPreparedStatement.setDouble(parameterIndex++, amount);
					
					stockDetailPreparedStatement.setString(parameterIndex++, stockDetail.getBilledQty());
					//stockDetailPreparedStatement.setString(parameterIndex++, stockDetail.getActualQty());
					stockDetailPreparedStatement.setString(parameterIndex++, stockDetail.getStatus());
					stockDetailPreparedStatement.setString(parameterIndex++, stockMaster.getVoucherKey());
					stockDetailPreparedStatement.setDate(parameterIndex++, Utility.getCurrentdate());
					stockDetailPreparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
					
					try {
						stockDetailPreparedStatement.executeUpdate();
					} catch (Exception e) {
						// TODO: handle exception
						if(null != e && null != e.getMessage() && e.getMessage().contains("Duplicate")) {
							System.out.println("Record is already available in Stock detail");
						} else {
							e.printStackTrace();
							//throw new RuntimeException(e);
						}
						
						continue;
					}
					
					//get batch UDF detail
					List<StockBatchUDF> stockBatchUDFs = stockDetail.getBatchUDF();
					
					if(null == stockBatchUDFs) continue;
					
					for(StockBatchUDF stockBatchUDF : stockBatchUDFs) {
						parameterIndex = 1;
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_671089649() && stockBatchUDF.getUDF_671089649().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_671089649()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_671089650() && stockBatchUDF.getUDF_671089650().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_671089650()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_671089651() && stockBatchUDF.getUDF_671089651().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_671089651()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_671089652() && stockBatchUDF.getUDF_671089652().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_671089652()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						/*amount = 0.0;
						if(null != stockBatchUDF.getUDF_671089655() && stockBatchUDF.getUDF_671089655().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_671089655()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_671089656() && stockBatchUDF.getUDF_671089656().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_671089656()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_671089657() && stockBatchUDF.getUDF_671089657().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_671089657()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_671089660() && stockBatchUDF.getUDF_671089660().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_671089660()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_788530753() && stockBatchUDF.getUDF_788530753().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_788530753()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_788538154() && stockBatchUDF.getUDF_788538154().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_788538154()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_788538155() && stockBatchUDF.getUDF_788538155().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_788538155()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);*/
						
						/*
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_788538156() && stockBatchUDF.getUDF_788538156().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_788538156()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						
						amount = 0.0;
						if(null != stockBatchUDF.getUDF_788538157() && stockBatchUDF.getUDF_788538157().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getUDF_788538157()));
						}
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);*/
						
						batchUDFPreparedStatement.setString(parameterIndex++, stockBatchUDF.getUDF_788538159());
						
						batchUDFPreparedStatement.setString(parameterIndex++, stockMaster.getVoucherKey());
						batchUDFPreparedStatement.setString(parameterIndex++, stockDetail.getStockDetailsId());
						batchUDFPreparedStatement.setDate(parameterIndex++, Utility.getCurrentdate());
						batchUDFPreparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
						
						batchUDFPreparedStatement.setString(parameterIndex++, stockBatchUDF.getBatchName());
						batchUDFPreparedStatement.setString(parameterIndex++, stockBatchUDF.getRate());
						
						amount = 0.0;
						if(null != stockBatchUDF.getAmount() && stockBatchUDF.getAmount().trim().length() > 0) {
							amount = Math.abs(Double.parseDouble(stockBatchUDF.getAmount()));
						} 
						batchUDFPreparedStatement.setDouble(parameterIndex++, amount);
						
						batchUDFPreparedStatement.setString(parameterIndex++, stockBatchUDF.getBilledQty());
						
						try {
							batchUDFPreparedStatement.executeUpdate();
						} catch (Exception e) {
							// TODO: handle exception
							if(null != e && null != e.getMessage() && e.getMessage().contains("Duplicate")) {
								System.out.println("Record is already available in Stock item detail");
							} else {
								e.printStackTrace();
								//throw new RuntimeException(e);
							}
						}
					}
				}
				
				//LOG.info(LOG_BASE_FORMAT, tallyInputDTO.getTrackingID(), "addTallyDayBook, data inserted in DB DAYBOOK_LEDGER for Party : " + dayBookMasterVO.getPartyLedgerName() + " , Ledger type : " + dayBookMasterVO.getVoucherType());
				
			}
			
			connection.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			if(null != connection) {
				try{
					connection.rollback();
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}
			}
			
			if(null != e && null != e.getMessage() && e.getMessage().contains("Duplicate")) {
				//LOG.warn(LOG_BASE_FORMAT, tallyInputDTO.getTrackingID(), "Record is already available");
			} else {
				e.printStackTrace();
				//throw new RuntimeException(e);
			}
			
			//response.setStatus(Constants.RESPONSE_STATUS_FAILED);
			//response.setStatusMessage(Constants.RESPONSE_MESSAGE_PRODUCT_ADD_FAILED);
		} finally {
			
			try {
			if(null != stockDetailPreparedStatement) { stockDetailPreparedStatement.close(); }
			if(null != batchUDFPreparedStatement) { batchUDFPreparedStatement.close(); }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			closeResources();
		}
		
		//return response;
	}

	public void addProductionSummary(TallyInputDTO tallyInputDTO) {
		
		PreparedStatement preparedStatement = null;
		PreparedStatement deletePreparedStatement = null;
		try {
			
			connection = DatabaseManager.getInstance().getConnection();
			connection.setAutoCommit(false);
			
			deletePreparedStatement = connection.prepareStatement(Constants.DB_HISTORY_DATA_DELETE);
			deletePreparedStatement.setString(1, tallyInputDTO.getYear());
			deletePreparedStatement.setString(2, tallyInputDTO.getGroup());
			deletePreparedStatement.setString(3, tallyInputDTO.getFrequency());
			deletePreparedStatement.setString(4, tallyInputDTO.getCompanyId());
			deletePreparedStatement.executeUpdate();
			
			System.out.println("History table deleted for year " + tallyInputDTO.getYear());
			
			for(ProductionSummaryVO productionSummaryVO : tallyInputDTO.getProductionSummaryVOs()) {
			
				//insert data into table
				preparedStatement = connection.prepareStatement(Constants.DB_HISTORY_DATA_ADD);
				
				int parameterIndex = 1;
				
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getGroup());
				//preparedStatement.setDouble(parameterIndex++, Utility.formatQty(productionSummaryVO.getAmount()));
				preparedStatement.setDouble(parameterIndex++, Utility.removeDecimal(productionSummaryVO.getAmount()));
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getFrequency());
				preparedStatement.setString(parameterIndex++, productionSummaryVO.getMonth());
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getYear());
				preparedStatement.setString(parameterIndex++, "Yes");
				preparedStatement.setDate(parameterIndex++, Utility.getCurrentdate());
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
				
				preparedStatement.executeUpdate();
				
			}
			
			connection.commit();
			
			System.out.println("Inserted data in DB for year " + tallyInputDTO.getYear());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			if(null != connection) {
				try{
					connection.rollback();
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}
			}
		} finally {
			
			try {
				if(null != deletePreparedStatement) deletePreparedStatement.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			closeResources();
		}
		
	}
	
	public void addSalesSummary(TallyInputDTO tallyInputDTO) {
		
		PreparedStatement preparedStatement = null;
		PreparedStatement deletePreparedStatement = null;
		try {
			
			connection = DatabaseManager.getInstance().getConnection();
			connection.setAutoCommit(false);
			
			deletePreparedStatement = connection.prepareStatement(Constants.DB_HISTORY_DATA_DELETE);
			deletePreparedStatement.setString(1, tallyInputDTO.getYear());
			deletePreparedStatement.setString(2, tallyInputDTO.getGroup());
			deletePreparedStatement.setString(3, tallyInputDTO.getFrequency());
			deletePreparedStatement.setString(4, tallyInputDTO.getCompanyId());
			deletePreparedStatement.executeUpdate();
			
			System.out.println("History table deleted for year " + tallyInputDTO.getYear());
			
			//double amount = 0.0;
			for(SalesSummaryVO salesSummaryVO : tallyInputDTO.getSalesSummaryVOs()) {
			
				//insert data into table
				preparedStatement = connection.prepareStatement(Constants.DB_HISTORY_DATA_ADD);
				
				int parameterIndex = 1;
				
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getGroup());
				
				preparedStatement.setInt(parameterIndex++, Utility.removeDecimal(salesSummaryVO.getAmount()));
				
				/*if(null != salesSummaryVO.getAmount() && salesSummaryVO.getAmount().trim().length() > 0) {
					amount = Math.abs(Double.parseDouble(salesSummaryVO.getAmount()));
				} 
				preparedStatement.setDouble(parameterIndex++, amount);
				amount = 0.0;*/
				
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getFrequency());
				preparedStatement.setString(parameterIndex++, salesSummaryVO.getMonth());
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getYear());
				preparedStatement.setString(parameterIndex++, "Yes");
				preparedStatement.setDate(parameterIndex++, Utility.getCurrentdate());
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
				
				preparedStatement.executeUpdate();
				
			}
			
			connection.commit();
			
			System.out.println("Inserted data in DB for year " + tallyInputDTO.getYear());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			if(null != connection) {
				try{
					connection.rollback();
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}
			}
		} finally {
			
			try {
				if(null != deletePreparedStatement) deletePreparedStatement.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			closeResources();
		}
		
	}
	
	public void addSalesOrder(TallyInputDTO tallyInputDTO) {
		
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = DatabaseManager.getInstance().getConnection();
			connection.setAutoCommit(false);
			
			//double amount = 0.0;
			for(SalesOrder salesOrder : tallyInputDTO.getSalesOrders()) {
			
				//insert data into table
				preparedStatement = connection.prepareStatement(Constants.DB_SALES_ORDER_ADD);
				
				int parameterIndex = 1;
				
				/*System.out.println("vc : " + salesOrder.getVoucherKey());
				System.out.println("on : " + salesOrder.getOrderNumber());
				System.out.println("bf : " + salesOrder.getBf());
				System.out.println("gsm : " + salesOrder.getGsm());
				System.out.println("size : " + salesOrder.getSize());
				*/
				
				preparedStatement.setString(parameterIndex++, salesOrder.getVoucherKey());
				preparedStatement.setString(parameterIndex++, salesOrder.getOrderDate());
				preparedStatement.setString(parameterIndex++, salesOrder.getCompany());
				preparedStatement.setString(parameterIndex++, salesOrder.getSize());
				preparedStatement.setString(parameterIndex++, salesOrder.getWeight());
				
				
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
				
				preparedStatement.setString(parameterIndex++, salesOrder.getOrderNumber());
				preparedStatement.setString(parameterIndex++, salesOrder.getBf());
				preparedStatement.setString(parameterIndex++, salesOrder.getGsm());
				preparedStatement.setString(parameterIndex++, salesOrder.getReel());
				
				
				try {
					preparedStatement.executeUpdate();
				}  catch (Exception e) {
					// TODO: handle exception
					if(null != e && null != e.getMessage() && e.getMessage().contains("Duplicate")) {
						System.out.println("Record is already available in Sales Orders table");
					} else {
						e.printStackTrace();
						//throw new RuntimeException(e);
					}
					
					continue;
				}
				
			}
			
			connection.commit();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			if(null != connection) {
				try{
					connection.rollback();
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}
			}
		} finally {
			
			closeResources();
		}
		
	}

	public void addSales(TallyInputDTO tallyInputDTO) {
		
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = DatabaseManager.getInstance().getConnection();
			double amount = 0.0;
			
			for(Sales sales : tallyInputDTO.getSalesList()) {
			
				preparedStatement = connection.prepareStatement(Constants.DB_ADD_SALES);
				
				//SALES_DETAILS(GST_NO, VOUCHER_NUMBER, PARTY_LEDGER_NAME, SALE_DATE, EFFECTIVE_DATE, VCH_TYPE, VOUCHER_KEY, LEDGER_NAME, AMOUNT, CREATED_DATE, MODIFIED_DATE, COMPANY_ID
						
				int parameterIndex = 1;
				preparedStatement.setString(parameterIndex++, sales.getGstNo());
				preparedStatement.setString(parameterIndex++, sales.getVoucherNumber());
				preparedStatement.setString(parameterIndex++, sales.getPartyLedgerName());
				preparedStatement.setString(parameterIndex++, sales.getDate());
				preparedStatement.setString(parameterIndex++, sales.getEffectiveDate());
				preparedStatement.setString(parameterIndex++, sales.getVoucherType());
				preparedStatement.setString(parameterIndex++, sales.getVoucherKey());
				preparedStatement.setString(parameterIndex++, sales.getLedgerName());
				if(null != sales.getAmount() && sales.getAmount().trim().length() > 0) {
					amount = Math.abs(Double.parseDouble(sales.getAmount()));
				} 
				preparedStatement.setDouble(parameterIndex++, amount);
				amount = 0.0;
				
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
				
				try {
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					if(null != e && null != e.getMessage() && e.getMessage().contains("Duplicate")) {
						System.out.println("Record is already available in Sales_details table");
					} else {
						e.printStackTrace();
						//throw new RuntimeException(e);
					}
					
					continue;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			closeResources();
		}
		
	}

	public void addReceipts(TallyInputDTO tallyInputDTO) {
		
		PreparedStatement preparedStatement = null;
		
		try {
			
			connection = DatabaseManager.getInstance().getConnection();
			
			double amount = 0.0;
			for(Receipt receipt : tallyInputDTO.getReceipts()) {
			
				preparedStatement = connection.prepareStatement(Constants.DB_ADD_RECEIPT);
				
				int parameterIndex = 1;
				preparedStatement.setString(parameterIndex++, receipt.getGstNo());
				preparedStatement.setString(parameterIndex++, receipt.getVoucherNumber());
				preparedStatement.setString(parameterIndex++, receipt.getPartyLedgerName());
				preparedStatement.setString(parameterIndex++, receipt.getDate());
				preparedStatement.setString(parameterIndex++, receipt.getEffectiveDate());
				preparedStatement.setString(parameterIndex++, receipt.getVoucherType());
				preparedStatement.setString(parameterIndex++, receipt.getVoucherKey());
				preparedStatement.setString(parameterIndex++, receipt.getLedgerName());
				if(null != receipt.getAmount() && receipt.getAmount().trim().length() > 0) {
					amount = Math.abs(Double.parseDouble(receipt.getAmount()));
				} 
				preparedStatement.setDouble(parameterIndex++, amount);
				amount = 0.0;
				preparedStatement.setString(parameterIndex++, tallyInputDTO.getCompanyId());
				
				try {
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					if(null != e && null != e.getMessage() && e.getMessage().contains("Duplicate")) {
						System.out.println("Record is already available in Receipt_details table");
					} else {
						e.printStackTrace();
						//throw new RuntimeException(e);
					}
					
					continue;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			closeResources();
		}
		
	}

	private void closeResources() {
		
		try {
			if(null != preparedStatement) {
				preparedStatement.close();
			}
			
			if(null != resultSet) {
				resultSet.close();
			}
			
			if(null != connection) {
				connection.close();
			}
		} catch (SQLException sqlException) {
			// TODO: handle exception
			System.out.println("Error in closing DB resources...");
			sqlException.printStackTrace();
		}
	}

}
