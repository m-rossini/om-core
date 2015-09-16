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
package br.com.auster.om.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.log4j.Logger;

/**
 * <p>
 * <b>Title:</b> ParserUtils
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
 * @version $Id: ParserUtils.java 456 2007-07-06 01:55:36Z mtengelm $
 */

public class ParserUtils {

	protected static Logger						log												= Logger
																																	.getLogger(ParserUtils.class);

	public static final String				DEFAULT_DATE_PATTERN			= "yyyyMMdd";
	public static final String				DEFAULT_DATETIME_PATTERN	= "yyyyMMddHHmmss";

	public static final String				EMPTY_STRING							= "";
	public static final Date					EMPTY_DATE								= new Date(0);

	private static final ThreadLocal	dateParser								= new ThreadLocal() {
																																protected Object initialValue() {
																																	return new SimpleDateFormat();
																																}
																															};

	private static final ThreadLocal	dttmParser								= new ThreadLocal() {
																																protected Object initialValue() {
																																	return new SimpleDateFormat();
																																}
																															};

	private static final ThreadLocal	doubleParser							= new ThreadLocal() {
																																protected Object initialValue() {
																																	return NumberFormat
																																			.getInstance(Locale.US);
																																}
																															};

	private static final ThreadLocal	intParser									= new ThreadLocal() {
																																protected Object initialValue() {
																																	return NumberFormat
																																			.getInstance(Locale.US);
																																}
																															};

	protected ParserUtils() {
	}

	/**
	 * If the specified value is not <code>null</code>, then trailing/ceiling
	 * white spaces are removed and the remaining string is returned. If the value
	 * is <code>null</code> then this method results in an empty string.
	 */
	public static String getString(String _value) {
		return (_value != null) ? _value.trim() : ParserUtils.EMPTY_STRING;
	}

	/**
	 * Parses the double value in string format into a <code>double</code>. The
	 * expected format for such string is the <code>en_US</code> pattern.
	 */
	public static double getDouble(String _value) {
		return getDouble(_value, 0);
	}

	/**
	 * Parses the double value in string format into a <code>double</code>. The
	 * expected format for such string is the <code>en_US</code> pattern.
	 * 
	 * If input value is not a valid number, then return the value of toReturn
	 * argument
	 */
	public static double getDouble(String _value, double toReturn) {
		if ((_value == null)) {
			return toReturn;
		}
		_value = _value.trim();
		if (_value.equals(EMPTY_STRING)) {
			return toReturn;
		}
		try {
			Number myNumber = null;
			myNumber = ((NumberFormat) doubleParser.get()).parse(_value);
			return myNumber.doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			return toReturn;
		}
	}

	/**
	 * Parses the integer value in string format into an <code>int</code>. The
	 * expected format for such string is the <code>en_US</code> pattern.
	 */
	public static int getInt(String _value) {
		return getInt(_value, 0);
	}

	/**
	 * Parses the integer value in string format into an <code>int</code>. The
	 * expected format for such string is the <code>en_US</code> pattern.
	 * 
	 * If the input value is not valid, ie, null, empty string or any NOT number
	 * string, then this method will return the toReturn input argument
	 */
	public static int getInt(String _value, int toReturn) {
		if (_value == null) {
			return toReturn;
		}
		_value = _value.trim();
		if (_value.equals(EMPTY_STRING)) {
			return toReturn;
		}
		try {
			Number myNumber = null;
			myNumber = ((NumberFormat) intParser.get()).parse(_value);
			return myNumber.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return toReturn;
		}
	}

	/**
	 * Parses the date value in string format into a <code>java.util.Date</code>.
	 * <p>
	 * This method executes its sibling {@link #getDate(String, String)} using
	 * <code>_pattern</code> as {@value #DEFAULT_DATE_PATTERN}.
	 */
	public static Date getDate(String _value) {
		return getDate(_value, DEFAULT_DATE_PATTERN);
	}

	/**
	 * Parses the date value in string format into a <code>java.util.Date</code>.
	 * <p>
	 * The pattern should be specified in the
	 * <code>java.text.SimpleDateFormat</code> style.
	 */
	public static Date getDate(String _value, String _pattern) {
		if (_value == null) {
			return null;
		}
		_value = _value.trim();
		if (_value.equals(EMPTY_STRING)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = (SimpleDateFormat) dateParser.get();
			sdf.applyPattern(_pattern);
			return sdf.parse(_value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Same as {@link #getDate(String)}, but the date is adjusted into
	 * <code>_adjustment</code> miliseconds.
	 * <p>
	 * This method executes its sibling {@link #getDate(String, String)} using
	 * <code>_pattern</code> as {@value #DEFAULT_DATE_PATTERN}.
	 */
	public static Date getDate(String _value, long _adjustment) {
		return getDate(_value, _adjustment, DEFAULT_DATE_PATTERN);
	}

	/**
	 * Same as {@link #getDate(String, long)}, except that this version allows
	 * the selection of the date pattern.
	 */
	public static Date getDate(String _value, long _adjustment, String _pattern) {
		if (_value == null) {
			return null;
		}
		_value = _value.trim();
		if (_value.equals(EMPTY_STRING)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = (SimpleDateFormat) dateParser.get();
			sdf.applyPattern(_pattern);

			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(_value));
			cal.add(Calendar.MILLISECOND, (int) _adjustment);
			return cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Parses the value date in the format _pattern
	 * 
	 * Formats a Date into a date/time string.
     * @param date the time value to be formatted into a time string.  
	 *    	  pattern the pattern describing the date and time format
	 * @return the formatted time string. 
     * @exception NullPointerException if the given pattern is null
     * @exception IllegalArgumentException if the given pattern is invalid	
	 */
	public static String getString(Date date, String _pattern){
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat dateFormat= new SimpleDateFormat(_pattern);
			return dateFormat.format(date);	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date getDateNoTime(String _value, String _pattern) {
		if (_value == null) {
			return null;
		}
		_value = _value.trim();
		if (_value.equals(EMPTY_STRING)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = (SimpleDateFormat) dateParser.get();
			sdf.applyPattern(_pattern);

			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(_value));
			
			return adjustCalendar(cal, _pattern, 0, 0,0,0).getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected static Calendar adjustCalendar(Calendar cal, String _pattern, int hour, int minute, int second, int milli) {
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE,minute);
		cal.set(Calendar.SECOND,second);
		cal.set(Calendar.MILLISECOND, milli);		
		return cal;
	}
	
	public static Date getDateLastMiliSecond(String _value, String _pattern) {
		if (_value == null) {
			return null;
		}
		_value = _value.trim();
		if (_value.equals(EMPTY_STRING)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = (SimpleDateFormat) dateParser.get();
			sdf.applyPattern(_pattern);

			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(_value));
			
			return adjustCalendar(cal, _pattern, 23, 59,59,999).getTime();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Parses the date/time value in string format into a
	 * <code>java.util.Date</code>.
	 * <p>
	 * This method executes its sibling {@link #getDate(String, String)} using
	 * <code>_pattern</code> as {@value #DEFAULT_DATETIME_PATTERN}.
	 */
	public static Date getDateTime(String _value) {
		return getDateTime(_value, DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * Parses the date/time value in string format into a
	 * <code>java.util.Date</code>.
	 * <p>
	 * The pattern should be specified in the
	 * <code>java.text.SimpleDateFormat</code> style.
	 */
	public static Date getDateTime(String _value, String _pattern) {
		if (_value == null) {
			return null;
		}
		_value = _value.trim();
		try {
			SimpleDateFormat sdf = (SimpleDateFormat) dttmParser.get();
			sdf.applyPattern(_pattern);
			return sdf.parse(_value);
		} catch (Exception e) {
			log.error("Problems Converting Date. See problem below. Null WAS returned. Value:"
					+ _value + ".Pattern:" + _pattern, e);
			return null;
		}
	}

	/**
	 * Parses the boolean value in string format into a <code>boolean</code>.
	 * <p>
	 * If the <code>val</code> is one of the following then the method returns
	 * <code>true</code>. True values are: <code>Y, S, T, yes, sim, true</code>.
	 * Otherwise, <code>false</code> is returned.
	 */
	public static boolean getBoolean(String val) {
		boolean ret = false;
		if (val == null) {
			return ret;
		}
		val = val.trim();
		if ((val != null)
				&& (("Y".equalsIgnoreCase(val)) || ("S".equalsIgnoreCase(val))
						|| ("T".equalsIgnoreCase(val)) || ("yes".equalsIgnoreCase(val))
						|| ("sim".equalsIgnoreCase(val)) || ("true".equalsIgnoreCase(val))))
			ret = true;
		return ret;
	}

	public static UnitCounter getUnitCounter(String type, String val) {
		UnitCounter counter = null;
		if ((type != null) && (val != null)) {
			val = val.trim();
			counter = new UnitCounter();
			if (type.equalsIgnoreCase("MINUTES")) {
				counter.setType(UnitCounter.TIME_COUNTER);
				counter.addMinutes(ParserUtils.getDouble(val));
			} else if (type.equalsIgnoreCase("MINUTOS")) {
				counter.setType(UnitCounter.TIME_COUNTER);
				counter.addMinutes(ParserUtils.getDouble(val));
			} else if (type.equalsIgnoreCase("SECONDS")) {
				counter.setType(UnitCounter.TIME_COUNTER);
				counter.addSeconds(ParserUtils.getInt(val));
			} else if (type.equalsIgnoreCase("SEGUNDOS")) {
				counter.setType(UnitCounter.TIME_COUNTER);
				counter.addSeconds(ParserUtils.getInt(val));
			} else if (type.equalsIgnoreCase("KB")) {
				counter.setType(UnitCounter.DATA_COUNTER);
				counter.addKBytes(ParserUtils.getDouble(val));
			} else if (type.equalsIgnoreCase("KBYTES")) {
				counter.setType(UnitCounter.DATA_COUNTER);
				counter.addKBytes(ParserUtils.getDouble(val));
			} else if (type.equalsIgnoreCase("MB")) {
				counter.setType(UnitCounter.DATA_COUNTER);
				counter.addMBytes(ParserUtils.getDouble(val));
			} else if (type.equalsIgnoreCase("DOWNLOAD")) {
				counter.setType(UnitCounter.UNIT_COUNTER);
				counter.addUnits(ParserUtils.getInt(val));
			} else if (type.equalsIgnoreCase("EVENT")) {
				counter.setType(UnitCounter.UNIT_COUNTER);
				counter.addUnits(ParserUtils.getInt(val));
			} else if (type.equalsIgnoreCase("EVENTO")) {
				counter.setType(UnitCounter.UNIT_COUNTER);
				counter.addUnits(ParserUtils.getInt(val));
			} else if (type.equalsIgnoreCase("UNIT")) {
				counter.setType(UnitCounter.UNIT_COUNTER);
				counter.addUnits(ParserUtils.getInt(val));
			} else if (type.equalsIgnoreCase("TIME")) {
				counter.setType(UnitCounter.TIME_COUNTER);
				counter.addSeconds(ParserUtils.getInt(val));
			} else {
				counter.setType(UnitCounter.TIME_COUNTER);
				counter.addMinutes(ParserUtils.getDouble(val));
			}
		}
		return counter;
	}

}
