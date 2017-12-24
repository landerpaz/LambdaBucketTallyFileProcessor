package com.tally.util;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;

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
		
		System.out.println(removeDecimal(null));
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
		
		try {
			if(null != fileName && fileName.contains("_")) {
				return (fileName.split("_"))[companyIdPositionInFileName].trim().toUpperCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}

	public static int removeDecimal(String qty) {
		
		System.out.println("qty : " + qty);
		
		if(null != qty && qty.contains(".")) {
			return Integer.parseInt((qty.split(".00"))[0].trim());
		} 
		
		return 0;
	}
		
}
