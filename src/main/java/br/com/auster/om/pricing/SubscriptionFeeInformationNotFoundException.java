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

import br.com.auster.om.invoice.ServiceDetail;
import br.com.auster.om.pricing.Service;

/**
 * @author framos
 * @version $Id: PricingInformationNotFoundException.java 54 2005-10-14 19:11:27Z framos $
 */
public class SubscriptionFeeInformationNotFoundException extends Exception {

	private static final long serialVersionUID = -3868127911046739357L;
	
	private ServiceDetail charge;
	private Service service;
	
	
	public SubscriptionFeeInformationNotFoundException(ServiceDetail _charge, Service _service) {
		this("", _charge, _service);
	}
	
	public SubscriptionFeeInformationNotFoundException(String _message, ServiceDetail _charge, Service _service) {
		super(_message);
		charge = _charge;
		service = _service;
	}
	
	public SubscriptionFeeInformationNotFoundException(String _message) {
		super(_message);
	}
	
	
	public ServiceDetail getRelatedUsage() {
		return charge;
	}
	
	public Service getRelatedService() {
		return service;
	}
	
}
