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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import br.com.auster.common.util.I18n;
import br.com.auster.common.xml.DOMUtils;
import br.com.auster.om.reference.ImportRecord;
import br.com.auster.udd.reader.FlatNIOReader;
import br.com.auster.udd.reader.SimpleFlatFileReader;

/**
 * @author framos
 * @version $Id$
 */
public abstract class LoaderBootstrapBase {


	public static final String ARGS_DEBUG_LEVEL = "debug";
	public static final String ARGS_FILENAME = "filename";
    public static final String ARGS_REMOVEUID = "remove-uid";
	public static final String ARGS_UDD_FILENAME = "udd-file";
	public static final String ARGS_ENCRYPTED_CONFIGURATION_FILES = "encrypted-flag";
	public static final String ARGS_HELP = "help";
	
	public static final String ARGS_DEBUG_MNEMONIC = "v";
	public static final String ARGS_FILENAME_MNEMONIC = "f";
    public static final String ARGS_REMOVEUID_MNEMONIC = "d";
	public static final String ARGS_UDD_FILE_MNEMONIC = "u";
	public static final String ARGS_ENCRYPTED_CONFIGURATION_FILES_MNEMONIC = "s";
	public static final String ARGS_HELP_MNEMONIC = "h";

		
	private static final I18n i18n = I18n.getInstance(LoaderBootstrapBase.class);
	
	protected static Logger log; 
	
	
	protected FlatNIOReader uddReader;
	
	protected boolean isDebugOn;
	
	
		
	protected Options createOptions() {
		  
		OptionBuilder.withArgName(ARGS_FILENAME);
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(i18n.getString("loader.help.filename"));
		Option filename = OptionBuilder.create(ARGS_FILENAME_MNEMONIC);

		OptionBuilder.withArgName(ARGS_UDD_FILENAME);
		OptionBuilder.hasArg();
		OptionBuilder.withDescription(i18n.getString("loader.help.uddFilename"));
		Option uddConfig = OptionBuilder.create(ARGS_UDD_FILE_MNEMONIC);

		OptionBuilder.withArgName(ARGS_DEBUG_LEVEL);
		OptionBuilder.withDescription(i18n.getString("loader.help.debug"));
		Option debug = OptionBuilder.create(ARGS_DEBUG_MNEMONIC);

		OptionBuilder.withArgName(ARGS_HELP);
		OptionBuilder.withDescription(i18n.getString("loader.help.help"));
		Option help = OptionBuilder.create(ARGS_HELP_MNEMONIC);

		OptionBuilder.withArgName(ARGS_ENCRYPTED_CONFIGURATION_FILES);
        OptionBuilder.hasArg();
		OptionBuilder.withDescription(i18n.getString("loader.help.encryptedFiles"));
		Option encrypted = OptionBuilder.create(ARGS_ENCRYPTED_CONFIGURATION_FILES_MNEMONIC);

        OptionBuilder.withArgName(ARGS_REMOVEUID);
        OptionBuilder.hasArg();
        OptionBuilder.withDescription(i18n.getString("loader.help.removeUid"));
        Option removeUid = OptionBuilder.create(ARGS_REMOVEUID_MNEMONIC);
        
		Options options = new Options();
		options.addOption(filename);
		options.addOption(uddConfig);
		options.addOption(debug);
		options.addOption(encrypted);
		options.addOption(help);
        options.addOption(removeUid);
		return options;
	}
	  
	protected void printHelp(Options _options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( this.getClass().getName(), _options );    		
	}
	
	protected void debugMode(boolean _on) {
		if (_on) {
			log.setLevel(Level.DEBUG);
		} else {
			log.setLevel(Level.INFO);
		}
		log.info(i18n.getString("loader.debugOn", String.valueOf(_on)) );
		isDebugOn = _on;
	}

	protected boolean isDebugMode() {
		return isDebugOn;
	}
	
	protected void configureUDD(String _uddFile, boolean _encrypted) throws ParserConfigurationException, SAXException, IOException, GeneralSecurityException {
		uddReader = new SimpleFlatFileReader(DOMUtils.openDocument(_uddFile, _encrypted));
	}

	protected ImportRecord createImportInfo(String _filename) {
		ImportRecord record = new ImportRecord();
		long previousImportId = getImportIdProperty();
		if (previousImportId > 0L) {
			record.setUid(previousImportId);
		} else {
			record.setImportDate( new Timestamp(Calendar.getInstance().getTimeInMillis()) );
			record.setFilename(_filename);
		}
		return record;
	}
	
	private static long getImportIdProperty() {
		String previousImportId = System.getProperty("import.useImportId");
		if (previousImportId != null && previousImportId.length() > 0) {
			return Long.parseLong(previousImportId);
		} else {
			return 0L;
		}
	}

	public boolean isStarted() {
		return (uddReader != null);
	}
	
	public void start(CommandLine _line) throws Exception {
		// check for command line arguments
		if (_line.hasOption(ARGS_HELP_MNEMONIC)) {
			this.printHelp(this.createOptions());
            System.exit(0);
        } else if (!(_line.hasOption(ARGS_FILENAME_MNEMONIC) &&
				     _line.hasOption(ARGS_UDD_FILE_MNEMONIC))) {
			this.printHelp(this.createOptions());
            System.exit(0);
        }
		// activate debug mode
		this.debugMode(_line.hasOption(ARGS_DEBUG_MNEMONIC));
		// configure command line application
        boolean encrypted = true;
        if (_line.hasOption(ARGS_ENCRYPTED_CONFIGURATION_FILES_MNEMONIC)) {
            encrypted = (new Boolean(_line.getOptionValue(ARGS_ENCRYPTED_CONFIGURATION_FILES_MNEMONIC))).booleanValue();
        }
		this.configureUDD(_line.getOptionValue(ARGS_UDD_FILE_MNEMONIC), encrypted); 
	}
}
