/*
 * Copyright (c) 2004-2007 Auster Solutions. All Rights Reserved.
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
 * Created on 22/03/2007
 */
package br.com.auster.om.invoice;

import junit.framework.TestCase;


/**
 * TODO What this class is responsible for
 *
 * @author mtengelm
 * @version $Id$
 * @since 22/03/2007
 */
public class UsageDetailIsEmptyTest extends TestCase {

	/**
	 * TODO why this methods was overriden, and what's the new expected behavior.
	 * 
	 * @throws java.lang.Exception
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * TODO why this methods was overriden, and what's the new expected behavior.
	 * 
	 * @throws java.lang.Exception
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link br.com.auster.om.invoice.UsageDetail#isCalledNumberEmpty()}.
	 */
	public void testIsCalledNumberEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isCalledNumberEmpty());
		ud.setCalledNumber("");
		assertTrue("Should be reported as empty", ud.isCalledNumberEmpty());
		ud.setCalledNumber("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isCalledNumberEmpty());		
	}

	public void testIsAreaEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isAreaEmpty());
		ud.setArea("");
		assertTrue("Should be reported as empty", ud.isAreaEmpty());
		ud.setArea("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isAreaEmpty());		
	}	
	
	public void testIsCallClassEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isCallClassEmpty());
		ud.setCallClass("");
		assertTrue("Should be reported as empty", ud.isCallClassEmpty());
		ud.setCallClass("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isCallClassEmpty());		
	}
	
	public void testIsCFOPEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isCFOPEmpty());
		ud.setDetailCFOP("");
		assertTrue("Should be reported as empty", ud.isCFOPEmpty());
		ud.setDetailCFOP("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isCFOPEmpty());		
	}
	
	public void testIsChannelIdEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isChannelIdEmpty());
		ud.setChannelId("");
		assertTrue("Should be reported as empty", ud.isChannelIdEmpty());
		ud.setChannelId("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isChannelIdEmpty());		
	}
	
	public void testIsDestinationCityEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isDestinationCityEmpty());
		ud.setDestinationCity("");
		assertTrue("Should be reported as empty", ud.isDestinationCityEmpty());
		ud.setDestinationCity("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isDestinationCityEmpty());		
	}
	
	public void testIsDestinationCountryEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isDestinationCountryEmpty());
		ud.setDestinationCountry("");
		assertTrue("Should be reported as empty", ud.isDestinationCountryEmpty());
		ud.setDestinationCountry("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isDestinationCountryEmpty());		
	}
	
	public void testIsDestinationStateEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isDestinationStateEmpty());
		ud.setDestinationState("");
		assertTrue("Should be reported as empty", ud.isDestinationStateEmpty());
		ud.setDestinationState("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isDestinationStateEmpty());		
	}	
		
	public void testIsDirectionEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isDirectionEmpty());
		ud.setDirection("");
		assertTrue("Should be reported as empty", ud.isDirectionEmpty());
		ud.setDirection("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isDirectionEmpty());		
	}

	public void testIsFreeIndicatorEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isFreeIndicatorEmpty());
		ud.setFreeIndicator("");
		assertTrue("Should be reported as empty", ud.isFreeIndicatorEmpty());
		ud.setFreeIndicator("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isFreeIndicatorEmpty());		
	}

	public void testIsGroupIdEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isGroupIdEmpty());
		ud.setGroupId("");
		assertTrue("Should be reported as empty", ud.isGroupIdEmpty());
		ud.setGroupId("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isGroupIdEmpty());		
	}	

	public void testIsObjectTypeEmpty() {
		UsageDetail ud = new UsageDetail();
		assertFalse("Should NOT be reported as empty", ud.isObjectTypeEmpty());		
		ud.setObjectType(null);
		assertTrue("Should be reported as empty", ud.isObjectTypeEmpty());
		ud.setObjectType("");
		assertTrue("Should be reported as empty", ud.isObjectTypeEmpty());
		ud.setObjectType("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isObjectTypeEmpty());		
	}	
	
	public void testIsOriginalDatetimeEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isOriginalDatetimeEmpty());
		ud.setOriginalDatetime("");
		assertTrue("Should be reported as empty", ud.isOriginalDatetimeEmpty());
		ud.setOriginalDatetime("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isOriginalDatetimeEmpty());		
	}	
	
	public void testIsOriginalUsageDurationEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isOriginalUsageDurationEmpty());
		ud.setOriginalUsageDuration("");
		assertTrue("Should be reported as empty", ud.isOriginalUsageDurationEmpty());
		ud.setOriginalUsageDuration("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isOriginalUsageDurationEmpty());		
	}	
	
	public void testIsOriginalUsageBilledUnitsEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isOriginalUsageBilledUnitsEmpty());
		ud.setOriginalUsageBilledUnits("");
		assertTrue("Should be reported as empty", ud.isOriginalUsageBilledUnitsEmpty());
		ud.setOriginalUsageBilledUnits("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isOriginalUsageBilledUnitsEmpty());		
	}	
	
	public void testIsOriginCityEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isOriginCityEmpty());
		ud.setOriginCity("");
		assertTrue("Should be reported as empty", ud.isOriginCityEmpty());
		ud.setOriginCity("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isOriginCityEmpty());		
	}		
	
	public void testIsOriginSIDEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isOriginSIDEmpty());
		ud.setOriginSID("");
		assertTrue("Should be reported as empty", ud.isOriginSIDEmpty());
		ud.setOriginSID("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isOriginSIDEmpty());		
	}	
	
	public void testIsOriginCountryEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isOriginCountryEmpty());
		ud.setOriginCountry("");
		assertTrue("Should be reported as empty", ud.isOriginCountryEmpty());
		ud.setOriginCountry("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isOriginCountryEmpty());		
	}	

	public void testIsOriginStateEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isOriginStateEmpty());
		ud.setOriginState("");
		assertTrue("Should be reported as empty", ud.isOriginStateEmpty());
		ud.setOriginState("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isOriginStateEmpty());		
	}	

	public void testIsStepEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isStepEmpty());
		ud.setStep("");
		assertTrue("Should be reported as empty", ud.isStepEmpty());
		ud.setStep("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isStepEmpty());		
	}	

	public void testIsSvcIdEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isSvcIdEmpty());
		ud.setSvcId("");
		assertTrue("Should be reported as empty", ud.isSvcIdEmpty());
		ud.setSvcId("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isSvcIdEmpty());		
	}

	public void testIsSvcPlanEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isSvcPlanEmpty());
		ud.setSvcPlan("");
		assertTrue("Should be reported as empty", ud.isSvcPlanEmpty());
		ud.setSvcPlan("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isSvcPlanEmpty());		
	}	
	
	public void testIsTariffClassEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isTariffClassEmpty());
		ud.setTariffClass("");
		assertTrue("Should be reported as empty", ud.isTariffClassEmpty());
		ud.setTariffClass("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isTariffClassEmpty());		
	}	
	
	public void testIsTariffEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isTariffEmpty());
		ud.setTariff("");
		assertTrue("Should be reported as empty", ud.isTariffEmpty());
		ud.setTariff("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isTariffEmpty());		
	}	
	
	public void testIsTypeEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isTypeEmpty());
		ud.setType("");
		assertTrue("Should be reported as empty", ud.isTypeEmpty());
		ud.setType("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isTypeEmpty());		
	}		
	/**
	 * Test method for {@link br.com.auster.om.invoice.UsageDetail#isCallIndicatorEmpty()}.
	 */
	public void testIsCallIndicatorEmpty() {
		UsageDetail ud = new UsageDetail();
		assertTrue("Should be reported as empty", ud.isCallIndicatorEmpty());
		ud.setCallIndicator("");
		assertTrue("Should be reported as empty", ud.isCallIndicatorEmpty());
		ud.setCallIndicator("ANYVALUE");
		assertFalse("Should NOT be reported as empty", ud.isCallIndicatorEmpty());	
	}

}
