package com.amazonaws.lambda.demo;

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
import com.tally.bc.TallyDayBookBC;
import com.tally.bc.TallyStockBC;
import com.tally.bc.TallySummaryBC;
import com.tally.dto.TallyInputDTO;
import com.tally.util.Constants;
import com.tally.util.Utility;

public class LambdaFunctionHandler implements RequestHandler<S3Event, String> {

    //private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    
    BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJVV726YW43DBG4TA", "LyMMDpZ1wgcIZ7InITAl/DQOpdVtXQrFOW7HKvFR");
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
        
        try {
            
        	S3Object response = s3.getObject(new GetObjectRequest(sourceBucket, sourceKey));
            String contentType = response.getObjectMetadata().getContentType();
            //context.getLogger().log("CONTENT TYPE: " + contentType);
            
            System.out.println("Processing file from S3 bucket..........");
            
            if(null != sourceKey && sourceKey.contains(Constants.TALLY_DAY_BOOK)) {
            	
            	System.out.println("Processing TALLY_DAY_BOOK file.");
            	
	            TallyDayBookBC tallyDayBookBC = new TallyDayBookBC();
	            TallyInputDTO tallyInputDTO = new TallyInputDTO();
	            tallyInputDTO.setTiny(false);
	            tallyInputDTO.setCompanyId("Spak");
	            
	            tallyDayBookBC.addTallyDayBookData(tallyInputDTO, response, sourceKey, sourceBucket);
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.TALLY_STOCK)) {
            	
            	System.out.println("Processing TALLY_STOCK file.");
            	
            	TallyStockBC tallyStockBC = new TallyStockBC();
	            TallyInputDTO tallyInputDTO = new TallyInputDTO();
	            tallyInputDTO.setCompanyId("Spak");
	            
	            tallyStockBC.addStockData(tallyInputDTO, response, sourceKey, sourceBucket);
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.PRODUCTION_SUMMARY)) {
            	
            	System.out.println("Processing PRODUCTION_SUMMARY file.");
            	
            	TallySummaryBC tallySummaryBC = new TallySummaryBC();
	            TallyInputDTO tallyInputDTO = new TallyInputDTO();
	            tallyInputDTO.setCompanyId("Spak");
	            tallyInputDTO.setYear(Utility.getYearFromFileName(sourceKey));
	            tallyInputDTO.setGroup("PROD");
	            tallyInputDTO.setFrequency("MONTH");
	            
	            tallySummaryBC.addProductionSummary(tallyInputDTO, response, sourceKey, sourceBucket);
	            
            } else if(null != sourceKey && sourceKey.contains(Constants.SALES_SUMMARY)) {
            	
            	System.out.println("Processing SALES_SUMMARY file.");
            	
            	TallySummaryBC tallySummaryBC = new TallySummaryBC();
	            TallyInputDTO tallyInputDTO = new TallyInputDTO();
	            tallyInputDTO.setCompanyId("Spak");
	            tallyInputDTO.setYear(Utility.getYearFromFileName(sourceKey));
	            tallyInputDTO.setGroup("SALES");
	            tallyInputDTO.setFrequency("MONTH");
		           
	            tallySummaryBC.addSalesSummary(tallyInputDTO, response, sourceKey, sourceBucket);
	            
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
            
            System.out.println("End..........");
            
            return contentType;
            
        } catch (Exception e) {
        	System.err.println(e.getMessage());
            e.printStackTrace();
            context.getLogger().log(String.format(
                "Error getting object %s from bucket %s. Make sure they exist and"
                + " your bucket is in the same region as this function.", sourceKey, sourceBucket));
            throw e;
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