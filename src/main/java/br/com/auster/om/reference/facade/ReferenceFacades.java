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
 * Created on 23/11/2005
 */
package br.com.auster.om.reference.facade;

import org.w3c.dom.Element;

/**
 * @author framos
 * @version $Id$
 */
public interface ReferenceFacades {
 
	/**
	 * Configures the current implementation of reference facade using part of a previously opened XML file.
	 * 
	 * @param _configuration the DOM Element with the facade configuration
	 * 
	 * @throws ConfigurationException if any exception was raised while reading/configuring the facade implementation
	 */
	public void configure(Element _configuration) throws ConfigurationException;

	/**
	 * Configures the current implementation of reference facade using the contents of a XML file.
	 *  <p>  
	 * This configuration file can be bundled inside a JAR set in the CLASSPATH. 
	 *   See @see br.com.auster.commons.io.IOUtils#openFileForRead(java.lang.String) for details of how this 
	 *   is handled.
	 * 
	 * @param _configurationFile the relative/absolute path to the configuration file
	 * 
	 * @throws ConfigurationException if any exception was raised while reading/configuring the facade implementation
	 */
	public void configure(String _configurationFile) throws ConfigurationException;

	/**
	 * Configures the current implementation of reference facade using the contents of a XML file. If the 
	 *    <code>_encrypted</code> parameter is set to <code>true</code>, then the XML file is encrypted with
	 *    a existing secret key.  
	 *  <p>  
	 * 	This configuration file can be bundled inside a JAR set in the CLASSPATH. 
	 *    See @see br.com.auster.commons.io.IOUtils#openFileForRead(java.lang.String) for details of how this 
	 *    is handled.
	 * 
	 * @param _configurationFile the relative/absolute path to the configuration file
	 * @param _encrypted flag indicating if the file is encrypted
	 * 
	 * @throws ConfigurationException if any exception was raised while reading/configuring the facade implementation
	 */
	public void configure(String _configurationFile, boolean _encrypted) throws ConfigurationException;

}
