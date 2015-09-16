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
 * Created on Jan 27, 2005
 */
package br.com.auster.om.invoice;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.auster.om.util.UnitCounter;

/**
 * @hibernate.class
 *            table="INV_SECTION"
 *              
 * <p><b>Title:</b> Section</p>
 * <p><b>Description:</b> Represents an Invoice Section</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: Section.java 469 2007-10-08 16:55:31Z mtengelm $
 */
public class Section extends InvoiceModelObject {
	
	private static final long serialVersionUID = -8775925167035719853L;
	
	private String planName;
	/** this section name                       */
	private String			sectionName;
	/** this section caption                    */
	private String			caption;
	/** this section total amount               */
	private double 			totalAmount;
	/** is this section a usage section         */
	private boolean			usage;
	/** access number for this section */
	private String      accessNbr;
	
	/** Subscription ID. Can be null if it is not a subscription section **/
	private String subscriptionid;
	/*** Subscription State. Can be null if it is not a subscription section ***/
	private String subscriptionState;
	
	/** a service provider associated with this section */
	private String 			carrierCode;
	private String			carrierState;
	
	/** the unit type used in this section  */
	private String            unitType;
	/** included free units in this section */
	private UnitCounter		includedUnits;
	/** used units in this section */
	private UnitCounter		usedUnits;
	/** billed units in this section */
	private UnitCounter		billedUnits;
	/** rollover units received by this section */
	private UnitCounter		rolloverReceived;
	/** rollover units forwarded from this section */
	private UnitCounter		rolloverForwarded;
	/** If this section was loaded it is false, if was created by any component (Loader, RUles, or whatever),
	 * meaning it does not exists in reality, then it is true
	 */
	private boolean virtualSection=false;

	
	/***
	 * Holds the number of occurrences of the details for this section.
	 * IF and ONLY IF it was informed in input file.
	 */
	private int numberOfDetails = Integer.MIN_VALUE;
	
	/** this section subsections        */
	private List       		subSections;
	/** this section details            */
	private List		  		details;
	
	private String channelId;
	// If this section handles mobile info (needed for wired phones)
	// Defaults to TRUE
	private boolean mobile;
	
	private WeakReference parentSection;
	
	/**
	 * public constructor
	 */
	public Section() {
		this.mobile = true;
		subSections = new ArrayList();
		details     = new ArrayList();
	}
	
	
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="BILLED_"
	 */
	public UnitCounter getBilledUnits() {
		return billedUnits;
	}
	public void setBilledUnits(UnitCounter billedUnits) {
		this.billedUnits = billedUnits;
	}
	
	/**
	 * @hibernate.property
	 *            column="CAPTION"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="INCLUDED_"
	 */
	public UnitCounter getIncludedUnits() {
		return includedUnits;
	}
	public void setIncludedUnits(UnitCounter includedUnits) {
		this.includedUnits = includedUnits;
	}
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="ROLLFWD_"
	 */
	public UnitCounter getRolloverForwarded() {
		return rolloverForwarded;
	}
	public void setRolloverForwarded(UnitCounter rolloverForwarded) {
		this.rolloverForwarded = rolloverForwarded;
	}
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="ROLLRECEIVED_"
	 */
	public UnitCounter getRolloverReceived() {
		return rolloverReceived;
	}
	public void setRolloverReceived(UnitCounter rolloverReceived) {
		this.rolloverReceived = rolloverReceived;
	}
	
	/**
	 * @hibernate.property
	 *            column="SECTION_NAME"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
  /**
   * @hibernate.list
   *            lazy="true"
   *            cascade="delete"
   *            
   * @hibernate.collection-key
   *            column="parent_section_id"
   *            
   * @hibernate.collection-index
   *            column="seq"
   *            
   * @hibernate.collection-one-to-many
   *            class="br.com.auster.om.invoice.Section"
   * 
   * @return
   */
	public List getSubSections() {
		return subSections;
	}
  public void setSubSections(List subsections) {
    this.subSections = subsections;
  }
	public void addSection(Section section) {
		subSections.add(section);
		section.setParentSection(this);
		section.setInvoice(this.getInvoice());
	}
	
	public void setParentSection(Section _section) {
		if (_section != null) {
			this.parentSection = new WeakReference(_section);
		}
	}
	public Section getParentSection() {
		if (this.parentSection != null) {
			return (Section) this.parentSection.get();
		}
		return null;
	}
	
	/**
	 * @hibernate.property
	 *            column="TOTAL_AMNT"
	 *            type="double"
	 *            not-null="false"
	 */
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	/**
	 * @hibernate.property
	 *            column="UNIT_TYPE"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
	/**
	 * @hibernate.property
	 *            column="USAGE_FLAG"
	 *            type="boolean"
	 *            not-null="false"
	 */
	public boolean isUsage() {
		return usage;
	}
	public void setUsage(boolean usage) {
		this.usage = usage;
	}
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="USED_"
	 */
	public UnitCounter getUsedUnits() {
		return usedUnits;
	}
	public void setUsedUnits(UnitCounter usedUnits) {
		this.usedUnits = usedUnits;
	}
	
	/**
	 * @hibernate.property
	 *            column="ACCESS_NUMBER"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getAccessNbr() {
		return accessNbr;
	}
	public void setAccessNbr(String accessNbr) {
		this.accessNbr = accessNbr;
	}
	
  /**
   * @hibernate.list
   *              lazy="true"
   *              inverse="true"
   *              cascade="delete"
   *              table="INV_RELATED_DETAIL"
   *
   * @hibernate.collection-key
   *              column="section_id"
   *
   * @hibernate.collection-index
   *              column="seq"
   *
   * @hibernate.many-to-any
   *              id-type="long"
   *              meta-type="org.hibernate.type.ClassType"
   *
   * @hibernate.many-to-any-column
   *              name="DETAIL_TYPE"
   *              not-null="true"
   *
   * @hibernate.many-to-any-column
   *              name="DETAIL_UID"
   *              not-null="true"
   * 
   * @return
   */
	public List getDetails() {
		return details;
	}
  public void setDetails(List details) {
    this.details = details;
  }
	public void addDetail(SectionDetail detail) {
		details.add(detail);
		detail.setInvoice(this.getInvoice());
		detail.setSection(this);
	}
	
	/**
	 * @hibernate.property
	 *            column="CARRIER_CODE"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	
	/**
	 * @hibernate.property
	 *            column="CARRIER_STATE"
	 *            type="string"
	 *            length="10"
	 *            not-null="false"
	 */
	public String getCarrierState() {
		return carrierState;
	}
	public void setCarrierState(String carrierState) {
		this.carrierState = carrierState;
	}
	
	public void setInvoice(Invoice inv) {
		super.setInvoice(inv);
		for(Iterator i=details.iterator(); i.hasNext(); ) {
			((SectionDetail) i.next()).setInvoice(inv);
		}
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
		if(o instanceof Section) {
			Section section = (Section) o;
			if((section.getSectionName() != null) &&
					(this.getSectionName() != null))
			{
				ret = this.getSectionName().compareTo(section.getSectionName());
			} 
		} else {
			throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
		}
		return ret;
	}



	/**
   * @return the subscriptionid
   */
  public String getSubscriptionid() {
  	return this.subscriptionid;
  }



	/**
   * @param subscriptionid the subscriptionid to set
   */
  public void setSubscriptionid(String subscriptionid) {
  	this.subscriptionid = subscriptionid;
  }
  
  public boolean isSubscriptionIdEqualsToChannelId() {
  	
  	if (this.getChannelId() == null && this.getSubscriptionid() == null ) {
  		return true;
  	}
  	
  	if (this.getChannelId() != null && this.getSubscriptionid() == null ) {
  		return false;
  	}
  	
  	if (this.getChannelId() == null && this.getSubscriptionid() != null ) {
  		return false;
  	}

  	return  this.getChannelId().equalsIgnoreCase(getSubscriptionid()); 
  }



	/**
   * @return the channelId
   */
  public String getChannelId() {
  	return this.channelId;
  }



	/**
   * @param channelId the channelId to set
   */
  public void setChannelId(String channelId) {
  	this.channelId = channelId;
  }



	
	/**
	 * Return the value of a attribute <code>subscriptionState</code>.
	 * @return return the value of <code>subscriptionState</code>.
	 */
	public String getSubscriptionState() {
		return this.subscriptionState;
	}



	
	/**
	 * Set the value of attribute <code>subscriptionState</code>.
	 * @param subscriptionState
	 */
	public void setSubscriptionState(String subscriptionState) {
		this.subscriptionState = subscriptionState;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	/**
	 * Return the value of a attribute <code>numberOfDetails</code>.
	 * This will return any thing meaningfull ONLY this value was present into input file.
	 * If this value was not in input file is responsability of the loader to NOT touch this value calling ita
	 * set Method, OR if doing so do it with Integer.MIN_VALUE
	 * @return return the value of <code>numberOfDetails</code>.
	 */
	public int getNumberOfDetails() {
		return this.numberOfDetails;
	}
	
	/**
	 * Set the value of attribute <code>numberOfDetails</code>.
	 * @param numberOfDetails
	 */
	public void setNumberOfDetails(int numberOfDetails) {
		this.numberOfDetails = numberOfDetails;
	}

	public boolean isMobile() {
		return this.mobile;
	}
	
	public void setMobile(boolean _mobile) {
		this.mobile = _mobile;
	}
	
	/***
	 * Convenience method to indicate if a Section has details or Not
	 * 
	 * @return true if there is any detail.
	 * false if section does not has any details or detail placeholder (The list) is null
	 */
	public boolean hasDetails() {
		return (details != null) && (details.size() > 0);
	}
	
	/***
	 * 
	 * Convenience method to indicate if a Section has sub sections or Not
	 * 
	 * @return true if there is any sub section.
	 * false if section does not has any sub sections or sub section placeholder (The list) is null
	 */
	public boolean hasSubsections() {
		return (subSections != null) && (subSections.size() > 0 );
	}
	
	public String toString() {
		String msg = this.getElementType() + "[" +
		this.getCaption() + ";" + 
		this.getSectionName() + ";" +
		this.getAccessNbr() + ";" +
		this.getSubscriptionid() + ";" +
		this.getSubscriptionState() + "" +
		"]" + "-" + 
		this.getClass().getCanonicalName();
		
		return msg;
	}
}
