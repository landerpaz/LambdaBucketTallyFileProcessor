package com.tally.dto;

import java.util.List;

import com.tally.vo.DayBookMasterVO;
import com.tally.vo.ProductionSummaryVO;
import com.tally.vo.Receipt;
import com.tally.vo.Sales;
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
	private List<Sales> salesList;
	private List<Receipt> receipts;
	private boolean hasError;
	private String error;
	private List<Result> results;
	private String customerName;
	private String gstNumber;
	private String custID;
	
	public String getCustID() {
		return custID;
	}
	public void setCustID(String custID) {
		this.custID = custID;
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<Result> getResults() {
		return results;
	}
	public void setResults(List<Result> results) {
		this.results = results;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<Sales> getSalesList() {
		return salesList;
	}
	public void setSalesList(List<Sales> salesList) {
		this.salesList = salesList;
	}
	public List<Receipt> getReceipts() {
		return receipts;
	}
	public void setReceipts(List<Receipt> receipts) {
		this.receipts = receipts;
	}
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
