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
 * Created on Mar 8, 2005
 */
package br.com.auster.om.pricing;

import java.util.List;
import java.util.Map;

import br.com.auster.om.invoice.Account;
import br.com.auster.om.invoice.ServiceDetail;
import br.com.auster.om.invoice.UsageDetail;
import br.com.auster.om.pricing.Service;
import br.com.auster.om.pricing.ServiceFreeUnits;
import br.com.auster.om.pricing.ServicePrice;
import br.com.auster.om.pricing.TaxCategory;
import br.com.auster.om.reference.facade.ReferenceFacades;
import br.com.auster.om.util.UnitConvertor;

/**
 * Defines the actions performed by a pricing engine implementation. 
 * <p>
 * Basically, aside of receiving configuration information in an external file - may be encrypted - it allows for 
 *  returning the pricing values for the current scenario or, as a convenience method, calculate the final cost added
 *  by the pre-configured tax rate.
 * <p>
 * The algorithm used to find the correct <code>ServicePrice</code> instance for the current scenario can be customized, and
 * 	can take into consideration the <code>UsageDetail</code> and <code>Service</code> being handled. 
 * <p>
 * 
 * @author framos
 * @version $Id: PricingEngine.java 55 2005-10-14 20:54:11Z framos $
 */
public interface PricingFacade extends ReferenceFacades {

	
	/**
	 * Returns the pricing information for the specified service charge. Each implementation should search for unique pricing values,
	 *   using the charge and service information passed as parameters. If there is no pricing value, then a 
	 *   <code>PricingInformationNotFoundException</code> exception will be raised.
	 * <p>
	 * Before calling this method make sure the specified service has pricing information in the reference tables, using the 
	 * {@link #hasSubscriptionFee()} method.
	 * 
	 * @param _charge the service charge 
	 * @param _service the service information
	 * 
	 * @return the pricing information for service subscription fee, based on the specified parameters
	 * 
	 * @throws SubscriptionFeeInformationNotFoundException if there is no pricing information for the specified charge and service
	 */
	public ServicePrice getSubscriptionFeeFor(ServiceDetail _charge, Service _service) throws SubscriptionFeeInformationNotFoundException;

	/**
	 * Same as {@link #getSubscriptionFeeFor(ServiceDetail, Service)}, where the <code>Service</code> attribute is defined using the
	 * 	<code>ServiceDetail</code> parameter.
	 * 
	 * @param _charge the service charge
	 * 
	 * @return the pricing information for service subscription fee, based on the specified parameters
	 * 
	 * @throws SubscriptionFeeInformationNotFoundException if there is no pricing information for the specified charge and service
	 */
	public ServicePrice getSubscriptionFeeFor(ServiceDetail _charge) throws SubscriptionFeeInformationNotFoundException;
	
	/**
	 * Identifies if the specified service change and information have any subscription fee registered in the pricing reference
	 *    tables.
	 *    
	 * @param _charge the service charge 
	 * @param _service the service information
	 * 
	 * @return if the charge and service pair have any pricing information 
	 */
	public boolean hasSubscriptionFee(ServiceDetail _charge, Service _service);
	
	/**
	 * Same as {@link #hasSubscriptionFee(ServiceDetail, Service)}, where the <code>Service</code> parameter is defined using the
	 * 	<code>ServiceDetail</code>.
	 * 
	 * @param _charge the service charge 
	 * 
	 * @return if the charge and service pair have any pricing information
	 */
	public boolean hasSubscriptionFee(ServiceDetail _charge);
		
	/**
	 * Returns the pricing information for the specified scenario. Each implementation can search for the unique pricing values using 
	 * 	the usage and service information passed as parameter. If there is no pricing value for the specified scenario, then a 
	 *  <code>PricingInformationNotFoundException</code> exception will be raised.
	 * </p>
	 * By using this method, the formula to calculate the final price must be handled by the calling object. As a convenience, a
	 * 	default formula is implemented in <code>getPriceFor()</code> method.
	 * 
	 * @param _usage the usage event data 
	 * @param _service the service related to such event
	 * 
	 * @return the pricing information 
	 * 
	 * @throws PricingInformationNotFoundException if no pricing information was found for the specified usage and service
	 */
	public ServicePrice getPriceFor(UsageDetail _usage, Service _service) throws PricingInformationNotFoundException;
	
	/**
	 * Same as {@link #getPriceFor(UsageDetail, Service)}, where the <code>Service</code> attribute is defined using the 
	 * {@link #getService(UsageDetail)} method.
	 *    
	 * @param _usage the usage event data 
	 * 
	 * @return the pricing information 
	 * 
	 * @throws PricingInformationNotFoundException if no pricing information was found for the specified usage and service
	 */
	public ServicePrice getPriceFor(UsageDetail _usage) throws PricingInformationNotFoundException;
	
	/**
	 * During {@link #getPriceFor(UsageDetail, Service)}, if the pricing information is found, the negotiated flag
	 * 	is enabled and the skip configuration flag is not turned on, then this method is called to load the 
	 *  customer-specific tariffs.
	 * <p>
	 * If the customer-specific pricing information is not found, then a <code>PricingInformationNotFoundException</code>
	 * 	exception is raised.
	 */
	public ServicePrice getNegotiatedPriceFor(UsageDetail _usage, Service _service, ServicePrice _price) throws PricingInformationNotFoundException;
	
	/**
	 * This method return the service detail negotiated by this subscription if it does exist.
	 * 
	 * @param serviceDetail
	 * @return
	 */
	public ServicePriceNegotiated getNegotiatedPriceForService(ServiceDetail serviceDetail);
	
	/***
	 * This method is intended to be implemented by low level concrete classes.
	 * What it does is to check if the negotiated map for an account is not empty.
	 * If it is empty, means there is not negotiation for that account.
	 * 
	 * If it is not empty it will lookup for a subscription.
	 * When found it will match the scenario ID from price list got in subscription lookup against the usage scenario.
	 * If not found, it means that there is no prioce negotiation for that subscription.
	 * 
	 * If matches are found it will return the ServicePrice that was negotiated. 
	 * If not found, it means there is no negotiation for that scenario.
	 * 
	 * @param _usage
	 * @return
	 * @throws PricingException
	 */
	public ServicePriceNegotiated getNegotiatedPriceFor(UsageDetail _usage) throws PricingException;
	/**
	 * Defines the criterias by which a usage event can have its cost calculated by the pricing engine implementation. This method
	 *  <strong>is not</strong> called during {@link #getPriceFor(UsageDetail, Service)} or {@link #calculatePriceFor(UsageDetail, Service)}.
	 *  It works as a convenience method to avoid <code>PricingInformationNotFoundException</code> exceptions when calling such 
	 *  methods. 
	 *  
	 * @param _usage the usage event data 
	 * @param _service the service related to such event
	 * 
	 * @return if the cost for the current usage event can be calculated
	 */
	public boolean canCalculatePrice(UsageDetail _usage, Service _service);
	
	/**
	 * Same as {@link #canCalculatePrice(UsageDetail, Service)}, where the <code>Service</code> attribute is defined using the 
	 * {@link #getService(UsageDetail)} method.
	 * 
	 * @param _usage the usage event data
	 *  
	 * @return if the cost for the current usage event can be calculated
	 */
	public boolean canCalculatePrice(UsageDetail _usage);
	
	/**
	 * Uses the <code>getPriceFor()</code> method to find the pricing information for the specified scenario. If found, then calculates
	 * 	the final cost.
	 * <p>	
	 * If no pricing information was found, then a <code>PricingInformationNotFoundException</code> exception is raised. In case there is not
	 *  enough information, for example, in the usage and service parameters, to calculate the final cost, then an <code>InvalidPricingCalculationStateException</code>
	 *  will be raised.
	 *   
	 * @param _usage the usage event data 
	 * @param _service the service related to such event
	 * 
	 * @return the final cost for the specified scenario
	 * 
	 * @throws PricingInformationNotFoundException if no pricing information was found for the specified usage and service
	 * @throws InvalidPricingCalculationStateException if there is not enough information to calculate the final cost
	 */
	public double calculatePriceFor(UsageDetail _usage, Service _service) throws PricingInformationNotFoundException, InvalidPricingCalculationStateException;

	/**
	 * Same as {@link #calculatePriceFor(UsageDetail, Service)}, where the <code>Service</code> attribute is defined using the 
	 * {@link #getService(UsageDetail)} method.
	 *    
	 * @param _usage the usage event data 
	 * 
	 * @return the final cost for the specified scenario
	 * 
	 * @throws PricingInformationNotFoundException if no pricing information was found for the specified usage and service
	 * @throws InvalidPricingCalculationStateException if there is not enough information to calculate the final cost
	 */
	public double calculatePriceFor(UsageDetail _usage) throws PricingInformationNotFoundException, InvalidPricingCalculationStateException;

	/**
	 * Calculates the price for the current usage, using the specified price information.
	 */
	public double calculatePriceFor(UsageDetail _usage, Service _service, ServicePrice _price) throws PricingInformationNotFoundException, InvalidPricingCalculationStateException;
	
	/**
	 * This helper method internalizes the calls for {@link #getPriceFor(UsageDetail, Service)} and 
	 * 	{@link #calculatePriceFor(UsageDetail, Service, ServicePrice)}, also setting the <code>ServicePrice</code>
	 *  and the calculated price into the specified usage detail parameter. 
	 * <p>
	 * The returning value is the final calculated value for the usage detail, or <code>Double.NaN</code> if 
	 * 	the price could not be calculated. 
	 * <p>
	 * If negotiated prices should be skipped, then set the <code>_ignoreNegotiables</code> flag to <code>true</code>. It will
	 *   set the usage with the <code>ServicePrice</code> for its scenario, but will skip calculating the correct
	 *   price, thus leaving the {@link UsageDetail#isRated()} flag <code>false</code>.
	 * 
	 * Exceptions of type {@link PricingInformationNotFoundException} or {@link InvalidPricingCalculationStateException} are
	 * 	handled internally in this method, and result in <code>NaN</code> as return value. Any other exceptions
	 *  are raised.
	 *   
	 * @param _usage the usage detail to use
	 * @param _ignoreNegotiables if should skip the calculation of negotiated tariffs
	 * 
	 * @return	the final cost of the usage, or <code>Double.NaN</code> when this value could not be calculated
	 */
	public double price(UsageDetail _usage, boolean _ignoreNegotiables);
	
	
	/***
	 * Method used to price a service detail.
	 * 
	 * In general, it is expected that concrete implementations of this method,
	 * sets all relevant attributes/fields of the Service Detail, regarding rating.
	 * 
	 * Among other, up to the implementation, below are the mandatory settings this methid MUST do:
	 * If there is no Service or no Service Price:
	 *    Rated flag must be set to false.
	 *    Calculated Fee must be set to Double.NaN
	 *    ServicePrice must be set to null.
	 *    
	 * If there is a Service Price to calculate service detail value, this methiod must set:
	 *    Rated Flag to true
	 *    Calculated Value to the one calculated.
	 *    Service Price with price used to calculate the value of the service.
	 *        
	 * @param service to be priced
	 * @return the calculated value of the service.
	 */
	public double price(ServiceDetail service);
	
	/**
	 * Uses the {@link #getSubscriptionFeeFor(ServiceDetail)} method to find the fee information for the scenario.
	 *   After applying the correct taxes, the final value is returned. This value represents a full month fee, so
	 *   if there is any pro-rata to be calculated, the caller component must take such actions.
	 * <p>
	 * If no pricing information is found, then a <code>SubscriptionFeeInformationNotFoundException</code> exception
	 *   is raised.
	 *     
	 * @param _charge the charged subscription information 
	 * @param _service the service this charge is associated with
	 * 
	 * @return the final cost of such service
	 * 
	 * @throws SubscriptionFeeInformationNotFoundException if no pricing information was found
	 */
	public double calculateSubscriptionFeeFor(ServiceDetail _charge, Service _service) throws SubscriptionFeeInformationNotFoundException;
	
	/**
	 * Such as {@link #calculateSubscriptionFeeFor(ServiceDetail, Service)}, except that the service instance
	 *   is found using the subscription charge parameter.
	 *   
	 * @param _charge the charged subscription information
	 * 
	 * @return the final cost of such service
	 * 
	 * @throws SubscriptionFeeInformationNotFoundException if no pricing information was found
	 */
	public double calculateSubscriptionFeeFor(ServiceDetail _charge) throws SubscriptionFeeInformationNotFoundException;
	
	/**
	 * Locates the correct tax rate to apply in the final cost calculation, for the current scenario. The <code>ServicePrice</code> instance
	 * 	previously loaded should point to a tax category, and using the usage and service objects, the correct rate, from the list of configured
	 *  rates for that specific tax category, will be elected.
	 * </p>
	 * If no tax rate if found for the current scenario, then a <code>PricingInformationNotFoundException</code> exception will be raised. This is just a
	 *  notification exception, since for most situations, having no tax rate configure does not means an incorrect scenario. So, it is like having a 1.0
	 *  tax rate.    
	 * 
	 * @param _price he pricing information previously loaded
	 * @param _usage the usage event data 
	 * @param _service the service related to such event
	 * 
	 * @return the tax rate to apply 
	 * 
	 * @throws PricingInformationNotFoundException if no tax rate was found
	 */
	public double getTaxRate(ServicePrice _price, UsageDetail usage, Service _service) throws PricingInformationNotFoundException;

	/**
	 * Same as {@link #getTaxRate(ServicePrice, UsageDetail, Service)}, except that this one
	 *   works with a subscription fee charge.
	 *    
	 * @param _price he pricing information previously loaded
	 * @param _charge subscription fee information
	 * @param _service the service related to such event
	 * 
	 * @return the tax rate to apply 
	 * 
	 * @throws PricingInformationNotFoundException if no tax rate was found
	 */
	public double getTaxRate(ServicePrice _price, ServiceDetail _charge, Service _service) throws PricingInformationNotFoundException;
	
	public double getTaxRate(ServiceDetail _charge) throws PricingInformationNotFoundException;
	
	/**
	 * Returns a <code>TaxCategory</code> instance, with all taxes for the specified unique identifier. If the <code>_uid</code> parameter
	 * 	does not point to a tax category, then <code>null</code> is returned.
	 * 
	 * @param _uid the unique identifier of a tax category instance
	 * 
	 * @return the <code>TaxCategory</code> for the specified id
	 */
	public TaxCategory getTaxCategory(long _uid);
	
	/**
	 * Same as {@link #getTaxCategory(long)}, but instead of using the unique identifier, the search criteria is the
	 * 	<strong>tax code</strong>. Although its not the primary key for tax categories, the tax code is unique across
	 * 	all categories. 
	 * 
	 * @param _taxCode the tax code searched for
	 * 
	 * @return the <code>TaxCategory</code> for the specified id
	 */
	public TaxCategory getTaxCategory(String _taxCode);
	
	/**
	 * Creates a <code>Service</code> instance, with the data of the service related to the current usage event. If the service could
	 * 	not be located, then <code>null</code> is returned. This instance can be used to find the correct pricing information for the 
	 *  usage event, for example. Also, note that for finding the price or calculating the final cost of an usage event, there is always
	 *  a convenience method that internally loads the correct service instance. 
	 *  
	 * @param _usage the usage event data
	 * 
	 * @return the related service for this usage event, or <code>null</code>
	 */
	public Service getServiceFor(UsageDetail _usage);
	
	/**
	 * Creates a <code>Service</code> instance, with the data of the service related to the current service charge. If the service could
	 * 	 not be located, then <code>null</code> is returned. This instance can be used to find the correct pricing information for the 
	 *   service charge, for example. Also, note that for finding the subscription fee, there is always  a convenience method that 
	 *   internally loads the correct service instance. 
	 *  
	 * @param _charge the service charge
	 * 
	 * @return the related service for this service charge or <code>null</code>
	 */
	public Service getServiceFor(ServiceDetail _charge);
	
	/**
	 * Returns the <code>Service</code> instance pointed by the specified unique identifier. If such parameter
	 * 	does not point to an existing service, then <code>null</code> is returned.
	 * 
	 * @param _uid the service unique identifier
	 * 
	 * @return the related service for this unique identifier, or <code>null</code>
	 */
	public Service getService(long _uid);

	/**
	 * Same as {@link #getService(long)}, but instead of using the unique identifier, the search criteria is the
	 * 	<strong>external id</strong>. This external id is an unique key across services.. If none is found, then 
	 *  an empty list will be returned.   
	 * 	 
	 * @param _externalId the external id of the service searched for
	 * 
	 * @return the related service for this unique identifier, or <code>null</code>
	 */
	public Service getService(String _externalId);

	/**
	 * Same as {@link #getService(long)}, but instead of using the unique identifier, the search criteria is the
	 * 	<strong>(external id, service name, service description)</strong> trio. This is a unique key across services. If none is found, then 
	 *  an empty list will be returned.
	 *
	 * @param _externalId the external id of the service searched for
	 * @param _serviceName name of the service (svc_name)
	 * @param _serviceDescription description of the service (caption_text)
	 *
	 * @return the related service for this unique identifier, or <code>null</code>
	 */
	public Service getService(String _externalId, String _serviceName, String _serviceDescription);

	/**
	 * Defines if the current usage event can be associated with a pricing information. If this method returns <code>false</code>, then
	 * 	the reference database is not completely populated or the usage event object was wrongly created.
	 *  
	 * @param _usage the usage event data 
	 * @param _service the service related to such event
	 * 
	 * @return if the usage and service guides to a pricing information
	 */
	public boolean hasPrice(UsageDetail _usage, Service _service);
	
	/**
	 * Same as {@link #hasPrice(UsageDetail, Service)}, where the <code>Service</code> attribute is defined using the 
	 * {@link #getService(UsageDetail)} method.
	 * 
	 * @param _usage the usage event data
	 *  
	 * @return if the usage and service guides to a pricing information
	 */
	public boolean hasPrice(UsageDetail _usage);
	
	/**
	 * Returns the <code>ServiceFreeUnits</code> instance pointed by the specified unique identifier. If such parameter
	 * 	does not point to an existing free units record, then <code>null</code> is returned.
	 * 
	 * @param _uid the free units record unique identifier
	 * 
	 * @return the related free units record for this unique identifier, or <code>null</code>
	 */
	public ServiceFreeUnits getServiceFreeUnit(long _uid);
	
	/**
	 * Same as {@link #getServiceFreeUnits(long)}, but instead of using the unique identifier, the search criteria is the
	 * 	<strong>external id</strong>. Since this external id is not an unique key across free units, all instances 
	 *  that match the specified value will be returned. If none is found, then an empty list will be returned. 
	 * 	 
	 * @param _externalId the external id of the free units record searched for
	 * 
	 * @return the list of matching services
	 */
	public List getServiceFreeUnits(String _externalId);
	
	/**
	 * Returns the unit convertor instance used by this pricing engine, to convert amounts to the unit type used in the pricing   
	 * 	records. This unit convertor instance is already initialized with the configured convertion map.
	 * 
	 * @return the unit convertor instance of this pricing engine 
	 */
	public UnitConvertor getLocalUnitConvertor();
	
	/***
	 * This method will return a List with Service Prices Negoatiated for the given account.
	 * If the account has NOT negotiated prices this method MUST return an empty List, 
	 * it cannot in any circumstances return null
	 * @param account
	 * @return
	 */
	public List getAccountNegotiatedPrices(Account account);
	
	/***
	 * Gets all the negotiated prices object for a given account.
	 * It returns a Map, where the key is a subscription where the value is the
	 * negotiated service
	 * @param account
	 * @return
	 */
	public Map getAccountNegotiatedPricesBySubscription(Account account) throws PricingException;
}