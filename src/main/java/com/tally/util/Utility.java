package com.tally.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tally.dao.TallyDAO;
import com.tally.dto.Result;
import com.tally.vo.InventoryEntryVO;

public class Utility {

	public static Date getCurrentdate() {
		
		Date date = new java.sql.Date(new java.util.Date().getTime());
		
		return date;
		
	}
	
	public static String getRandomNumber() {
		
		return UUID.randomUUID().toString();
		
	}
	
	public static void formatInventory(InventoryEntryVO inventoryEntryVO) {
		
		if(null != inventoryEntryVO) {
			if(null != inventoryEntryVO.getRate() && inventoryEntryVO.getRate().contains("/Ton")) {
				inventoryEntryVO.setRate(inventoryEntryVO.getRate().replace("/Ton", ""));
			} 
			
			if(null != inventoryEntryVO.getBilledQuantity() && inventoryEntryVO.getBilledQuantity().contains("=")) {
				inventoryEntryVO.setBilledQuantity((inventoryEntryVO.getBilledQuantity().split("="))[0]);
			}
		}
		
	}
	
	public static int getCount(Document doc, XPath xpath, String expression) {
        String count = null;
        try {
        	//System.out.println(expression);
            XPathExpression expr = xpath.compile(expression);
            count = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return null != count ? Integer.parseInt(count) : 0;
    }
	
	public static String getPrimaryData(Document doc, XPath xpath, String expressionPrefix, String expressionSuffix, int index) {
        
		String result = null;
        
        try {
            XPathExpression expr = xpath.compile(new StringBuilder(expressionPrefix).append(index).append(expressionSuffix).toString());
            result = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return null != result ? result.trim() : result;
    }
	
	public static String getSecondaryData(Document doc, XPath xpath, String expressionPrefix, String expression, String expressionSuffix, int index, int subIndex) {
        
		String result = null;
        
        try {
            XPathExpression expr = xpath.compile(new StringBuilder(expressionPrefix).append(index).append(expression).append(subIndex).append(expressionSuffix).toString());
            result = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return null != result ? result.trim() : result;
    }
	
	public static String getThirdData(Document doc, XPath xpath, 
			String expression1, String expression2, String expression3, String expression4, int index, int subIndex, int thirdIndex) {
        
		String result = null;
        
        try {
            XPathExpression expr = xpath.compile(new StringBuilder(expression1).append(index).append(expression2)
            		.append(subIndex).append(expression3)
            		.append(thirdIndex).append(expression4).toString());
            result = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return null != result ? result.trim() : result;
    }
	
	public static String getData(Document doc, XPath xpath, String input, Object...objects)  {
		
		String result = null;
		
		try {
			//System.out.println(input);
			//System.out.println(MessageFormat.format(input, objects));
			XPathExpression expr = xpath.compile(MessageFormat.format(input, objects));
			result = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			System.out.println(e.getMessage());
            e.printStackTrace();
            //throw new Exception();
        }
		
        return null != result ? result.trim() : result;
	}
	
	public static int getCount(Document doc, XPath xpath, String input, Object...objects) {
        String count = null;
        try {
        	//System.out.println(expression);
        	XPathExpression expr = xpath.compile(MessageFormat.format(input, objects));
            count = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
        	e.printStackTrace();
            
        }

        return null != count ? Integer.parseInt(count) : 0;
    }
	
	public static String getCurrentdateForRandomNumber() {
		
		String str = null;
		try {
			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter fmt = DateTimeFormat.forPattern(Constants.DATE_FORMAT_1);
		    str = date.toString(fmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}

	public static String getRandomNumber(String currentDate) {
		return new StringBuilder(currentDate).append(Long.toString(System.currentTimeMillis())).toString();
		
		//return new StringBuilder(currentDate).append(Integer.toString(new Random().nextInt((9999 - 1000) + 1) + 1000)).toString();
		
	}
	
	public static String getRandomNumberWithSeed() {
		return new Random(System.currentTimeMillis()).toString();
	}
	
	public static void main(String a[]) {
		//getCurrentdate();
		
		//System.out.println("sales_summary__2017__.xml".contains(Constants.SALES_SUMMARY));
		
		//System.out.println(removeDecimal("978000.00"));
		//int i = (int)Double.parseDouble("978000.00");
		//System.out.println(i);
	    //sendMail("SPAK", "testfile", "success", "ashok.arulsamy@gmail.com,selvaraj.arumuthu@gmail.com");
		
		System.out.println(getRandomNumberBasedOnTime());
	}
	
	public static String getCurrentYear() {
		Calendar now = Calendar.getInstance();   // Gets the current date and time
		return Integer.toString(now.get(Calendar.YEAR));
	}
	
	
	public static double formatQty(String qty) {
		
		if(null != qty && qty.contains("Kgs")) {
			return Double.parseDouble((qty.split("Kgs"))[0].trim());
		} 
		
		return 0;
	}
	
	public static String getYearFromFileName(String fileName) {
		
		if(null != fileName && fileName.contains("_")) {
			return (fileName.split("_"))[0].trim();
		} else {
			return getCurrentYear();
		}
	}
	
	public static String getCompanyFromFileName(String fileName, int companyIdPositionInFileName) {
		
		String companyName = null;
		
		try {
			if(null != fileName && fileName.contains("_")) {
				companyName = (fileName.split("_"))[companyIdPositionInFileName].trim().toUpperCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		System.out.println("Company Name : " + companyName);
		
		return companyName;
	}

	public static int removeDecimal(String qty) {
		
		System.out.println("qty : " + qty);
		
		int output = 0;
		
		if(null != qty && qty.contains(".")) {
			//return Integer.parseInt((qty.split(".00"))[0].trim());
			qty = qty.replace("Kgs", "").trim();
			output = (int)Double.parseDouble(qty);
		} 
		
		return output;
	}
	
	public static String[] formatItem(String item) {
		
		//System.out.println(item);
		String[] result = new String[3];
		String parsedItem = null;
		String bf = null;
		String gsm = null;
		String size = null;
		
		//System.out.println(item);
		
		//if(null != item && item.contains("CM")) { //CM
		if(null != item && StringUtils.containsIgnoreCase(item, "CM")) { //CM
			
			//String[] values = item.replace(" CM", "CM").split(" ");
			String[] values = StringUtils.replaceIgnoreCase(item, " CM", "CM").split(" ");
			String quantity = values[values.length-1];
			
			parsedItem = item.replace(quantity, "");
			//size = quantity.replace("CM", "");
			size = StringUtils.replaceIgnoreCase(quantity,"CM", "");
			
			
			//System.out.println("result[1] : " + result[1]);
			
			double inchValue = Double.parseDouble(size)/2.54;
			
			size = Double.toString(Math.round(inchValue * 100.0) / 100.0 );
			
		} else if (null != item && item.contains("\"")) { //inch
			
			String[] values = item.replace(" \"", "\"").split(" ");
			String quantity = values[values.length-1];
			
			parsedItem = item.replace(quantity, "");
			size = quantity.replace("\"", "");
			
		}
		
		//System.out.println(parsedItem);
		
		parsedItem = parsedItem.replace("BF", "!!!");
		parsedItem = parsedItem.replace("GSM", "!!!");
		
		String[] parsedItems = parsedItem.split("!!!");
		
		result[0] = parsedItems[0].trim();
		result[1] = parsedItems[1].trim();
		result[2] = size;
		
		
		return result;
	}
		
	public static double formatQtyTon(String qty) {
		
		if(null != qty && qty.contains("Ton")) {
			return Double.parseDouble((qty.split("="))[1].trim().replace(" Ton", ""));
		} 
		
		return 0;
	}
	
	public static String getReel(String size, double weight) {
		String reel =  null;
		
		if (null != size) {
			
			double sizeValue = Double.parseDouble(size);
			//double weightValue = Double.parseDouble(weight);
			
			double temp = (weight * 1000) / (sizeValue * 10);
			//System.out.println("temp : " + temp);
			int valueX = (int) Math.round(temp);
			reel = Integer.toString(valueX);
		}
		
		return reel;
		
	}
	
	public static boolean validateCompany(String companyName) {
		
		TallyDAO tallyDAO = new TallyDAO();
		List<String> companies = tallyDAO.getCompanies();
		boolean validCompany = false;
		
		System.out.println(companyName);
		
		for(String company : companies) {
			if(companyName.contains(company)) {
				validCompany = true;
				break;
			}
		}
		
		return validCompany;
		
	}
	


	public static String getDataForOneParamter(Document doc, XPath xpath, String expression, int index) {
	    String result = null;
	    try {
	    	//System.out.println(expression);
	    	//System.out.println(expression);
			//System.out.println(MessageFormat.format(expression, new Integer(index)));
			
	        XPathExpression expr = xpath.compile(MessageFormat.format(expression, new Integer(index)).replace(",", ""));
	        result = (String) expr.evaluate(doc, XPathConstants.STRING);
	    } catch (XPathExpressionException e) {
	        e.printStackTrace();
	        System.out.println(e.getMessage());
	        //throw new Exception();
	    }
	    return result;
	}
  
    
	public static String getData(Document doc, XPath xpath, String expression) {
        String companyName = null;
        try {
        	//System.out.println(expression);
            XPathExpression expr = xpath.compile(expression);
            companyName = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return companyName;
    }
	
	public static void sendMail(String companyId, String fileName, String status, String toMail, List<Result> results) {

	  try {

		StringBuilder service = new StringBuilder("http://spak-env.qupnvxxvp7.ap-south-1.elasticbeanstalk.com/services/tallyservice/tally/mail/")
				.append(companyId).append("/").append(fileName).append("/").append(status).append("/").append(toMail);
		  
		 /* StringBuilder service = new StringBuilder("http://localhost:8080/restws/services/tallyservice/tally/mail/")
					.append(companyId).append("/").append(fileName).append("/").append(status).append("/").append(toMail);
		*/  
		URL url = new URL(service.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2Vycy9Uek1Vb2NNRjRwIiwiZXhwIjoxNTUxMjUyNDE4LCJuYW1lIjoiRmlyc3ROYW1lIExhc3ROYW1lIiwic2NvcGUiOiJBZG1pbiJ9.8Wl8qvKT-IYjkfRxW7GZiYUep-Qcz_BNr-px-wQYZf4");

		if(null != results) {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(results);
			
			if(null != jsonInString) {
				OutputStream os = conn.getOutputStream();
				os.write(jsonInString.getBytes());
				os.flush();
			}
		}
		
		if (conn.getResponseCode() != 200) {
			System.out.println("sendMail : Failed response");
		} else {
			System.out.println("Mail Sent!");
		}	

		conn.disconnect();

	  } catch (MalformedURLException e) {
		  System.out.println("Error in sending mail : " + e.getMessage());
		  e.printStackTrace();
	  } catch (IOException e) {
		  System.out.println("Error in sending mail : " + e.getMessage());
		  e.printStackTrace();
	  }

	}
	
	public static String getRandomNumberBasedOnTime(){
		return System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000, 999999);
	}
}
