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
 * Created on Jan 20, 2005
 */
package br.com.auster.om.invoice;

/**
 * @hibernate.class
 *              table="INV_SERVICE_PROVIDER"
 *          
 * <p><b>Title:</b> ServiceProvider</p>
 * <p><b>Description:</b> </p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: ServiceProvider.java 34 2005-08-26 15:25:38Z etirelli $
 */

public class ServiceProvider extends InvoiceModelObject {
	private static final long serialVersionUID = 1824369801744813298L;
	
	private String selectionCode = null;
	private String stateCode = null;
	private String juridicName = null;
	private String businessName = null;
	private Address address = null;
	private Identity nationalTaxCode = null;
	private Identity stateTaxCode = null;
	
	/**
	 * @return Returns the address.
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	/**
	 * @hibernate.property
	 *            column="BUSINESS_NAME"
	 *            type="string"
	 *            length="256"
	 *            not-null="false"
	 *            
	 * @return Returns the businessName.
	 */
	public String getBusinessName() {
		return businessName;
	}
	/**
	 * @param businessName The businessName to set.
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	/**
	 * @hibernate.property
	 *            column="JURIDIC_NAME"
	 *            type="string"
	 *            length="256"
	 *            not-null="true"
	 *            
	 * @return Returns the juridicName.
	 */
	public String getJuridicName() {
		return juridicName;
	}
	/**
	 * @param juridicName The juridicName to set.
	 */
	public void setJuridicName(String juridicName) {
		this.juridicName = juridicName;
	}
	/**
	 * @return Returns the nationalTaxCode.
	 */
	public Identity getNationalTaxCode() {
		return nationalTaxCode;
	}
	/**
	 * @param nationalTaxCode The nationalTaxCode to set.
	 */
	public void setNationalTaxCode(Identity nationalTaxCode) {
		this.nationalTaxCode = nationalTaxCode;
	}
	/**
	 * @hibernate.property
	 *            column="SELECTION_CODE"
	 *            type="string"
	 *            length="10"
	 *            not-null="true"
	 *            
	 * @return Returns the selectionCode.
	 */
	public String getSelectionCode() {
		return selectionCode;
	}
	/**
	 * @param selectionCode The selectionCode to set.
	 */
	public void setSelectionCode(String selectionCode) {
		this.selectionCode = selectionCode;
	}
	/**
	 * @hibernate.property
	 *            column="STATE_CODE"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 *            
	 * @return Returns the stateCode.
	 */
	public String getStateCode() {
		return stateCode;
	}
	/**
	 * @param stateCode The stateCode to set.
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	/**
	 * @return Returns the stateTaxCode.
	 */
	public Identity getStateTaxCode() {
		return stateTaxCode;
	}
	/**
	 * @param stateTaxCode The stateTaxCode to set.
	 */
	public void setStateTaxCode(Identity stateTaxCode) {
		this.stateTaxCode = stateTaxCode;
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
		if(o instanceof ServiceProvider) {
			ServiceProvider svcProvider = (ServiceProvider) o;
			if( (svcProvider.getSelectionCode() != null) &&
					(svcProvider.getStateCode() != null) &&
					(this.getSelectionCode() != null) &&
					(this.getStateCode() != null)) {
				ret = svcProvider.getSelectionCode().compareTo(this.getSelectionCode());
				if(ret == 0) {
					ret = svcProvider.getStateCode().compareTo(this.getStateCode());
				}
			}
		} else {
			throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
		}
		return ret;
		
	}
	
}
