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

import br.com.auster.om.reference.CatalogEntity;


/**
 * @hibernate.class
 *          table="PRC_SERVICE_FREE_UNITS"
 *           
 * @author framos
 * @version $Id: ServiceFreeUnits.java 59 2005-10-25 16:59:10Z framos $
 */
public class ServiceFreeUnits extends CatalogEntity {

	
	
	// ---------------------------
	// Instance variables
	// ---------------------------	
	
	private Double unitQuantity;
	private String unitType;
	
	private String shareMode;
	private String externalId;
	

	
	// ---------------------------
	// Constructors
	// ---------------------------	
	
	public ServiceFreeUnits() {
		super();
	}
	
	public ServiceFreeUnits(long _uid) {
		super(_uid);		
	}

	
	
	// ---------------------------
	// Public methods
	// ---------------------------	
	
	/**
	 * @hibernate.property
	 *            column="FREE_UNITS_EXTERNAL_ID"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 */	
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * @hibernate.property
	 *            column="FREE_UNITS_SHARE_MODE"
	 *            type="string"
	 *            length="1"
	 *            not-null="false"
	 */		
	public String getShareMode() {
		return shareMode;
	}

	public void setShareMode(String shareMode) {
		this.shareMode = shareMode;
	}

	/**
	 * @hibernate.property
	 *            column="FREE_UNITS_QTY"
	 *            type="double"
	 *            not-null="false"
	 */		
	public Double getFreeUnitsQuantity() {
		return unitQuantity;
	}

	public void setFreeUnitsQuantity(Double unitQuantity) {
		this.unitQuantity = unitQuantity;
	}

	/**
	 * @hibernate.property
	 *            column="FREE_UNITS_TYPE"
	 *            type="string"
	 *            length="10"
	 *            not-null="false"
	 */		
	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return 
			"[ServiceFreeUnits]" +
			" uid=" + getUid() +
			" type=" + getUnitType() +
			" quantity=" + getFreeUnitsQuantity() + 
			" mode=" + getShareMode() +
			" externalId=" + getExternalId();
	}
	
}
