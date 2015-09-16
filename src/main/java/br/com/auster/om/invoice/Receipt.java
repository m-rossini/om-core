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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.auster.om.util.ParserUtils;

/**
 * @hibernate.class
 *              table="INV_RECEIPT"
 *              
 *              
 * <p><b>Title:</b> Receipt</p>
 * <p><b>Description:</b> Represents a Receipt that belongs to an Invoice</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: Receipt.java 468 2007-09-14 21:39:31Z mtengelm $
 */

public class Receipt extends InvoiceModelObject {
	private static final long serialVersionUID = -8693630080538998612L;
	 //atributos de "LD_GENERAL_INFO"
	private String 			receiptNbr;		  //"ItemNo"
    private String 			receiptClass;     //"LongDistanceCarrierSeries"
    private String 			receiptSubclass;  //"LongDistanceCarrierSubSeries"
    /** service provider             */   
    private String carrierCode;				  //"LongDistanceCarrierID"
    private String carrierName;				  //"LongDistanceCarrierName"
    private String carrierState;			  //"LongDistanceCarrierUF"
    private String invoiceIssueDate;		  //"InvoiceIssueDate"
    
    private double 			totalAmount;	
    private double 			totalInvoiceAmount; //key 202X
    private List			taxes;
    private List            details;  
    private List	discounts;
    
    private boolean local;
    
    // attributes for fiscal hashcode
    private String digitalAuthKey;
    
    // CNPJ, IE and carrier address
	protected Address address;
	protected Identity cnpj;
	protected Identity ie;
	
    public Receipt() {
        taxes = new ArrayList();
        details = new ArrayList();
        discounts = new ArrayList();
        local = false;
    }
    

    /**
     * @hibernate.property
     *            column="RECEIPT_CLASS"
     *            type="string"
     *            length="32"
     *            not-null="false"
     */
    public String getReceiptClass() {
        return receiptClass;
    }
    public void setReceiptClass(String receiptClass) {
        this.receiptClass = receiptClass;
    }

    /**
     * @hibernate.property
     *            column="RECEIPT_NUMBER"
     *            type="string"
     *            length="128"
     *            not-null="false"
     */
    public String getReceiptNbr() {
        return receiptNbr;
    }
    public void setReceiptNbr(String receiptNbr) {
        this.receiptNbr = receiptNbr;
    }
    
    /**
     * @hibernate.property
     *            column="RECEIPT_SUBCLASS"
     *            type="string"
     *            length="32"
     *            not-null="false"
     */
    public String getReceiptSubclass() {
        return receiptSubclass;
    }
    public void setReceiptSubclass(String receiptSubclass) {
        this.receiptSubclass = receiptSubclass;
    }

    /**
     * @hibernate.property
     *            column="CARRIER_CODE"
     *            type="string"
     *            length="64"
     *            not-null="false"
     */
    public String getCarrierCode() {
        return carrierCode;
    }
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    /**
     * @hibernate.property
     *            column="CARRIER_NAME"
     *            type="string"
     *            length="64"
     *            not-null="false"
     */
    public String getCarrierName() {
        return carrierName;
    }
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /**
     * @hibernate.property
     *            column="CARRIER_STATE"
     *            type="string"
     *            length="10"
     *            not-null="false"
     */
    public String getCarrierState() {
        return carrierState;
    }
    public void setCarrierState(String carrierState) {
        this.carrierState = carrierState;
    }

    public String getInvoiceIssueDate() {
		return invoiceIssueDate;
	}

	public void setInvoiceIssueDate(String invoiceIssueDate) {
		this.invoiceIssueDate = invoiceIssueDate;
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
   
   public double getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}

	public void setTotalInvoiceAmount(double totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}

/**
    * @hibernate.list
    *            lazy="true"
    *            cascade="delete"
    *            
    * @hibernate.collection-key
    *            column="receipt_id"
    *            
    * @hibernate.collection-index
    *            column="seq"
    *            
    * @hibernate.collection-one-to-many
    *            class="br.com.auster.om.invoice.ChargedTax"
    * 
    * @return Returns a Synchronized Map<String taxName, List<ChargedTax>> with all taxes charged to this receipt detail.
    */
    public List getTaxes() {
        return taxes;
    }
    public void setTaxes(List taxes) {
        this.taxes = taxes;
    }
    public void addTax(ChargedTax tax) {
        taxes.add(tax);
        tax.setInvoice(this.getInvoice());
    }
    public ChargedTax removeTax(ChargedTax tax) {
        if (taxes.remove(tax)) {
            return tax; 
        } else { 
            return null;
        }
    }
       
    /** 
    * @hibernate.list
    *            lazy="true"
    *            cascade="delete"
    *            
    * @hibernate.collection-key
    *            column="receipt_id"
    *            
    * @hibernate.collection-index
    *            column="seq"
    *            
    * @hibernate.collection-one-to-many
    *            class="br.com.auster.om.invoice.ReceiptDetail"
    * 
    * Returns a synchronized list of the details
    * @return
    */
    public List getDetails() {
      return details;
    }
    public void setDetails(List details) {
      this.details = details;
    }
    public void addDetail(ReceiptDetail detail) {
      details.add(detail);
      detail.setInvoice(this.getInvoice());
      detail.setReceipt(this);
    }

    public void setInvoice(Invoice inv) {
      super.setInvoice(inv);
      for(Iterator i=details.iterator(); i.hasNext(); ) {
        ((ReceiptDetail) i.next()).setInvoice(inv);
      }
    }
    
    public List getDiscounts() {
    	return discounts;
    }
    public void setDiscounts(List discounts) {
    	this.discounts = discounts;
    }
    public void addDiscount(ReceiptDiscount dis) {
    	discounts.add(dis);
    	dis.setInvoice(this.getInvoice());
    	dis.setReceipt(this);
    }
    
    public String getDigitalAuthKey() {
		return digitalAuthKey;
	}

	public void setDigitalAuthKey(String _digitalAuthKey) {
		this.digitalAuthKey = _digitalAuthKey;
	}

	// Needed by CORE rule A11
	public final boolean isLocal() {
		return this.local;
	}
	public final void setLocal(boolean _local) {
		this.local = _local;
	}

	// Needed by CORE rule A12
	public final Address getAddress() {
		return address;
	}
	public final void setAddress(Address address) {
		this.address = address;
	}
	public final Identity getCNPJ() {
		return cnpj;
	}
	public final void setCNPJ(Identity cnpj) {
		this.cnpj = cnpj;
	}
	public final Identity getIE() {
		return ie;
	}
	public final void setIE(Identity ie) {
		this.ie = ie;
	}
    
    
	public String toString() {
		return "Receipt[" +
		"class:" + this.getClass().getCanonicalName() + ";" +
		"code:" + this.getCarrierCode() + ";" +
		"state:" + this.getCarrierState() + 
		"]";
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
        if(o instanceof Receipt) {
            Receipt receipt = (Receipt) o;
            if((receipt.getReceiptNbr() != null) &&
                            (receipt.getReceiptClass() != null) &&
                            (this.getReceiptNbr() != null) &&
                            (this.getReceiptClass() != null))
            {
                ret = this.getReceiptClass().compareTo(receipt.getReceiptClass());
                if(ret == 0) {
                    ret = this.getReceiptNbr().compareTo(receipt.getReceiptNbr());
                }
            } 
        } else {
            throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
        }
        return ret;
    }
}
