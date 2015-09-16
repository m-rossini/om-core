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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import br.com.auster.common.util.DateUtils;

/**
 * @hibernate.class table="INV_ONETIME_DETAIL"
 * 
 * 
 * <p>
 * <b>Title:</b> OneTimeEventDetail
 * </p>
 * <p>
 * <b>Description:</b>
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2004
 * </p>
 * <p>
 * <b>Company:</b> Auster Solutions
 * </p>
 * 
 * @author etirelli
 * @version $Id: OneTimeEventDetail.java 399 2007-03-01 03:16:06Z mtengelm $
 */

public class OneTimeEventDetail extends SectionDetail {
	private static final long	serialVersionUID	= -210757675008150198L;

	/** event date */
	private Date	            eventDate;
	private String	          originalEventDate;

	/*****************************************************************************
	 * Start Date.
	 */
	private Date	            startDate;
	private String	          originalStartDate;

	private String	          accessNumber;

	/**
	 * @hibernate.property column="EVENT_DATE" type="date" not-null="false"
	 */
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @hibernate.property column="SRC_EVENT_DATE" type="string" length="30"
	 *                     not-null="false"
	 * 
	 * This attributes handles the unformatted version of the {@link #eventDate}
	 */
	public String getOriginalEventDate() {
		return originalEventDate;
	}

	public void setOriginalEventDate(String _originalString) {
		this.originalEventDate = _originalString;
	}

	public String getAccessNumber() {
		return accessNumber;
	}

	public void setAccessNumber(String accessNumber) {
		this.accessNumber = accessNumber;
	}

	/**
	 * @return the originalStartDate
	 */
	public String getOriginalStartDate() {
		return this.originalStartDate;
	}

	/**
	 * @param originalStartDate
	 *          the originalStartDate to set
	 */
	public void setOriginalStartDate(String originalStartDate) {
		this.originalStartDate = originalStartDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate
	 *          the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/***
	 * Returns true if start date is null OR
	 * If start date is before event date.
	 * @return
	 */
	public boolean isStartBeforeEvent() {
		if (this.getStartDate() == null) {
			return true;
		}
		if (this.getEventDate() == null) {
			return true;
		}
		return this.getStartDate().before(this.getEventDate());
	}
	
	/***
	 * Returns the difference in days between event date and start date.
	 * If start date is null, then returns Long.MIN_VALUE;
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	public long getDifferenceInDays() throws IllegalArgumentException, ParseException {
		if (this.getStartDate() == null) {
			return Long.MIN_VALUE;
		}
		if (this.getEventDate() == null) {
			return Long.MIN_VALUE;
		}		
		return DateUtils.difference(this.getStartDate(), this.getEventDate(), 'd');
	}
	
	/***
	 * Returns the differente between event date and start date in months.
	 * If start date is null return Long.MIN_VALUE
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	public long getDifferenceInMonths() throws IllegalArgumentException, ParseException {
		if (this.getStartDate() == null) {
			return Long.MIN_VALUE;
		}
		if (this.getEventDate() == null) {
			return Long.MIN_VALUE;
		}		
		return DateUtils.difference(this.getStartDate(), this.getEventDate(), 'M');
	}	
	
	/***
	 * Returns the difference between event date and start date.
	 * Same as @see getDifferenceInMonths() except this method SETs the Day of the date to be the first day in the month,
	 *  ie., Day=1.
	 *  
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	public long getDifferenceInMonthsByFirstDay() throws IllegalArgumentException, ParseException {
		if (this.getStartDate() == null) {
			return Long.MIN_VALUE;
		}
		if (this.getEventDate() == null) {
			return Long.MIN_VALUE;
		}
		Calendar eventCalendar = Calendar.getInstance();
		eventCalendar.setLenient(false);
		eventCalendar.setTime(getEventDate());
		eventCalendar.set(Calendar.DAY_OF_MONTH, 1);		
		eventCalendar.set(Calendar.HOUR_OF_DAY,0);
		eventCalendar.set(Calendar.MINUTE,0);
		eventCalendar.set(Calendar.SECOND,0);
		eventCalendar.set(Calendar.MILLISECOND,0);
		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setLenient(false);
		startCalendar.setTime(getStartDate());
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);		
		startCalendar.set(Calendar.HOUR_OF_DAY,0);
		startCalendar.set(Calendar.MINUTE,0);
		startCalendar.set(Calendar.SECOND,0);
		startCalendar.set(Calendar.MILLISECOND,0);
		
		return DateUtils.difference(startCalendar.getTime(), eventCalendar.getTime(), 'M');
	}	
	
}
