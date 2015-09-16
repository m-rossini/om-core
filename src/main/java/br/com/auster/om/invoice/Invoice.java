/*
 * Copyright (c) 2004 Auster Solutions. All Rights Reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on Jan 26, 2005
 */
package br.com.auster.om.invoice;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *          
 * <p><b>Title:</b> Invoice</p>
 * <p><b>Description:</b> Represents a Invoice</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: Invoice.java 445 2007-06-06 00:42:34Z mtengelm $
 * 
 * @hibernate.class
 *              table="INV_INVOICE"
 */

public class Invoice extends InvoiceModelObject {
	private static final long serialVersionUID = 679338699591546497L;
	
	private WeakReference	account;
	
	// Cycle code 
	private String    cycleCode;
	// Cycle start and end date (cut date)
	private Date cycleStartDate;
	private String originalCycleStartDate;
	private Date cycleEndDate;
	private String originalCycleEndDate;
	// Issue and due date for this invoice
	private Date issueDate;
	private String originalIssueDate;
	private Date dueDate;
	private String originalDueDate;
	// Balance from last invoice
	private double 	previousBalance;
	// Payments and adjustments made since last invoice
	private double 	paymentsAmount;
	private double 	adjustmentsAmount;
	// Amount past last due date
	private double    pastDueBalanceAmount;
	private double 	penaltiesAmount;
	// For current period
	private double 	eqpInstmtAmount;
	private double    stateTaxAmount;
	private double    chargesAmount;
	private double 	currentPeriodAmount;
	// Other values
	private double 	interestsAmount;
	private double 	monthlyInstmtAmount;
	// Current debit parcel and total number of parcels 
	private int    	currentMonthlyInstmt;
	private int    	totalMonthlyInstmts;
	// Total amount for this invoice
	private double    totalAmount;
	
	// CFOP code and description
	private String 	cfopCode;
	private String 	cfopDescription;
	
	//direct debit information and account digit verification;
	private String directDebit;
	private String dirDebitMsgInd;
	
	// type of invoice
	private String 	docType;
	
	// The following fields are not supposed to be used for now  
	private double 	disputeCredits;
	private double 	retentionAmount;
	private double 	contractBreakAmount;
	private double 	penaltiesReturnAmount;
	private double 	interestsReturnAmount;
	
	// Subsections for this invoice
	private List sections = new ArrayList();
	private List receipts = new ArrayList();
	
	private BarCode barcode;
	
	private String invoiceNumber;
	
	private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	private DecimalFormat df = new DecimalFormat(NUMBER_FORMAT);
	
	//File where this invoice was read from
	public String fileName;
	
	//Due to BCK VIVO TKT143. Held Bill, retencao , totalAmountDue
	private double totalAmountDue;
	private double heldBillAmount;

	// CNPJ/CPF of client associated with this invoice
	/**
	 */
    private String cnpjCPF;

    /*** 
     * Holds the information about equipment installment break penalty.
     * This field is meant to be set by getter/setter during rules evaluation
     */
   private double equipmentInstallmentBreakPenalty;
   
   /***
    * 
    * Creates a new instance of the class <code>Invoice</code>.
    */
	public Invoice() {
		super.setInvoice(this);
	}
	
	
	/**
	 * @hibernate.property
	 *            column="INV_NUMBER"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 */
	/***
	 * Returns the Invoice Number as in the source file used to load.
	 * It can return null or even empty string if this information is not 
	 * present at the source file.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String _number) {
		this.invoiceNumber = _number;
	}
	
	/**
	 * @hibernate.property
	 *            column="AJUSTMENT_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getAdjustmentsAmount() {
		return adjustmentsAmount;
	}
	public void setAdjustmentsAmount(double adjustmentsAmount) {
		this.adjustmentsAmount = adjustmentsAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="CFOP_CODE"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getCfopCode() {
		return cfopCode;
	}
	public void setCfopCode(String cfopCode) {
		this.cfopCode = cfopCode;
	}
	
	/**
	 * @hibernate.property
	 *            column="CFOP_DESCRIPTION"
	 *            type="string"
	 *            length="256"
	 *            not-null="false"
	 */
	public String getCfopDescription() {
		return cfopDescription;
	}
	public void setCfopDescription(String cfopDescription) {
		this.cfopDescription = cfopDescription;
	}
	
	/**
	 * @hibernate.property
	 *            column="DOCUMENT_TYPE"
	 *            type="string"
	 *            length="8"
	 *            not-null="false"
	 */
	public String getDocumentType() {
		return this.docType;
	}
	public void setDocumentType(String _type) {
		this.docType = _type;
	}	
	
	/**
	 * @hibernate.property
	 *            column="CONTRACT_BREAK_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getContractBreakAmount() {
		return contractBreakAmount;
	}
	public void setContractBreakAmount(double contractBreakAmount) {
		this.contractBreakAmount = contractBreakAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="CURR_MONTH_INSTMT_AMNT"
	 *            type="integer"
	 *            not-null="false"
	 */
	public int getCurrentMonthlyInstmt() {
		return currentMonthlyInstmt;
	}
	public void setCurrentMonthlyInstmt(int currentDebitInstmt) {
		this.currentMonthlyInstmt = currentDebitInstmt;
	}
	
	/**
	 * @hibernate.property
	 *            column="MONTH_INSTMT_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getMonthlyInstmtAmount() {
		return monthlyInstmtAmount;
	}
	public void setMonthlyInstmtAmount(double debitInstmtAmount) {
		this.monthlyInstmtAmount = debitInstmtAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="DISPUTE_CREDITS"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getDisputeCredits() {
		return disputeCredits;
	}
	public void setDisputeCredits(double disputeCredits) {
		this.disputeCredits = disputeCredits;
	}
	
	/**
	 * @hibernate.property
	 *            column="DUE_DATE"
	 *            type="date"
	 *            not-null="true"
	 */
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * @hibernate.property
	 *            column="SRC_DUE_DATE"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 *            
	 * This attributes handles the unformatted version of the
	 * 	{@link #dueDate}  
	 */
	public String getOriginalDueDate() {
		return originalDueDate;
	}
	public void setOriginalDueDate(String _originalString) {
		this.originalDueDate = _originalString;
	}
	
	/**
	 * @hibernate.property
	 *            column="EQPMNT_INSTMT_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getEqpInstmtAmount() {
		return eqpInstmtAmount;
	}
	public void setEqpInstmtAmount(double eqpInstmtAmount) {
		this.eqpInstmtAmount = eqpInstmtAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="INTERESTS_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getInterestsAmount() {
		return interestsAmount;
	}
	public void setInterestsAmount(double interestsAmount) {
		this.interestsAmount = interestsAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="INTERESTS_RETURN_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getInterestsReturnAmount() {
		return interestsReturnAmount;
	}
	public void setInterestsReturnAmount(double interestsReturnAmount) {
		this.interestsReturnAmount = interestsReturnAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="ISSUE_DATE"
	 *            type="date"
	 *            not-null="false"
	 */
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	/**
	 * @hibernate.property
	 *            column="SRC_ISSUE_DATE"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 *            
	 * This attributes handles the unformatted version of the
	 * 	{@link #issueDate}  
	 */
	public String getOriginalIssueDate() {
		return originalIssueDate;
	}
	public void setOriginalIssueDate(String _originalString) {
		this.originalIssueDate = _originalString;
	}
		
	/**
	 * @hibernate.property
	 *            column="PAYMENTS_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getPaymentsAmount() {
		return paymentsAmount;
	}
	public void setPaymentsAmount(double paymentsAmount) {
		this.paymentsAmount = paymentsAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="PENALTIES_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getPenaltiesAmount() {
		return penaltiesAmount;
	}
	public void setPenaltiesAmount(double penaltiesAmount) {
		this.penaltiesAmount = penaltiesAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="PENALTIES_RETURN_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getPenaltiesReturnAmount() {
		return penaltiesReturnAmount;
	}
	public void setPenaltiesReturnAmount(double penaltiesReturnAmount) {
		this.penaltiesReturnAmount = penaltiesReturnAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="PREVIOUS_BALANCE_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(double previousBalance) {
		this.previousBalance = previousBalance;
	}
	
	/**
	 * @hibernate.property
	 *            column="CYCLE_END"
	 *            type="date"
	 *            not-null="false"
	 */
	public Date getCycleEndDate() {
		return cycleEndDate;
	}
	public void setCycleEndDate(Date refEndDate) {
		this.cycleEndDate = refEndDate;
	}
	
	/**
	 * @hibernate.property
	 *            column="SRC_CYCLE_END"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 *            
	 * This attributes handles the unformatted version of the
	 * 	{@link #cycleEndDate}  
	 */
	public String getOriginalCycleEndDate() {
		return originalCycleEndDate;
	}
	public void setOriginalCycleEndDate(String _originalString) {
		this.originalCycleEndDate = _originalString;
	}
	
	/**
	 * @hibernate.property
	 *            column="CYCLE_START"
	 *            type="date"
	 *            not-null="false"
	 */
	public Date getCycleStartDate() {
		return cycleStartDate;
	}
	public void setCycleStartDate(Date refStartDate) {
		this.cycleStartDate = refStartDate;
	}
	
	/**
	 * @hibernate.property
	 *            column="SRC_CYCLE_START"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 *            
	 * This attributes handles the unformatted version of the
	 * 	{@link #cycleStartDate}  
	 */
	public String getOriginalCycleStartDate() {
		return originalCycleStartDate;
	}
	public void setOriginalCycleStartDate(String _originalString) {
		this.originalCycleStartDate = _originalString;
	}
	
	/**
	 * @hibernate.property
	 *            column="RETENTION_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getRetentionAmount() {
		return retentionAmount;
	}
	public void setRetentionAmount(double retentionAmount) {
		this.retentionAmount = retentionAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="CURR_PERIOD_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getCurrentPeriodAmount() {
		return currentPeriodAmount;
	}
	public void setCurrentPeriodAmount(double totalAmount) {
		this.currentPeriodAmount = totalAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="TOTAL_MONTH_INSTMTS"
	 *            type="integer"
	 *            not-null="false"
	 */
	public int getTotalMonthlyInstmts() {
		return totalMonthlyInstmts;
	}
	public void setTotalMonthlyInstmts(int totalDebitInstmts) {
		this.totalMonthlyInstmts = totalDebitInstmts;
	}
	
	/**
   * @hibernate.many-to-one
   *        column="account_id"
   *        class="br.com.auster.om.invoice.Account
   * 
	 * @return Returns the account.
	 */
	public Account getAccount() {
		return (this.account != null) ? (Account) account.get() : null;
	}
	public void setAccount(Account account) {
		this.account = new WeakReference(account);
	}
	
	/**
	 * @hibernate.property
	 *            column="CYCLE_CODE"
	 *            type="string"
	 *            length="10"
	 *            not-null="true"
	 */
	public String getCycleCode() {
		return cycleCode;
	}
	public void setCycleCode(String cycleCode) {
		this.cycleCode = cycleCode;
	}
	
	/**
	 * @hibernate.property
	 *            column="PAST_DUE_BALANCE_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getPastDueBalanceAmount() {
		return pastDueBalanceAmount;
	}
	public void setPastDueBalanceAmount(double pastDueBalanceAmount) {
		this.pastDueBalanceAmount = pastDueBalanceAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="STATE_TAX_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getStateTaxAmount() {
		return stateTaxAmount;
	}
	public void setStateTaxAmount(double stateTaxAmount) {
		this.stateTaxAmount = stateTaxAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="CHARGES_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getChargesAmount() {
		return chargesAmount;
	}
	public void setChargesAmount(double chargesAmount) {
		this.chargesAmount = chargesAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="TOTAL_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	/**
   * @hibernate.list
   *            lazy="true"
   *            cascade="delete"
   *            
   * @hibernate.collection-key
   *            column="invoice_id"
   *            
   * @hibernate.collection-index
   *            column="seq"
   *            
   * @hibernate.collection-one-to-many
   *            class="br.com.auster.om.invoice.Section"
   * 
	 * @return Returns the sections.
	 */
	public List getSections() {
		return sections;
	}
  
  public void setSections(List sections) {
    this.sections = sections;
  }
  
	public void addSection(Section section) {
		sections.add(section);
		section.setInvoice(this);
	}
	
	/**
   * @hibernate.list
   *            lazy="true"
   *            cascade="delete"
   *            
   * @hibernate.collection-key
   *            column="invoice_id"
   *            
   * @hibernate.collection-index
   *            column="seq"
   *            
   * @hibernate.collection-one-to-many
   *            class="br.com.auster.om.invoice.Receipt"
   * 
	 * @return Returns the sections.
	 */
	public List getReceipts() {
		return receipts;
	}
  
  public void setReceipts(List receipts) {
    this.receipts = receipts;
  }
  
	public void addReceipt(Receipt receipt) {
		receipts.add(receipt);
		receipt.setInvoice(this);
	}
	
	
	public void setInvoice(Invoice inv) {
		super.setInvoice(inv);
		for(Iterator i=receipts.iterator(); i.hasNext(); ) {
			((Receipt) i.next()).setInvoice(inv);
		}
		for(Iterator i=sections.iterator(); i.hasNext(); ) {
			((Section) i.next()).setInvoice(inv);
		}
	}
	
  /**
   * @hibernate.one-to-one
   *            class="br.com.auster.om.invoice.BarCode"
   *            cascade="delete"
   *            property-ref="invoice"
   */
	public BarCode getBarCode() {
		return barcode;
	}
	
	public void setBarCode(BarCode _barcode) {
		this.barcode = _barcode;
    if(this.barcode != null)
		  this.barcode.setInvoice(this);
	}
	
	/**
	 * Compares 2 user objects
	 * 
	 * @param o object to compare
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		int ret = 0;
		if(o instanceof Invoice) {
			Invoice invoice = (Invoice) o;
			if((invoice.getCycleEndDate() != null) &&
					(this.getCycleEndDate() != null)) 
			{
				ret = this.getCycleEndDate().compareTo(invoice.getCycleEndDate());
			} 
		} else {
			throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
		}
		return ret;
	}
	
	public String toString() {
		return "CycleCode=" + this.getCycleCode() 
                + INNER_SEP_ATR + "CycleStart=" + (this.getCycleStartDate() != null ? sdf.format(this.getCycleStartDate()) : "null") 
                + INNER_SEP_ATR + "CycleEnd=" + (this.getCycleEndDate() != null ? sdf.format(this.getCycleEndDate()) : "null")
		+ INNER_SEP_ATR + "Value=" + df.format(this.getTotalAmount())
		+ INNER_SEP_ATR + "Element_Type=" + this.getElementType() ;  
	}


	/**
   * @return the fileName
   */
  public String getFileName() {
  	return this.fileName;
  }


	/**
   * @param fileName the fileName to set
   */
  public void setFileName(String fileName) {
  	this.fileName = fileName;
  }


	/**
   * @return the heldBillAmount
   */
  public double getHeldBillAmount() {
  	return this.heldBillAmount;
  }


	/**
   * @param heldBillAmount the heldBillAmount to set
   */
  public void setHeldBillAmount(double heldBillAmount) {
  	this.heldBillAmount = heldBillAmount;
  }


	/**
   * @return the totalAmountDue
   */
  public double getTotalAmountDue() {
  	return this.totalAmountDue;
  }


	/**
   * @param totalAmountDue the totalAmountDue to set
   */
  public void setTotalAmountDue(double totalAmountDue) {
  	this.totalAmountDue = totalAmountDue;
  }


	public String getCnpjCpf() {
		return cnpjCPF;
	}

	public void setCnpjCpf(String _cnpjCPF) {
		this.cnpjCPF = _cnpjCPF;
	}


	
	/**
	 * Return the value of a attribute <code>cnpjCPF</code>.
	 * @return return the value of <code>cnpjCPF</code>.
	 */
	public String getCnpjCPF() {
		return this.cnpjCPF;
	}


	
	/**
	 * Set the value of attribute <code>cnpjCPF</code>.
	 * @param cnpjCPF
	 */
	public void setCnpjCPF(String cnpjCPF) {
		this.cnpjCPF = cnpjCPF;
	}


	
	/**
	 * Return the value of a attribute <code>equipmentInstallmentBreakPenalty</code>.
	 * @return return the value of <code>equipmentInstallmentBreakPenalty</code>.
	 */
	public double getEquipmentInstallmentBreakPenalty() {
		return this.equipmentInstallmentBreakPenalty;
	}


	
	/**
	 * Set the value of attribute <code>equipmentInstallmentBreakPenalty</code>.
	 * @param equipmentInstallmentBreakPenalty
	 */
	public void setEquipmentInstallmentBreakPenalty(
			double equipmentInstallmentBreakPenalty) {
		this.equipmentInstallmentBreakPenalty = equipmentInstallmentBreakPenalty;
	}


	public String getDirectDebit() {
		return directDebit;
	}
	
	public void setDirectDebit(String directDebit) {
		this.directDebit = directDebit;
	}


	public String getDirDebitMsgInd() {
		return dirDebitMsgInd;
	}


	public void setDirDebitMsgInd(String dirDebitMsgInd) {
		this.dirDebitMsgInd = dirDebitMsgInd;
	}
	
}
