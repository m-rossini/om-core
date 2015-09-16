/*
 * Copyright (c) 2004-2005 Auster Solutions do Brasil. All Rights Reserved.
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
 * Created on Sep 16, 2005
 */
package br.com.auster.om.pricing;

import java.sql.Timestamp;
import java.util.Arrays;

import br.com.auster.om.reference.CatalogEntity;

/**
 * @hibernate.class
 *          table="PRC_SERVICE_PRICE"
 *          
 * @author framos
 * @version $Id: ServicePrice.java 59 2005-10-25 16:59:10Z framos $
 */
public class ServicePrice extends CatalogEntity {

	

	// ---------------------------
	// Class constants
	// ---------------------------
	
	public static final String NEGOTIABLE_FLAG_TRUE  = "T";
	public static final String NEGOTIABLE_FLAG_FALSE = "F";
	
	public static final String[] PASSTHROUGH_FLAG = new String[] { "M", "P"};
	
	// ---------------------------
	// Instance variables
	// ---------------------------	
	
	private Service service;
	private String  pricingArea;
	private ServiceFreeUnits freeUnits;
	private Timestamp effectiveDate;
	private Timestamp expirationDate;
	private Timestamp updatedDate;
	private String scenario;
	private String chargeType;
	private String chargeTypeId;
	
	private Double initCost;
	private Double stepCost;
	private Double initQuantity;
	private Double initIncludedQuantity;
	
	private Double stepQuantity;
	private Double stepIncludedQuantity;
	private String initType;
	private String stepType;
	
	private TaxCategory taxCategory;
	
	private String negotiable;
	private String passthrough;
	
	private transient boolean negotiated;
	
	
	
	// ---------------------------
	// Constructors
	// ---------------------------
	
	public ServicePrice() {
		super();
	}
	
	public ServicePrice(long _uid) {
		super(_uid);
	}
	

	
	// ---------------------------
	// Public methods
	// ---------------------------
	
	/**
	 * @hibernate.property
	 *            column="CHARGE_TYPE"
	 *            type="string"
	 *            length="1"
	 *            not-null="false"
	 */		
	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @hibernate.property
	 *            column="EFFECTIVE_DATE"
	 *            type="timestamp"
	 *            not-null="true"
	 */		
	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @hibernate.property
	 *            column="EXPIRATION_DATE"
	 *            type="timestamp"
	 *            not-null="false"
	 */		
	public Timestamp getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @hibernate.many-to-one
	 * 			  column="FREE_UNITS_ID"
	 * 			  not-null="false"
	 * 			  outer-join="true"
	 */
	public ServiceFreeUnits getFreeUnits() {
		return freeUnits;
	}

	public void setFreeUnits(ServiceFreeUnits freeUnits) {
		this.freeUnits = freeUnits;
	}

	/**
	 * @hibernate.property
	 *            column="INITIAL_UNIT_VALUE"
	 *            type="double"
	 *            not-null="false"
	 */		
	public Double getInitCost() {
		return initCost;
	}

	public void setInitCost(Double initCost) {
		this.initCost = initCost;
	}
	
	/**
	 * @hibernate.property
	 *            column="INITIAL_UNIT_QTY"
	 *            type="double"
	 *            not-null="true"
	 */		
	public Double getInitQuantity() {
		return initQuantity;
	}

	public void setInitQuantity(Double initQuantity) {
		this.initQuantity = initQuantity;
	}
	
	/**
	 * @hibernate.property
	 *            column="INITIAL_UNIT_TYPE"
	 *            type="string"
	 *            length="10"
	 *            not-null="true"
	 */		
	public String getInitType() {
		return initType;
	}

	public void setInitType(String initType) {
		this.initType = initType;
	}

	/**
	 * @hibernate.property
	 *            column="NEGOTIABLE_FLAG"
	 *            type="string"
	 *            length="1"
	 *            not-null="false"
	 */		
	public String getNegotiable() {
		return negotiable;
	}
	
	public void setNegotiable(String negotiable) {
		this.negotiable = negotiable;
	}

	public boolean isNegotiable() {
		return ((negotiable != null) && 
				(negotiable.equalsIgnoreCase("T") || negotiable.equalsIgnoreCase("Y")));
	}

	/**
	 * @hibernate.property
	 *            column="PASSTHROUGH_FLAG"
	 *            type="string"
	 *            length="1"
	 *            not-null="true"
	 */		
	public String getPassThrough() {
		return (passthrough==null) ? "N": passthrough;
	}
	
	public void setPassThrough(String _passthrough) {
		if ( (_passthrough == null) || "".equals(_passthrough)) {
			this.passthrough = "N";
		} else {
			this.passthrough = _passthrough;
		}
	}

	public boolean isPassThrough() {		
		return ((passthrough != null) && (findPassthruFlagIndex() >= 0));
//				PASSTHROUGH_FLAG.equalsIgnoreCase(passthrough));
				//(passthrough.equalsIgnoreCase("T") || passthrough.equalsIgnoreCase("Y")));
	}
	
	protected int findPassthruFlagIndex() {
		return Arrays.binarySearch(PASSTHROUGH_FLAG, passthrough);
	}
	/**
	 * @hibernate.property
	 *            column="SP_AREA_ID"
	 *            type="string"
	 *            length="15"
	 *            not-null="true"
	 */		
	public String getPricingArea() {
		return pricingArea;
	}

	public void setPricingArea(String pricingArea) {
		this.pricingArea = pricingArea;
	}

	/**
	 * @hibernate.property
	 *            column="SCENARIO_ID"
	 *            type="string"
	 *            length="20"
	 *            not-null="true"
	 */		
	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	/**
	 * @hibernate.many-to-one
     *          column="SERVICE_ID"
     *          not-null="true"
     *          outer-join="true"
	 */
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * @hibernate.property
	 *            column="STEP_UNIT_VALUE"
	 *            type="double"
	 *            not-null="false"
	 */		
	public Double getStepCost() {
		return stepCost;
	}

	public void setStepCost(Double stepCost) {
		this.stepCost = stepCost;
	}

	/**
	 * @hibernate.property
	 *            column="STEP_UNIT_QTY"
	 *            type="double"
	 *            not-null="false"
	 */		
	public Double getStepQuantity() {
		return stepQuantity;
	}

	public void setStepQuantity(Double stepQuantity) {
		this.stepQuantity = stepQuantity;
	}			

	/**
	 * @hibernate.property
	 *            column="STEP_UNIT_TYPE"
	 *            type="string"
	 *            length="10"
	 *            not-null="false"
	 */		
	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	/**
	 * @hibernate.property
	 *            column="UPDATED_DATE"
	 *            type="timestamp"
	 *            not-null="true"
	 */			
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	/**
	 * @hibernate.many-to-one
	 * 			  column="TAX_CODE"
	 * 			  not-null="false"
	 * 			  outer-join="true"
	 */
	public TaxCategory getTaxCategory() {
		return taxCategory;
	}
	
	public void setTaxCategory(TaxCategory _tax) {
		this.taxCategory = _tax;
	}	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return 
			"[ServicePrice] " +
			" uid=" + getUid() +
			" service=" + getService().getServiceName() +
			" area=" + getPricingArea() +
			(getFreeUnits() != null ? " freeUnits" + getFreeUnits().getUid() : "") +
			" effectDate=" + getEffectiveDate() +
			" expireDate=" + getExpirationDate() +
			" updatedDate=" + getUpdatedDate() +
			" scenario=" + getScenario() +
			" chargeType=" + getChargeType() +
			( getTaxCategory() != null ? " rates=" + getTaxCategory().getTaxRates() : "") +
			" initQuantity=" + getInitQuantity() +
			" initType=" + getInitType() +
			" initCost=" + getInitCost() +
			" stepQuantity=" + getStepQuantity() +
			" stepType=" + getStepType() +
			" stepCost=" + getStepCost() +
			" negotiable=" + isNegotiable();
	}

	/**
	 * @hibernate.property
	 *            column="INITIAL_UNIT_INCL_QTY"
	 *            type="double"
	 *            not-null="true"
	 */	
	public Double getInitIncludedQuantity() {
		return this.initIncludedQuantity;
	}

	/**
	 * @param initIncludedQuantity
	 *            the initIncludedQuantity to set
	 */
	public void setInitIncludedQuantity(Double initIncludedQuantity) {
		this.initIncludedQuantity = initIncludedQuantity;
	}

	/**
	 * @hibernate.property column="STEP_UNIT_INCL_QTY" type="double"
	 *                     not-null="true"
	 */	
	public Double getStepIncludedQuantity() {
		return this.stepIncludedQuantity;
	}

	/**
	 * @param stepIncludedQuantity
	 *            the stepIncludedQuantity to set
	 */
	public void setStepIncludedQuantity(Double stepIncludedQuantity) {
		this.stepIncludedQuantity = stepIncludedQuantity;
	}
	
	public boolean isPriceNegotiated() {
		return this.negotiated;
	}
	public void setPriceNegotiated(boolean _flag) {
		this.negotiated = _flag;
	}

	/**
	 * @hibernate.property
	 *            column="CHARGE_TYPE_ID"
	 *            type="string"
	 *            length="25"
	 *            not-null="false"
	 */		
	public String getChargeTypeId() {
		return chargeTypeId;
	}

	public void setChargeTypeId(String chargeTypeId) {
		this.chargeTypeId = chargeTypeId;
	}
  
}
