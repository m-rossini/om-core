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
 * Created on 28/06/2007
 */
package br.com.auster.om.invoice;

import br.com.auster.om.pricing.ServicePrice;
import br.com.auster.om.pricing.ServicePriceNegotiated;
import junit.framework.TestCase;


/**
 * TODO What this class is responsible for
 *
 * @author mtengelm
 * @version $Id$
 * @since 28/06/2007
 */
public class UsageNegotiated extends TestCase {

	public void testNegoatiated() {
		ServicePrice sp;
		
		ServicePrice p1 = new ServicePrice();		
		sp=p1;		
		if (sp instanceof ServicePrice) {
			assertNotNull(p1);
		} else {
			fail("SP Should be an instance of ServicePrice.");
		}

		if (sp instanceof ServicePriceNegotiated) {
			fail("SP Should be an instance of ServicePrice.");			
		} else {
			assertNotNull(p1);
		}		
		
		ServicePriceNegotiated p2 = new ServicePriceNegotiated();
		sp=p2;
		if (sp instanceof ServicePriceNegotiated) {
			assertNotNull(p2);
		} else {
			fail("SP Should be an instance of ServicePrice.");
		}		
	}
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

}
