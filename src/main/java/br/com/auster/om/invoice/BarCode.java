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
 * Created on Jan 20, 2005
 */
package br.com.auster.om.invoice;


/**
 * @hibernate.class 
 * 			table="INV_BARCODE"
 *              
 *              
 * @author etirelli
 * @version $Id: BarCode.java 187 2006-08-18 14:22:38Z framos $
 */
public class BarCode extends InvoiceModelObject {
	private static final long serialVersionUID = -1493208760714302196L;
  
    private String ocrLeftLine = null;
	private String ocrRightLine = null;
	private String alphaNumericLine = null;
	
	
	public BarCode() {
	}
	
	/**
	 * @hibernate.property
	 *            column="OCR_LEFT_LINE"
	 *            type="string"
	 *            length="60"
	 *            not-null="false"
	 */
	public String getOCRLeftLine() {
		return ocrLeftLine;
	}
	public void setOCRLeftLine(String _ocrLeftLine) {
		this.ocrLeftLine = _ocrLeftLine;
	}
	
	/**
	 * @hibernate.property
	 *            column="OCR_RIGHT_LINE"
	 *            type="string"
	 *            length="60"
	 *            not-null="false"
	 */
	public String getOCRRightLine() {
		return ocrRightLine;
	}
	public void setOCRRightLine(String _ocrRightLine) {
		this.ocrRightLine = _ocrRightLine;
	}
	
	/**
	 * @hibernate.property
	 *            column="ALPHA_BARCODE"
	 *            type="string"
	 *            length="200"
	 *            not-null="true"
	 */
	public String getAlphaNumericBarCode() {
		return alphaNumericLine;
	}
	public void setAlphaNumericBarCode(String _alphaNumericLine) {
		this.alphaNumericLine = _alphaNumericLine;
	}
	

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getName() + "=" + this.ocrLeftLine;
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		if(o instanceof BarCode) {
			BarCode barcode = (BarCode) o;
			return this.alphaNumericLine.compareTo(barcode.alphaNumericLine);
		} else {
			throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
		}
	}
	
}
