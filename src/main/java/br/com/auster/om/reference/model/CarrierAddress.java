/*
 * Copyright (c) 2004 TTI Tecnologia. All Rights Reserved.
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
 * Created on Nov 22, 2005
 */
package br.com.auster.om.reference.model;


import br.com.auster.om.reference.CatalogEntity;

/**
 * @hibernate.class
 *          table="REF_CARRIER_ADDRESS"
 * 
 * @author framos
 * @version $Id$
 */
public class CarrierAddress extends CatalogEntity {

	
	
	// ---------------------------
	// Instance variables
	// ---------------------------
	
	private String address;
	private String number;
	private String complement1;
	private String complement2;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	
	
	
	// ---------------------------
	// Constructors
	// ---------------------------
	
	public CarrierAddress() {
		super();
	}
	
	public CarrierAddress(long _uid) {
		super(_uid);
	}



	// ---------------------------
	// Public methods
	// ---------------------------

	/**
	 * @hibernate.property
	 *            column="STREET_NAME"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getStreetName() {
		return address;
	}

	public void setStreetName(String address) {
		this.address = address;
	}

	/**
	 * @hibernate.property
	 *            column="STREET_NUMBER"
	 *            type="string"
	 *            length="20"
	 *            not-null="false"
	 */
	public String getAddressNumber() {
		return number;
	}

	public void setAddressNumber(String number) {
		this.number = number;
	}
	
	/**
	 * @hibernate.property
	 *            column="COMPLEMENT_1"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getComplement1() {
		return complement1;
	}

	public void setComplement1(String complement1) {
		this.complement1 = complement1;
	}

	/**
	 * @hibernate.property
	 *            column="COMPLEMENT_2"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getComplement2() {
		return complement2;
	}

	public void setComplement2(String complement2) {
		this.complement2 = complement2;
	}

	/**
	 * @hibernate.property
	 *            column="CITY_NAME"
	 *            type="string"
	 *            length="64"
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
	 *            column="STATE_CODE"
	 *            type="string"
	 *            length="10"
	 *            not-null="false"
	 */
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @hibernate.property
	 *            column="COUNTRY_NAME"
	 *            type="string"
	 *            length="32"
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
	 *            column="ZIP_CODE"
	 *            type="string"
	 *            length="20"
	 *            not-null="false"
	 */
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return 
		"[CarrierAddress] " +
		" uid=" + getUid() +
		" street=" + getStreetName() +
		" number=" + getAddressNumber() +
		" complement1=" + getComplement1() +
		" complement2=" + getComplement2() +
		" city=" + getCity() +
		" state=" + getState() +
		" country=" + getCountry() +
		" zipCode=" + getZipCode();
	}
}