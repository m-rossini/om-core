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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.auster.common.datastruct.RangeMap;
import br.com.auster.om.reference.CatalogEntity;

/**
 * @hibernate.class
 *          table="PRC_TAX_CATEGORY"
 *          
 * @author framos
 * @version $Id: TaxCategory.java 59 2005-10-25 16:59:10Z framos $
 */
public class TaxCategory extends CatalogEntity {

	

	// ---------------------------
	// Instance variables
	// ---------------------------
	
	private String taxCode;
	private Timestamp updatedDate;
	private Set taxRates;
	private Map taxRatesByExternalId;
	
	
	
	// ---------------------------
	// Constructors
	// ---------------------------
	
	public TaxCategory() {
		super();
		this.taxRates = new LinkedHashSet();
		this.taxRatesByExternalId = new LinkedHashMap();
	}

	public TaxCategory(long _uid) {
		super(_uid);
		this.taxRates = new LinkedHashSet();
		this.taxRatesByExternalId = new LinkedHashMap();
	}
	
	
	
	// ---------------------------
	// Public methods
	// ---------------------------
	
	/**
	 * @hibernate.property
	 *            column="TAX_CODE"
	 *            type="string"
	 *            length="30"
	 *            not-null="true"
	 */	
	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
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
	 * @hibernate.collection-one-to-many
	 *            lazy="false"
	 *            class="br.com.auster.om.pricing.TaxRate"
	 *            
     * @hibernate.set
     *              inverse="false"
     *              cascade="all-delete-orphan"
     *
     * @hibernate.collection-key
     *              column="TAX_CATEGORY_ID"
     *              not-null="true"
	 */
	public Set getTaxRates() {
		return taxRates;
	}
	
	public void setTaxRates(Set _rates) {
		taxRates = _rates;
		buildTaxRateMap(_rates);
	}
	
	/**
	 * Returns the <code>TaxRate</code> instance for the specified external id, using the current date as the 
	 * 	search period. Check out the {@link #getTaxRate(String, Date)} documentation for more details on how 
	 * 	the tax rate is found.
	 * 
	 * @param _externalId the tax rate external id
	 * 
	 * @return the tax rate for the current date and the external id specified
	 */
	public TaxRate getTaxRate(String _externalId) {
		return getTaxRate(_externalId, null);
	}
	
	/**
	 * Returns the <code>TaxRate</code>, for the specified external id, where the <code>_searchDate</code> parameter
	 * 	is between the effective and expiration date.
	 * If the external id does not exist, or if there is no tax rate defined for the specified search date, then <code>null</code>
	 * 	is returned. Likewise, if there is more than on tax rate for the search date, then the first one loaded from the database
	 * 	will be returned. 
	 *  
	 * @param _externalId the tax rate external id
	 * @param _sarchDate the date being searched
	 *  
	 * @return the tax rate for the sarched date and the external id specified
	 */
	public TaxRate getTaxRate(String _externalId, Date _searchDate) {
		if (_searchDate == null) {
			_searchDate = Calendar.getInstance().getTime();
		}
		RangeMap m = (RangeMap) taxRatesByExternalId.get(_externalId);
		if (m != null) {
			List rateList = m.get(_searchDate.getTime());
			if ((rateList != null) && (rateList.size() > 0)) {
				Collections.sort(rateList, new SortByEffectiveDate());
				return (TaxRate) rateList.get(0);
			}
		}
		return null;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return 
			"[TaxCategory]" +
			" uid:" + getUid() +
			" updatedDate:" + getUpdatedDate() +
			" code:" + getTaxCode() +
 			" #OfRates:" + getTaxRates().size();
	}

	
	
	// ---------------------------
	// Private methods
	// ---------------------------
	
	private void buildTaxRateMap(Set _rates) {
		if (taxRatesByExternalId == null) {
			taxRatesByExternalId = new LinkedHashMap();
		}
		taxRatesByExternalId.clear();
		// building map of ranges, for tax rates
		for (Iterator it=_rates.iterator(); it.hasNext();) {
			TaxRate r = (TaxRate) it.next();
			RangeMap m = (RangeMap) taxRatesByExternalId.get(r.getExternalId()); 
			if (m == null) {
				m = new RangeMap();
				taxRatesByExternalId.put(r.getExternalId(), m);
			}
			long start = r.getEffectiveDate().getTime();
			long end = r.getExpirationDate() == null ? Long.MAX_VALUE : r.getExpirationDate().getTime()+1;
			m.add(start, end, r);
		}
	}
	
	/**
	 * Sorts a list of tax rate elements by effective date, from the most recent to the least one (descending order).
	 */
	private class SortByEffectiveDate implements Comparator {

		public int compare(Object _t1, Object _t2) {
			TaxRate t1 = (TaxRate) _t1;
			TaxRate t2 = (TaxRate) _t2;
			return t2.getEffectiveDate().compareTo(t1.getEffectiveDate());
		}
	}
}
