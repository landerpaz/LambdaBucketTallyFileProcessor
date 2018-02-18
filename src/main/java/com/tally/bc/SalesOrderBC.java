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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.amazonaws.services.s3.model.S3Object;
import com.tally.dao.TallyDAO;
//import com.tally.dto.Response;
import com.tally.dto.TallyInputDTO;
import com.tally.util.Constants;
import com.tally.util.Utility;
//import com.tally.util.Utility;
import com.tally.vo.DayBookMasterVO;
import com.tally.vo.InventoryEntryVO;
import com.tally.vo.LedgerEntryVO;
import com.tally.vo.ProductionSummaryVO;
import com.tally.vo.SalesOrder;
import com.tally.vo.SalesSummaryVO;
import com.tally.vo.StockBatchUDF;
import com.tally.vo.StockDetail;
import com.tally.vo.StockMaster;

public class SalesOrderBC {
	
	//private final Logger LOG = LoggerFactory.getLogger(TallyDayBookBC.class);
	
	private String formatString(String xpath, Object...objects) {
		
		return MessageFormat.format(xpath, objects);
	}
	
	public static void main(String[] args) {
		//TallyDayBookBC tallyDayBookBC = new TallyDayBookBC();
		//Response response = tallyDayBookBC.addTallyDayBookData(new TallyInputDTO());
		//tallyDayBookBC.getTallyDayBookData(new TallyInputDTO());
		
		/*InventoryEntryVO inventoryEntryVO = new InventoryEntryVO();
		inventoryEntryVO.setRate("25000.00/Ton");
		inventoryEntryVO.setBilledQuantity("102.000 Kgs = 1.200 Ton");
		Utility.formatInventory(inventoryEntryVO);
		
		System.out.println(inventoryEntryVO.getRate());
		System.out.println(inventoryEntryVO.getBilledQuantity());*/
		
		try {
			
			TallyInputDTO tallyInputDTO = new TallyInputDTO();
            tallyInputDTO.setCompanyId("Spak");
            //tallyInputDTO.setYear(Utility.getYearFromFileName("production_summary__2016__.xml"));
            tallyInputDTO.setYear(Utility.getYearFromFileName("sales_summary__2017__.xml"));
            tallyInputDTO.setGroup("SALES");
            tallyInputDTO.setFrequency("MONTH");
		SalesOrderBC tallySummaryBC = new SalesOrderBC();
		//tallySummaryBC.addFromFile(new TallyInputDTO());
		tallySummaryBC.addSalesOrder(tallyInputDTO, new S3Object(), "", "");
			
			//message format - start
			
			/*TallyDayBookBC tallyDayBookBC = new TallyDayBookBC();
			String formattedXpth = tallyDayBookBC.formatString("//hello/hi[{0}]/wow[{1}]//true[{2}]//go", 10, 20, 30);
			System.out.println(formattedXpth);*/
			//message format - end
			
			
			/*Path path = Paths.get("/Users/ashokarulsamy/Downloads/day_book_latest.xml");
			Charset charset = StandardCharsets.UTF_8;

			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll("&#", "");
			Files.write(path, content.getBytes(charset));*/
			
			
			/*TallyInputDTO tallyInputDTO = new TallyInputDTO();
			tallyInputDTO.setTiny(false);
			String bucket = "tallyselvabk";
			//String key = "REQUEST_XML_DAY_BOOK__2017-10-14-01-56-31.xml";
			String key ="day_book_latest.xml";
			
			BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJVV726YW43DBG4TA", "LyMMDpZ1wgcIZ7InITAl/DQOpdVtXQrFOW7HKvFR");
		    AmazonS3 s3 = AmazonS3ClientBuilder.standard()
		    						.withRegion(Regions.fromName("ap-south-1"))
                                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
		                            .build();
		    
		    TallyDayBookBC tallyDayBookBC = new TallyDayBookBC();
		    tallyDayBookBC.addTallyDayBookData(tallyInputDTO, s3, key, bucket);*/
			
			/*TallyInputDTO tallyInputVO = new TallyInputDTO();
			TallyDayBookBC tallyDayBookBC = new TallyDayBookBC();
			tallyDayBookBC.addFromStockFile(tallyInputVO);
			*/
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
	}
	
	public void addSalesOrder(TallyInputDTO tallyInputDTO, S3Object s3, String key, String bucket)  {
		
		System.out.println("Parsing sales order file..........");
		
		//get data from tally response
		List<SalesOrder> salesOrders = loadDataFromBucketForSalesOrder(tallyInputDTO, s3, key, bucket);
		//List<ProductionSummaryVO> productionSummaryVOs = addFromFile(tallyInputDTO);
				
		System.out.println("Number of records : " + salesOrders.size());
		
		/*for(SalesOrder salesOrder : salesOrders) {
			System.out.println(salesOrder.getVoucherKey());
		}*/
		 
		//insert data in DB
		if(null != salesOrders && salesOrders.size() > 0) {
		
			System.out.println("Inserting data in DB..........");
			
			tallyInputDTO.setSalesOrders(salesOrders);
			TallyDAO tallyDAO = new TallyDAO();
			tallyDAO.addSalesOrder(tallyInputDTO);
			
		}	
	}
	
	
	
	private List<SalesOrder> loadDataFromBucketForSalesOrder(TallyInputDTO tallyInputDTO, S3Object s3object, String key, String bucket) {
		
		InputStream input = null;
		BufferedReader reader = null;
		List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();
		SalesOrder salesOrder = null;
		String line = null;
		
		try {
			
			input = s3object.getObjectContent();
			
			reader = new BufferedReader(new InputStreamReader(input));
	        
		    while((line = reader.readLine()) != null) {
		        
	            	System.out.println("line : " + line);
		            
	            	
	            	String[] values = line.split("!!!");
		            
		            salesOrder = new SalesOrder();
		            salesOrder.setVoucherKey(values[0]);
		            salesOrder.setCompany(values[1]);
		            salesOrder.setOrderDate(values[2]);
		            salesOrder.setBf(values[3]);
		            salesOrder.setGsm(values[4]);
		            salesOrder.setSize(values[5]);
		            salesOrder.setWeight(values[6]);
		            salesOrder.setReel(values[7]);
		            salesOrder.setOrderNumber(values[8]);
		            
		            salesOrder.setCompanyId(tallyInputDTO.getCompanyId());
		           
		            salesOrders.add(salesOrder);
	         }
	            //System.out.println("    " + line);
	       
	        
	        
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error in parsing the line : " + line);
			e.printStackTrace();
			
		} finally {
			if(null != reader) reader = null;
		}
		
		return salesOrders;
	}

	
	
}
