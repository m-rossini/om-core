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
 * Created on 18/02/2007
 */
package br.com.auster.om.pricing;

/**
 * @author mtengelm
 *
 */
public class ServicePriceNegotiated extends ServicePrice {
	
	public static final String TAX_INCLUDED_YES = "Y";
	public static final String TAX_INCLUDED_NOT = "D";
	
	public static final String PRICE_ABSOLUTE = "A";
	public static final String PRICE_DISCOUNTED = "D";
	public static final String PRICE_INCREASED = "I";
	
	private String accountNumber;
	private String subscriptionId;
	private double discountPercent;
	private String taxIncludedFlag;
	private String absolutePriceFlag;
	
	public ServicePriceNegotiated() {
		
	}
//	public boolean isTaxIncluded()
//	public boolean isPriceAbsolut()

	/**
   * @return the absolutePriceFlag
   */
  public String getAbsolutePriceFlag() {
  	return this.absolutePriceFlag;
  }

	/**
   * @param absolutePriceFlag the absolutePriceFlag to set
   */
  public void setAbsolutePriceFlag(String absolutePriceFlag) {
  	this.absolutePriceFlag = absolutePriceFlag;
  }

	/**
   * @return the accountNumber
   */
  public String getAccountNumber() {
  	return this.accountNumber;
  }

	/**
   * @param accountNumber the accountNumber to set
   */
  public void setAccountNumber(String accountNumber) {
  	this.accountNumber = accountNumber;
  }

	/**
   * @return the discountPercent
   */
  public double getDiscountPercent() {
  	return this.discountPercent;
  }

	/**
   * @param discountPercent the discountPercent to set
   */
  public void setDiscountPercent(double discountPercent) {
  	this.discountPercent = discountPercent;
  }

	/**
   * @return the subscriptionId
   */
  public String getSubscriptionId() {
  	return this.subscriptionId;
  }

	/**
   * @param subscriptionId the subscriptionId to set
   */
  public void setSubscriptionId(String subscriptionId) {
  	this.subscriptionId = subscriptionId;
  }

	/**
   * @return the taxIncludedFlag
   */
  public String getTaxIncludedFlag() {
  	return this.taxIncludedFlag;
  }

	/**
   * @param taxIncludedFlag the taxIncludedFlag to set
   */
  public void setTaxIncludedFlag(String taxIncludedFlag) {
  	this.taxIncludedFlag = taxIncludedFlag;
  }

  public int hashCode() {
	  final int PRIME = 31;
	  int result = super.hashCode();
	  result = PRIME * result + ((this.absolutePriceFlag == null) ? 0 : this.absolutePriceFlag.hashCode());
	  result = PRIME * result + ((this.accountNumber == null) ? 0 : this.accountNumber.hashCode());
	  long temp;
	  temp = Double.doubleToLongBits(this.discountPercent);
	  result = PRIME * result + (int) (temp ^ (temp >>> 32));
	  result = PRIME * result + ((this.subscriptionId == null) ? 0 : this.subscriptionId.hashCode());
	  result = PRIME * result + ((this.taxIncludedFlag == null) ? 0 : this.taxIncludedFlag.hashCode());
	  return result;
  }

  public boolean equals(Object obj) {
	  if (this == obj)
		  return true;
	  if (!super.equals(obj))
		  return false;
	  if (getClass() != obj.getClass())
		  return false;
	  final ServicePriceNegotiated other = (ServicePriceNegotiated) obj;
	  if (this.absolutePriceFlag == null) {
		  if (other.absolutePriceFlag != null)
			  return false;
	  } else if (!this.absolutePriceFlag.equals(other.absolutePriceFlag))
		  return false;
	  if (this.accountNumber == null) {
		  if (other.accountNumber != null)
			  return false;
	  } else if (!this.accountNumber.equals(other.accountNumber))
		  return false;
	  if (Double.doubleToLongBits(this.discountPercent) != Double.doubleToLongBits(other.discountPercent))
		  return false;
	  if (this.subscriptionId == null) {
		  if (other.subscriptionId != null)
			  return false;
	  } else if (!this.subscriptionId.equals(other.subscriptionId))
		  return false;
	  if (this.taxIncludedFlag == null) {
		  if (other.taxIncludedFlag != null)
			  return false;
	  } else if (!this.taxIncludedFlag.equals(other.taxIncludedFlag))
		  return false;
	  return true;
  }

  public String toString() {
	  return super.toString() + ".TaxIncluded:" + this.getTaxIncludedFlag() + 
	  ".NegotType:" + this.getAbsolutePriceFlag() + ".Pct:" + this.getDiscountPercent() +
	  ".Account:" + this.getAccountNumber() + ".Subscription:" + this.getSubscriptionId();
  }
  
  
}
