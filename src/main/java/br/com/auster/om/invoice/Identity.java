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
 * Created on Jan 26, 2005
 */
package br.com.auster.om.invoice;

/**
 * @hibernate.class
 *              table="INV_IDENTITY"
 *          
 *          
 * <p><b>Title:</b> Identity</p>
 * <p><b>Description:</b> Represents an Identity for an Entity. This usually is a document like
 * CPF or CNPJ</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: Identity.java 107 2006-01-12 13:59:20Z etirelli $
 */
public class Identity extends InvoiceModelObject {
	private static final long serialVersionUID = 5402298548649418797L;
	
	private String identityType;
	private String identityNumber;
	private String identityAttrib1;
	private String identityAttrib2;
	private String identityAttrib3;
	
	
	
	/**
	 * @hibernate.property
	 *            column="ATTRIB_1"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getIdentityAttrib1() {
		return identityAttrib1;
	}
	public void setIdentityAttrib1(String identityAttrib1) {
		this.identityAttrib1 = identityAttrib1;
	}
	
	/**
	 * @hibernate.property
	 *            column="ATTRIB_2"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getIdentityAttrib2() {
		return identityAttrib2;
	}
	public void setIdentityAttrib2(String identityAttrib2) {
		this.identityAttrib2 = identityAttrib2;
	}
	
	/**
	 * @hibernate.property
	 *            column="ATTRIB_3"
	 *            type="string"
	 *            length="128"
	 *            not-null="false"
	 */
	public String getIdentityAttrib3() {
		return identityAttrib3;
	}
	public void setIdentityAttrib3(String identityAttrib3) {
		this.identityAttrib3 = identityAttrib3;
	}
	
	/**
	 * @hibernate.property
	 *            column="IDENT_NUMBER"
	 *            type="string"
	 *            length="128"
	 *            not-null="true"
	 */
	public String getIdentityNumber() {
		return identityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
	
	/**
	 * @hibernate.property
	 *            column="IDENT_TYPE"
	 *            type="string"
	 *            length="32"
	 *            not-null="true"
	 */
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	
	/**
	 * Compares 2 user objects
	 * 
	 * @param o object to compare
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		int ret = 0;
		if(o instanceof Identity) {
			Identity identity = (Identity) o;
			if( (identity.getIdentityType() != null) &&
					(identity.getIdentityNumber() != null) &&
					(this.getIdentityType() != null) &&
					(this.getIdentityNumber() != null)) {
				ret = identity.getIdentityType().compareTo(this.getIdentityType());
				if(ret == 0) {
					ret = identity.getIdentityNumber().compareTo(this.getIdentityNumber());
				}
			}
		} else {
			throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
		}
		return ret;
	}
	
}
