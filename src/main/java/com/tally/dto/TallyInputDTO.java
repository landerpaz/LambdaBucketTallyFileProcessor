package com.tally.dto;

import java.util.List;

import com.tally.vo.DayBookMasterVO;
import com.tally.vo.ProductionSummaryVO;
import com.tally.vo.SalesOrder;
import com.tally.vo.SalesSummaryVO;
import com.tally.vo.StockMaster;

public class TallyInputDTO {

	
	private String companyId;
	private String trackingID;
	private String dayBook;
	private List<DayBookMasterVO> dayBookMasterVOs;
	private String voucherKey;
	private boolean tiny;
	private List<StockMaster> stockMasters;
	private List<ProductionSummaryVO> productionSummaryVOs;
	private List<SalesSummaryVO> salesSummaryVOs;
	private String year;
	private String frequency;
	private String group;
	private List<SalesOrder> salesOrders;
	
	public List<SalesOrder> getSalesOrders() {
		return salesOrders;
	}
	public void setSalesOrders(List<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<SalesSummaryVO> getSalesSummaryVOs() {
		return salesSummaryVOs;
	}
	public void setSalesSummaryVOs(List<SalesSummaryVO> salesSummaryVOs) {
		this.salesSummaryVOs = salesSummaryVOs;
	}
	public List<ProductionSummaryVO> getProductionSummaryVOs() {
		return productionSummaryVOs;
	}
	public void setProductionSummaryVOs(List<ProductionSummaryVO> productionSummaryVOs) {
		this.productionSummaryVOs = productionSummaryVOs;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public List<StockMaster> getStockMasters() {
		return stockMasters;
	}
	public void setStockMasters(List<StockMaster> stockMasters) {
		this.stockMasters = stockMasters;
	}
	public boolean isTiny() {
		return tiny;
	}
	public void setTiny(boolean tiny) {
		this.tiny = tiny;
	}
	public String getVoucherKey() {
		return voucherKey;
	}
	public void setVoucherKey(String voucherKey) {
		this.voucherKey = voucherKey;
	}
	public List<DayBookMasterVO> getDayBookMasterVOs() {
		return dayBookMasterVOs;
	}
	public void setDayBookMasterVOs(List<DayBookMasterVO> dayBookMasterVOs) {
		this.dayBookMasterVOs = dayBookMasterVOs;
	}
	public String getTrackingID() {
		return trackingID;
	}
	public void setTrackingID(String trackingID) {
		this.trackingID = trackingID;
	}
	public String getDayBook() {
		return dayBook;
	}
	public void setDayBook(String dayBook) {
		this.dayBook = dayBook;
	}
	
	
}
