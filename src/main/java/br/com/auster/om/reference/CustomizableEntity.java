package br.com.auster.om.reference;


import java.io.Serializable;

/**
 * @author framos
 * @version $Id: CustomizableEntity.java 47 2005-09-29 20:05:07Z framos $
 */
public abstract class CustomizableEntity extends PKEnabledEntity implements Serializable {

	
	
	// ---------------------------
	// Instance variables
	// ---------------------------
	
	private String custom1;
	private String custom2;
	private String custom3;
	
	
	
	// ---------------------------
	// Constructors
	// ---------------------------
	
	public CustomizableEntity() {
		this(0);
	}
	
	public CustomizableEntity(long _uid) {
		setUid(_uid);
	}
	
	
	
	// ---------------------------
	// Public methods
	// ---------------------------
	
	/**
	 * @hibernate.property
     *   column="CUSTOM_1"
     *   type="string"
     *   length="30"
     *   not-null="false"
	 */	
	public String getCustom1() {
		return custom1;
	}
	
	public void setCustom1(String _custom) {
		custom1 = _custom;
	}
	
	/**
	 * @hibernate.property
     *   column="CUSTOM_2"
     *   type="string"
     *   length="30"
     *   not-null="false"
	 */	
	public String getCustom2() {
		return custom2;
	}
	
	public void setCustom2(String _custom) {
		custom2 = _custom;
	}
	
	/**
	 * @hibernate.property
     *   column="CUSTOM_3"
     *   type="string"
     *   length="30"
     *   not-null="false"
	 */		
	public String getCustom3() {
		return custom3;
	}
	
	public void setCustom3(String _custom) {
		custom3 = _custom;
	}	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString();
	}
	
	
	
	// ---------------------------
	// static methods
	// ---------------------------
	
	/**
	 * Merges the attributes of two <code>CustomizableEntity</code> into one of them.
	 * <p>
	 * From the two parameters, one is elected 
	 */
	public static CustomizableEntity mergeNonKeyAttributes(CustomizableEntity _c1, CustomizableEntity _c2) {
		if (_c1 == null) {
			return _c2;
		} else if (_c2 == null) {
			return _c1;
		}
		CustomizableEntity finalObj = (_c1.getUid() > 0 ? _c1 : _c2);
		CustomizableEntity otherObj = (_c1.getUid() > 0 ? _c2 : _c1);

		finalObj.setCustom1((finalObj.getCustom1() == null ? otherObj.getCustom1() : finalObj.getCustom1()));
		finalObj.setCustom2((finalObj.getCustom2() == null ? otherObj.getCustom2() : finalObj.getCustom2()));
		finalObj.setCustom3((finalObj.getCustom3() == null ? otherObj.getCustom3() : finalObj.getCustom3()));
		return finalObj;
	}	
}
