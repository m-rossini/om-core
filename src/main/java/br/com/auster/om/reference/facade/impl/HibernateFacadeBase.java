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
 * Created on 23/11/2005
 */
package br.com.auster.om.reference.facade.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import br.com.auster.common.io.IOUtils;
import br.com.auster.common.log.LogFactory;
import br.com.auster.common.xml.DOMUtils;
import br.com.auster.om.reference.facade.ConfigurationException;
import br.com.auster.om.reference.facade.ReferenceFacades;

/**
 * @author framos
 * @version $Id$
 */
public abstract class HibernateFacadeBase implements ReferenceFacades {

	

	// ---------------------------
	// Instance variables
	// ---------------------------
	
	private static final Logger log = LogFactory.getLogger(HibernateFacadeBase.class);
	
	public static final String	CONFIGURATION_FILE_ATTR	= "config-file";
	public static final String	ENCRYPTED_FLAG_ATTR = "encrypted";
	
	protected SessionFactory factory;
	
	
	
	// ---------------------------
	// Public methods
	// ---------------------------	
	
	/**
	 * @see br.com.auster.om.reference.facade.ReferenceFacades#configure(org.w3c.dom.Element)
	 */
	public void configure(Element _configuration) throws ConfigurationException {
		String filename = DOMUtils.getAttribute(_configuration, CONFIGURATION_FILE_ATTR, true);
		boolean encrypted = DOMUtils.getBooleanAttribute(_configuration, ENCRYPTED_FLAG_ATTR, false);
		this.configure(filename, encrypted);
	}

	/**
	 * @see br.com.auster.om.reference.facade.ReferenceFacades#configure(java.lang.String)
	 */
	public void configure(String _configurationFile) throws ConfigurationException {
		configure(_configurationFile, false);
	}

	/**
	 * @see br.com.auster.om.reference.facade.ReferenceFacades#configure(java.lang.String, boolean)
	 */
	public void configure(String _configurationFile, boolean _encrypted) throws ConfigurationException {
		try {
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setNamespaceAware(true);
	        dbf.setValidating(false);	        
	        log.debug("configuring hibernate reference facade with file " + _configurationFile + ", encrypted=" + _encrypted);
	        Document doc = dbf.newDocumentBuilder().parse(IOUtils.openFileForRead(_configurationFile, _encrypted));
	        Configuration sfConfig = new Configuration();
	        sfConfig.configure(doc);
	        factory = sfConfig.buildSessionFactory();
		} catch (SAXException saxe) {
			throw new ConfigurationException("Could not read configuration file in facade", saxe);
		} catch (IOException ioe) {
			throw new ConfigurationException("Could not read configuration file in facade", ioe);
		} catch (ParserConfigurationException pce) {
			throw new ConfigurationException("Could not read configuration file in facade", pce);
		} catch (GeneralSecurityException gse) {
			throw new ConfigurationException("Could not read configuration file in facade", gse);
		} catch (HibernateException he) {
			throw new ConfigurationException("Error configuring Hibernate factory in facade", he);
		}
	}
	
}
