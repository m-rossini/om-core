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
 * Created on 23/02/2007
 */
package br.com.auster.om.invoice;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import br.com.auster.om.util.ParserUtils;

import junit.framework.TestCase;

/**
 * @author mtengelm
 *
 */
// TODO Comment this class br.com.auster.om.invoice "." OneTimeDetailTest.java
public class OneTimeDetailTest extends TestCase {

	private OneTimeEventDetail detail;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		detail = new OneTimeEventDetail();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link br.com.auster.om.invoice.OneTimeEventDetail#setStartDate(java.util.Date)}.
	 */
	public void testDates() {
		assertNotNull(detail);
		
		Calendar eventCalendar = Calendar.getInstance();
		eventCalendar.setLenient(false);
		eventCalendar.set(Calendar.YEAR, 2007);
		eventCalendar.set(Calendar.MONTH, 0);
		eventCalendar.set(Calendar.DAY_OF_MONTH, 23);		
		eventCalendar.set(Calendar.HOUR_OF_DAY,0);
		eventCalendar.set(Calendar.MINUTE,0);
		eventCalendar.set(Calendar.SECOND,0);
		eventCalendar.set(Calendar.MILLISECOND,0);
		detail.setEventDate(eventCalendar.getTime());
		
		detail.setOriginalEventDate("20070123");		
		Date reconvertedEventDate = ParserUtils.getDate(detail.getOriginalEventDate());
		
		assertEquals("Error in Event Date:", reconvertedEventDate, detail.getEventDate());
		
		Calendar startDate = Calendar.getInstance();
		startDate.setLenient(false);
		startDate.set(Calendar.YEAR, 2006);
		startDate.set(Calendar.MONTH, 0);
		startDate.set(Calendar.DAY_OF_MONTH, 01);		
		startDate.set(Calendar.HOUR_OF_DAY,0);
		startDate.set(Calendar.MINUTE,0);
		startDate.set(Calendar.SECOND,0);
		startDate.set(Calendar.MILLISECOND,0);
		detail.setStartDate(startDate.getTime());
		
		detail.setOriginalStartDate("20060101");
		Date reconvertedStartDate = ParserUtils.getDate(detail.getOriginalStartDate());
		assertEquals("Error in Start Date:",reconvertedStartDate, detail.getStartDate());		
		
		assertTrue("1.Start Date MUST be BEFORE Event Date:", detail.getStartDate().before(detail.getEventDate()));
				
		assertTrue("2.Start Date MUST be BEFORE Event Date:", detail.isStartBeforeEvent());
		
		detail.setStartDate(null);
		assertTrue("3.Start Date MUST be BEFORE Event Date:", detail.isStartBeforeEvent());		
		
		
		try {
			detail.setStartDate(null);			
			assertEquals("The Days Difference MUST BE Long.MIN_VALUE:" , detail.getDifferenceInDays(),Long.MIN_VALUE);
			assertEquals("The Month Difference MUST BE Long.MIN_VALUE:" , detail.getDifferenceInMonths(),Long.MIN_VALUE);
			assertEquals("The Month By First Day Difference MUST BE Long.MIN_VALUE:" , detail.getDifferenceInMonthsByFirstDay(),Long.MIN_VALUE);			
			
			detail.setStartDate(startDate.getTime());
	    assertEquals("The Days Difference MUST BE 366 Days:" , detail.getDifferenceInDays(),387);
			assertEquals("The Month Difference MUST BE 12:" , detail.getDifferenceInMonths(),13);
			assertEquals("The Month By First Day Difference MUST BE X:" , detail.getDifferenceInMonthsByFirstDay(),12);	    
    } catch (IllegalArgumentException e) {
	    e.printStackTrace();
	    fail("Test Failed. See Exception");
    } catch (ParseException e) {
	    e.printStackTrace();
	    fail("Test Failed. See Exception");	    
    }
	}

}
