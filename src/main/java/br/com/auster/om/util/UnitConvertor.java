
/*
 * Copyright (c) 2004-2005 Auster Solutions do Brasil. All Rights Reserved.
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
 * Created on Oct 11, 2005
 */
package br.com.auster.om.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


/**
 * Helper class to convert from customized unit types into known units. Known units are
 * 	those defined in the <code>UnitCounter</code> class.
 * 
 * @author framos
 * @version $Id: UnitConvertor.java 76 2005-11-08 20:20:09Z framos $
 */
public class UnitConvertor {

	
	
	private Map convertion;
	

	protected UnitConvertor() {
		convertion = new HashMap();
	}
	

	
	public synchronized static UnitConvertor getInstance(Connection _connection) throws SQLException  {
		
		UnitConvertor uc = new UnitConvertor();
		Statement stmt = null;
		ResultSet rset = null;
		try {
			stmt = _connection.createStatement();
			rset = stmt.executeQuery("select * from om_unit_convertion");
			while (rset.next()) {
				uc.addConvertionFactor(rset.getString("from_unit"), rset.getString("to_unit"), rset.getDouble("convertion_rate"));
			}
		} finally {
			if (stmt != null) { stmt.close(); }
			if (rset != null) { rset.close(); }
		}
		return uc;
	}

	/**
	 * Adds a new convertion factor. This maps a customized unit type into a knwon one, with a convertion factor.
	 *  The <code>_rate</code> parameter indicates the value used to convert from the customized unit type.
	 *  
	 * @param _fromUnit the customized unit type
	 * @param _toUnit the known unit type
	 * @param _rate the factor which to multiply when converting units
	 * 
	 * @return if there was a previously set convertion factor
	 */
	public void addConvertionFactor(String _fromUnit, String _toUnit, double _rate) {
		Map m = (Map) convertion.get(_fromUnit.toUpperCase());
		if (m == null) {
			m = new HashMap();
			convertion.put(_fromUnit.toUpperCase(), m);
		}
		m.put(_toUnit.toUpperCase(), Double.valueOf(_rate));
	}
	
	/**
	 * Returns the double value to be used as factor to convert from <code>_fromUnit</code> units
	 * 	into <code>_toUnit</code> units. If units are equals, or there is no convertion factor defined
	 * 	for the specified pair of types, then <code>1L</code> will be returned, so it wont influenciate
	 * 	the final value. 
	 * 
	 * @param _fromUnit the usage unit type
	 * @param _toUnit the service price unit type 
	 * 
	 * @return the factor used to transform from the usage unit type to the service price unit type
	 */
	public double getConvertionFactor(String _fromUnit, String _toUnit) {
		if (_fromUnit.toUpperCase().equals(_toUnit.toUpperCase())) {
			return 1D;
		}
		Map m = (Map) convertion.get(_fromUnit.toUpperCase());
		if (m == null) {
			return 1D;
		}
		Double d = (Double) m.get(_toUnit.toUpperCase());
		return (d == null ? 1D : d.doubleValue());
	}
	
	public void doNothing() {
	}	
}
