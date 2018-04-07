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

import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.amazonaws.services.s3.model.S3Object;
import com.tally.dao.TallyDAO;
import com.tally.dto.TallyInputDTO;
import com.tally.util.Constants;
import com.tally.util.Utility;
import com.tally.vo.Customer;
import com.tally.vo.DayBookMasterVO;
import com.tally.vo.InventoryEntryVO;
import com.tally.vo.LedgerEntryVO;
import com.tally.vo.Receipt;
import com.tally.vo.Sales;
import com.tally.vo.SalesOrder;
import com.tally.vo.StockBatchUDF;
import com.tally.vo.StockDetail;
import com.tally.vo.StockMaster;

public class CustomerBC {
	
	private String formatString(String xpath, Object...objects) {
		
		return MessageFormat.format(xpath, objects);
	}
	
	public static void main(String[] args) {
		
		try {
			/*CustomerBC tallyBC = new CustomerBC();
            TallyInputDTO tallyInputDTO = new TallyInputDTO();
            tallyInputDTO.setTiny(false);
            tallyInputDTO.setCompanyId("DEMO");*/
            
            //tallyInputDTO = tallyBC.processDataFromXML(tallyInputDTO);
            
            /*if(null != tallyInputDTO && !tallyInputDTO.isHasError()) {
        		Utility.sendMail("DEMO", "testfile", "Success", Constants.mailTo, tallyInputDTO.getResults());
        	} else {
        		Utility.sendMail("DEMO", "testfile", "Failed", Constants.mailTo, tallyInputDTO.getResults());
        	}*/
			
			//CustomerBC tallyBC = new CustomerBC();
			//tallyBC.extractDataFromXML(new TallyInputDTO());
			
			/*TallyInputDTO tallyInputDTO = new TallyInputDTO();
            tallyInputDTO.setCompanyId("DEMO");
			tallyBC.processDataFromXML(tallyInputDTO);*/
            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
	}
	
	//public TallyInputDTO processDataFromXML(TallyInputDTO tallyInputDTO)  {
	public TallyInputDTO processDataFromXML(TallyInputDTO tallyInputDTO, S3Object s3, String key, String bucket)  {
		
		
		System.out.println("Parsing customer xml file..........");
		//List<Customer> customers = extractDataFromXML(tallyInputDTO);
		List<Customer> customers = extractDataFromXML(tallyInputDTO, s3, key, bucket);
		tallyInputDTO.setCustomers(customers);
		System.out.println("Parsing customer xml file completed.");
		
		TallyDAO tallyDAO = new TallyDAO();
		
		//System.out.println("Number of Customer records : " + customers.size());
		System.out.println("Inserting Customer data in DB..........");
		
		tallyInputDTO = tallyDAO.addConsumerDetail(tallyInputDTO);
		
		System.out.println("Inserting Customer in DB completed");

		
		return tallyInputDTO;
	}
	
	//private List<Customer> extractDataFromXML(TallyInputDTO tallyInputDTO) {
	private List<Customer> extractDataFromXML(TallyInputDTO tallyInputDTO, S3Object s3object, String key, String bucket) {
		
		System.out.println("CustomerBC:extractDataFromXML In");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		
		
		List<Customer> customers = new ArrayList<>();
		String currentTimeForRandomNumber = null;
		
		try {
			
			/*Read File from local - START*/
			/*builder = factory.newDocumentBuilder();
			doc = builder.parse("/Users/ashokarulsamy/Downloads/Ledger-balance.xml");
            //doc = builder.parse("/Users/ashokarulsamy/Downloads/CurrentBalance1.xml");
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
            
            currentTimeForRandomNumber = Utility.getCurrentdateForRandomNumber();
            int tallyMessageCount = Utility.getCount(doc, xpath, Constants.CUSTOMER_CURRENT_BALANCE_COUNT_EXP);
            
            //System.out.println("CustomerBC : tallyMessageCount : " + tallyMessageCount);
            
            for(int index=1; index<=tallyMessageCount; index++) {
            	
            	//System.out.println(index);
            	String parent = Utility.getDataForOneParamter(doc, xpath, Constants.CUSTOMER_CURRENT_BALANCE_PARENT ,index);
            	//System.out.println("parent : "  + parent);
            	
            	if(null != parent && parent.contains("Sales Direct")) {
            	
            		
	            	Customer customer = new Customer();
	            	customer.setCustomerName(Utility.getDataForOneParamter(doc, xpath, Constants.CUSTOMER_CURRENT_BALANCE_CUSTOMER_NAME ,index));
	            	customer.setCurrentBalance(Utility.getDataForOneParamter(doc, xpath, Constants.CUSTOMER_CURRENT_BALANCE_CLOSING_BALANCE ,index));
	            	customers.add(customer);
	            	
	            	//System.out.println("1 : " + Utility.getData(doc, xpath, Constants.CUSTOMER_CURRENT_BALANCE_CUSTOMER_NAME ,index));
	            	//System.out.println("2 : " + Utility.getData(doc, xpath, Constants.CUSTOMER_CURRENT_BALANCE_CLOSING_BALANCE ,index));
	            	
	            	//System.out.println("------------");
            	}
            }
            
            System.out.println("Customers count : " + customers.size());
            
        	System.out.println("CustomerBC:extractDataFromXML Out");
        	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("CustomerBC:extractDataFromXML:error: " + e.getMessage());
			e.printStackTrace();
			tallyInputDTO.setHasError(true);
			tallyInputDTO.setError(e.getMessage());
		}
		
		return customers;
	}
}
