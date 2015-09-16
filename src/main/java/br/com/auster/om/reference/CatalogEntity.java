package br.com.auster.om.reference;



/**
 * @author framos
 * @version $Id: CatalogEntity.java 59 2005-10-25 16:59:10Z framos $
 */
public abstract class CatalogEntity extends CustomizableEntity {

	 
	
	// ---------------------------
	// Instance variables
	// ---------------------------
	
	private ImportRecord importInfo;

	
	
	// ---------------------------
	// Constructors
	// ---------------------------
	
	public CatalogEntity() {
		super();
	}
	
	public CatalogEntity(long _uid) {
		super(_uid);
	}	
	
	
	
	// ---------------------------
	// Public methods
	// ---------------------------
	
	/**
     * @hibernate.many-to-one
     *          column="IMPORT_ID"
     *          not-null="true"          
     */
	public ImportRecord getImportInfo() {
		return importInfo;
	}

	public void setImportInfo(ImportRecord importInfo) {
		this.importInfo = importInfo;
	}		

}
