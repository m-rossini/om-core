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
 * Created on Feb 3, 2005
 */
package br.com.auster.om.invoice;

import java.util.Date;

import br.com.auster.om.util.UnitCounter;

/**
 * @hibernate.class
 *              table="INV_REDEBIT_DETAIL"
 * 
 * 
 * <p><b>Title:</b> RedebitDetail</p>
 * <p><b>Description:</b> </p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: RedebitDetail.java 188 2006-08-18 14:43:22Z framos $
 */
public class RedebitDetail extends SectionDetail {
	private static final long serialVersionUID = -2813290275197023941L;
	
	private String  groupId;
	private String  accessNbr;
	private String  calledNbr;
	private String  callingNbr;
	private Date    datetime;
	private String originalDatetime;
	private boolean international;
	private int     callDirection;
	private boolean roaming;
	private UnitCounter units;
	private String  originState;
	private String  destinationState;
	private String  originCity;
	private String  destinationCity;
	private String  eventType;
	private String  servicePlan;
	
	
	
	
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
	public String getCalledNbr() {
		return calledNbr;
	}
	public void setCalledNbr(String calledNbr) {
		this.calledNbr = calledNbr;
	}
	
	/**
	 * @hibernate.property
	 *            column="CALLING_NUMBER"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getCallingNbr() {
		return callingNbr;
	}
	public void setCallingNbr(String callingNbr) {
		this.callingNbr = callingNbr;
	}
	
	/**
	 * @hibernate.property
	 *            column="REDEBIT_DATETIME"
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
	 *            column="SRC_REDEBIT_DATETIME"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 *      
	 *   This attributes handles the unformatted version of the
	 * 	{@link #datetime}  
	 */
	public String getOriginalDatetime() {
		return originalDatetime;
	}
	public void setOriginalDatetime(String _originalString) {
		this.originalDatetime = _originalString;
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
	public void setAccessNbr(String dialedNbr) {
		this.accessNbr = dialedNbr;
	}
	
	/**
	 * @hibernate.property
	 *            column="EVENT_TYPE"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 */
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
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
	
	/**
	 * @hibernate.property
	 *            column="INTERNATIONAL_FLAG"
	 *            type="boolean"
	 *            not-null="false"
	 */
	public boolean isInternational() {
		return international;
	}
	public void setInternational(boolean international) {
		this.international = international;
	}
	
	/**
	 * @hibernate.property
	 *            column="ORIGIN_CITY"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	
	/**
	 * @hibernate.property
	 *            column="ORIGIN_STATE"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getOriginState() {
		return originState;
	}
	public void setOriginState(String originState) {
		this.originState = originState;
	}
	
	/**
	 * @hibernate.property
	 *            column="IS_ROAMING"
	 *            type="boolean"
	 *            not-null="false"
	 */
	public boolean isRoaming() {
		return roaming;
	}
	public void setRoaming(boolean roaming) {
		this.roaming = roaming;
	}
	
	/**
	 * @hibernate.property
	 *            column="SERVICE_PLAN"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getServicePlan() {
		return servicePlan;
	}
	public void setServicePlan(String servicePlan) {
		this.servicePlan = servicePlan;
	}
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="COUNTER_"
	 */
	public UnitCounter getUnits() {
		return units;
	}
	public void setUnits(UnitCounter units) {
		this.units = units;
	}
	
}
