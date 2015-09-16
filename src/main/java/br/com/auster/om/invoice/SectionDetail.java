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
 * Created on Jan 27, 2005
 */
package br.com.auster.om.invoice;

import java.lang.ref.WeakReference;

/**
 * @hibernate.class table="INV_SECTION_DETAIL"
 * 
 * 
 * <p>
 * <b>Title:</b> SectionDetail
 * </p>
 * <p>
 * <b>Description:</b> Represents a section detail. This is also a super-class
 * for different kinds of Section Details
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2004
 * </p>
 * <p>
 * <b>Company:</b> Auster Solutions
 * </p>
 * 
 * @author etirelli
 * @version $Id: SectionDetail.java 450 2007-06-15 13:14:04Z mtengelm $
 */

public class SectionDetail extends InvoiceModelObject {
	private static final long serialVersionUID = -1752429492387166934L;

	/** sequence number */
	private int seqNbr;
	/** caption for the detail */
	private String caption;
	/** is usage detail? */
	private boolean usage;
	/** total amount in this detail */
	private double totalAmount;

	/** Calculated Amount for this detail */
	private double calculatedAmount = Double.NaN;

	/**
	 * Same as totalAmount, except this field is not handled in any way. It is
	 * stored as it comes from input.
	 */
	private String originalTotalAmount;

	/** service provider */
	private String carrierCode;
	private String carrierState;
	/** section this detail belongs to */
	private WeakReference section;

	/***************************************************************************
	 * Indicates if a Detail was correctly rated by rating rules. This is due to
	 * the new checklist report (That could be integratd to EP)
	 */
	public static final int NOT_RATED = 0;
	public static final int CORRECTLY_RATED = 1;
	public static final int JUST_RATED = 2;
	public static final int WRONGLY_RATED = -1;
	protected int rated = NOT_RATED;

	/***************************************************************************
	 * The CFOP for a given call. It can be null, in such cases, what could
	 * happen is: The feature is not implemented yet. OR Guiding has failed to
	 * store the correct CFOP OR CFOP does not make sense for this type of call
	 * OR CFOP does not make sense for the customer using this class
	 */
	private String detailCFOP;

	/**
	 * @hibernate.property column="CAPTION" type="string" length="128"
	 *                     not-null="false"
	 */
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Section getSection() {
		return (Section) section.get();
	}

	public void setSection(Section parentSection) {
		this.section = new WeakReference(parentSection);
	}

	/**
	 * @hibernate.property column="SEQUENCE_NUMBER" type="integer"
	 *                     not-null="false"
	 */
	public int getSeqNbr() {
		return seqNbr;
	}

	public void setSeqNbr(int seqNbr) {
		this.seqNbr = seqNbr;
	}

	/**
	 * @hibernate.property column="TOTAL_AMNT" type="double" not-null="false"
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @hibernate.property column="USAGE_FLAG" type="boolean" not-null="false"
	 */
	public boolean isUsage() {
		return usage;
	}

	public void setUsage(boolean usage) {
		this.usage = usage;
	}

	/**
	 * @hibernate.property column="CARRIER_CODE" type="string" length="64"
	 *                     not-null="false"
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @hibernate.property column="CARRIER_STATE" type="string" length="10"
	 *                     not-null="false"
	 */
	public String getCarrierState() {
		return carrierState;
	}

	public void setCarrierState(String carrierState) {
		this.carrierState = carrierState;
	}

	/**
	 * Compares 2 user objects
	 * 
	 * @param o
	 *            object to compare
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		int ret = 0;
		if (o instanceof SectionDetail) {
			SectionDetail detail = (SectionDetail) o;
			if ((detail.getSection() != null) && (this.getSection() != null)) {
				if (this.getSeqNbr() != detail.getSeqNbr()) {
					ret = (this.getSeqNbr() > detail.getSeqNbr()) ? 1 : -1;
				}
			}
		} else {
			throw new ClassCastException("Can't compare an "
					+ this.getClass().getName() + " to an "
					+ o.getClass().getName());
		}
		return ret;
	}

	public String getOriginalTotalAmount() {
		return originalTotalAmount;
	}

	public void setOriginalTotalAmount(String originalTotalAmount) {
		this.originalTotalAmount = originalTotalAmount;
	}

	/**
	 * 
	 * Returns if a call was correctly rated or not. -1 - Wrongly rated 0 - Not
	 * Rated 1 - Correctly rated
	 * 
	 * @return the correctlyRated
	 */
	public int getRated() {
		return this.rated;
	}

	/**
	 * Sets the results of the rating for a Detail. See public static on class
	 * docs to see allowed values.
	 * 
	 * @param correctlyRated
	 *            the correctlyRated to set
	 */
	public void setRated(int correctlyRated) {
		this.rated = correctlyRated;
	}

	/***************************************************************************
	 * Sets a section as Unrated.
	 * 
	 */
	public void setNotRated() {
		this.setRated(NOT_RATED);
	}

	public boolean isRated() {
		return getRated() != NOT_RATED;
	}

	/**
	 * Return the value of a attribute <code>calculatedAmount</code>.
	 * 
	 * @return return the value of <code>calculatedAmount</code>.
	 */
	public double getCalculatedAmount() {
		return this.calculatedAmount;
	}

	/**
	 * Set the value of attribute <code>calculatedAmount</code>.
	 * 
	 * @param calculatedAmount
	 */
	public void setCalculatedAmount(double calculatedAmount) {
		this.calculatedAmount = calculatedAmount;
	}

	/***************************************************************************
	 * If calculated if was previously set. When initialized, the calculated fee
	 * is set to Double.NaN. This method returns true if calculated fee ==
	 * Double.NaN and false otherwise.
	 * 
	 * @return
	 */
	public boolean isCalculatedAmountAvailable() {
		return this.calculatedAmount != Double.NaN;
	}

	/**
	 * Gets the CFOP (Codigo Fiscal de Operacao) of a Given CALL
	 * 
	 * @see <code> setUsageCFOP(String usageCFOP) </code>
	 * @return the usageCFOP
	 */
	public String getDetailCFOP() {
		return this.detailCFOP;
	}

	/**
	 * Sets the CFOP for a given usage. This is the way the Operator Fiscal Area
	 * will classify this call, And this will be usefull for rules trying to
	 * match a service against a fiscal code.
	 * 
	 * @param usageCFOP
	 *            the usageCFOP to set
	 */
	public void setDetailCFOP(String usageCFOP) {
		this.detailCFOP = usageCFOP;
	}

	public boolean isCFOPEmpty() {
		return ((this.detailCFOP == null) || ("".equals(this.detailCFOP))) ? true
				: false;
	}
}
