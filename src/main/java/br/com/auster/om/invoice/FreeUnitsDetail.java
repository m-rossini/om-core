/*
 * Copyright (c) 2004-2005 Auster Solutions. All Rights Reserved.
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
 * Created on Oct 6, 2005
 */
package br.com.auster.om.invoice;

import java.util.Date;

import br.com.auster.om.util.UnitCounter;

/**
 * @hibernate.class
 *              table="INV_FREEUNITS_DETAIL"
 *
 * <p><b>Title:</b> FreeUnitsDetail</p>
 * <p><b>Description:</b> A free units section detail</p>
 * <p><b>Copyright:</b> Copyright (c) 2004-2005</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: FreeUnitsDetail.java 188 2006-08-18 14:43:22Z framos $
 */
public class FreeUnitsDetail extends SectionDetail {
	private static final long serialVersionUID = -7732310266272572911L;
	
	private String accessNumber;
	private String serviceName;
	private Date startDate;
	private String originalStartDate;
	private Date endDate;
	private String originalEndDate;
	private UnitCounter receivedRolloverUnits;
	private UnitCounter totalIncludedUnits;
	private UnitCounter usedUnits;
	private UnitCounter totalUsedUnits;
	private UnitCounter remainingUnits;
	private UnitCounter maxRolloverUnits;
    private UnitCounter rolledOverUnits;
    private String allocationType;
    private String sharingType;
    private String allocationKey;
    
	/**
	 * @hibernate.property
	 *            column="ACCESS_NUMBER"
	 *            type="string"
	 *            length="32"
	 *            not-null="false"
	 *            
	 * @return Returns the accessNumber.
	 */
	public String getAccessNumber() {
		return accessNumber;
	}
	/**
	 * @param accessNumber The accessNumber to set.
	 */
	public void setAccessNumber(String accessNbr) {
		this.accessNumber = accessNbr;
	}

	/**
	 * @hibernate.property
	 *            column="SERVICE_NAME"
	 *            type="string"
	 *            length="40"
	 *            not-null="false"
	 *            
	 * @return Returns the serviceName.
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName The serviceName to set.
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	/**
	 * @hibernate.property
	 *            column="START_DATE"
	 *            type="date"
	 *            not-null="false"
	 *            
	 * @return Returns the startDate.
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Date fromDate) {
		this.startDate = fromDate;
	}

	/**
	 * @hibernate.property
	 *            column="SRC_START_DATE"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 *            
	 * This attributes handles the unformatted version of the
	 * 	{@link #startDate}  
	 */
	public String getOriginalStartDate() {
		return originalStartDate;
	}
	public void setOriginalStartDate(String _fromDate) {
		this.originalStartDate = _fromDate;
	}
	
	/**
	 * @hibernate.property
	 *            column="END_DATE"
	 *            type="date"
	 *            not-null="false"
	 *            
	 * @return Returns the endDate.
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Date toDate) {
		this.endDate = toDate;
	}

	/**
	 * @hibernate.property
	 *            column="SRC_END_DATE"
	 *            type="string"
	 *            length="30"
	 *            not-null="false"
	 *            
	 * This attributes handles the unformatted version of the
	 * 	{@link #endDate}  
	 */
	public String getOriginalEndDate() {
		return originalEndDate;
	}
	public void setOriginalEndDate(String _toDate) {
		this.originalEndDate = _toDate;
	}
	
	/**
   * @hibernate.component
   *            class="br.com.auster.om.util.UnitCounter"
   *            prefix="RECEIVED_ROLLOVER_"
   *            
	 * @return Returns the receivedRolloverUnits.
	 */
	public UnitCounter getReceivedRolloverUnits() {
		return receivedRolloverUnits;
	}
	/**
	 * @param receivedRolloverUnits The receivedRolloverUnits to set.
	 */
	public void setReceivedRolloverUnits(UnitCounter receivedRolloverUnits) {
		this.receivedRolloverUnits = receivedRolloverUnits;
	}

	/**
   * @hibernate.component
   *            class="br.com.auster.om.util.UnitCounter"
   *            prefix="TOTAL_INCLUDED_"
	 *            
	 * @return Returns the totalIncludedUnits.
	 */
	public UnitCounter getTotalIncludedUnits() {
		return totalIncludedUnits;
	}
	/**
	 * @param totalIncludedUnits The totalIncludedUnits to set.
	 */
	public void setTotalIncludedUnits(UnitCounter totalIncludedUnits) {
		this.totalIncludedUnits = totalIncludedUnits;
	}

	/**
   * @hibernate.component
   *            class="br.com.auster.om.util.UnitCounter"
   *            prefix="USED_"
	 *            
	 * @return Returns the usedUnits.
	 */
	public UnitCounter getUsedUnits() {
		return usedUnits;
	}
	/**
	 * @param usedUnits The usedUnits to set.
	 */
	public void setUsedUnits(UnitCounter usedUnits) {
		this.usedUnits = usedUnits;
	}

	/**
   * @hibernate.component
   *            class="br.com.auster.om.util.UnitCounter"
   *            prefix="TOTAL_USED_"
	 *            
	 * @return Returns the totalUsedUnits.
	 */
	public UnitCounter getTotalUsedUnits() {
		return totalUsedUnits;
	}
	/**
	 * @param totalUsedUnits The totalUsedUnits to set.
	 */
	public void setTotalUsedUnits(UnitCounter totalUsedUnits) {
		this.totalUsedUnits = totalUsedUnits;
	}

	/**
   * @hibernate.component
   *            class="br.com.auster.om.util.UnitCounter"
   *            prefix="REMAINING_"
   *            
	 * @return Returns the remainingUnits.
	 */
	public UnitCounter getRemainingUnits() {
		return remainingUnits;
	}
	/**
	 * @param remainingUnits The remainingUnits to set.
	 */
	public void setRemainingUnits(UnitCounter remainingUnits) {
		this.remainingUnits = remainingUnits;
	}

	/**
   * @hibernate.component
   *            class="br.com.auster.om.util.UnitCounter"
   *            prefix="MAX_ROLLOVER_"
	 *            
	 * @return Returns the maxRolloverUnits.
	 */
	public UnitCounter getMaxRolloverUnits() {
		return maxRolloverUnits;
	}
	/**
	 * @param maxRolloverUnits The maxRolloverUnits to set.
	 */
	public void setMaxRolloverUnits(UnitCounter maxRolloverUnits) {
		this.maxRolloverUnits = maxRolloverUnits;
	}

	/**
   * @hibernate.component
   *            class="br.com.auster.om.util.UnitCounter"
   *            prefix="ROLLED_OVER_"
	 *            
	 * @return Returns the rolledOverUnits.
	 */
	public UnitCounter getRolledOverUnits() {
		return rolledOverUnits;
	}
	/**
	 * @param rolledOverUnits The rolledOverUnits to set.
	 */
	public void setRolledOverUnits(UnitCounter rolledOverUnits) {
		this.rolledOverUnits = rolledOverUnits;
	}

	/**
	 * @hibernate.property
	 *            column="ALLOCATION_KEY"
	 *            type="string"
	 *            length="40"
	 *            not-null="false"
	 *            
	 * @return Returns the allocationKey.
	 */
	public String getAllocationKey() {
		return allocationKey;
	}
	/**
	 * @param allocationKey The allocationKey to set.
	 */
	public void setAllocationKey(String allocationKey) {
		this.allocationKey = allocationKey;
	}

	/**
	 * @hibernate.property
	 *            column="ALLOCATION_TYPE"
	 *            type="string"
	 *            length="2"
	 *            not-null="false"
	 *            
	 * @return Returns the allocationType.
	 */
	public String getAllocationType() {
		return allocationType;
	}
	/**
	 * @param allocationType The allocationType to set.
	 */
	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}

	/**
	 * @hibernate.property
	 *            column="SHARING_TYPE"
	 *            type="string"
	 *            length="2"
	 *            not-null="false"
	 *            
	 * @return Returns the sharingType.
	 */
	public String getSharingType() {
		return sharingType;
	}
	/**
	 * @param sharingType The sharingType to set.
	 */
	public void setSharingType(String sharingType) {
		this.sharingType = sharingType;
	}
}
