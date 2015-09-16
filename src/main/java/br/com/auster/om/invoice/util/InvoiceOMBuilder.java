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
 * Created on Aug 26, 2005
 */

package br.com.auster.om.invoice.util;

import java.util.Stack;

import br.com.auster.om.invoice.Account;
import br.com.auster.om.invoice.Invoice;
import br.com.auster.om.invoice.Section;

/**
 * <p><b>Title:</b> InvoiceOMBuilder</p>
 * <p><b>Description:</b> A builder class to help creating Invoice OM structures</p>
 * <p><b>Copyright:</b> Copyright (c) 2004-2005</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: InvoiceOMBuilder.java 38 2005-09-07 13:33:40Z etirelli $
 */

public class InvoiceOMBuilder {
	private Stack context = new Stack();
	private Account currentAccount = null;
	private Invoice currentInvoice = null;
	private Section currentSection = null;

	public Account createAccount() {
		currentAccount = new Account();
		return currentAccount;
	}
	
	public Invoice createInvoice() {
		currentInvoice = new Invoice();
		return currentInvoice;
	}
	
	public Section createSection() {
		currentSection = new Section();
		return currentSection;
	}
	
	public void attach(Account account) {
		currentAccount = account;
		context.push(account);
	}
	
	public void attach(Invoice invoice) {
		this.currentAccount.addInvoice(invoice);
		context.push(invoice);
	}
	
	public void attach(Section section) {
		if(this.currentSection == null) {
			this.currentInvoice.addSection(section);
		}
	}
	
	public void finishContext() {
		context.pop();
	}
	
}
