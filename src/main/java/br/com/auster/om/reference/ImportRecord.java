package br.com.auster.om.reference;


import java.sql.Timestamp;

/**
 * 
 * @hibernate.class
 *          table="REF_IMPORT_HISTORY"
 *          
 * @author framos
 * @version $Id: ImportRecord.java 59 2005-10-25 16:59:10Z framos $
 */
public class ImportRecord extends CustomizableEntity {

	
	
	// ---------------------------
	// Instance variables
	// ---------------------------

	private Timestamp importDate;
	private String filename;
	
	
	
	// ---------------------------
	// Constructors
	// ---------------------------
	
	public ImportRecord() {
		this(0);
	}
	
	public ImportRecord(long _uid) {
		super(_uid);
	}


	
	// ---------------------------
	// Public methods
	// ---------------------------	

	/**
     * @hibernate.property
     *          column="IMPORTED_FILENAME"
     *          type="string"
     *          length="128"
     *          not-null="true"
	 */
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
     * @hibernate.property
     *          column="IMPORT_DATE"
     *          type="timestamp"
     *          not-null="true"
	 */
	public Timestamp getImportDate() {
		return importDate;
	}

	public void setImportDate(Timestamp importDate) {
		this.importDate = importDate;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[ImportRecord]" +
		   " uid=" + getUid() +
		   " importDate=" + getImportDate() +
		   " filename=" + getFilename();
	}
}
