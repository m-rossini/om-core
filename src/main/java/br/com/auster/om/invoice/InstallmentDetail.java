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

/**
 * @hibernate.class
 *              table="INV_INSTMT_DETAIL"
 *              
 *              
 * <p><b>Title:</b> InstallmentDetail</p>
 * <p><b>Description:</b> </p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: InstallmentDetail.java 34 2005-08-26 15:25:38Z etirelli $
 */

public class InstallmentDetail extends SectionDetail {
	private static final long serialVersionUID = -4589902219208877038L;
	
	/** current parcel number */
	private int currentParcel;
	/** total number of parcels */
	private int totalParcels;
	
	
	
	
	/**
	 * @hibernate.property
	 *            column="CURR_PARCEL"
	 *            type="integer"
	 *            not-null="false"
	 */
	public int getCurrentParcel() {
		return currentParcel;
	}
	public void setCurrentParcel(int currentParcel) {
		this.currentParcel = currentParcel;
	}
	
	/**
	 * @hibernate.property
	 *            column="TOTAL_PARCELS"
	 *            type="int"
	 *            not-null="false"
	 */
	public int getTotalParcels() {
		return totalParcels;
	}
	public void setTotalParcels(int totalParcels) {
		this.totalParcels = totalParcels;
	}
}
