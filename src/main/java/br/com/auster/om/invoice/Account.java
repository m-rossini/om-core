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

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *              
 *              
 * <p><b>Title:</b> Account</p>
 * <p><b>Description:</b> A class to represent billing accounts</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: Account.java 385 2007-02-08 20:17:59Z framos $
 * 
 * 
 *  @hibernate.class
 *              table="INV_ACCOUNT"
 *              
 */
public class Account extends InvoiceModelObject {
	private static final long serialVersionUID = -8786326200990343679L;
	
	private String carrierCode = null;
	private String carrierState = null;
	private String carrierName = null;
	private String accountNumber = null;
	private String accountName = null;
    private String accountType = null;
	private String accountState = null;
	
	// Map<String identType, Identity identity>
	private Map identities;
	// Map<String addressType, Address address>
	private Map addresses;
	// Map<Date referenceDate, Invoice invoice>
	private Map invoices;
	
	//Due to need to override account state....
	private String changedAccountState = null;
	private boolean accountStateModified = false;
	
	public String customerServiceArea = null;
	
	
	/*** 
	 * Holds information of services negotiated for this account.
	 * It is Map where Key is a String with subscription number and
	 * value are the a List of negotiated prices for this subscription id.
	 * 
	 * @see method set and get NegotiatedPrices
	 * @see method isPricesNegotiated.
	 */
	private Map negotiatedPrices = null;
	
	/***
	 * 
	 * Creates a new instance of the class <code>Account</code>.
	 */
	public Account() {
		identities = new HashMap();
		addresses = new HashMap();
		invoices = new HashMap();
	}
	
	/***
	 * Attaches all negotiated prices of a given account.
	 * This class specifically does not except nothing different from a Map,
	 * neither has special requeriments for the Map content.
	 * 
	 * It is responsability of the producer and the consumer of the Map to
	 * agree on its contents.
	 * 
	 * This class is just a Map "custody".
	 * 
	 * @param A Map with negotiated prices for the account
	 */
	public void setNegotiatedPrices(Map prices) {
		this.negotiatedPrices = prices;
	}
	
	/***
	 * Returns a map of negotiated prices.
	 * Please, see the setNegotiated(Map prices) method for details.
	 * 
	 * @return A Map with negotiated prices for the account
	 */
	public Map getNegotiatedPrices() {
		return this.negotiatedPrices;
	}
	
	/***
	 * If the map holding negotiated prices is null OR
	 * if this map has size of zero, then there is NO negotiated prices.
	 * 
	 * @return A boolean indicating if there is negotiated prices (true) or not (false)
	 */
	public boolean isPricesNegotiated() {
		if ( (this.negotiatedPrices == null) || (this.negotiatedPrices.size() == 0 ) ) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a String representing the account name
	 * 
	 * @hibernate.property
	 *            column="ACCOUNT_NAME"
	 *            type="string"
	 *            length="256"
	 *            not-null="false"
	 *            
	 *  @return A String with account name          
	 */
	public String getAccountName() {
		return accountName;
	}
	
	/***
	 * Sets the account name.
	 * 
	 * @param A String with account name
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * 
	 * Gets the account number
	 * Note that besides the fact the name of the method tells a number is retuned,
	 * it is not true. It returns a String, and the content is a String.
	 * 
	 * If it can be represented by a number or not, is out of the scope
	 * of this class and method.
	 * 
	 * 
	 * @hibernate.property
	 *            column="ACCOUNT_NUMBER"
	 *            type="string"
	 *            length="64"
	 *            not-null="true"
	 *            
	 * @return A String with account number.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	
	/***
	 * Sets the account number
	 * 
	 * @param A String representiong the account number
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
  /**
   * 
	 * Gets the account Type.
   * 
   * @hibernate.property
   *            column="ACCOUNT_TYPE"
   *            type="string"
   *            length="2"
   *            not-null="false"
   *            
	 * @return A String with account type.
	 * 
   */
  public String getAccountType() {
    return accountType;
  }
  
  /***
   * Sets the account type.
   * 
   * The loader/rules has to populate this field.
   * 
   * This class has no special reqeriments for this method.
   * So, it can be any value meaningull or not to the business.
   * 
   * @param A String representing the account type
   */
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
  
	/**
	 * 
   * Gets the addresses of an account
	 * 
   * @hibernate.map
   *            lazy="true"
   *            cascade="delete"
   *            
   * @hibernate.collection-key
   *            column="account_id"
   *            
   * @hibernate.collection-index
   *            column="ADDRESS_TYPE"
   *            type="string"
   *            length="32"
   *            
   * @hibernate.collection-one-to-many
   *            class="br.com.auster.om.invoice.Address"
   * 
   * 
	 * @return Returns a Synchronized Map<String addressType, Address address> with all addresses.
	 */
  public Map getAddresses() {
    return addresses;
  }
  
  /***
   * Sets an account addresses.
   * 
   * The contents of the Map are irrelevant for this class.
   * It is up to the producer and consumer to agree upon map contents
   * This method just replaces the old one by the new one totally.
   * 
   * @param addresses A Map with addresses
   */
  public void setAddresses(Map addresses) {
    this.addresses = addresses;
  }
  
  /**
   * Returns the address of a given address type.
   * 
   * @param addressType The address type for which one wants the address.
   * @return address. The address returned or null if no one was found.
   */
	public Address getAddress(String addressType) {
		return (Address) addresses.get(addressType);
	}
	
	/***
	 * Add an address to this account.
	 * This method will add the address to the address Map, using:
	 * As key the address.getAddressType() method.
	 * As value the address object itself.
	 * 
	 * It will no check for exitent key on the map.
	 * 
	 * @param address
	 */
	public void addAddress(Address address) {
		addresses.put(address.getAddressType(), address);
	}
	
	
	/**
	 * 
	 * This method gets all identities attached to this account.
	 * 
   * @hibernate.map
   *            lazy="true"
   *            cascade="delete"
   *            
   * @hibernate.collection-key
   *            column="account_id"
   *            
   * @hibernate.collection-index
   *            column="IDENTITY_TYPE"
   *            type="string"
   *            length="32"
   * 
   * @hibernate.collection-one-to-many
   *            class="br.com.auster.om.invoice.Identity"
   * 
   * 
   * 
   * 
	 * @return Returns a Synchronized Map<String identType, Identity identity> with all identities.
	 */
  public Map getIdentities() {
    return identities;
  }
  
  /***
   * Replaces any exitent Map of identities by the one in parameter.
   * 
   * This class does not enfornce any content for this Map.
   * 
   * @param identities
   */
  public void setIdentities(Map identities) {
    this.identities = identities;
  }

  /**
   * Returns the identity from identity Map.
   * Uses the identity type to lookup on the Map.
   * If nothing is found, null is returned.
   * 
   * @param A String representing addressType
   * @return The found Identity or null.
   */
	public Identity getIdentity(String identityType) {
		return (Identity) identities.get(identityType);
	}
	
	/***
	 * Adds a new identity to the identities Map.
	 * 
	 * Uses as key to lookup the Map, the identity.getIdentityType() method.
	 * It does not check for already exitent identity with same key.
	 * @param identity
	 */
	public void addIdentity(Identity identity) {
		identities.put(identity.getIdentityType(), identity);
	}
	
	/**
	 * 
   * Get all Invoices attached to this account.
	 * 
   * @hibernate.map
   *            lazy="true"
   *            cascade="delete"
   *            inverse="true"
   *            
   * @hibernate.collection-key
   *            column="account_id"
   *            
   * @hibernate.collection-index
   *            column="DUE_DATE"
   *            type="date"
   * 
   * @hibernate.collection-one-to-many
   *            class="br.com.auster.om.invoice.Invoice"
   * 
   * 
   * 
	 * @return Returns the invoices, which is a Map.
	 */
	public Map getInvoices() {
		return invoices;
	}
	
	/***
	 * Returns a Collection with all invoices for this account.
	 * 
	 * The returned list is the values() element of the invoice map.
	 * 
	 * @return invoiceList. Returns a collection with invoices.
	 */
	public Collection getInvoiceList() {
		return invoices.values();
	}
	
	/***
	 * Returns a Collection (Currently a Set) with all invoices map keys.
	 * In other words, this Collection will be a Collection with Date objects,
	 * representing all invoices due dates.
	 * 
	 * @return A Collection with Invoice Dates gotten from invoices map
	 */
	public Collection getInvoiceReferenceDateList() {
		return invoices.keySet();
	}
	
	/***
	 * Sets the invoice map.
	 * 
	 * This method entirely replaces the current invoice map.
	 * 
	 * @param invoices
	 */
  public void setInvoices(Map invoices) {
    this.invoices = invoices;
  }
  
  /***
   * Lookup on the invoices map for an invoice with a given due date.
   * 
   * @param referenceDate A Date representing an Invoice due date.
   * @return The found invoice or null if no invoice exists for the given due date.
   */
  public Invoice getInvoice(Date referenceDate) {
		return (Invoice) invoices.get(referenceDate);
	}
  
  /***
   * Adds a new invoice to this account
   * This class will keep it on a Map.
   * The key will be a Date from invoice.getDueDate() method.
   * It does not check for already exitent due dates.
   * 
   * @param invoice
   */
	public void addInvoice(Invoice invoice) {
		invoices.put(invoice.getDueDate(), invoice);
		invoice.setAccount(this);
	}
	
	/**
	 * 
	 * Gets the carrier code to which this account belongs to.
	 * 
	 * @hibernate.property
	 *            column="CARRIER_CODE"
	 *            type="string"
	 *            length="64"
	 *            not-null="false"
	 *            
	 *	@return carrierCode. It is a String representing the carrier code.            
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	
	/***
	 *
	 * Sets the carrier code for this account.
	 * 
	 * @param carrierCode. The carrier code to be set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	
	/**
	 * 
	 * Returns the carrier state to which this account belongs to.
	 * 
	 * @hibernate.property
	 *            column="CARRIER_STATE"
	 *            type="string"
	 *            length="10"
	 *            not-null="false"
	 *            
	 *	@return carrierState. A String representing the state.            
	 */
	public String getCarrierState() {
		return carrierState;
	}
	
	/***
	 * Sets the carrier state for this account.
	 * 
	 * @param carrierState. A String representing the carrier state.
	 */
	public void setCarrierState(String carrierState) {
		this.carrierState = carrierState;
	}
	
	/***
	 *
	 * Gets the carrier name to which this account belongs to.
	 * 
	 * It can be an empty string or even null.
	 * 
	 * @return carrierName. A String (May be empty) with the carrier name or null if not set.
	 */
	public String getCarrierName() {
		return carrierName;
	}
	
	/***
	 *
	 * Sets the carrier name for this account.
	 * 
	 * @param _carrierName. The carrier name to be set.
	 */
	public void setCarrierName(String _carrierName) {
		this.carrierName = _carrierName;
	}
	
	/**
	 * 
	 * Gets the account state.
	 * 
	 * It can return an empty string if account state was not set.
	 * It never returns null.
	 * 
	 * If the account state was modified after load, it returns the modified state.
	 * If the account state was not modified after load, it returns the original state.
	 * 
	 * @hibernate.property
	 *            column="ACCOUNT_STATE"
	 *            type="string"
	 *            length="10"
	 *            not-null="false"
	 *            
	 *	@return accountState. A String representing the account state or an empty string if not set.            
	 */
	public String getAccountState() {
		String ret = (this.isAccountStateModified()) ? this.getChangedAccountState() : this.accountState;
		ret = ( ret == null || ret.equals("")) ? this.accountState : ret;
		return ret;
	}
	
	/**
	 * Sets the account state.
	 * 
	 * @param accountState The accountState to set.
	 */
	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}
	
	/***
	 * 
	 * @return A String representing this account.
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getName() + "=" + this.accountNumber;
	}
	
	/**
	 * Compares 2 user objects.
	 * 
	 * This overriden method compares the following:
	 * this and parm must be an account object.
	 * both account number must not null and equals to each other
	 * both carrier code must not null and equals to each other
	 * both carrier state must not null and equals to each other
	 * 
	 * @param o object to compare
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		int ret = 0;
		if(o instanceof Account) {
			Account account = (Account) o;
			if((this.getAccountNumber() != null) &&
					(this.getCarrierCode() != null) &&
					(this.getCarrierState() != null) &&
					(account.getAccountNumber() != null) &&
					(account.getCarrierCode() != null) &&
					(account.getCarrierState() != null)) {
				ret = this.getCarrierCode().compareTo(account.getCarrierCode());
				if(ret == 0) {
					ret = this.getCarrierState().compareTo(account.getCarrierState());
				}
				if(ret == 0) {
					ret = this.getAccountNumber().compareTo(account.getAccountNumber());
				}
			}
		} else {
			throw new ClassCastException("Can't compare an "+this.getClass().getName()+" to an "+o.getClass().getName());
		}
		return ret;
	}

	/***
	 * Returns the original Account State.
	 * This method is usefull if account state was modified after load.
	 * 
	 * @return accountState. A String representing the loaded account state.
	 */
	public String getOriginalAccountState() {
		return this.accountState;
	}
	/**
	 * @see {{@link #accountStateModified}
	 * 
   * @return accountState. Returns the account state modified. 
   * 
   */
  public boolean isAccountStateModified() {
  	return this.accountStateModified;
  }

	/**
	 * This method is intended to be used as part of rules.
	 * 
	 * If we need to set account state differently from the original file,
	 * one must call this method with true.
	 * 
   * @param accountStateModified the accountStateModified to set
   */
  public void setAccountStateModified(boolean accountStateModified) {
  	this.accountStateModified = accountStateModified;
  }

	/**
	 * Retunrs the is the changed account state by rule
	 * 
	 * @see {{@link #accountStateModified} method.
	 * 
   * @return the changedAccountState
   */
  public String getChangedAccountState() {
  	return this.changedAccountState;
  }

	/**
	 * In the case one needs to set the account state differntly from the source that was loaded.
	 * 
   * @param changedAccountState the changedAccountState to set
   */
  public void setChangedAccountState(String changedAccountState) {
  	this.changedAccountState = changedAccountState;
  }

	/**
	 * 
	 * Returns the customer service area as in the source data.
	 * 
   * @return the customerServiceArea
   */
  public String getCustomerServiceArea() {
  	return this.customerServiceArea;
  }

	/**
	 * Sets the customer service area.
	 * 
   * @param customerServiceArea the customerServiceArea to set
   */
  public void setCustomerServiceArea(String customerServiceArea) {
  	this.customerServiceArea = customerServiceArea;
  }
	
}
