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
import com.tally.vo.SalesSummaryVO;
import com.tally.vo.StockBatchUDF;
import com.tally.vo.StockDetail;
import com.tally.vo.StockMaster;

public class TallySummaryBC {
	
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
		TallySummaryBC tallySummaryBC = new TallySummaryBC();
		//tallySummaryBC.addFromFile(new TallyInputDTO());
		tallySummaryBC.addSalesSummary(tallyInputDTO, new S3Object(), "", "");
		//tallySummaryBC.addProductionSummary(tallyInputDTO, new S3Object(), "", "");
			
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
	
	public void addProductionSummary(TallyInputDTO tallyInputDTO, S3Object s3, String key, String bucket)  {
		
		System.out.println("Parsing production summary xml file..........");
		
		//get data from tally response
		List<ProductionSummaryVO> productionSummaryVOs = loadDataFromBucketForProductionSummary(tallyInputDTO, s3, key, bucket);
		//List<ProductionSummaryVO> productionSummaryVOs = addFromFile(tallyInputDTO);
				
		System.out.println("Number of records : " + productionSummaryVOs.size());
		 
		//insert data in DB
		if(null != productionSummaryVOs && productionSummaryVOs.size() > 0) {
		
			System.out.println("Inserting data in DB..........");
			
			tallyInputDTO.setProductionSummaryVOs(productionSummaryVOs);
			TallyDAO tallyDAO = new TallyDAO();
			tallyDAO.addProductionSummary(tallyInputDTO);
			
		}	
	}
	
	public void addSalesSummary(TallyInputDTO tallyInputDTO, S3Object s3, String key, String bucket)  {
		
		System.out.println("Parsing sales summary xml file..........");
		
		//get data from tally response
		List<SalesSummaryVO> salesSummaryVOs = loadDataFromBucketForSalesSummary(tallyInputDTO, s3, key, bucket);
		//List<SalesSummaryVO> salesSummaryVOs = addFromFile(tallyInputDTO);
				
		System.out.println("Number of records : " + salesSummaryVOs.size());
		 
		//insert data in DB
		if(null != salesSummaryVOs && salesSummaryVOs.size() > 0) {
		
			System.out.println("Inserting data in DB..........");
			
			tallyInputDTO.setSalesSummaryVOs(salesSummaryVOs);
			TallyDAO tallyDAO = new TallyDAO();
			tallyDAO.addSalesSummary(tallyInputDTO);
			
		}	
	}

	private List<SalesSummaryVO> addFromFile(TallyInputDTO tallyInputVO) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		DayBookMasterVO dayBookMasterVO = null;
		LedgerEntryVO ledgerEntryVO = null;
		InventoryEntryVO inventoryEntryVO = null;
		ProductionSummaryVO productionSummaryVO = null;
		SalesSummaryVO salesSummaryVO = null;
		List<ProductionSummaryVO> productionSummaryVOs = new ArrayList<>();
		List<SalesSummaryVO> salesSummaryVOs = new ArrayList<>();
		String[] months = {"April", "May", "June", "July", "August", "September", "October", "November", "December", "January", "Febrarury", "March"};
		
		try {
			
			builder = factory.newDocumentBuilder();
            //doc = builder.parse("/Users/ashokarulsamy/Downloads/2.xml");
            //doc = builder.parse("/Users/ashokarulsamy/Downloads/day_book.xml");
            //doc = builder.parse("/Users/ashokarulsamy/Downloads/test_file_1.xml");
			//doc = builder.parse("/Users/ashokarulsamy/Downloads/db_923-2.xml");
			doc = builder.parse("/Users/ashokarulsamy/tally/sales_summary__2017__.xml");
			//doc = builder.parse("/Users/ashokarulsamy/tally/production_summary__2016__.xml");
			
            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();

            /*int tallyMessageCount = Utility.getCount(doc, xpath, Constants.ENVELOPE_DSPSTKINFO_COUNT);
            
            System.out.println("loadDataFromBucketForProductionSummary : tallyMessageCount : " + tallyMessageCount);
            
            //if(true) return dayBookMasterVOs;
            
            for(int index=1; index<=tallyMessageCount; index++) {
            	
            	System.out.println(months[index-1]);
            	System.out.println(getData(doc, xpath, Constants.ENVELOPE_DSPSTKINFO_DSPSTKIN_DSPINQTY, index));
            	
            	
            	productionSummaryVO = new ProductionSummaryVO();
            	productionSummaryVO.setMonth(months[index-1]);
            	productionSummaryVO.setAmount(getData(doc, xpath, Constants.ENVELOPE_DSPSTKINFO_DSPSTKIN_DSPINQTY, index));
            
            	productionSummaryVOs.add(productionSummaryVO);
            	
            }*/
            
            int tallyMessageCount = Utility.getCount(doc, xpath, Constants.ENVELOPE_DSPACCINFO_COUNT);
            
            System.out.println("loadDataFromBucketForSalesSummary : tallyMessageCount : " + tallyMessageCount);
            
            //if(true) return dayBookMasterVOs;
            
            for(int index=1; index<=tallyMessageCount; index++) {
            	
            	System.out.println(months[index-1]);
            	
            	salesSummaryVO = new SalesSummaryVO();
            	salesSummaryVO.setMonth(months[index-1]);
            	salesSummaryVO.setAmount(getData(doc, xpath, Constants.ENVELOPE_DSPACCINFO_DSPCRAMT_DSPDRAMTA, index));
            
            	salesSummaryVOs.add(salesSummaryVO);
            	
            }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
		return salesSummaryVOs;
		
	}
	
	private List<ProductionSummaryVO> loadDataFromBucketForProductionSummary(TallyInputDTO tallyInputDTO, S3Object s3object, String key, String bucket) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		List<ProductionSummaryVO> productionSummaryVOs = new ArrayList<>();
		ProductionSummaryVO productionSummaryVO = null;
		String[] months = {"April", "May", "June", "July", "August", "September", "October", "November", "December", "January", "Febrarury", "March"};
		
		
		try {
			
			//S3Object s3object = s3.getObject(new GetObjectRequest(bucket, key));
			
			builder = factory.newDocumentBuilder();
			//InputSource is = new InputSource(new StringReader(tallyInputDTO.getDayBook()));
            doc = builder.parse(s3object.getObjectContent());
       	
            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();

            int tallyMessageCount = Utility.getCount(doc, xpath, Constants.ENVELOPE_DSPSTKINFO_COUNT);
            
            System.out.println("loadDataFromBucketForProductionSummary : tallyMessageCount : " + tallyMessageCount);
            
            //if(true) return dayBookMasterVOs;
            
            for(int index=1; index<=tallyMessageCount; index++) {
            	
            	System.out.println(months[index-1]);
            	System.out.println(getData(doc, xpath, Constants.ENVELOPE_DSPSTKINFO_DSPSTKIN_DSPINQTY, index));
            	
            	
            	productionSummaryVO = new ProductionSummaryVO();
            	productionSummaryVO.setMonth(months[index-1]);
            	productionSummaryVO.setAmount(getData(doc, xpath, Constants.ENVELOPE_DSPSTKINFO_DSPSTKIN_DSPINQTY, index));
            
            	productionSummaryVOs.add(productionSummaryVO);
            	
            }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
		return productionSummaryVOs;
	}

	private List<SalesSummaryVO> loadDataFromBucketForSalesSummary(TallyInputDTO tallyInputDTO, S3Object s3object, String key, String bucket) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		List<SalesSummaryVO> salesSummaryVOs = new ArrayList<>();
		SalesSummaryVO salesSummaryVO = null;
		String[] months = {"April", "May", "June", "July", "August", "September", "October", "November", "December", "January", "Febrarury", "March"};
		
		
		try {
			
			//S3Object s3object = s3.getObject(new GetObjectRequest(bucket, key));
			
			builder = factory.newDocumentBuilder();
			//InputSource is = new InputSource(new StringReader(tallyInputDTO.getDayBook()));
            doc = builder.parse(s3object.getObjectContent());
       	
            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();

            int tallyMessageCount = Utility.getCount(doc, xpath, Constants.ENVELOPE_DSPACCINFO_COUNT);
            
            System.out.println("loadDataFromBucketForSalesSummary : tallyMessageCount : " + tallyMessageCount);
            
            //if(true) return dayBookMasterVOs;
            
            for(int index=1; index<=tallyMessageCount; index++) {
            	
            	System.out.println(months[index-1]);
            	
            	salesSummaryVO = new SalesSummaryVO();
            	salesSummaryVO.setMonth(months[index-1]);
            	salesSummaryVO.setAmount(getData(doc, xpath, Constants.ENVELOPE_DSPACCINFO_DSPCRAMT_DSPDRAMTA, index));
            
            	salesSummaryVOs.add(salesSummaryVO);
            	
            }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
		return salesSummaryVOs;
	}

	public static String getData(Document doc, XPath xpath, String input, Object...objects) {
		
		String result = null;
		
		try {
			XPathExpression expr = xpath.compile(MessageFormat.format(input, objects));
			result = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
            e.printStackTrace();
        }
		
        return null != result ? result.trim() : result;
	}
	
	public static String getData(Document doc, XPath xpath, String expression) {
        String data = null;
        try {
        	//System.out.println(expression);
            XPathExpression expr = xpath.compile(expression);
            data = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return data;
    }
	
}
