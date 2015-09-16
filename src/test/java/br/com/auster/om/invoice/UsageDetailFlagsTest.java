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

import junit.framework.TestCase;

/**
 * @author mtengelm
 * 
 */
// TODO Comment this class br.com.auster.om.invoice "."
// UsageDetailFlagstest.java
public class UsageDetailFlagsTest extends TestCase {
	UsageDetail	usage	= null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		usage = new UsageDetail();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link br.com.auster.om.invoice.UsageDetail#isOutgoingCall()}.
	 */
	public void testCallIndicator() {
		assertNotNull("1.Usage MUST not be null", usage);
		
		usage.setCallIndicator("1");
		assertFalse("2.The call is OUTGOING and SHOULD be reported as this.", usage.isIncomingCall());

		usage.setCallIndicator("1");
		assertTrue("3.The call is OUTGOING and SHOULD be reported as this.", usage.isOutgoingCall());		
		
		usage.setCallIndicator("2");
		assertFalse("4.The call is INCOMING and SHOULD be reported as this.", usage.isOutgoingCall());

		usage.setCallIndicator("2");
		assertTrue("5.The call is INCOMING and SHOULD be reported as this.", usage.isIncomingCall());			
		
		usage.setCallIndicator("3");
		assertFalse("6.The call UNKNOWN and MUST NOT be reported as Incoming.", usage.isIncomingCall());				
		assertFalse("7.The call UNKNOWN and MUST NOT be reported as Outgoing.", usage.isOutgoingCall());
	}

	/**
	 * Test method for
	 * {@link br.com.auster.om.invoice.UsageDetail#setCallAsLocal()}.
	 */
	public void testSetCallAsLocal() {
		assertNotNull("1.Usage MUST not be null", usage);

		assertFalse("2.The call is not marked yet. Should NOT return as local.", usage.isLocalCall());	
		
		usage.setCallAsLocal();
		assertTrue("3.The call is marked as LOCAL and should be reported as this.", usage.isLocalCall());
		assertFalse("4.The call is marked as LOCAL and should NOT be report as Long Distance.", usage.isLongDistanceCall());		
		assertFalse("5.The call is marked as LOCAL and should NOT be report as International.", usage.isInternationalCall());		
	}

	/**
	 * Test method for
	 * {@link br.com.auster.om.invoice.UsageDetail#setCallAsLongDistance()}.
	 */
	public void testSetCallAsLongDistance() {
		assertNotNull("1.Usage MUST not be null", usage);

		assertFalse("2.The call is not marked yet. Should NOT return as long distance.", usage.isLongDistanceCall());	
		
		usage.setCallAsLongDistance();
		assertTrue("3.The call is marked as LONG DISTANCE and should be reported as this.", usage.isLongDistanceCall());
		assertFalse("4.The call is marked as LONG DISTANCE and should NOT be report as Local.", usage.isLocalCall());		
		assertFalse("5.The call is marked as LONG DISTANCE and should NOT be report as International.", usage.isInternationalCall());		
	}

	/**
	 * Test method for
	 * {@link br.com.auster.om.invoice.UsageDetail#setCallAsInternational()}.
	 */
	public void testSetCallAsInternational() {
		assertNotNull("1.Usage MUST not be null", usage);

		assertFalse("2.The call is not marked yet. Should NOT return as long distance.", usage.isInternationalCall());	
		
		usage.setCallAsInternational();
		assertTrue("3.The call is marked as INTERNATIONAL and should be reported as this.", usage.isInternationalCall());
		assertFalse("4.The call is marked as INTERNATIONAL and should NOT be report as Local.", usage.isLocalCall());		
		assertTrue("5.The call is marked as INTERNATIONAL and MUST be report as Long distance.", usage.isLongDistanceCall());		
	}

	public void testSetCallAsHome() {
		assertNotNull("1.Usage MUST not be null", usage);

		assertFalse("2.The call is not marked yet. Should NOT return as Home Call.", usage.isHomeCall());	
		
		usage.setCallAsHome();
		assertTrue("3.The call is marked as HOME and should be reported as this.", usage.isHomeCall());
		assertFalse("4.The call is marked as HOME and should NOT be report as Roaming.", usage.isRoamingCall());			
	}
	
	public void testSetCallAsRoaming() {
		assertNotNull("1.Usage MUST not be null", usage);

		assertFalse("2.The call is not marked yet. Should NOT return as Roaming Call.", usage.isRoamingCall());	
		
		usage.setCallAsRoaming();
		assertTrue("3.The call is marked as ROAMING and should be reported as this.", usage.isRoamingCall());
		assertFalse("4.The call is marked as ROAMING and should NOT be report as Roaming.", usage.isHomeCall());			
	}	
	
	public void testFreeInicatorAsUnset() {
		assertNotNull("1.Usage MUST not be null", usage);

		assertFalse("2.The call is not marked yet. Should NOT return as Charged Call.", usage.isChargedCall());	
		assertFalse("3.The call is not marked yet. Should NOT return as Default Call.", usage.isDefaultCall());
		assertFalse("4.The call is not marked yet. Should NOT return as Free Call.", usage.isFreeCall());
		assertFalse("5.The call is not marked yet. Should NOT return as Promotion Call.", usage.isPromotionCall());
		assertFalse("6.The call is not marked yet. Should NOT return as Included Call.", usage.isIncludedCall());						
			
	}
	
	public void testFreeIndicatorAsInvalid() {
		usage.setFreeIndicator("X");
		assertFalse("2.The call set as X free indicator. Should NOT return as Charged Call.", usage.isChargedCall());	
		assertFalse("3.The call set as X free indicator. Should NOT return as Default Call.", usage.isDefaultCall());
		assertFalse("4.The call set as X free indicator. Should NOT return as Free Call.", usage.isFreeCall());
		assertFalse("5.The call set as X free indicator. Should NOT return as Promotion Call.", usage.isPromotionCall());
		assertFalse("6.The call set as X free indicator. Should NOT return as Included Call.", usage.isIncludedCall());	
	}
	
	public void setFreeIndicatorAsCharged() {
		usage.setFreeIndicator("U");
		assertTrue("2.The call set as U free indicator. MUST return as Charged Call.", usage.isChargedCall());	
		assertFalse("3.The call set as U free indicator. Should NOT return as Default Call.", usage.isDefaultCall());
		assertFalse("4.The call set as U free indicator. Should NOT return as Free Call.", usage.isFreeCall());
		assertFalse("5.The call set as U free indicator. Should NOT return as Promotion Call.", usage.isPromotionCall());
		assertFalse("6.The call set as U free indicator. Should NOT return as Included Call.", usage.isIncludedCall());			
	}
	
	public void setFreeIndicatorAsDefault() {
		usage.setFreeIndicator(" ");
		assertFalse("2.The call set as DEFAULT free indicator. Should NOT return as Charged Call.", usage.isChargedCall());	
		assertTrue("3.The call set as DEFAULT free indicator. MUST return as Default Call.", usage.isDefaultCall());
		assertFalse("4.The call set as DEFAULT free indicator. Should NOT return as Free Call.", usage.isFreeCall());
		assertFalse("5.The call set as DEFAULT free indicator. Should NOT return as Promotion Call.", usage.isPromotionCall());
		assertFalse("6.The call set as DEFAULT free indicator. Should NOT return as Included Call.", usage.isIncludedCall());			
	}	

	public void setFreeIndicatorAsFree() {
		usage.setFreeIndicator("F");
		assertFalse("2.The call set as FREE free indicator. Should NOT return as Charged Call.", usage.isChargedCall());	
		assertFalse("3.The call set as FREE free indicator.  Should NOT return as Default Call.", usage.isDefaultCall());
		assertTrue("4.The call set as FREE free indicator. MUST return as Free Call.", usage.isFreeCall());
		assertFalse("5.The call set as FREE free indicator. Should NOT return as Promotion Call.", usage.isPromotionCall());
		assertFalse("6.The call set as FREE free indicator. Should NOT return as Included Call.", usage.isIncludedCall());			
	}	

	public void setFreeIndicatorAsPromotion() {
		usage.setFreeIndicator("M");
		assertFalse("2.The call set as PROMOTION free indicator. Should NOT return as Charged Call.", usage.isChargedCall());	
		assertFalse("3.The call set as PROMOTION free indicator.  Should NOT return as Default Call.", usage.isDefaultCall());
		assertFalse("4.The call set as PROMOTION free indicator. Should NOT return as Free Call.", usage.isFreeCall());
		assertTrue("5.The call set as PROMOTION free indicator. Should NOT return as Promotion Call.", usage.isPromotionCall());
		assertFalse("6.The call set as PROMOTION free indicator. Should NOT return as Included Call.", usage.isIncludedCall());			
	}		
	
	public void setFreeIndicatorAsIncluded() {
		usage.setFreeIndicator("I");
		assertFalse("2.The call set as INCLUDED free indicator. Should NOT return as Charged Call.", usage.isChargedCall());	
		assertFalse("3.The call set as INCLUDED free indicator.  Should NOT return as Default Call.", usage.isDefaultCall());
		assertFalse("4.The call set as INCLUDED free indicator. Should NOT return as Free Call.", usage.isFreeCall());
		assertFalse("5.The call set as INCLUDED free indicator. Should NOT return as Promotion Call.", usage.isPromotionCall());
		assertTrue("6.The call set as INCLUDED free indicator. MUST return as Included Call.", usage.isIncludedCall());			
	}		
}
