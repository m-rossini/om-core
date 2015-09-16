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
 * Created on Sep 16, 2005
 */
package br.com.auster.om.pricing;

import br.com.auster.om.reference.CatalogEntity;


/**
 * @hibernate.class
 *          table="PRC_SERVICE"
 *          
 *           
 * @author framos
 * @version $Id: Service.java 59 2005-10-25 16:59:10Z framos $
 */
public class Service extends CatalogEntity {

	 
	
	// ---------------------------
	// Instance variables
	// ---------------------------

	private String name;
	private String description;	
	private String externalId;
	private String type;
	
	private Service parent;
	
	
	
	// ---------------------------
	// Constructors
	// ---------------------------
	
	public Service() {
		this(0);
	}
	
	public Service(long _uid) {
		super(_uid);
	}
	


	// ---------------------------
	// Public methods
	// ---------------------------
	
	/**
	 * @hibernate.property
	 *            column="SERVICE_DESCRIPTION"
	 *            type="string"
	 *            length="60"
	 *            not-null="false"
	 */	
	public String getServiceDescription() {
		return description;
	}

	public void setServiceDescription(String description) {
		this.description = description;
	}

	/**
	 * @hibernate.property
	 *            column="SERVICE_EXTERNAL_ID"
	 *            type="string"
	 *            length="30"
	 *            not-null="true"
	 */	
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * @hibernate.property
	 *            column="SERVICE_NAME"
	 *            type="string"
	 *            length="20"
	 *            not-null="false"
	 */	
	public String getServiceName() {
		return name;
	}

	public void setServiceName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property
	 *            column="SERVICE_TYPE"
	 *            type="string"
	 *            length="1"
	 *            not-null="false"
	 */	
	public String getServiceType() {
		return type;
	}

	public void setServiceType(String type) {
		this.type = type;
	}
	
	/**
     * @hibernate.many-to-one
     *          column="PARENT_SERVICE_ID"
     *          not-null="false"
     */
	public Service getParentService() {
		return parent;
	}

	public void setParentService(Service parent) {
		this.parent = parent;
	}	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return 
		"[Service]" +
		" uid=" + getUid() +
		" name=" + getServiceName() +
		" desc=" + getServiceDescription() + 
		" type=" + getServiceType();
 	}

}
