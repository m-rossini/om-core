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
 * Created on 20/11/2007
 */
package br.com.auster.om.util;

import java.util.Date;
import junit.framework.TestCase;

/**
 * TODO What this class is responsible for
 *
 * @author mtengelm
 * @version $Id$
 * @since JDK1.4
 */
public class TestParseUtils extends TestCase  {

	@Override
	protected void setUp() throws Exception {
		// These test cases work only if user.timezone=GMT-3
		// since it is the way Billcheckout is actually run.
		System.setProperty("user.timezone", "GMT-3");
	}

	public void testGetDateNoTime() {
		String pattern = "yyyyMMdd";
		
		String d1 = "20080201";
		String d2 = "20080202";
		String d3 = "20080203";
			
		Date date1 = ParserUtils.getDateNoTime(d1, pattern);
		long l1 = 1201834800000L;
		assertEquals((long) date1.getTime(), l1);
		
		Date date2 = ParserUtils.getDateNoTime(d2, pattern);
		long l2 = 1201921200000L;
		assertEquals((long) date2.getTime(), l2);
		
		Date date3 = ParserUtils.getDateNoTime(d3, pattern);
		long l3 = 1202007600000L;
		assertEquals((long) date3.getTime(), l3);

		System.out.println(date1.getTime());
		System.out.println(date2.getTime());
		System.out.println(date3.getTime());
	}
	
	public void testGetDateLastMilliSecond() {
		String pattern = "yyyyMMdd";
		
		String d1 = "20071103";
		String d2 = "20071104";
		String d3 = "20071105";
			
		Date date1 = ParserUtils.getDateLastMiliSecond(d1, pattern);
		long l1 = 1194145199999L;
		assertEquals((long) date1.getTime(), l1);
		
		Date date2 = ParserUtils.getDateLastMiliSecond(d2, pattern);
		long l2 = 1194231599999L;
		assertEquals((long) date2.getTime(), l2);
		
		Date date3 = ParserUtils.getDateLastMiliSecond(d3, pattern);
		long l3 = 1194317999999L;
		assertEquals((long) date3.getTime(), l3);
	}
}
