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


import java.util.HashSet;
import java.util.Set;

import br.com.auster.om.reference.CatalogEntity;

/**
 * @hibernate.class
 *          table="REF_CARRIER_COMPANY"
 * 
 * @author framos
 * @version $Id$
 */
public class CarrierCompany extends CatalogEntity {

	
	
	// ---------------------------
	// Instance variables
	// ---------------------------
	
	private String name;
	private String marketingName;
	private String code;
	private String countryCode;
	private String regNumber;
	
	private Set addresses;
	private Set opCodes;
	
	
	
	// ---------------------------
	// Constructors
	// ---------------------------
	
	public CarrierCompany() {
		super();
	}
	
	public CarrierCompany(long _uid) {
		super(_uid);
	}



	// ---------------------------
	// Public methods
	// ---------------------------

	/**
	 * @hibernate.property
	 *            type="string"
	 *            
	 * @hibernate.column
	 *            name="COUNTRY_CODE"
	 *            length="5"
	 *            not-null="true"
	 *            unique-key="REF_CARRIER_COMPANY_UNIQUE_1"            
	 */
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String code) {
		this.countryCode = code;
	}
	
	/**
	 * @hibernate.property
	 *            type="string"
	 *            
	 * @hibernate.column
	 *            name="NATIONAL_CODE"
	 *            length="5"
	 *            not-null="true"
	 *            unique-key="REF_CARRIER_COMPANY_UNIQUE_1"            
	 */
	public String getNationalCode() {
		return code;
	}

	public void setNationalCode(String code) {
		this.code = code;
	}

	/**
	 * @hibernate.property
	 *            column="MARKETING_NAME"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getMarketingName() {
		return marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}

	/**
	 * @hibernate.property
	 *            column="INSTITUTIONAL_NAME"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property
	 *            column="REGISTRATION_NUMBER"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getRegistrationNumber() {
		return regNumber;
	}

	public void setRegistrationNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	
	/**
	 * @hibernate.collection-one-to-many
	 *            lazy="false"
	 *            class="br.com.auster.om.reference.model.CarrierAddress"
	 *            
	 * @hibernate.collection-key
	 *            column="CARRIER_ID"
	 *            not-null="true"               
	 *                        
	 * @hibernate.set
	 *            lazy="false"
	 * 	          cascade="all-delete-orphan"
	 *                       
	 */
	public Set getCompanyAddresses() {
		return addresses;
	}
	
	public void setCompanyAddresses(Set _addresses) {
		this.addresses = _addresses; 
	}

	public void addCompanyAddress(CarrierAddress _address) {
		if (this.addresses == null) {
			this.addresses = new HashSet();
		}
		this.addresses.add(_address);
	}
	
	/**
	 * @hibernate.collection-many-to-many
	 *            lazy="false"
	 *            column="FISCAL_OPCODE_ID"
	 *            class="br.com.auster.om.reference.model.FiscalOpCode"
	 *
	 * @hibernate.collection-key
	 *            column="CARRIER_ID"
	 *            
	 * @hibernate.set
	 * 	          cascade="all"
	 *            table="REF_CARRIER_OPCODES_ALLOWED"
	 */
	public Set getAllowedFiscalOpCodes() {
		return opCodes;
	}

	public void setAllowedFiscalOpCodes(Set _set) {
		opCodes = _set;
	}
	
	public void allowFiscalOpCode(FiscalOpCode _code) {
		if (opCodes == null) {
			opCodes = new HashSet();
		}
		opCodes.add(_code);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return 
			"[CarrierCompany] " +
			" uid=" + getUid() +
			" name=" + getName() +
			" markt.Name=" + getMarketingName() +
			" countryCode=" + getCountryCode() +
			" nationalCode=" + getNationalCode() +
			" reg.Number=" + getRegistrationNumber();
	}

}