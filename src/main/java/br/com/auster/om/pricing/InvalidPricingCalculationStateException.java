/*
 * Copyright (c) 2004-2005 Auster Solutions do Brasil. All Rights Reserved.
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
 * Created on Sep 16, 2005
 */
package br.com.auster.om.pricing;

import br.com.auster.om.pricing.Service;
import br.com.auster.om.invoice.UsageDetail;

/**
 * @author framos
 * @version $Id: InvalidPricingCalculationStateException.java 54 2005-10-14 19:11:27Z framos $
 */
public class InvalidPricingCalculationStateException extends Exception {

	private static final long serialVersionUID = -7761094321620190821L;
	
	private UsageDetail usage;
	private Service service;
	
	
	public InvalidPricingCalculationStateException(UsageDetail _usage, Service _service) {
		this("", _usage, _service);
	}
	
	public InvalidPricingCalculationStateException(String _message, UsageDetail _usage, Service _service) {
		super(_message);
		usage = _usage;
		service = _service;
	}
	
	public InvalidPricingCalculationStateException(String _message) {
		super(_message);
	}
	
	
	public UsageDetail getRelatedUsage() {
		return usage;
	}
	
	public Service getRelatedService() {
		return service;
	}
	
}
