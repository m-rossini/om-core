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
import java.util.ArrayList;
import java.util.List;

import br.com.auster.om.util.ParserUtils;
import br.com.auster.om.util.UnitCounter;

/**
 * @hibernate.class
 *              table="INV_RECEIPT_DETAIL"
 *
 * 
 * <p><b>Title:</b> ReceiptDetail</p>
 * <p><b>Description:</b> Represents a line in the receipt</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: ReceiptDetail.java 468 2007-09-14 21:39:31Z mtengelm $
 */
public class ReceiptDetail extends InvoiceModelObject {
    private static final long serialVersionUID = -7510998282621586641L;

    private WeakReference     receipt;
    private int               sequenceNbr;
    private String            fiscalCode;
    private String            serviceId;
    private String            caption;
    private String            unitType;
    private String            unitCount;
    private double            totalAmount;

    /****
     * Once the unitType drives uniquely the value of unitCount, 2 more fields are gonna be added.
     * Currently IF unitType handling is fully dependent on loader, and will continue like this.
     * The problem with current loading and with this class is unitType drives the unitCount.
     * Even for Receipts having (Usage NF Items are typical example) both count and duration, we just get
     * one of them on the unitCount (Again, depending on the loader and unitType value).
     * What we are doing is to keep current behavior for unitType and unitCount values.
     * We are also, adding to additional fields. One to store the number of events and other to
     * store the duration of the event.
     * 
     * In a given moment after loading the object unitCount will be redundant with one of the two new fields.
     * There are business scenarios that depends on having this facility.
     * 
     * Additionally, if user of this class avoid getUnitCounter method, we can also avoid generating
     * a new object every time.
     * 
     */
    private int eventQuantity;
    private UnitCounter eventUnits;
    
    
    private List              taxes;

		private UnitCounter unitCounter = null;

    public ReceiptDetail() {
        taxes = new ArrayList();
    }

    /**
     * @hibernate.property
     *            column="FISCAL_CODE"
     *            type="string"
     *            length="64"
     *            not-null="false"
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    /**
     * @return Returns the receipt.
     */
    public Receipt getReceipt() {
        return (Receipt) receipt.get();
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = new WeakReference( receipt );
    }

    /**
     * @hibernate.property
     *            column="SEQUENCE_NUMBER"
     *            type="integer"
     *            not-null="true"
     */
    public int getSequenceNbr() {
        return sequenceNbr;
    }

    public void setSequenceNbr(int sequenceNbr) {
        this.sequenceNbr = sequenceNbr;
    }

    /**
     * @hibernate.property
     *            column="SERVICE_ID"
     *            type="string"
     *            length="64"
     *            not-null="false"
     */
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public UnitCounter getUnitCounter() {
    	if (unitCounter == null) {
    		this.unitCounter = ParserUtils.getUnitCounter(getUnitType(), getUnitCount());
    	}
    	return unitCounter ;
    }
    
    public void setUnitCounter(UnitCounter unitCounter) {
    	this.unitCounter = unitCounter;
    }
    /**
     * @hibernate.property
     *            column="UNIT_COUNT"
     *            type="string"
     *            length="128"
     *            not-null="false"
     */
    public String getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(String unitCount) {
        this.unitCount = unitCount;
    }

    /**
     * @hibernate.property
     *            column="UNIT_TYPE"
     *            type="string"
     *            length="32"
     *            not-null="false"
     *            
     * @return Returns the unitType.
     */
    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    /**
     * @hibernate.list
     *            lazy="true"
     *            cascade="delete"
     *            
     * @hibernate.collection-key
     *            column="receipt_detail_id"
     *            
     * @hibernate.collection-index
     *            column="seq"
     *            
     * @hibernate.collection-one-to-many
     *            class="br.com.auster.om.invoice.ChargedTax"
     * 
     * @return Returns a Map<String taxName, ChargedTax tax> with all taxes charged to this receipt detail.
     */
    public List getTaxes() {
        return taxes;
    }

    public void setTaxes(List taxes) {
        this.taxes = taxes;
    }

    public void addTax(ChargedTax tax) {
        taxes.add( tax );
        tax.setInvoice( this.getInvoice() );
    }

    public ChargedTax removeTax(ChargedTax tax) {
        if ( taxes.remove( tax ) ) {
            return tax;
        } else {
            return null;
        }
    }

    /**
     * @hibernate.property
     *            column="CAPTION"
     *            type="string"
     *            length="128"
     *            not-null="false"
     *            
     * @return Returns the caption.
     */
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String toString() {
    	return "ReceiptDetail[" +
    	"class:" + this.getClass().getCanonicalName() + ";" + 
    	"code:" + this.getFiscalCode() + ";" + 
    	"seq:" + this.getSequenceNbr() + "]" ;
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
        if ( o instanceof ReceiptDetail ) {
            ReceiptDetail detail = (ReceiptDetail) o;
            ret = (this.getSequenceNbr() > detail.getSequenceNbr()) ? 1 : -1;
        } else {
            throw new ClassCastException( "Can't compare an " + this.getClass().getName() + " to an " + o.getClass().getName() );
        }
        return ret;
    }

		
		/**
		 * Return the value of a attribute <code>eventQuantity</code>.
		 * @return return the value of <code>eventQuantity</code>.
		 */
		public int getEventQuantity() {
			return this.eventQuantity;
		}

		
		/**
		 * Set the value of attribute <code>eventQuantity</code>.
		 * @param eventQuantity
		 */
		public void setEventQuantity(int eventQuantity) {
			this.eventQuantity = eventQuantity;
		}

		
		/**
		 * Return the value of a attribute <code>eventUNits</code>.
		 * @return return the value of <code>eventUNits</code>.
		 */
		public UnitCounter getEventUnits() {
			return this.eventUnits;
		}

		
		/**
		 * Set the value of attribute <code>eventUNits</code>.
		 * @param eventUNits
		 */
		public void setEventUnits(UnitCounter eventUnits) {
			this.eventUnits = eventUnits;
		}

}
