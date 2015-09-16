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

/**
 * @hibernate.class
 *              table="INV_CHARGED_TAX"
 *
 *              
 * <p><b>Title:</b> ReceiptDetailTax</p>
 * <p><b>Description:</b> Represents a Tax charged to a record</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: ChargedTax.java 34 2005-08-26 15:25:38Z etirelli $
 */
public class ChargedTax extends InvoiceModelObject {
	private static final long serialVersionUID = 1107292889847890831L;
	
	private String taxName;		  
	private double taxRate;		  
	private double taxableAmount; 
	private double taxAmount;  	  
	private double nonTaxableAmount;
	private double totalTaxesAmount; 
	
	/**
	 * @hibernate.property
	 *            column="NON_TAXABLE_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getNonTaxableAmount() {
		return nonTaxableAmount;
	}
	public void setNonTaxableAmount(double nonTaxableAmount) {
		this.nonTaxableAmount = nonTaxableAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="TAXABLE_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getTaxableAmount() {
		return taxableAmount;
	}
	public void setTaxableAmount(double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="TAX_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="TAX_NAME"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	
	/**
	 * @hibernate.property
	 *            column="TAX_RATE"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	
	public double getTotalTaxesAmount() {
		return totalTaxesAmount;
	}
	public void setTotalTaxesAmount(double totalTaxesAmount) {
		this.totalTaxesAmount = totalTaxesAmount;
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
		if(o instanceof ChargedTax) {
			ChargedTax tax = (ChargedTax) o;
			if(tax.getTaxName() != null)
			{
				ret = this.getTaxName().compareTo(tax.getTaxName());
				if(ret == 0) {
					ret = (this.getTaxRate() > tax.getTaxRate()) ?  1 : -1;
				}
				if(ret == 0) {
					ret = (this.getTaxableAmount() > tax.getTaxableAmount()) ?  1 : -1;
				}
				if(ret == 0) {
					ret = (this.getTaxAmount() > tax.getTaxAmount()) ?  1 : -1;
				}
				if(ret == 0) {
					ret = (this.getNonTaxableAmount() > tax.getNonTaxableAmount()) ?  1 : -1;
				}
			} 
		} else {
			throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
		}
		return ret;
	}	
}
