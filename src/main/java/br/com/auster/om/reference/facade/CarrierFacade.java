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
 * Created on 22/11/2005
 */
package br.com.auster.om.reference.facade;

import java.util.Set;

import br.com.auster.om.reference.model.CarrierCompany;
import br.com.auster.om.reference.model.FiscalOpCode;

/**
 * Facade for manipulating the list of configured carriers and fiscal op. codes. Implementations of this
 * 	interface should be used to query the reference tables instead of direct database connections. 
 * 
 * @author framos
 * @version $Id$
 */
public interface CarrierFacade extends ReferenceFacades {

	/**
	 * Loads the carrier information, along with its addresses and allowed fiscal codes. The search key
	 * 	 used is the unique identifier assigned to the carrier when imported into the reference database.
	 * <p>
	 * If the specified uid does not points to a carrier, then <code>null</code> is returned. Likewise, if
	 * 	it returns more than one carrier an <code>IllegalStateException</code> will be raised.  
	 * 
	 * @param _uid the unique identifier of the carrier
	 * 
	 * @return the carrier data-object, or <code>null</code> if there is no such uid
	 */
	public CarrierCompany getCarrier(long _uid);
	
	/**
	 * Loads the carrier information, along with its addresses and allowed fiscal codes. The search key
	 * 	 used is the country and national code assigned to the carrier, which should be unique as a pair.
	 * <p>
	 * If the specified pair of codes does not points to a carrier, then <code>null</code> is returned. 
	 * 
	 * @parma _countryCode the code assign for the carrier country
	 * @param _nationalCode the carrier national code 
	 * 
	 * @return the carrier data-object, or <code>null</code> if there is no such uid
	 */
	public CarrierCompany getCarrier(String _countryCode, String _nationalCode);
	
	
	/**
	 * Returns the list of fiscal operation code, currently loaded to the reference database.
	 * 
	 * @return the fiscal code list
	 */
	public Set getAllFiscalCodes();
	
	/**
	 * Loads the fiscal code information, specified by the uid. This uid is a unique identifier assigned to the
	 * 	fiscal code when imported into the reference database. 
	 * <p>
	 * If the specified uid does not points to a fiscal code, then <code>null</code> is returned. Likewise, if
	 * 	it returns more than one fiscal code an <code>IllegalStateException</code> will be raised.  
	 * 
	 * @param _uid the unique identifier of the fiscal code
	 * 
	 * @return the fiscal code data-object, or <code>null</code> if there is no such uid
	 */
	public FiscalOpCode getFiscalCode(long _uid);
	
	/**
	 * Loads the fiscal code information, specified by the operation code. This operation code should be unique 
	 * 	across all fiscal codes. 
	 * <p>
	 * If the specified operation code does not points to a fiscal code, then <code>null</code> is returned. 
	 * 	Likewise, if it returns more than one fiscal code an <code>IllegalStateException</code> will be raised.  
	 * 
	 * @param _opCode the fiscal operation code 
	 * 
	 * @return the fiscal code data-object, or <code>null</code> if there is no such uid
	 */
	public FiscalOpCode getFiscalCode(String _opCode);
	
}
