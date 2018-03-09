package com.tally.bc;

import static com.tally.util.Constants.ACTION;
import static com.tally.util.Constants.ALTERID;
import static com.tally.util.Constants.AMOUNT;
import static com.tally.util.Constants.BILLEDQTY;
import static com.tally.util.Constants.DATE;
import static com.tally.util.Constants.EFFECTIVEDATE;
import static com.tally.util.Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE;
import static com.tally.util.Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_TINY;
import static com.tally.util.Constants.LEDGERNAME;
import static com.tally.util.Constants.MASTERID;
import static com.tally.util.Constants.PARTYLEDGERNAME;
import static com.tally.util.Constants.PERSISTEDVIEW;
import static com.tally.util.Constants.RATE;
import static com.tally.util.Constants.STOCKITEMNAME;
import static com.tally.util.Constants.TALLYMESSAGE_COUNT_EXP;
import static com.tally.util.Constants.TALLYMESSAGE_COUNT_EXP_TINY;
import static com.tally.util.Constants.VCHTYPE;
import static com.tally.util.Constants.VOUCHERKEY;
import static com.tally.util.Constants.VOUCHERNUMBER;
import static com.tally.util.Constants.VOUCHERTYPENAME;
import static com.tally.util.Constants.VOUCHER_ALLLEDGERENTRIES_LIST;
import static com.tally.util.Constants.VOUCHER_ALLLEDGERENTRIES_LIST_COUNT_1;
import static com.tally.util.Constants.VOUCHER_ALLLEDGERENTRIES_LIST_COUNT_1_TINY;
import static com.tally.util.Constants.VOUCHER_ALLLEDGERENTRIES_LIST_COUNT_2;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIESOUT_STATUS;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIES_IN_LIST;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIES_IN_LIST_COUNT_2;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIES_LIST;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIES_LIST_COUNT_1;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIES_LIST_COUNT_1_TINY;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIES_LIST_COUNT_2;
import static com.tally.util.Constants.VOUCHER_LEDGERENTRIES_LIST;
import static com.tally.util.Constants.VOUCHER_LEDGERENTRIES_LIST_COUNT_1;
import static com.tally.util.Constants.VOUCHER_LEDGERENTRIES_LIST_COUNT_1_TINY;
import static com.tally.util.Constants.VOUCHER_LEDGERENTRIES_LIST_COUNT_2;
import static com.tally.util.Constants.TALLYMESSAGE_COUNT_EXP_MANUAL;
import static com.tally.util.Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL;
import static com.tally.util.Constants.INVENTORYENTRIESIN_BATCHALLOCATIONS_LIST_COUNT_MANUAL;
import static com.tally.util.Constants.UDF_671089649_XPATH_MANUAL;
import static com.tally.util.Constants.UDF_671089650_XPATH_MANUAL;
import static com.tally.util.Constants.UDF_671089651_XPATH_MANUAL;
import static com.tally.util.Constants.UDF_671089652_XPATH_MANUAL;
import static com.tally.util.Constants.UDF_788538159_XPATH_MANUAL;
import static com.tally.util.Constants.BATCH_ALLOCATION_BATCH_NAME1_MANUAL;
import static com.tally.util.Constants.BATCH_ALLOCATION_AMOUNT1_MANUAL;
import static com.tally.util.Constants.BATCH_ALLOCATION_BILLED_QTY1_MANUAL;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIESOUT_LIST_COUNT1_MANUAL;
import static com.tally.util.Constants.STOCKITEMNAME_XPATH_MANUAL;
import static com.tally.util.Constants.RATE_XPATH_MANUAL;
import static com.tally.util.Constants.AMOUNT_XPATH_MANUAL;
import static com.tally.util.Constants.BILLEDQTY_XPATH_MANUAL;
import static com.tally.util.Constants.BASICBUYERNAME;
import static com.tally.util.Constants.PARTYGSTIN;
import static com.tally.util.Constants.ORDERNO;
import static com.tally.util.Constants.VCHTYPE_SALES_GST;
import static com.tally.util.Constants.VCHTYPE_RECEIPTS;
import static com.tally.util.Constants.VCHTYPE_STOCK_JOURAL_FG;
import static com.tally.util.Constants.VCHTYPE_SALES_ORDER;
import static com.tally.util.Constants.VCHTYPE_RECEIPTS;
import static com.tally.util.Constants.VCHTYPE_SALES_GST;
import static com.tally.util.Constants.VCHTYPE_SALES_ORDER;
import static com.tally.util.Constants.ACTUALQTY;
import static com.tally.util.Constants.BATCHALLOCATIONS_LIST;
import static com.tally.util.Constants.VCHTYPE_STOCK_JOURAL_FG;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIESIN_STATUS;
import static com.tally.util.Constants.INVENTORYENTRIESIN_BATCHALLOCATIONS_LIST_COUNT;
import static com.tally.util.Constants.UDF_671089649_XPATH;
import static com.tally.util.Constants.UDF_671089650_XPATH;
import static com.tally.util.Constants.UDF_671089651_XPATH;
import static com.tally.util.Constants.UDF_671089652_XPATH;
import static com.tally.util.Constants.UDF_788538159_XPATH;
import static com.tally.util.Constants.BATCH_ALLOCATION_BATCH_NAME1;
import static com.tally.util.Constants.BATCH_ALLOCATION_AMOUNT1;
import static com.tally.util.Constants.BATCH_ALLOCATION_BILLED_QTY1;
import static com.tally.util.Constants.VOUCHER_INVENTORYENTRIESOUT_LIST_COUNT1;
import static com.tally.util.Constants.STOCKITEMNAME_XPATH;
import static com.tally.util.Constants.RATE_XPATH;
import static com.tally.util.Constants.AMOUNT_XPATH;
import static com.tally.util.Constants.BILLEDQTY_XPATH;
import static com.tally.util.Constants.COMPANY_MANUAL;
import static com.tally.util.Constants.COMPANY_AUTOMATIC;

import static com.tally.util.Utility.getData;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.amazonaws.services.s3.model.S3Object;
import com.tally.dao.TallyDAO;
import com.tally.dto.TallyInputDTO;
import com.tally.util.Constants;
import com.tally.util.Utility;
import com.tally.vo.DayBookMasterVO;
import com.tally.vo.InventoryEntryVO;
import com.tally.vo.LedgerEntryVO;
import com.tally.vo.Receipt;
import com.tally.vo.Sales;
import com.tally.vo.SalesOrder;
import com.tally.vo.StockBatchUDF;
import com.tally.vo.StockDetail;
import com.tally.vo.StockMaster;

public class TallyBC {
	
	private String formatString(String xpath, Object...objects) {
		
		return MessageFormat.format(xpath, objects);
	}
	
	public static void main(String[] args) {
		
		try {
			TallyBC tallyBC = new TallyBC();
            TallyInputDTO tallyInputDTO = new TallyInputDTO();
            tallyInputDTO.setTiny(false);
            tallyInputDTO.setCompanyId("DEMO");
            
            //tallyInputDTO = tallyBC.processDataFromXML(tallyInputDTO);
            
            /*if(null != tallyInputDTO && !tallyInputDTO.isHasError()) {
        		Utility.sendMail("DEMO", "testfile", "Success", Constants.mailTo, tallyInputDTO.getResults());
        	} else {
        		Utility.sendMail("DEMO", "testfile", "Failed", Constants.mailTo, tallyInputDTO.getResults());
        	}*/
            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
	}
	
	//public TallyInputDTO processDataFromXML(TallyInputDTO tallyInputDTO)  {
	public TallyInputDTO processDataFromXML(TallyInputDTO tallyInputDTO, S3Object s3, String key, String bucket)  {
		
		System.out.println("Parsing xml file..........");
		//TallyInputDTO result = extractDataFromXML(tallyInputDTO);
		TallyInputDTO result = extractDataFromXML(tallyInputDTO, s3, key, bucket);
		System.out.println("Parsing xml file completed.");
		
		TallyDAO tallyDAO = new TallyDAO();
		
		if(null != result && null != result.getDayBookMasterVOs() && result.getDayBookMasterVOs().size() > 0) {
			System.out.println("Number of DAYBOOK records : " + result.getDayBookMasterVOs().size());
			System.out.println("Inserting DAYBOOK data in DB..........");
			
			result = tallyDAO.addTallyDayBook(result);
			
			System.out.println("Inserting DAYBOOK data in DB completed");
		} 
		
		if(null != result && null != result.getStockMasters() && result.getStockMasters().size() > 0) {
			System.out.println("Number of STOCK records : " + result.getStockMasters().size());
			System.out.println("Inserting STOCK data in DB..........");
			
			result = tallyDAO.addTallyStock(result);
			
			System.out.println("Inserting STOCK data in DB completed");
		}
		
		if(null != result && null != result.getSalesOrders() && result.getSalesOrders().size() > 0) {
			System.out.println("Number of SALESORDER records : " + result.getSalesOrders().size());
			System.out.println("Inserting SALESORDER data in DB..........");
			
			result = tallyDAO.addSalesOrder(result);
			
			System.out.println("Inserting SALESORDER in DB completed");
		}
		
		if(null != result && null != result.getSalesList() && result.getSalesList().size() > 0) {
			System.out.println("Number of SALES records : " + result.getSalesList().size());
			System.out.println("Inserting SALES data in DB..........");
			
			result = tallyDAO.addSales(result);
			
			System.out.println("Inserting SALES in DB completed");
		}
		
		if(null != result && null != result.getReceipts() && result.getReceipts().size() > 0) {
			System.out.println("Number of RECEIPT records : " + result.getReceipts().size());
			System.out.println("Inserting RECEIPT data in DB..........");
			
			result = tallyDAO.addReceipts(result);
			
			System.out.println("Inserting RECEIPT in DB completed");
		}
		
		return result;
	}
	
	//private TallyInputDTO extractDataFromXML(TallyInputDTO tallyInputDTO) {
	private TallyInputDTO extractDataFromXML(TallyInputDTO tallyInputDTO, S3Object s3object, String key, String bucket) {
		
		System.out.println("TallyBC:extractDataFromXML In");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		
		/*Day book - start*/
		DayBookMasterVO dayBookMasterVO = null;
		LedgerEntryVO ledgerEntryVO = null;
		InventoryEntryVO inventoryEntryVO = null;
		List<DayBookMasterVO> dayBookMasterVOs = new ArrayList<>();
		/*Day book - end*/
		
		/*Sales order - start*/
		StockMaster stockMaster = null;
		StockDetail stockDetail = null;
		List<StockDetail> stockDetails = null;
		StockBatchUDF stockBatchUDF = null;
		List<StockMaster> stockMasters = new ArrayList<>();
		String currentTimeForRandomNumber = null;
		/*Sales order - end*/
		
		/*Sales - START*/
		Sales sales = null;
		List<Sales> salesList = new ArrayList<>();
		/*Sales - END*/
		
		/*Receipt - START*/
		Receipt receipt = null;
		List<Receipt> receipts = new ArrayList<>();
		/*Receipt - END*/
		
		
		/*Sales order - start*/
		SalesOrder salesOrder = null;
		List<SalesOrder> salesOrders = new ArrayList<>();
		/*Sales order - end*/
		
		String vouherType = null;
		String voucherTypeName = null;
		String voucherNumber = null;
		String voucherKey = null;
		String alteredId = null;
		String masterId = null;
		String persistedView = null;
		String action = null;
		String date = null;
		String effectiveDate = null;
		String voucherAction = null;
		String partyLedgerName = null;
		String basicBuyerName = null;
		
		try {
			
			/*Read File from local - START*/
			/*builder = factory.newDocumentBuilder();
            doc = builder.parse("/Users/ashokarulsamy/Downloads/SPAK_TALLY_DAY_BOOK__2018-03-07-04-15-06-x.xml");
            //doc = builder.parse("/Users/ashokarulsamy/Downloads/SPAK_TALLY_DAY_BOOK_AUTO.xml");
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();*/
            /*Read File from local - END*/
            
            /*Read File from S3 - SRART*/
			builder = factory.newDocumentBuilder();
			doc = builder.parse(s3object.getObjectContent());
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            /*Read File from S3 - END*/
            
            boolean manual = false;
            currentTimeForRandomNumber = Utility.getCurrentdateForRandomNumber();
            int tallyMessageCount = Utility.getCount(doc, xpath, TALLYMESSAGE_COUNT_EXP);
            
            System.out.println("Automatic : tallyMessageCount : " + tallyMessageCount);
            
            if(tallyMessageCount < 1) {
            	manual = true;
            	tallyMessageCount = Utility.getCount(doc, xpath, TALLYMESSAGE_COUNT_EXP_MANUAL);
            	System.out.println("TallyBC : Daybook Manual processing");
            	
            	System.out.println("Manual : tallyMessageCount : " + tallyMessageCount);
            	
            } else {
            	System.out.println("TallyBC : Daybook Automatic processing");
            }
            
            
            //validate the company
            if(!Utility.validateCompany(Utility.getData(doc, xpath, manual ? COMPANY_MANUAL : COMPANY_AUTOMATIC))) {
            	System.out.println("TallyBC : Company Name is not valid!!!");
            	return tallyInputDTO;
            } else {
            	System.out.println("TallyBC : Company Name is valid!!!");
            }
            
            //if(true) return tallyInputDTO;
            
            for(int index=1; index<=tallyMessageCount; index++) {
            	
            	vouherType = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VCHTYPE, index);
        		voucherTypeName = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHERTYPENAME, index);
        		voucherNumber = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHERNUMBER, index);
        		voucherKey = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHERKEY, index);
        		persistedView = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, PERSISTEDVIEW, index);
        		alteredId = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, ALTERID, index);
        		masterId = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, MASTERID, index);
        		action = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, ACTION, index);
        		date = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, DATE, index);
        		effectiveDate = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, EFFECTIVEDATE, index);
        		voucherAction = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, ACTION, index);
        		partyLedgerName = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, PARTYLEDGERNAME, index); 
        		basicBuyerName = Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, BASICBUYERNAME, index);
        		
        		dayBookMasterVO = new DayBookMasterVO();
            	dayBookMasterVO.setVoucherType(vouherType);
            	dayBookMasterVO.setVoucherAction(voucherAction);
            	dayBookMasterVO.setVoucherDate(date);
            	dayBookMasterVO.setVoucherTypeName(voucherTypeName);
            	dayBookMasterVO.setVoucherNumber(voucherNumber);
            	dayBookMasterVO.setPartyLedgerName(partyLedgerName);
            	dayBookMasterVO.setVoucherKey(voucherKey);
            	dayBookMasterVO.setEffectiveDate(effectiveDate);
            	dayBookMasterVO.setPersistedView(persistedView);
            	dayBookMasterVO.setAlterId(alteredId);
            	dayBookMasterVO.setMasterId(masterId);
            	
            	/*Sales - start*/
            	if(vouherType.equals(VCHTYPE_SALES_GST)) {
	            	sales = new Sales();
	            	sales.setVoucherType(vouherType);
	            	sales.setVoucherNumber(voucherNumber);
	            	sales.setVoucherKey(voucherKey);
	            	sales.setPartyLedgerName(partyLedgerName);
	            	sales.setDate(effectiveDate);
	            	sales.setEffectiveDate(effectiveDate);
	            	sales.setGstNo(Utility.getPrimaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, PARTYGSTIN, index));
            	}
            	/*Sales - end*/
            	
            	/*Receipt - start*/
            	if(vouherType.equals(VCHTYPE_RECEIPTS)) {
	            	receipt = new Receipt();
	            	receipt.setVoucherType(vouherType);
	            	receipt.setVoucherNumber(voucherNumber);
	            	receipt.setVoucherKey(voucherKey);
	            	receipt.setPartyLedgerName(partyLedgerName);
	            	receipt.setDate(effectiveDate);
	            	receipt.setEffectiveDate(effectiveDate);
            	}
            	/*Receipt - end*/
            	
            	/*Stock - start*/
            	if(vouherType.equals(VCHTYPE_STOCK_JOURAL_FG)) {
	            	stockMaster = new StockMaster();
	            	stockMaster.setVoucherType(vouherType);
	            	stockMaster.setAction(action);
	            	stockMaster.setVoucherDate(date);
	            	stockMaster.setVoucherTypeName(voucherTypeName);
	            	stockMaster.setVoucherNumber(voucherNumber);
	            	stockMaster.setVoucherKey(voucherKey);
	            	stockMaster.setVoucherEffectiveDate(effectiveDate);
	            	stockMaster.setPersistedView(persistedView);
	            	stockMaster.setAlterId(alteredId);
	            	stockMaster.setMasterId(masterId);
            	}
            	/*Stock - end*/
            	
            	/*Sales - start*/
            	if(vouherType.equals(VCHTYPE_SALES_ORDER)) {
	            	salesOrder = new SalesOrder();
	            	salesOrder.setVoucherKey(voucherKey);
	            	salesOrder.setOrderDate(date);
	            	salesOrder.setCompany(basicBuyerName);
	            	salesOrder.setCompanyId(tallyInputDTO.getCompanyId());
		            
            	}
            	/*Sales - end*/
            	
            	/************************************************All Ledger Entries - START (DAYBOOK)*************************************************/
            	int allLedgerEntriesListCount = Utility.getCount(doc, xpath, new StringBuilder(manual ? Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_COUNT_MANUAL : Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_COUNT_AUTOMATIC).append(index).append(VOUCHER_ALLLEDGERENTRIES_LIST_COUNT_2).toString());
            	List<LedgerEntryVO> ledgerEntryVOs = new ArrayList<>();
            	for(int subIndex=1; subIndex<=allLedgerEntriesListCount; subIndex++) {
            		String ledgerName = Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_ALLLEDGERENTRIES_LIST, LEDGERNAME, index, subIndex);
            		String amount = Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_ALLLEDGERENTRIES_LIST, AMOUNT, index, subIndex); 
            		
            		ledgerEntryVO = new LedgerEntryVO();
            		ledgerEntryVO.setLedgerName(ledgerName);
            		ledgerEntryVO.setAmount(amount);
            		ledgerEntryVOs.add(ledgerEntryVO);
            		
            		if(vouherType.equals(VCHTYPE_RECEIPTS) && (subIndex == 1)) {
            			receipt.setLedgerName(ledgerName);
            			receipt.setAmount(amount);
            			receipts.add(receipt);
            		}
            	}
            	/************************************************All Ledger Entries - END*************************************************/
            	
            	/************************************************Ledger Entries - START (DAYBOOK)*************************************************/
            	int ledgerEntriesListCount = Utility.getCount(doc, xpath, new StringBuilder(manual ? Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_COUNT_MANUAL : Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_COUNT_AUTOMATIC).append(index).append(VOUCHER_LEDGERENTRIES_LIST_COUNT_2).toString());
            	for(int subIndex=1; subIndex<=ledgerEntriesListCount; subIndex++) {
            		
            		String ledgerName = Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_LEDGERENTRIES_LIST, LEDGERNAME, index, subIndex);
            		String amount = Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_LEDGERENTRIES_LIST, AMOUNT, index, subIndex);
            		
            		ledgerEntryVO = new LedgerEntryVO();
            		ledgerEntryVO.setLedgerName(ledgerName);
            		ledgerEntryVO.setAmount(amount);
            		
            		ledgerEntryVOs.add(ledgerEntryVO);
            		
            		if(vouherType.equals(VCHTYPE_SALES_GST) && (subIndex == 1)) {
            			sales.setLedgerName(ledgerName);
            			sales.setAmount(amount);
            			salesList.add(sales);
            		}
            	}
            	/************************************************Ledger Entries - END*************************************************/
            	
            	//updated dayBookMasterVO - set ledger list in master vo in one place for both allledgerentrieslist and ledgerentrieslist, because either one will be available not both for same tallmessage
            	dayBookMasterVO.setLedgerEntryVOs(ledgerEntryVOs);
            	
            	/************************************************Inventory Entries - START (DAYBOOK, SALES)*************************************************/
            	int inventoryEntriesListCount = Utility.getCount(doc, xpath, new StringBuilder(manual ? Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_COUNT_MANUAL : Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_COUNT_AUTOMATIC).append(index).append(VOUCHER_INVENTORYENTRIES_LIST_COUNT_2).toString());
            	List<InventoryEntryVO> inventoryEntryVOs = new ArrayList<>();
            	for(int subIndex=1; subIndex<=inventoryEntriesListCount; subIndex++) {
            		inventoryEntryVO = new InventoryEntryVO();
            		
            		String stockItemName = Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_LIST, STOCKITEMNAME, index, subIndex);
            		inventoryEntryVO.setStockItemName(stockItemName);
            		inventoryEntryVO.setRate(Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_LIST, RATE, index, subIndex));
            		inventoryEntryVO.setAmount(Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_LIST, AMOUNT, index, subIndex));
            		inventoryEntryVO.setBilledQuantity(Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_LIST, BILLEDQTY, index, subIndex));
            		inventoryEntryVOs.add(inventoryEntryVO);
            		
            		/*Sales - start*/
            		if(vouherType.equals(VCHTYPE_SALES_ORDER) && null != stockItemName && !stockItemName.trim().equals("")) {
            			String[] itemDetail = Utility.formatItem(stockItemName);
	            		double weight = Utility.formatQtyTon(Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_LIST, ACTUALQTY, index, subIndex));
	        			
	            		salesOrder = new SalesOrder();
		            	salesOrder.setVoucherKey(voucherKey);
		            	salesOrder.setOrderDate(date);
		            	salesOrder.setCompany(basicBuyerName);
		            	salesOrder.setCompanyId(tallyInputDTO.getCompanyId());
		            	
	            		salesOrder.setBf(itemDetail[0]);
			            salesOrder.setGsm(itemDetail[1]);
			            salesOrder.setSize(itemDetail[2]);
			            salesOrder.setWeight(Double.toString(weight));
			            salesOrder.setReel(Utility.getReel(itemDetail[2], weight));
			            salesOrder.setOrderNumber(Utility.getThirdData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_LIST, BATCHALLOCATIONS_LIST, "]//ORDERNO", index, subIndex, 1));
	            		salesOrders.add(salesOrder);
            		}
            		/*Sales - end*/
            	}
            	/************************************************Inventory Entries - END*************************************************/
            	
            	/************************************************Inventory Entries In - START (DAYBOOK, STOCK)*************************************************/
            	stockDetails = new ArrayList<>();
            	int inventoryEntriesInListCount = Utility.getCount(doc, xpath, new StringBuilder(manual ? Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_COUNT_MANUAL : Constants.ENVELOPE_BODY_DATA_TALLYMESSAGE_COUNT_AUTOMATIC).append(index).append(VOUCHER_INVENTORYENTRIES_IN_LIST_COUNT_2).toString());
            	for(int subIndex=1; subIndex<=inventoryEntriesInListCount; subIndex++) {
            		
            		String stockItemName = Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_IN_LIST, STOCKITEMNAME, index, subIndex);
            		String rate = Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_IN_LIST, RATE, index, subIndex);
            		String amount = Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_IN_LIST, AMOUNT, index, subIndex);
            		String billedQuantity = Utility.getSecondaryData(doc, xpath, manual ? ENVELOPE_BODY_DATA_TALLYMESSAGE_MANUAL : ENVELOPE_BODY_DATA_TALLYMESSAGE, VOUCHER_INVENTORYENTRIES_IN_LIST, BILLEDQTY, index, subIndex);
            		
            		inventoryEntryVO = new InventoryEntryVO();
            		inventoryEntryVO.setStockItemName(stockItemName);
            		inventoryEntryVO.setRate(rate);
            		inventoryEntryVO.setAmount(amount);
            		inventoryEntryVO.setBilledQuantity(billedQuantity);
            		inventoryEntryVOs.add(inventoryEntryVO);
            		
            		/*Stock - start*/
            		if(vouherType.equals(VCHTYPE_STOCK_JOURAL_FG)) {
	            		stockDetail = new StockDetail();
	            		stockDetail.setStockDetailsId(Utility.getRandomNumber(currentTimeForRandomNumber));
	            		stockDetail.setStockItemName(stockItemName);
	            		stockDetail.setRate(rate);
	            		stockDetail.setAmount(amount);
	            		stockDetail.setBilledQty(billedQuantity);
	            		stockDetail.setStatus(VOUCHER_INVENTORYENTRIESIN_STATUS);
	            		
	            		
	            		//get inventoryEntriesInBatchAllocationListCount
	                	//int inventoryEntriesInBatchAllocationListCount = Utility.getCount(doc, xpath,INVENTORYENTRIESIN_BATCHALLOCATIONS_LIST_COUNT1 ,index, subIndex);
	                	int inventoryEntriesInBatchAllocationListCount = Utility.getCount(doc, xpath, manual ? INVENTORYENTRIESIN_BATCHALLOCATIONS_LIST_COUNT_MANUAL : INVENTORYENTRIESIN_BATCHALLOCATIONS_LIST_COUNT ,index, subIndex);
	                	List<StockBatchUDF> stockBatchUDFs = new ArrayList<>();
	                	for(int thirdIndex=1; thirdIndex<=inventoryEntriesInBatchAllocationListCount; thirdIndex++) {
	                		
	                		stockBatchUDF = new StockBatchUDF();
	                		
	                		stockBatchUDF.setUDF_671089649(Utility.getData(doc, xpath, manual ? UDF_671089649_XPATH_MANUAL : UDF_671089649_XPATH ,index, subIndex, thirdIndex));
	                		stockBatchUDF.setUDF_671089650(Utility.getData(doc, xpath, manual ? UDF_671089650_XPATH_MANUAL : UDF_671089650_XPATH ,index, subIndex, thirdIndex));
	                		stockBatchUDF.setUDF_671089651(Utility.getData(doc, xpath, manual ? UDF_671089651_XPATH_MANUAL : UDF_671089651_XPATH ,index, subIndex, thirdIndex));
	                		stockBatchUDF.setUDF_671089652(Utility.getData(doc, xpath, manual ? UDF_671089652_XPATH_MANUAL : UDF_671089652_XPATH ,index, subIndex, thirdIndex));
	                		
//	                		stockBatchUDF.setUDF_671089655(Utility.getData(doc, xpath, Constants.UDF_671089655_XPATH ,index, subIndex, thirdIndex));
//	                		stockBatchUDF.setUDF_671089656(Utility.getData(doc, xpath, Constants.UDF_671089656_XPATH ,index, subIndex, thirdIndex));
//	                		stockBatchUDF.setUDF_671089657(Utility.getData(doc, xpath, Constants.UDF_671089657_XPATH ,index, subIndex, thirdIndex));
//	                		stockBatchUDF.setUDF_671089660(Utility.getData(doc, xpath, Constants.UDF_671089660_XPATH ,index, subIndex, thirdIndex));
//	                		stockBatchUDF.setUDF_788530753(Utility.getData(doc, xpath, Constants.UDF_788530753_XPATH ,index, subIndex, thirdIndex));
//	                		stockBatchUDF.setUDF_788538154(Utility.getData(doc, xpath, Constants.UDF_788538154_XPATH ,index, subIndex, thirdIndex));
//	                		stockBatchUDF.setUDF_788538155(Utility.getData(doc, xpath, Constants.UDF_788538155_XPATH ,index, subIndex, thirdIndex));
//	                		stockBatchUDF.setUDF_788538156(Utility.getData(doc, xpath, Constants.UDF_788538156_XPATH ,index, subIndex, thirdIndex));
//	                		stockBatchUDF.setUDF_788538157(Utility.getData(doc, xpath, Constants.UDF_788538157_XPATH ,index, subIndex, thirdIndex));
	                		
	                		stockBatchUDF.setUDF_788538159(Utility.getData(doc, xpath, manual ? UDF_788538159_XPATH_MANUAL : UDF_788538159_XPATH ,index, subIndex, thirdIndex));
	                		stockBatchUDF.setBatchName(Utility.getData(doc, xpath, manual ? BATCH_ALLOCATION_BATCH_NAME1_MANUAL : BATCH_ALLOCATION_BATCH_NAME1 ,index, subIndex, thirdIndex));
	                		stockBatchUDF.setRate("");
	                		stockBatchUDF.setAmount(Utility.getData(doc, xpath, manual ? BATCH_ALLOCATION_AMOUNT1_MANUAL : BATCH_ALLOCATION_AMOUNT1, index, subIndex, thirdIndex));
	                		stockBatchUDF.setBilledQty(Utility.getData(doc, xpath, manual ? BATCH_ALLOCATION_BILLED_QTY1_MANUAL : BATCH_ALLOCATION_BILLED_QTY1, index, subIndex, thirdIndex));
	                		
	                		stockBatchUDFs.add(stockBatchUDF);
	                	}
	                	
	                	stockDetail.setBatchUDF(stockBatchUDFs);
	                	stockDetails.add(stockDetail);
            		}
            		/*Stock - end*/
            	}
            	/************************************************Inventory Entries IN - END*************************************************/
            	
            	/************************************************Inventory Entries Out - START (STOCK)*************************************************/
            	/*Stock - start*/
            	if(vouherType.equals(VCHTYPE_STOCK_JOURAL_FG)) {
	            	int inventoryEntriesOutListCount = Utility.getCount(doc, xpath, manual ? VOUCHER_INVENTORYENTRIESOUT_LIST_COUNT1_MANUAL : VOUCHER_INVENTORYENTRIESOUT_LIST_COUNT1,index);
	            	for(int subIndex=1; subIndex<=inventoryEntriesOutListCount; subIndex++) {
	            		stockDetail = new StockDetail();
	            		stockDetail.setStockDetailsId(Utility.getRandomNumber(currentTimeForRandomNumber));
	            		stockDetail.setStockItemName(getData(doc, xpath, manual ? STOCKITEMNAME_XPATH_MANUAL : STOCKITEMNAME_XPATH, index, subIndex));
	            		stockDetail.setRate(getData(doc, xpath, manual ? RATE_XPATH_MANUAL : RATE_XPATH, index, subIndex));
	            		stockDetail.setAmount(getData(doc, xpath, manual ? AMOUNT_XPATH_MANUAL : AMOUNT_XPATH, index, subIndex));
	            		stockDetail.setBilledQty(getData(doc, xpath, manual ? BILLEDQTY_XPATH_MANUAL : BILLEDQTY_XPATH, index, subIndex));
	            		stockDetail.setStatus(VOUCHER_INVENTORYENTRIESOUT_STATUS);
	                	stockDetails.add(stockDetail);
	            	}
	            	
	            	stockMaster.setStockDetails(stockDetails);
	            	stockMasters.add(stockMaster);
            	}
            	/*Stock - end*/
            	/************************************************Inventory Entries - END*************************************************/
            	
            	//update dayBookMasterVO - set inventory list in master vo in one place for both ALLINVENTORYENTRIES.LIST and INVENTORYENTRIESIN.LIST, because either one will be available not both for same tallmessage
            	dayBookMasterVO.setInventoryEntryVOs(inventoryEntryVOs);
            	dayBookMasterVOs.add(dayBookMasterVO);
            }
            
            tallyInputDTO.setDayBookMasterVOs(dayBookMasterVOs);
        	tallyInputDTO.setStockMasters(stockMasters);
        	tallyInputDTO.setSalesOrders(salesOrders);
        	
        	tallyInputDTO.setSalesList(salesList);
        	tallyInputDTO.setReceipts(receipts);
        	
        	System.out.println("TallyBC:extractDataFromXML Out");
        	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("TallyBC:extractDataFromXML:error: " + e.getMessage());
			e.printStackTrace();
			tallyInputDTO.setHasError(true);
			tallyInputDTO.setError(e.getMessage());
		}
		
		return tallyInputDTO;
	}
}
