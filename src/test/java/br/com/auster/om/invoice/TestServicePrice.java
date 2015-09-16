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
 * Created on 13/03/2007
 */
package br.com.auster.om.invoice;

import br.com.auster.om.pricing.ServicePrice;
import junit.framework.TestCase;


/**
 * TODO What this class is responsible for
 *
 * @author mtengelm
 * @version $Id$
 * @since 13/03/2007
 */
public class TestServicePrice extends TestCase {

	private ServicePrice	sp;

	/**
	 * TODO why this methods was overriden, and what's the new expected behavior.
	 * 
	 * @throws java.lang.Exception
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();		
	}

	public void testPassthruFlag() {
		sp = new ServicePrice();
		
		assertFalse("Should be FALSE.", sp.isPassThrough());
		
		sp.setPassThrough("A");
		assertFalse("Should be FALSE.", sp.isPassThrough());

		sp.setPassThrough("1");
		assertFalse("Should be FALSE.", sp.isPassThrough());

		sp.setPassThrough("@");
		assertFalse("Should be FALSE.", sp.isPassThrough());
		
		
		sp.setPassThrough("Z");
		assertFalse("Should be FALSE.", sp.isPassThrough());
		
		sp.setPassThrough("P");
		assertTrue("Should be TRUE.", sp.isPassThrough());
		
		sp.setPassThrough("M");
		assertTrue("Should be TRUE.", sp.isPassThrough());		

	}
	/**
	 * TODO why this methods was overriden, and what's the new expected behavior.
	 * 
	 * @throws java.lang.Exception
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		sp = null;
		super.tearDown();
	}

}
