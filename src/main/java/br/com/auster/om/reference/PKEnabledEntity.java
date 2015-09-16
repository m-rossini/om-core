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
 * Created on 18/08/2006
 */
package br.com.auster.om.reference;

/**
 * @author framos
 * @version $Id$
 */
public abstract class PKEnabledEntity implements Comparable {

	

	// ---------------------------
	// Instance variables
	// ---------------------------
	
	private long uid;
	
	
	
	
	// ---------------------------
	// Public methods
	// ---------------------------

	/**
	 * @hibernate.id
     *   column="OBJID"
     *   type="long"
     *   not-null="true"
     *   unsaved-value="0"
     *   generator-class="native"
	 */
	public long getUid() { 
		return uid;
	}		
	public void setUid(long _uid) {
		uid = _uid;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _other) {
		if (_other == null) {
			return false;
		}
		return ((this.compareTo(_other) == 0) && (this.getUid() != 0));
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17;
		result = result*37 + (int) getUid();
		return result;
	}	

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object _other) {
		if (!_other.getClass().isAssignableFrom(this.getClass())) {
			throw new ClassCastException("cannot compare " + _other.getClass().getName() + " with " + this.getClass().getName());
		}
		return (int) (this.hashCode() - _other.hashCode());
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName() + "/" + this.getUid();
	}
		
}
