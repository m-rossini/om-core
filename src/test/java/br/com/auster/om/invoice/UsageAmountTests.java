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

import br.com.auster.om.util.UnitCounter;
import junit.framework.TestCase;

/**
 * @author mtengelm
 * 
 */
// TODO Comment this class br.com.auster.om.invoice "."
// UsageDetailFlagstest.java
public class UsageAmountTests extends TestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAmountRatio() {
		UsageDetail usage = new UsageDetail(); 
		assertNotNull("1.Usage MUST not be null", usage);
		
		usage.setTotalAmount(10);
		assertEquals("MUST BE Double.NaN, because UC is NULL", usage.getAmountRatio(), Double.NaN);
		
		UnitCounter uc = new UnitCounter();
		uc.setType(UnitCounter.TIME_COUNTER);
		uc.setUnits(0);
		usage.setUsageDuration(uc);
		assertEquals("MUST BE Double.NaN, because UC is ZERO", usage.getAmountRatio(), Double.NaN);
		
		uc.setUnits(1);
		assertEquals("MUST BE 10", usage.getAmountRatio(), 10.0D);	
		
		uc.setUnits(2);
		assertEquals("MUST BE 5", usage.getAmountRatio(), 5.0D);	
		
		uc.setUnits(4);
		assertEquals("MUST BE 2.5", usage.getAmountRatio(), 2.5D);
		
		uc.setUnits(5);
		assertEquals("MUST BE 2", usage.getAmountRatio(), 2.0D);

		uc.setUnits(8);
		assertEquals("MUST BE 1.25", usage.getAmountRatio(), 1.25D);
		
		uc.setUnits(10);
		assertEquals("MUST BE 1", usage.getAmountRatio(), 1.0D);
		
		uc.setUnits(3);
		assertEquals("MUST BE 3.333->", usage.getAmountRatio(), 3.333D, 0.001D);
		
		uc.setUnits(6);
		assertEquals("MUST BE 1.666->", usage.getAmountRatio(), 1.666D, 0.001D);
		
		uc.setUnits(7);
		assertEquals("MUST BE 1.428->", usage.getAmountRatio(), 1.428D, 0.001D);
		
		uc.setUnits(9);
		assertEquals("MUST BE 1.111->", usage.getAmountRatio(), 1.111D, 0.001D);			
	}
}
