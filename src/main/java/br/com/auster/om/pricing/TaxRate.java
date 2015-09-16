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
 * Created on 13/01/2006
 */
package br.com.auster.om.pricing;

import java.sql.Date;

import br.com.auster.om.reference.CatalogEntity;

/**
 * @hibernate.class
 *          table="PRC_TAX_RATE"
 *          
 * @author framos
 * @version $Id$
 */
public class TaxRate extends CatalogEntity {

	
	
	// ---------------------------
	// Instance variables
	// ---------------------------
	
	private double rate;
	private String externalId;
	private Date effectiveDate;
	private Date expirationDate;
	
	
	// ---------------------------
	// Constructors
	// ---------------------------
	
	public TaxRate() {
	}


	
	// ---------------------------
	// Public methods
	// ---------------------------
	
	/**
	 * @hibernate.property
	 *            column="EFFECTIVE_DATE"
	 *            type="date"
	 *            not-null="true"
	 */	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @hibernate.property
	 *            column="EXPIRATION_DATE"
	 *            type="date"
	 *            not-null="false"
	 */	
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @hibernate.property
	 *            column="RATE_EXTERNAL_ID"
	 *            type="string"
	 *            length="30"
	 *            not-null="true"
	 */	
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * @hibernate.property
	 *            column="TAX_RATE"
	 *            type="double"
	 *            not-null="true"
	 */	
	public double getTaxRate() {
		return rate;
	}

	public void setTaxRate(double rate) {
		this.rate = rate;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return 
			"[TaxRate]" +
			" uid:" + getUid() +
			" effectiveDate:" + getEffectiveDate() +
			" expirationDate:" + getExpirationDate() +
			" externalId:" + getExternalId() +
 			" rate:" + getTaxRate();
	}
}
