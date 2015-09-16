/*
 * Copyright (c) 2004-2005 Auster Solutions. All Rights Reserved.
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
 * Created on 23/11/2005
 */
package br.com.auster.om.reference.facade.hibernate;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.apache.log4j.Logger;

import br.com.auster.common.log.LogFactory;
import br.com.auster.om.reference.facade.CarrierFacade;
import br.com.auster.om.reference.facade.ConfigurationException;
import br.com.auster.om.reference.facade.impl.HibernateFacadeBase;
import br.com.auster.om.reference.model.CarrierCompany;
import br.com.auster.om.reference.model.FiscalOpCode;

/**
 * @author framos
 * @version $Id$
 */
public class CarrierHibernateFacadeImpl extends HibernateFacadeBase 
                                        implements CarrierFacade {

	
		
	// ---------------------------
	// Class constants
	// ---------------------------
	
	private static final Logger log = LogFactory.getLogger(CarrierHibernateFacadeImpl.class);
	
	public static final String CARRIER_CODE_CONCAT_CHAR = ":";

	
	
	// ---------------------------
	// Instance variables
	// ---------------------------
	
	protected Map carriers;
	protected Map carriersByCodes;
	protected Map fiscalCodes;
	protected Map fiscalCodesByCode;
	 
	
	
	// ---------------------------
	// Public methods
	// ---------------------------	
	
	/**
	 * @see br.com.auster.om.reference.facade.CarrierFacade#getCarrier(long)
	 */
	public CarrierCompany getCarrier(long _uid) {
		log.debug("locating carrier with key [uid] = " + _uid);
		return (CarrierCompany) carriers.get(String.valueOf(_uid));
	}

	/**
	 * @see br.com.auster.om.reference.facade.CarrierFacade#getCarrier(java.lang.String, java.lang.String)
	 */
	public CarrierCompany getCarrier(String _countryCode, String _nationalCode) {
		String key = _countryCode + CARRIER_CODE_CONCAT_CHAR + _nationalCode;
		log.debug("locating carrier with key [country code + national code] = " + key);
		return (CarrierCompany) carriersByCodes.get(key);
	}

	/**
	 * @see br.com.auster.om.reference.facade.CarrierFacade#getAllFiscalCodes()
	 */
	public Set getAllFiscalCodes() {
		log.debug("creating set with all fiscal codes");
		HashSet set = new HashSet(fiscalCodes.values());
		return set;
	}

	/**
	 * @see br.com.auster.om.reference.facade.CarrierFacade#getFiscalCode(long)
	 */
	public FiscalOpCode getFiscalCode(long _uid) {
		log.debug("locating fiscal code with key [uid] = " + _uid);
		return (FiscalOpCode) fiscalCodes.get(String.valueOf(_uid));
	}

	/**
	 * @see br.com.auster.om.reference.facade.CarrierFacade#getFiscalCode(java.lang.String)
	 */
	public FiscalOpCode getFiscalCode(String _opCode) {
		log.debug("locating fiscal code with key [fiscalCode] = " + _opCode);
		return (FiscalOpCode) fiscalCodesByCode.get(_opCode);
	}
	
	/**
	 * @see br.com.auster.om.reference.facade.impl.HibernateFacadeBase#configure(java.lang.String,boolean)
	 */
	public void configure(String _configurationFile, boolean _encrypted) throws ConfigurationException {
		super.configure(_configurationFile, _encrypted);
		try {
			loadCache();
		} catch (HibernateException he) {
			throw new ConfigurationException("Error loading carrier information into cache", he);
		}
	}
	
	
	
	// ---------------------------
	// Private methods
	// ---------------------------	
	
	private void loadCache() throws HibernateException {
		// inits all cache maps
		carriers = new HashMap();
		carriersByCodes = new HashMap();
		fiscalCodes = new HashMap();
		fiscalCodesByCode = new HashMap();
		
		Session s = null;
		try {
			s = factory.openSession();
			// loading carrier list
			Criteria c = s.createCriteria(CarrierCompany.class);
			loadCarrierList(c.list());
			c = s.createCriteria(FiscalOpCode.class);
			loadFiscalOpCodes(c.list());
		} finally {
			s.close();
		}
	}
	
	private void loadCarrierList(Collection _list) throws HibernateException {
		log.debug("caching list of carriers");
		for (Iterator it=_list.iterator(); it.hasNext();) {
			CarrierCompany cc = (CarrierCompany) it.next();
			String key = cc.getCountryCode() + CARRIER_CODE_CONCAT_CHAR + cc.getNationalCode();
			carriers.put(String.valueOf(cc.getUid()), cc);
			carriersByCodes.put(key, cc);
			log.debug("carrier with uid/key = [" + cc.getUid() + "/" + key + "] added to cache");
		}
		log.debug("list of carriers cached");
	}
	
	private void loadFiscalOpCodes(Collection _list) throws HibernateException {
		log.debug("caching list of fiscal codes");
		for (Iterator it=_list.iterator(); it.hasNext();) {
			FiscalOpCode fc = (FiscalOpCode) it.next();
			fiscalCodes.put(String.valueOf(fc.getUid()), fc);
			fiscalCodesByCode.put(fc.getOpCode(), fc);
			log.debug("fiscal code with uid/key = [" + fc.getUid() + "/" + fc.getOpCode() + "] added to cache");
		}
		log.debug("list of fiscal codes cached");
	}
	
}
