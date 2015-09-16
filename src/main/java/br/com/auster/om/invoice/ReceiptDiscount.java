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
import java.util.Date;

/**
 * @author mtengelm
 */
public class ReceiptDiscount extends InvoiceModelObject {
	private static final long serialVersionUID = 1107292889847890831L;
	
	private WeakReference receipt;
	
	private String discontName;
	private double discountAmount;
	private Date discountDate;
	private String originalDiscountAmount;
	private String originalDiscountDate;	
	
	public ReceiptDiscount() {
		//EMPTY CONSTRUCTOR
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
	 * Compares 2 user objects
	 * 
	 * @param o object to compare
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		if(o instanceof ReceiptDiscount) {
			ReceiptDiscount dis = (ReceiptDiscount) o;
			if (this.getDiscountAmount() == dis.getDiscountAmount() ) {
				return 0;
			}
			return (this.getDiscountAmount() > dis.getDiscountAmount()) ? 1 : -1;
		} else {
			throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
		}
	}
	

	/**
   * @return the discontName
   */
  public String getDiscontName() {
  	return this.discontName;
  }
	/**
   * @param discontName the discontName to set
   */
  public void setDiscontName(String discontName) {
  	this.discontName = discontName;
  }
	/**
   * @return the discountAmount
   */
  public double getDiscountAmount() {
  	return this.discountAmount;
  }
	/**
   * @param discountAmount the discountAmount to set
   */
  public void setDiscountAmount(double discountAmount) {
  	this.discountAmount = discountAmount;
  }
	/**
   * @return the discountDate
   */
  public Date getDiscountDate() {
  	return this.discountDate;
  }
	/**
   * @param discountDate the discountDate to set
   */
  public void setDiscountDate(Date discountDate) {
  	this.discountDate = discountDate;
  }
	/**
   * @return the originalDiscountAmount
   */
  public String getOriginalDiscountAmount() {
  	return this.originalDiscountAmount;
  }
	/**
   * @param originalDiscountAmount the originalDiscountAmount to set
   */
  public void setOriginalDiscountAmount(String originalDiscountAmount) {
  	this.originalDiscountAmount = originalDiscountAmount;
  }
	/**
   * @return the originalDiscountDate
   */
  public String getOriginalDiscountDate() {
  	return this.originalDiscountDate;
  }
	/**
   * @param originalDiscountDate the originalDiscountDate to set
   */
  public void setOriginalDiscountDate(String originalDiscountDate) {
  	this.originalDiscountDate = originalDiscountDate;
  }
	/* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
 
  public int hashCode() {
	  final int PRIME = 31;
	  int result = 1;
	  result = PRIME * result + ((this.discontName == null) ? 0 : this.discontName.hashCode());
	  long temp;
	  temp = Double.doubleToLongBits(this.discountAmount);
	  result = PRIME * result + (int) (temp ^ (temp >>> 32));
	  result = PRIME * result + ((this.discountDate == null) ? 0 : this.discountDate.hashCode());
	  return result;
  }
	/* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  
  public boolean equals(Object obj) {
	  if (this == obj)
		  return true;
	  if (obj == null)
		  return false;
	  if (getClass() != obj.getClass())
		  return false;
	  final ReceiptDiscount other = (ReceiptDiscount) obj;
	  if (this.discontName == null) {
		  if (other.discontName != null)
			  return false;
	  } else if (!this.discontName.equals(other.discontName))
		  return false;
	  if (Double.doubleToLongBits(this.discountAmount) != Double.doubleToLongBits(other.discountAmount))
		  return false;
	  if (this.discountDate == null) {
		  if (other.discountDate != null)
			  return false;
	  } else if (!this.discountDate.equals(other.discountDate))
		  return false;
	  return true;
  }
	
}
