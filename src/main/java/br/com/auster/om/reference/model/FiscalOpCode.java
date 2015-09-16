/*
 * Copyright (c) 2004 TTI Tecnologia. All Rights Reserved.
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
 * Created on Nov 22, 2005
 */
package br.com.auster.om.reference.model;

import br.com.auster.om.reference.CatalogEntity;

/**
 * @hibernate.class
 *          table="REF_FISCAL_OPCODE"
 * 
 * @author framos
 * @version $Id$
 */
public class FiscalOpCode extends CatalogEntity {

	
	
	// ---------------------------
	// Instance variables
	// ---------------------------
	
	private String opCode;
	private String opDescription;

	
	
	// ---------------------------
	// Constructors
	// ---------------------------

	public FiscalOpCode() {
		super();
	}
	
	public FiscalOpCode(long _uid) {
		super(_uid);
	}
	
	
	
	// ---------------------------
	// Public methods
	// ---------------------------
	
	/**
	 * @hibernate.property
	 *            column="OPERATION_CODE"
	 *            type="string"
	 *            length="10"
	 *            unique="true"
	 *            not-null="true"
	 */
	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	/**
	 * @hibernate.property
	 *            column="OPERATION_DESCRIPTION"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getOpDescription() {
		return opDescription;
	}

	public void setOpDescription(String opDescription) {
		this.opDescription = opDescription;
	}

	/**
	 * @see java.lang.Object#toString() 
	 */
	public String toString() {
		return "[FiscalOpCode] " +
		       " uid=" + getUid() +
		       " code=" + getOpCode() +
		       " description=" + getOpDescription(); 
	}
}
