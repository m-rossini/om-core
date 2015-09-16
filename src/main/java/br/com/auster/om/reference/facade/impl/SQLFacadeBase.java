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
 * Created on 10/05/2006
 */
package br.com.auster.om.reference.facade.impl;

import br.com.auster.om.reference.facade.ReferenceFacades;

/**
 * @author framos
 * @version $Id$
 */
public abstract class SQLFacadeBase implements ReferenceFacades {

//	 table and fields names, for import_history table
	public static final String IMPORT_HISTORY_TABLENAME = "ref_import_history";
	public static final String IMPORT_HISTORY_UID = "objid";
	public static final String IMPORT_HISTORY_FILENAME = "imported_filename";
	public static final String IMPORT_HISTORY_DATE = "import_date";
	public static final String IMPORT_HISTORY_CUSTOM_1 = "custom_1";
	public static final String IMPORT_HISTORY_CUSTOM_2 = "custom_2";
	public static final String IMPORT_HISTORY_CUSTOM_3 = "custom_3";


	
	public static final String IMPORT_HISTORY_LOAD_QUERY = 
		"select " + 
		IMPORT_HISTORY_TABLENAME + "." + IMPORT_HISTORY_FILENAME + ", " +
		IMPORT_HISTORY_TABLENAME + "." + IMPORT_HISTORY_DATE + ", " +
		IMPORT_HISTORY_TABLENAME + "." + IMPORT_HISTORY_CUSTOM_1 + ", " +
		IMPORT_HISTORY_TABLENAME + "." + IMPORT_HISTORY_CUSTOM_2 + ", " +
		IMPORT_HISTORY_TABLENAME + "." + IMPORT_HISTORY_CUSTOM_3 + 
		" from " +  IMPORT_HISTORY_TABLENAME + 
		" where " + IMPORT_HISTORY_TABLENAME + "." + IMPORT_HISTORY_UID + " = ?";
	
}
