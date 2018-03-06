package com.amazonaws.lambda.demo;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.tally.bc.SalesOrderBC;
import com.tally.bc.TallyBC;
import com.tally.bc.TallyDayBookBC;
import com.tally.bc.TallyStockBC;
import com.tally.bc.TallySummaryBC;
import com.tally.dto.Result;
import com.tally.dto.TallyInputDTO;
import com.tally.util.Constants;
import com.tally.util.Utility;

public class LambdaFunctionHandler implements RequestHandler<S3Event, String> {

    //private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
    BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJ236YYHVV3UTSRCA", "3NF/bbIZeiNFv5O7ZXn4eUkRXSfOjxDVzPDZijmV");
    private AmazonS3 s3 = AmazonS3ClientBuilder.standard()
    						.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                            .build();

    public LambdaFunctionHandler() {}

    // Test purpose only.
    LambdaFunctionHandler(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public String handleRequest(S3Event event, Context context) {
    	
    	System.out.println("Start..........");
        
    	context.getLogger().log("Received event: " + event);

        // Get the object from the event and show its content type
        String sourceBucket = event.getRecords().get(0).getS3().getBucket().getName();
        String sourceKey = event.getRecords().get(0).getS3().getObject().getKey();
        
        System.out.println("sourceBucket : " + sourceBucket);
        System.out.println("sourceKey : " + sourceKey);
        
        TallyInputDTO tallyInputDTO = new TallyInputDTO();
        List<Result> results = new ArrayList<>();
        tallyInputDTO.setResults(results); //added here to send non blank mail body, even if no data is available when processing the data, to avoid error in mail service
        
        try {
            
        	S3Object response = s3.getObject(new GetObjectRequest(sourceBucket, sourceKey));
            String contentType = response.getObjectMetadata().getContentType();
            //context.getLogger().log("CONTENT TYPE: " + contentType);
            
            System.out.println("Processing file from S3 bucket..........");
            
            if(null != sourceKey && sourceKey.contains(Constants.TALLY_DAY_BOOK)) {
            	
            	System.out.println("Processing TALLY_DAY_BOOK file.");
            	
	            //TallyInputDTO tallyInputDTO = new TallyInputDTO();
	            tallyInputDTO.setTiny(false);
	            //tallyInputDTO.setCompanyId("Spak");
	            tallyInputDTO.setCompanyId(Utility.getCompanyFromFileName(sourceKey, 0));
	            
	            //process day book only
	            //TallyDayBookBC tallyDayBookBC = new TallyDayBookBC();
	            //tallyDayBookBC.addTallyDayBookData(tallyInputDTO, response, sourceKey, sourceBucket);
	            
	            //process daybook, stock and sales
	            TallyBC tallyBC = new TallyBC();
	            tallyInputDTO = tallyBC.processDataFromXML(tallyInputDTO, response, sourceKey, sourceBucket);
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.TALLY_STOCK)) {
            	
            	System.out.println("Processing TALLY_STOCK file.");
            	
            	TallyStockBC tallyStockBC = new TallyStockBC();
	            //TallyInputDTO tallyInputDTO = new TallyInputDTO();
	            tallyInputDTO.setCompanyId("Spak");
	            
	            tallyStockBC.addStockData(tallyInputDTO, response, sourceKey, sourceBucket);
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.PRODUCTION_SUMMARY)) {
            	
            	System.out.println("Processing PRODUCTION_SUMMARY file.");
            	
            	TallySummaryBC tallySummaryBC = new TallySummaryBC();
	            //TallyInputDTO tallyInputDTO = new TallyInputDTO();
	            //tallyInputDTO.setCompanyId("Spak");
	            tallyInputDTO.setCompanyId(Utility.getCompanyFromFileName(sourceKey, 1));
	            tallyInputDTO.setYear(Utility.getYearFromFileName(sourceKey));
	            tallyInputDTO.setGroup("PROD");
	            tallyInputDTO.setFrequency("MONTH");
	            
	            tallySummaryBC.addProductionSummary(tallyInputDTO, response, sourceKey, sourceBucket);
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.SALES_SUMMARY)) {
            	
            	System.out.println("Processing SALES_SUMMARY file.");
            	
            	TallySummaryBC tallySummaryBC = new TallySummaryBC();
	            //TallyInputDTO tallyInputDTO = new TallyInputDTO();
	            //tallyInputDTO.setCompanyId("Spak");
	            tallyInputDTO.setCompanyId(Utility.getCompanyFromFileName(sourceKey, 1));
	            tallyInputDTO.setYear(Utility.getYearFromFileName(sourceKey));
	            tallyInputDTO.setGroup("SALES");
	            tallyInputDTO.setFrequency("MONTH");
		           
	            tallySummaryBC.addSalesSummary(tallyInputDTO, response, sourceKey, sourceBucket);
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.SALES_ORDER)) {
            	
            	System.out.println("Processing SALES_ORDER file.");
            	
            	SalesOrderBC salesOrderBC = new SalesOrderBC();
	            //TallyInputDTO tallyInputDTO = new TallyInputDTO();
	            //tallyInputDTO.setCompanyId("Spak");
	            tallyInputDTO.setCompanyId(Utility.getCompanyFromFileName(sourceKey, 1));
	            
	            salesOrderBC.addSalesOrder(tallyInputDTO, response, sourceKey, sourceBucket);
	            
            } else {
            	System.out.println("Invalid file name!");
            }
            
            System.out.println("Processed file from bucket..........");
            
            System.out.println("Copy file to backup bucket..........");
            
            //copy file from source to backup bucket
            copyFileToBackupBucket(sourceBucket, sourceKey, "tallyselvabk", sourceKey);
            
            System.out.println("Delete file to from source bucket..........");
            
            //move file from source to backup bucket
            deleteFileFromSourceBucket(sourceBucket, sourceKey);
            
            /*System.out.println("Sending mail..........");
            
            Utility.sendMail(Utility.getCompanyFromFileName(sourceKey, 1), sourceKey, "Success", Constants.mailTo);
            
            System.out.println("End..........");*/
            
            return contentType;
            
        } catch (Exception e) {
        	System.err.println(e.getMessage());
            e.printStackTrace();
            context.getLogger().log(String.format(
                "Error getting object %s from bucket %s. Make sure they exist and"
                + " your bucket is in the same region as this function.", sourceKey, sourceBucket));
            throw e;
        } finally {
        	
        	System.out.println("Sending mail..........");
            
        	if(null != tallyInputDTO && !tallyInputDTO.isHasError()) {
        		Utility.sendMail(Utility.getCompanyFromFileName(sourceKey, 0), sourceKey, "Success", Constants.mailTo, tallyInputDTO.getResults());
        	} else {
        		Utility.sendMail(Utility.getCompanyFromFileName(sourceKey, 0), sourceKey, "Failed", Constants.mailTo, tallyInputDTO.getResults());
        	}
            
            System.out.println("End..........");
        }
        
    }
    
    private void copyFileToBackupBucket(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey) {
    	s3.copyObject(sourceBucket, sourceKey, destinationBucket, destinationKey);
    }
    
    private void deleteFileFromSourceBucket(String sourceBucket, String sourceKey) {
    	s3.deleteObject(new DeleteObjectRequest(sourceBucket, sourceKey));
    }
    
    public static void main(String a[]) {
    	try {
    		
    		String sourceKey = "SALES_SUMMARY.xml";
    		
    		if(null != sourceKey && sourceKey.contains(Constants.TALLY_DAY_BOOK)) {
            	
            	System.out.println("Processing TALLY_DAY_BOOK file.");
            	
	            
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.TALLY_STOCK)) {
            	
            	System.out.println("Processing TALLY_STOCK file.");
            	
            	
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.PRODUCTION_SUMMARY)) {
            	
            	System.out.println("Processing PRODUCTION_SUMMARY file.");
            	
            	
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.SALES_SUMMARY)) {
            	
            	System.out.println("Processing SALES_SUMMARY file.");
            	
            	
	            
            } else {
            	System.out.println("Invalid file name!");
            }

    	} catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    }
}