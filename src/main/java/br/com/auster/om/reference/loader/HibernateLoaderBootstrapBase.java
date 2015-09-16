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
 * Created on Sep 29, 2005
 */
package br.com.auster.om.reference.loader;

import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;

import br.com.auster.common.io.IOUtils;
import br.com.auster.common.util.I18n;

/**
 * @author framos
 * @version $Id$
 */
public abstract class HibernateLoaderBootstrapBase extends LoaderBootstrapBase {

	
	
	public static final String ARGS_HIBERNATE_FILENAME = "hibernate-file";
	public static final String ARGS_HIBERNATE_FILE_MNEMONIC = "c";


	private static final I18n i18n = I18n.getInstance(HibernateLoaderBootstrapBase.class);
		
	protected SessionFactory factory;
	

	
	
	protected Options createOptions() {
		Options options = super.createOptions();
		OptionBuilder.withArgName(ARGS_HIBERNATE_FILENAME);
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(i18n.getString("loader.help.hibernateFilename"));
		Option hibernateConfig = OptionBuilder.create(ARGS_HIBERNATE_FILE_MNEMONIC);
		options.addOption(hibernateConfig);
		return options;
	}

	protected void configureHibernate(String _hibernateFile, boolean _encrypted) throws HibernateException, FileNotFoundException {
		Configuration configuration = new Configuration();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			dbf.setValidating(false);
			Document doc = dbf.newDocumentBuilder().parse(IOUtils.openFileForRead(_hibernateFile, _encrypted));
			configuration.configure(doc);
			factory = configuration.buildSessionFactory();
		} catch (FileNotFoundException fnfe) {
			log.error(i18n.getString("loader.hibernate.fileNotFound", _hibernateFile));
			throw fnfe;
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}
	
	public boolean isStarted() {
		return ((factory != null) && super.isStarted());
	}
	
	public void start(CommandLine _line) throws Exception {
		// check for command line arguments
        if (!_line.hasOption(ARGS_HIBERNATE_FILE_MNEMONIC)) {
			this.printHelp(this.createOptions());
            System.exit(0);
        }
        super.start(_line);
		// configure command line application
        boolean encrypted = true;
        if (_line.hasOption(ARGS_ENCRYPTED_CONFIGURATION_FILES_MNEMONIC)) {
            encrypted = (new Boolean(_line.getOptionValue(ARGS_ENCRYPTED_CONFIGURATION_FILES_MNEMONIC))).booleanValue();
        }
		this.configureHibernate(_line.getOptionValue(ARGS_HIBERNATE_FILE_MNEMONIC), encrypted);
	}
}
