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
package br.com.auster.om.invoice;

import br.com.auster.om.util.UnitCounter;

/**
 * @hibernate.class
 *              table="INV_USAGESUM_DETAIL"
 *              
 *               
 * <p><b>Title:</b> UsageSummaryDetail</p>
 * <p><b>Description:</b> </p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: UsageSummaryDetail.java 34 2005-08-26 15:25:38Z etirelli $
 */
public class UsageSummaryDetail extends SectionDetail {
	private static final long serialVersionUID = 2981117124587405071L;
	
	/** the unit type used in this section  */
	private String            unitType;
	/** included free units in this section */
	private UnitCounter		includedUnits;
	/** used units in this section */
	private UnitCounter		usedUnits;
	/** billed units in this section */
	private UnitCounter		billedUnits;
	/** rollover units received by this section */
	private UnitCounter		rolloverReceived;
	/** rollover units forwarded from this section */
	private UnitCounter		rolloverForwarded;
	/** group ID */
	private String            groupId;
	
	
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="BILLED_"
	 */
	public UnitCounter getBilledUnits() {
		return billedUnits;
	}
	public void setBilledUnits(UnitCounter billedUnits) {
		this.billedUnits = billedUnits;
	}
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="INCLUDED_"
	 */
	public UnitCounter getIncludedUnits() {
		return includedUnits;
	}
	public void setIncludedUnits(UnitCounter includedUnits) {
		this.includedUnits = includedUnits;
	}
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="ROLLFWD_"
	 */
	public UnitCounter getRolloverForwarded() {
		return rolloverForwarded;
	}
	public void setRolloverForwarded(UnitCounter rolloverForwarded) {
		this.rolloverForwarded = rolloverForwarded;
	}
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="ROLLRECEIVED_"
	 */
	public UnitCounter getRolloverReceived() {
		return rolloverReceived;
	}
	public void setRolloverReceived(UnitCounter rolloverReceived) {
		this.rolloverReceived = rolloverReceived;
	}
	
	/**
	 * @hibernate.property
	 *            column="UNIT_TYPE"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 */
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
	/**
	 * @hibernate.component
	 *            class="br.com.auster.om.util.UnitCounter"
	 *            prefix="USED_"
	 */
	public UnitCounter getUsedUnits() {
		return usedUnits;
	}
	public void setUsedUnits(UnitCounter usedUnits) {
		this.usedUnits = usedUnits;
	}
	
	/**
	 * @hibernate.property
	 *            column="GROUP_ID"
	 *            type="string"
	 *            length="512"
	 *            not-null="false"
	 */
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
