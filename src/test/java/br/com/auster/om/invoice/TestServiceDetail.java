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
 * Created on 06/02/2007
 */
package br.com.auster.om.invoice;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

/**
 * @author mtengelm
 *
 */
public class TestServiceDetail extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link br.com.auster.om.invoice.ServiceDetail#isEndDateNull()}.
	 */
	public void testIsEndDateNull() {
		ServiceDetail detail = new ServiceDetail();
		assertTrue("This Should be TRUE-1", detail.isEndDateNull());
		
		detail.setEndDate(new Date());
		assertTrue("This Should be TRUE-2", detail.isEndDateNull());
		
		detail.setOriginalEndDate("");
		assertTrue("This Should be TRUE-3", detail.isEndDateNull());
		
		detail.setOriginalEndDate(detail.getEndDate().toString());
		assertFalse("This Should be False-1", detail.isEndDateNull());		
	}

	/**
	 * Test method for {@link br.com.auster.om.invoice.ServiceDetail#isStartDateNull()}.
	 */
	public void testIsStartDateNull() {
		ServiceDetail detail = new ServiceDetail();
		assertTrue("This Should be TRUE-1", detail.isStartDateNull());
		
		detail.setStartDate(new Date());
		assertTrue("This Should be TRUE-2", detail.isStartDateNull());
		
		detail.setOriginalStartDate("");
		assertTrue("This Should be TRUE-3", detail.isStartDateNull());
		
		detail.setOriginalStartDate(detail.getStartDate().toString());
		assertFalse("This Should be False-1", detail.isStartDateNull());		
	}

	/**
	 * Test method for {@link br.com.auster.om.invoice.ServiceDetail#isStarDateBeforeEndDate()}.
	 */
	public void testIsStarDateBeforeEndDate() {
		ServiceDetail detail = new ServiceDetail();
		assertTrue("This Should be TRUE-1", detail.isStartDateNull());		
		assertTrue("This Should be TRUE-2", detail.isEndDateNull());
		
		Date sdate = new Date();
		detail.setStartDate(sdate);
		detail.setOriginalStartDate(detail.getStartDate().toString());
		
		Calendar cal = null;
		cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, ((1000 * 60 * 60 * 24)*5) ); //Add five days
		
		Date edate = new Date(cal.getTimeInMillis());		
		detail.setEndDate(edate);				
		detail.setOriginalEndDate(detail.getEndDate().toString());
		
		assertFalse("This Should be False-1", detail.isStartDateNull());	
		assertFalse("This Should be False-2", detail.isEndDateNull());	
		
		assertTrue("This should be TRUE-3", detail.isStarDateBeforeEndDate());
		
		detail.setEndDate(sdate);
		detail.setOriginalEndDate(detail.getEndDate().toString());
		assertFalse("This should be False-3", detail.isStarDateBeforeEndDate());
		
		cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, ((1000 * 60 * 60 * 24)*5) * -1 ); //Subtract Five Days
		
		edate = new Date(cal.getTimeInMillis());
		detail.setEndDate(edate);				
		detail.setOriginalEndDate(detail.getEndDate().toString());		
		assertFalse("This should be False-4", detail.isStarDateBeforeEndDate());		
	}

}
