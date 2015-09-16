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
 * Created on Feb 1, 2005
 */
package br.com.auster.om.invoice;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import br.com.auster.om.pricing.ServicePrice;
import br.com.auster.om.pricing.ServicePriceNegotiated;
import br.com.auster.om.util.UnitCounter;

/**
 * @hibernate.class
 *              table="INV_USAGE_DETAIL"
 *              discriminator-value="DETAIL"
 *              
 * @hibernate.discriminator
 *              column="USAGE_TYPE"
 *              type="string"
 *              length="10"
 *              not-null="true"
 *              
 *              
 * <p><b>Title:</b> UsageDetail</p>
 * <p><b>Description:</b> </p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: UsageDetail.java 455 2007-06-28 23:35:12Z mtengelm $
 * 
 * @since 14/ABR/2007
 * Added getAmountRatio().
 * 
 */
public class UsageDetail extends SectionDetail implements Cloneable {
	private static final long serialVersionUID = 5815517554054919220L;
	private static final Logger log = Logger.getLogger(UsageDetail.class);
	public static final String OBJECT_TYPE_COMPLETE  = "C";
	public static final String OBJECT_TYPE_PARTIAL   = "P";
	public static final String OBJECT_TYPE_AGGREGATE = "A";

	private Date   datetime;
	private String originalDatetime;
	private String originalUsageDuration;
	private String originalUsageBilledUnits;
	// This next attribute doesnot demand an originalXXX version
	//   since its calculated at runtime 
	private Date   endDatetime;
	private String originSID;
	private int    callDirection;
	private String destinationCity;
	private String destinationState;
	private String destinationCountry;
	private String calledNumber;
	private String svcId;
	private String svcPlan;
	
	public static final String CALLINDICATOR_OUTGOING = "1";
	public static final String CALLINDICATOR_INCOMING = "2";	
	private String callIndicator;
	private String originCity;
	private String originState;
	private String originCountry;
	private String channelId;
	private String groupId;
	private UnitCounter duration;
	private UnitCounter billedUnits;
	private String tariff;
	private String direction;
	private String type;
	private String step;
	private String area;
		
	/* GPORTUGA - 20071129 - BEGIN - Changes due to P8 new fields */
	private int splitPart;
	private int splitTotal;
	
	private boolean displacement;
	private double taxRate;
	private UnitCounter realDuration;
	private Date realEndDateTime;
	/* GPORTUGA - 20071129 - END */

	private WeakReference next;

	// Attributes filled by the system by inference from other fields 
	private String callClass;
	private String callSubclass;
	private String tariffClass;

	// This is a classification attribute. Expected values are:
	// "C" - full event, "P" - partial event, 
	// "A" - virtual event generated by the system to agregate partial events
	private String objectType;
	// in case of partial events, this nbr will indicate a sequence number inside the event
	// in case of virtual events, this nbr will indicate the total number of events that were agregated
	private int    partNumber;
	// in case this is a OBJECT_TYPE_AGGREGATE object, this is the list of the parts that belongs to this object
	private Set    parts;
	// in case of "P" objects, this is a weak reference to the agregated object
	private WeakReference aggregateObject;
	
	/***
	 * Constants to be used with methods:
	 * isLocalCall()
	 * isLongDistanceCall()
	 * isInternationlCall()
	 */
	public static final int LOCAL_CALL_INDICATOR = 1;
	public static final int LONG_DISTANCE_CALL_INDICATOR = 2;
	public static final int INTERNATIONAL_CALL_INDICATOR = 4;
	private int localCallIndicator;
	
	/***
	 * Constants for indicating if this is a HOME or ROAM call.
	 */
	public static final int HOME_CALL = 1;
	public static final int ROAMING_CALL = 2;
	private int homeCallIndicator;
	
	
	/****
	 * Cosntatns to indicate the Charging type for an Usage Event.
	 * Valid values are the ones below:
	 */
	public static final String FREEIND_CHARGED = "U";	
	public static final String FREEIND_FREE = "F";
	public static final String FREEIND_INCLUDED = "I";
	public static final String FREEIND_PROMOTION = "M";	
	public static final String FREEIND_DEFAULT = " ";
	private String freeIndicator;

	
	public static final int COLLECT_CALL = 1;
	public static final int NOT_COLLECT_CALL = 2;
	private int collectCallIndicator = NOT_COLLECT_CALL;
	
	//*******************************************
	//GUIDING AND RATING INFO AND FLAGS
	//*******************************************
	
	//Used by Guiding Rules for tracking purposes.
	/***
	 * Indicates if a Usage was guided or not
	 */
	private boolean guided = false;
	/***
	 * If a usage has being guided, this fields indicates which rule has guided the current usage
	 */
	private String guideRule = null;

	// price info and flags
	/***
	 * Stores the service price associated with this usage
	 */
	private ServicePrice price;
	
	/***
	 * Indicates if a usage has being rated.
	 * There is a lot of reasons a usage was not rated, including but not limited to:
	 * 1.Service Not Found
	 * 2.Price Not Found (Not found or expired)
	 * 3.It is a PassThru rating
	 * 4.It is negotiable price, BUT the negotiable price engine is not active
	 */
	private boolean rated;
	
	/***
	 * The Billcheckout calculated value for this usage
	 */
	private double calculatedValue;	
	
	/***
	 * Indicates if the service exists
	 */
	private boolean serviceExists=false;	
	
	public UsageDetail() {
		this.parts = null;
		init();
	}

	private void init() {
		super.setUsage(true);
//		this.setUsage(true);
		this.partNumber=1;
//		this.setPartNumber(1);
		this.objectType=OBJECT_TYPE_COMPLETE;
//		this.setObjectType(OBJECT_TYPE_COMPLETE);
		this.rated=false;
	}

	/**
	 * @hibernate.property
	 *            column="CALL_DIRECTION"
	 *            type="integer"
	 *            not-null="false"
	 */
	public int getCallDirection() {
		return callDirection;
	}
	public void setCallDirection(int callDirection) {
		this.callDirection = callDirection;
	}

	/**
	 * @hibernate.property
	 *            column="CALLED_NUMBER"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getCalledNumber() {
		return calledNumber;
	}
	public void setCalledNumber(String calledNumber) {
		this.calledNumber = calledNumber;
	}
	public boolean isCalledNumberEmpty() {
		return ( (this.calledNumber == null) || ("".equals(this.calledNumber)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="CALL_INDICATOR"
	 *            type="string"
	 *            length="12"
	 *            not-null="false"
	 */
	public String getCallIndicator() {
		return callIndicator;
	}
	public void setCallIndicator(String callIndicator) {
		this.callIndicator = callIndicator;
	}
	public boolean isCallIndicatorEmpty() {
		return ( (this.callIndicator == null) || ("".equals(this.callIndicator)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="CHANNEL_ID"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public boolean isChannelIdEmpty() {
		return ( (this.channelId == null) || ("".equals(this.channelId)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="CALL_DATETIME"
	 *            type="timestamp"
	 *            not-null="false"
	 */
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	/**
	 * @hibernate.property
	 *            column="SRC_CALL_DATETIME"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 *            
	 * This attributes handles the unformatted version of the
	 * 	{@link #datetime}  
	 */
	public String getOriginalDatetime() {
		return originalDatetime;
	}
	public void setOriginalDatetime(String _originalString) {
		this.originalDatetime = _originalString;
	}	
	public boolean isOriginalDatetimeEmpty() {
		return ( (this.originalDatetime == null) || ("".equals(this.originalDatetime)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="DESTINATION_CITY"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public boolean isDestinationCityEmpty() {
		return ( (this.destinationCity == null) || ("".equals(this.destinationCity)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="DESTINATION_COUNTRY"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getDestinationCountry() {
		return destinationCountry;
	}
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}
	public boolean isDestinationCountryEmpty() {
		return ( (this.destinationCountry == null) || ("".equals(this.destinationCountry)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="DESTINATION_STATE"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getDestinationState() {
		return destinationState;
	}
	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}
	public boolean isDestinationStateEmpty() {
		return ( (this.destinationState == null) || ("".equals(this.destinationState)) ) ? true : false;
	}
	/**
	 * @deprecated Use {@link #getUsageDuration()} instead
	 */
	public UnitCounter getDuration() {
		return getUsageDuration();
	}

	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="DURATION_"
	 *            
	 */
	public UnitCounter getUsageDuration() {
		return duration;
	}

	/**
	 * @deprecated Use {@link #setUsageDuration(UnitCounter)} instead
	 */
	public void setDuration(UnitCounter duration) {
		setUsageDuration(duration);
	}

	public void setUsageDuration(UnitCounter duration) {
		this.duration = duration;
		this.endDatetime = null; // invalidating any previous date/time
	}

	/**
	 * @hibernate.property
	 *            column="ORIGIN_CITY"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 *            
	 */
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public boolean isOriginCityEmpty() {
		return ( (this.originCity == null) || ("".equals(this.originCity)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="ORIGIN_SID"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getOriginSID() {
		return originSID;
	}
	public void setOriginSID(String originSID) {
		this.originSID = originSID;
	}
	public boolean isOriginSIDEmpty() {
		return ( (this.originSID == null) || ("".equals(this.originSID)) ) ? true : false;
	}
	
	public boolean isCaptionEmpty() {
		return ( (this.getCaption() == null) || ("".equals(this.getCaption())) ) ? true : false;
	}
	
	public boolean isOriginalTotalAmountEmpty() {
		return ( (this.getOriginalTotalAmount() == null) || ("".equals(this.getOriginalTotalAmount())) ) ? true : false;
	}
	
	/**
	 * @hibernate.property
	 *            column="ORIGIN_STATE"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getOriginState() {
		return originState;
	}
	public void setOriginState(String originState) {
		this.originState = originState;
	}
	public boolean isOriginStateEmpty() {
		return ( (this.originState == null) || ("".equals(this.originState)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="SERVICE_PLAN"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getSvcPlan() {
		return svcPlan;
	}
	public void setSvcPlan(String svcPlan) {
		this.svcPlan = svcPlan;
	}
	public boolean isSvcPlanEmpty() {
		return ( (this.svcPlan == null) || ("".equals(this.svcPlan)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="SERVICE_ID"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getSvcId() {
		return svcId;
	}
	public void setSvcId(String svcId) {
		this.svcId = svcId;
	}
	public boolean isSvcIdEmpty() {
		return ( (this.svcId == null) || ("".equals(this.svcId)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="GROUP_ID"
	 *            type="string"
	 *            length="512"
	 *            not-null="false"
	 */
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public boolean isGroupIdEmpty() {
		return ( (this.groupId == null) || ("".equals(this.groupId)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="ORIGIN_CONTRY"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	public boolean isOriginCountryEmpty() {
		return ( (this.originCountry == null) || ("".equals(this.originCountry)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="TARIFF"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.tariff = tariff;    
	}
	public boolean isTariffEmpty() {
		return ( (this.tariff == null) || ("".equals(this.tariff)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="CALL_CLASS"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getCallClass() {
		return callClass;
	}
	public void setCallClass(String callClass) {
		this.callClass = callClass;
	}
	public boolean isCallClassEmpty() {
		return ( (this.callClass == null) || ("".equals(this.callClass)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="CALL_SUBCLASS"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getCallSubclass() {
		return callSubclass;
	}
	public void setCallSubclass(String callSubclass) {
		this.callSubclass = callSubclass;
	}
	public boolean isCallSubclassEmpty() {
		return ( (this.callSubclass == null) || ("".equals(this.callSubclass)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="TARIFF_CLASS"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getTariffClass() {
		return tariffClass;
	}
	public void setTariffClass(String tariffClass) {
		this.tariffClass = tariffClass;
	}
	public boolean isTariffClassEmpty() {
		return ( (this.tariffClass == null) || ("".equals(this.tariffClass)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="OBJECT_TYPE"
	 *            type="string"
	 *            length="1"
	 *            not-null="false"
	 *            
	 *  This is a classification attribute. Expected values are:
	 *  
	 *  <li> "C" - full event </li> 
	 *  <li> "P" - partial event </li>
	 *  <li> "A" - virtual event generated by the system to agregate partial events </li>
	 *            
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 *  This is a classification attribute. Expected values are:
	 *  
	 *  <li> "C" - full event </li> 
	 *  <li> "P" - partial event </li>
	 *  <li> "A" - aggregate event generated by the system to agregate partial events </li>
	 * 
	 * @param objectType
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public boolean isObjectTypeEmpty() {
		return ( (this.objectType == null) || ("".equals(this.objectType)) ) ? true : false;
	}
	/**
	 * @hibernate.property
	 *            column="PART_NUMBER"
	 *            type="integer"
	 *            not-null="false"
	 * 
	 * This attribute meaning depends on the value of the object type attribute:
	 * 
	 * <li><b>If object type is OBJECT_TYPE_COMPLETE:</b> this is always 1</li> 
	 * <li><b>If object type is OBJECT_TYPE_PARTIAL:</b> this will indicate a sequence number for this partial event</li> 
	 * <li><b>If object type is OBJECT_TYPE_AGGREGATE:</b> this will indicate the total number of partial events 
	 * agregated in this virtual event</li> 
	 *            
	 */
	public int getPartNumber() {
		return partNumber;
	}

	/**
	 * This attribute meaning depends on the value of the object type attribute:
	 * 
	 * <li><b>If object type is OBJECT_TYPE_COMPLETE:</b> this is always 1</li> 
	 * <li><b>If object type is OBJECT_TYPE_PARTIAL:</b> this will indicate a sequence number for this partial event</li> 
	 * <li><b>If object type is OBJECT_TYPE_AGGREGATE:</b> this will indicate the total number of partial events 
	 * agregated in this virtual event</li>
	 *  
	 * @param partNumber
	 */
	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * @hibernate.set
	 *              inverse="true"
	 *
	 * @hibernate.collection-key
	 *              column="aggregate_usage_id"
	 *
	 * @hibernate.collection-one-to-many
	 *              class="br.com.auster.om.invoice.UsageDetail"
	 *
	 * @return
	 */
	public Set getParts() {
		return (this.parts != null) ? this.parts : Collections.EMPTY_SET;
	}

	/**
	 * Adds a partial event to this aggregate object
	 * 
	 * @param part
	 */
	public void addPart(UsageDetail part) {
		this.setObjectType(OBJECT_TYPE_AGGREGATE);
		part.setObjectType(OBJECT_TYPE_PARTIAL);
		if(this.parts == null) {
			this.setParts(new HashSet());
		}
		this.parts.add(part);
		part.setAggregateObject(this);
		this.setPartNumber(this.getPartNumber()+1);
		part.setPartNumber(this.getPartNumber());
		if(this.getDuration().getType().equals(UnitCounter.TIME_COUNTER) ||
				this.getDuration().getType().equals(UnitCounter.DATA_COUNTER)) {
			if(this.getCaption().contains("ADICIONAL")) {
				this.setDuration((UnitCounter) part.getDuration().clone());
				this.setCaption(part.getCaption());
				this.setSvcPlan(part.getSvcPlan());
			} else if(this.getDuration().getType().equals(part.getDuration().getType()) &&
					(!part.getCaption().contains("ADICIONAL"))) {
				this.getDuration().addUnits(part.getDuration().getUnits());
			}
		} else if(part.getDuration().getType().equals(UnitCounter.TIME_COUNTER) ||
				part.getDuration().getType().equals(UnitCounter.DATA_COUNTER)) {
			this.setDuration((UnitCounter)part.getDuration().clone());
			this.setCaption(part.getCaption());
			this.setSvcPlan(part.getSvcPlan());
		}
		this.setTotalAmount(this.getTotalAmount()+part.getTotalAmount());
	}

	/**
	 * This is a helper method that adds details to an aggregate usage detail in 
	 * a similar way as addPart(), but it does not make any attribute values
	 * checking or modification.
	 * 
	 * @param detail
	 */
	public void addDetail(UsageDetail detail) {
		if(this.parts == null) {
			this.parts = new HashSet();
		}
		detail.setInvoice(this.getInvoice());
		this.parts.add(detail);
		detail.setInvoice(this.getInvoice());
	}

	/**
	 * @param parts
	 */
	public void setParts(Set parts) {
		this.parts = parts;
	}

	/**
	 * Removed @ from tag bellow do disable bidirectional relationship 
	 * hibernate.many-to-one
	 *              name="aggregateObject"
	 *              class="br.com.auster.om.invoice.UsageDetail"
	 *              column="aggregate_object"
	 * 
	 * A weak reference to the aggregate object in case this is a partial usage detail
	 */
	public UsageDetail getAggregateObject() {
		return (UsageDetail) ((this.aggregateObject != null) ? this.aggregateObject.get() : null);
	}

	/**
	 * A weak reference to the aggregate object in case this is a partial usage detail
	 * 
	 * @param aggregate
	 */
	public void setAggregateObject(UsageDetail aggregate) {
		this.aggregateObject = (aggregate != null) ? new WeakReference(aggregate) : null;
	}

	/**
	 * Returns the end datetime for a usage
	 * @deprecated Use {@link #getUsageEndDateTime()} instead
	 * @return A Date
	 */
	public Date getEndDatetime() {
		return this.getUsageEndDatetime();
	}

	/***
	 * Replaces the now deprecated method {@link #getEndDateTime()}
	 * 
	 * @return A Date representing the date and time of this current call.
	 */
	public Date getUsageEndDatetime() {
		if((this.endDatetime == null) && 
				(this.getUsageDuration() != null) && 
				(this.getDatetime() != null) &&
				(this.getUsageDuration().getType().equals(UnitCounter.TIME_COUNTER))) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.getDatetime());
			cal.add(Calendar.SECOND, (int)this.getUsageDuration().getSeconds());
			this.endDatetime = cal.getTime();
		}
		return this.endDatetime;
	}

	public UsageDetail getNextUsage() {
		if (next != null) {
			return (UsageDetail) next.get();
		}
		return null;
	}

	public void setNextUsage(UsageDetail _detail) {
		if (_detail != null) {
			next = new WeakReference(_detail);
		}
	}

	public void setPrice(ServicePrice _price) {
		this.price = _price;
	}
	
	/***
	 * 
	 * Returns the price used to rate this usage.
	 * It can be null if the usage was not rated yet OR
	 * Service Price used to rate it was not found.
	 * 
	 * @return A Service Price used to rate this usage
	 * 
	 * @see br.com.auster.om.invoice.isRated().
	 */
	public ServicePrice getPrice() {
		return this.price;
	}
	
	public boolean isPassThrough() {
		return ((this.price != null) && this.price.isPassThrough());
	}
	
	/***
	 * Returns if a price exists for the usage.
	 * If so, if it is NEGOTIABLE (Please note this is different of being NEGOTIATED).
	 * If both are true, then this method returns true.
	 * 
	 * <p>
	 * Example:
	 * <pre>
	 *    Create a use example.
	 * </pre>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isPriceNegotiable() {
		return ((this.price != null) && this.price.isNegotiable());
	}
	
	/***
	 * Returns true if the current price used to rate the call is NEGOTIATED.
	 * Please note it is different from being NEGOTIABLE
	 * <p>
	 * Example:
	 * <pre>
	 *    Create a use example.
	 * </pre>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isPriceNegotiated() {
		if (this.price==null || !this.price.isNegotiable()) {
			return false;
		}
		
		if (this.price instanceof ServicePriceNegotiated) {
			return true;
		}
		return false;
	}
	
	public boolean isRated() {
		return rated;
	}
	
	public void setCalculatedValue(double _value) {
		this.calculatedValue = _value;
		this.rated = (_value != Double.NaN);
	}
	public double getCalculatedValue() {		
		return this.calculatedValue;
	}
	
	/**
	 * Returns the absolute difference between {@link #getTotalAmount()} and {@link #getCalculatedValue()}.
	 * 
	 * If {@link #calculatedValue} was not defined, then {@link #isRated()} is <code>false</code>, and this
	 * 	method will return zero.
	 */
	public double getAmountDifference() {
		if (!isRated()) {
			return 0D;
		}
		double diff = Math.abs(this.getTotalAmount()) - Math.abs(this.calculatedValue);
		return Math.abs(diff);
	}
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		DecimalFormat df = new DecimalFormat(NUMBER_FORMAT);
		
		String datetimeStr = getDatetime() == null? null: sdf.format(getDatetime());
		
		return "AccessNumber=" + this.getChannelId()
			+ INNER_SEP_ATR + "CallDateTime=" + datetimeStr
			+ INNER_SEP_ATR + "CallDuration=" + this.getUsageDuration()
			+ INNER_SEP_ATR + "CallOriginalValue=" + df.format(getTotalAmount())
			+ INNER_SEP_ATR + "ElementType=" + this.getElementType()
			+ INNER_SEP_ATR + "ObjectType=" + this.getObjectType();
	}

	public Object clone() {
		UsageDetail clone = this.createNewInstance();

		// InvoiceModelObject attributes
		clone.setId(0);
		clone.setTag(this.getTag());
		clone.setElementType(this.getElementType());
		clone.setInvoice(this.getInvoice());

		// Section Detail attributes
		clone.setSeqNbr(this.getSeqNbr());
		clone.setCaption(this.getCaption());
		clone.setUsage(this.isUsage());
		clone.setTotalAmount(this.getTotalAmount());
		clone.setCarrierState(this.getCarrierState());
		clone.setCarrierCode(this.getCarrierCode());
		clone.setSection(this.getSection());

		// Usage Detail attributes
		clone.setDatetime((Date) this.getDatetime().clone());
		clone.setOriginSID(this.getOriginCity());
		clone.setCallDirection(this.getCallDirection());
		clone.setDestinationCity(this.getDestinationCity());
		clone.setDestinationState(this.getDestinationState());
		clone.setDestinationCountry(this.getDestinationCountry());
		clone.setCalledNumber(this.getCalledNumber());
		clone.setSvcId(this.getSvcId());
		clone.setSvcPlan(this.getSvcPlan());
		clone.setCallIndicator(this.getCallIndicator());
		clone.setOriginCity(this.getOriginCity());
		clone.setOriginState(this.getOriginState());
		clone.setOriginCountry(this.getOriginCountry());
		clone.setChannelId(this.getChannelId());
		clone.setGroupId(this.getGroupId());
		clone.setDuration((UnitCounter) this.getDuration().clone());
		clone.setTariff(this.getTariff());
		clone.setCallClass(this.getCallClass());
		clone.setCallSubclass(this.getCallSubclass());
		clone.setTariffClass(this.getTariffClass());
		clone.setObjectType(this.getObjectType());
		clone.setPartNumber(this.getPartNumber());
		clone.setAggregateObject(this.getAggregateObject());
		clone.setDirection(this.getDirection());
		clone.setArea(this.getArea());
		clone.setType(this.getType());
		clone.setStep(this.getStep());

		clone.setNextUsage(this.getNextUsage());

		clone.setParts((this.parts != null) ? new HashSet(this.parts) : null);

		// clone guiding info
		clone.setGuided(this.guided);
		clone.setGuideRule(this.getGuideRule());
		// clone pricing info
		clone.setPrice(this.getPrice());
		if (this.isRated()) {
			clone.setCalculatedValue(this.getCalculatedValue());
		}
		
		clone.setServiceExists(this.getServiceExists());
		clone.setDetailCFOP(this.getDetailCFOP());
		clone.setFreeIndicator(getFreeIndicator());
		clone.setHomeCallIndicator(getHomeCallIndicator());
		clone.setLocalCallIndicator(getLocalCallIndicator());
		
		return clone;
	}

	protected UsageDetail createNewInstance() {
		return new UsageDetail();
	}


	/**
	 * @return Returns the area.
	 */
	public String getArea() {
		return area;
	}


	/**
	 * @param area The area to set.
	 */
	public void setArea(String area) {
		this.area = area;
	}
	public boolean isAreaEmpty() {
		return ( (this.area == null) || ("".equals(this.area))) ? true : false ;
	}

	/**
	 * @return Returns the direction.
	 */
	public String getDirection() {
		return direction;
	}
	public boolean isDirectionEmpty() {
		return ( (this.direction == null) || ("".equals(this.direction)) ) ? true : false;
	}

	/**
	 * @param direction The direction to set.
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}


	/**
	 * @return Returns the step.
	 */
	public String getStep() {
		return step;
	}
	public boolean isStepEmpty() {
		return ( (this.step == null) || ("".equals(this.step)) ) ? true : false;
	}

	/**
	 * @param step The step to set.
	 */
	public void setStep(String step) {
		this.step = step;
	}


	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	public boolean isTypeEmpty() {
		return ( (this.type == null) || ("".equals(this.type)) ) ? true : false;
	}


	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getOriginalUsageDuration() {
		return originalUsageDuration;
	}
	public void setOriginalUsageDuration(String originalUsageDuration) {
		this.originalUsageDuration = originalUsageDuration;
	}
	public boolean isOriginalUsageDurationEmpty() {
		return ( (this.originalUsageDuration == null) || ("".equals(this.originalUsageDuration)) ) ? true : false;
	}
	
	public UnitCounter getUsageBilledUnits() {
		return billedUnits;
	}

	public void setUsageBilledUnits(UnitCounter billedUnits) {
		this.billedUnits = billedUnits;
	}

	public String getOriginalUsageBilledUnits() {
		return originalUsageBilledUnits;
	}
	public void setOriginalUsageBilledUnits(String originalUsageBilledUnits) {
		this.originalUsageBilledUnits = originalUsageBilledUnits;
	}
	public boolean isOriginalUsageBilledUnitsEmpty() {
		return ( (this.originalUsageBilledUnits == null) || ("".equals(this.originalUsageBilledUnits)) ) ? true : false;
	}
	
	/***
	 * Helper method to indicate if the origin and destination state of a call are the same.
	 * Please Note that, this method is also called by @seeisSameCity()
	 * 
	 * @return true if are the same, false otherwise
	 */
	public boolean isSameState() {
		return (this.getOriginState().equals(this.getDestinationState()));
	}
	
	/***
	 * Helper method to indicate if origin and destination city AND state of a call are the same
	 * @return true if are the same, false otherwise
	 */
	public boolean isSameCity() {
		return (this.isSameState()) && (this.getOriginCity().equals(this.getDestinationCity()));
	}

	/**
   * @return the guided flag. If guiding was able to drive this call returns true. False otherwise
   */
  public boolean isGuided() {
  	return this.guided;
  }

	/**
   * @param guided the guided to set
   */
  public void setGuided(boolean guided) {
  	this.guided = guided;
  }

	/**
   * @return the guideRule, when guiding was able to drive this call.
   */
  public String getGuideRule() {
  	return this.guideRule;
  }

	/**
   * @param guideRule the guideRule to set
   */
  public void setGuideRule(String guideRule) {
  	this.guideRule = guideRule;
  }
	
  /***
   * Helper method meant to be used by Business Rules.
   * @return true if call is outgoing (Originated)
   */
  public boolean isOutgoingCall() {
  	if (this.getCallIndicator() != null) {
  		return (this.getCallIndicator().equals(CALLINDICATOR_OUTGOING)) ? true : false ;
  	}
  	return false;
  }
  
  /***
   * Helper method meant to be used by Business Rules.
   * @return true if call is incoming (Terminated)
   */	
  public boolean isIncomingCall() {
  	if (this.getCallIndicator() != null) {
  		return (this.getCallIndicator().equals(CALLINDICATOR_INCOMING)) ? true : false ;
  	}
  	return false;
  }
  
	/**
   * @param localCallIndicator the localCallIndicator to set
   */
  protected void setLocalCallIndicator(int localCallIndicator) {
  	this.localCallIndicator = localCallIndicator;
  }
  public int getLocalCallIndicator() {
  	return this.localCallIndicator;
  }
  public void setCallAsLocal() {
  	this.setLocalCallIndicator(UsageDetail.LOCAL_CALL_INDICATOR);
  }
  public void setCallAsLongDistance() {
  	this.setLocalCallIndicator(UsageDetail.LONG_DISTANCE_CALL_INDICATOR);
  }
  public void setCallAsInternational() {
  	this.setLocalCallIndicator(UsageDetail.INTERNATIONAL_CALL_INDICATOR | UsageDetail.LONG_DISTANCE_CALL_INDICATOR) ;
  }   
  
  /***
   * Helper method meant to be used by Business Rules.
   * The data for this method will be populated by Guiding Rules.
   * If no guiding rules were applied over this usage, the results are unpredictable.
   * 
   * @return true if call is Local Call
   */
  public boolean isLocalCall() {
  	return (this.getLocalCallIndicator() & UsageDetail.LOCAL_CALL_INDICATOR) == UsageDetail.LOCAL_CALL_INDICATOR;  	
  }
  
  /***
   * Helper method meant to be used by Business Rules.
   * The data for this method will be populated by Guiding Rules.
   * If no guiding rules were applied over this usage, the results are unpredictable.
   * 
   * A Long Distance Call is EVERY call that is NOT Local
   * @return true if call is Long Distance Call
   */  
  public boolean isLongDistanceCall() {
  	return (this.getLocalCallIndicator() & UsageDetail.LONG_DISTANCE_CALL_INDICATOR) == UsageDetail.LONG_DISTANCE_CALL_INDICATOR;
  }
    
  /***
   * Helper method meant to be used by Business Rules.
   * The data for this method will be populated by Guiding Rules.
   * If no guiding rules were applied over this usage, the results are unpredictable.
   * 
   * A International Call is ALWAYS a Long Distance Call
   * 
   * @return true if call is a International Call
   */    
  public boolean isInternationalCall() {
  	return (this.getLocalCallIndicator() & UsageDetail.INTERNATIONAL_CALL_INDICATOR) == UsageDetail.INTERNATIONAL_CALL_INDICATOR;
  }
  
  public boolean isCallCollect() {
  	return (this.collectCallIndicator == COLLECT_CALL);
  }
  
  public void setCallAsCollect() {
  	this.collectCallIndicator = COLLECT_CALL;
  }
  
  public int getHomeCallIndicator() {
  	return this.homeCallIndicator;
  }
  
  protected void setHomeCallIndicator(int indicator) {
  	this.homeCallIndicator=indicator;
  }
  
  public void setCallAsHome() {
  	this.setHomeCallIndicator(HOME_CALL);  	
  }
  
  public void setCallAsRoaming() {
  	this.setHomeCallIndicator(ROAMING_CALL);
  }
  /***
   * Helper method meant to be used by Business Rules.
   * The data for this method will be populated by Guiding Rules.
   * If no guiding rules were applied over this usage, the results are unpredictable.
   * 
   * A Home Call is a Call happening at Registration Area of the Subscriber.
   * Most of the times, a Home Call will also be a Local Call, but this is not always true.
   * Sometimes, when the user is in Roaming a Local Call will NOT be a Home Call.
   * @return true if call is an Home Call
   */  
  public boolean isHomeCall() {
  	return this.getHomeCallIndicator() == HOME_CALL;
  }
  
  /***
   * Helper method meant to be used by Business Rules.
   * The data for this method will be populated by Guiding Rules.
   * If no guiding rules were applied over this usage, the results are unpredictable.
   * 
   * A Roaming Call is a Call happening outside of Registration Area of the Subscriber.
   * 
   * @return true if call is Roaming Call
   */  
  public boolean isRoamingCall() {
  	return this.getHomeCallIndicator() == ROAMING_CALL;
  }

	/**
   * @return the freeIndicator
   */
  public String getFreeIndicator() {
  	return this.freeIndicator;
  }
	public boolean isFreeIndicatorEmpty() {
		return ( (this.freeIndicator == null) || ("".equals(this.freeIndicator)) ) ? true : false;
	}

	/**
	 * Sets the Free Indicator as per Input File Definition.
	 * Sometimes it does not exists and then it will be null.
	 * On such cases the get method for this will return null.
	 * All other spceific methods (isFreeCall() for example) will return false.
   * @param freeIndicator the freeIndicator to set
   * 
   */
  public void setFreeIndicator(String freeIndicator) {
  	this.freeIndicator = freeIndicator;
  }
  
  public boolean isFreeCall() {
  	return (this.freeIndicator == null) ? false : this.freeIndicator.equalsIgnoreCase(FREEIND_FREE);
  }

  public boolean isChargedCall() {
  	return (this.freeIndicator == null) ? false : this.freeIndicator.equalsIgnoreCase(FREEIND_CHARGED);
  }
  
  public boolean isIncludedCall() {
  	return (this.freeIndicator == null) ? false : this.freeIndicator.equalsIgnoreCase(FREEIND_INCLUDED);  	
  }  
  
  public boolean isPromotionCall() {
  	return (this.freeIndicator == null) ? false : this.freeIndicator.equalsIgnoreCase(FREEIND_PROMOTION);
  } 
  
  public boolean isDefaultCall() {
  	return (this.freeIndicator == null) ? false : this.freeIndicator.equalsIgnoreCase(FREEIND_DEFAULT);
  }    
//  
//  /***
//   * Helper method meant to be used by Business Rules.
//   * @return true if call is outgoing (Originated)
//   */  
//  public boolean isDestinationMobile() {
//  	
//  }
//  
//  /***
//   * Helper method meant to be used by Business Rules.
//   * @return true if call is outgoing (Originated)
//   */
//  public boolean isDestinationLandLine() {
//  	
//  }

	/**
	 * Return the value of a attribute <code>serviceExists</code>.
	 * @return return the value of <code>serviceExists</code>.
	 */
	public boolean getServiceExists() {
		return this.serviceExists;
	}

	
	/**
	 * Set the value of attribute <code>serviceExists</code>.
	 * @param serviceExists
	 */
	public void setServiceExists(boolean serviceExists) {
		this.serviceExists = serviceExists;
	}
	
	public double getAmountRatio() {
		if (this.getUsageDuration() == null) {
			return Double.NaN;
		}
		if (this.getUsageDuration().getUnits() == 0) {
			return Double.NaN;
		} 
		double result = this.getTotalAmount() / this.getUsageDuration().getUnits();
		log.trace("getAmountRatio.TotalAmount:" + this.getTotalAmount() + 
				".Units:" + this.getUsageDuration().getUnits() + 
				"Result:" + result);
		return result;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public UnitCounter getRealDuration() {
		return realDuration;
	}

	public void setRealDuration(UnitCounter realDuration) {
		this.realDuration = realDuration;
	}

	public boolean isDisplacement() {
		return displacement;
	}

	public void setDisplacement(boolean displacement) {
		this.displacement = displacement;
	}

	public Date getRealEndDateTime() {
		return realEndDateTime;
	}

	public void setRealEndDateTime(Date realEndDateTime) {
		this.realEndDateTime = realEndDateTime;
	}

	public int getSplitPart() {
		return splitPart;
	}

	public void setSplitPart(int splitPart) {
		this.splitPart = splitPart;
	}

	public int getSplitTotal() {
		return splitTotal;
	}

	public void setSplitTotal(int splitTotal) {
		this.splitTotal = splitTotal;
	}
}



