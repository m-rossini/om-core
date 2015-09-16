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
 *              table="INV_ADDRESS"
 *              
 *              
 * <p><b>Title:</b> Address</p>
 * <p><b>Description:</b> Represents a address</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: Address.java 34 2005-08-26 15:25:38Z etirelli $
 */
public class Address extends InvoiceModelObject {
	private static final long serialVersionUID = 6806235215173620376L;
	
	private String addressType 			= "";
	private String addressStreet		= "";
	private String addressNumber		= "";
	private String addressComplement	= "";
	private String neighborhood			= "";
	private String city					= "";
	private String state				= "";
	private String country				= "";
	private String postalCode			= "";
	private String postalCodeCompl		= "";
	
	
	/**
	 * @hibernate.property
	 *            column="COMPLEMENT"
	 *            type="string"
	 *            length="256"
	 *            not-null="false"
	 */
	public String getAddressComplement() {
		return addressComplement;
	}
	public void setAddressComplement(String addressComplement) {
		this.addressComplement = addressComplement;
	}
	
	/**
	 * @hibernate.property
	 *            column="STREET_NUMBER"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getAddressNumber() {
		return addressNumber;
	}
	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}
	
	/**
	 * @hibernate.property
	 *            column="STREET_NAME"
	 *            type="string"
	 *            length="256"
	 *            not-null="false"
	 */
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	
	/**
	 * @hibernate.property
	 *            column="ADDRESS_TYPE"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	
	/**
	 * @hibernate.property
	 *            column="CITY_NAME"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @hibernate.property
	 *            column="COUNTRY_NAME"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @hibernate.property
	 *            column="NEIGHBORHOOD"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	
	/**
	 * @hibernate.property
	 *            column="POSTAL_CODE"
	 *            type="string"
	 *            length="10"
	 *            not-null="false"
	 */
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	/**
	 * @hibernate.property
	 *            column="POSTAL_CODE_COMPL"
	 *            type="string"
	 *            length="10"
	 *            not-null="false"
	 */
	public String getPostalCodeCompl() {
		return postalCodeCompl;
	}
	public void setPostalCodeCompl(String postalCodeCompl) {
		this.postalCodeCompl = postalCodeCompl;
	}
	
	/**
	 * @hibernate.property
	 *            column="STATE_NAME"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
		if(o instanceof Address) {
			Address address = (Address) o;
			ret = address.getAddressType().compareTo(this.getAddressType());
			if(ret == 0) {
				ret = address.getPostalCode().compareTo(this.getPostalCode());
			}
			if(ret == 0) {
				ret = address.getPostalCodeCompl().compareTo(this.getPostalCodeCompl());
			}
			if(ret == 0) {
				ret = address.getCountry().compareTo(this.getCountry());
			}
			if(ret == 0) {
				ret = address.getState().compareTo(this.getState());
			}
			if(ret == 0) {
				ret = address.getCity().compareTo(this.getCity());
			}
			if(ret == 0) {
				ret = address.getAddressStreet().compareTo(this.getAddressStreet());
			}
			if(ret == 0) {
				ret = address.getAddressNumber().compareTo(this.getAddressNumber());
			}
			if(ret == 0) {
				ret = address.getAddressComplement().compareTo(this.getAddressComplement());
			}
		} else {
			throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
		}
		return ret;
		
	}
	
}
