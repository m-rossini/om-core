/*
 * Copyright (c) 2004 Auster Solutions. All Rights Reserved.
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
 * Created on Jan 20, 2005
 */
package br.com.auster.om.invoice;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * <p><b>Title:</b> InvoiceModelObject</p>
 * <p><b>Description:</b> A Superclass for all invoice model objects. It 
 * will contain common methods to all objects</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: InvoiceModelObject.java 249 2006-10-03 14:51:31Z framos $
 */
public abstract class InvoiceModelObject implements Serializable, Comparable {
  private static ThreadLocal formatter = new ThreadLocal() {
	  protected Object initialValue() {
		  return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	  };
  };
	
  /** general purpose field        */
  private String elementType;
  /** general purpose field        */
  private String tag;
  /** invoice this section belongs to         */
  private WeakReference invoice;
  private long id;

  
  /** General USE Constants for Object Model */
  public static final String INNER_SEP_ATR = ";";
  public static final String DATE_FORMAT = "yyyy/MM/dd";
  public static final String DATE_TIME_FORMAT = "yyyy/MM/dd.HH:mm:ss";
  public static final String NUMBER_FORMAT = "#,###,###,##0.00";
  
  /**
   * @hibernate.property
   *        column="ELEMENT_TYPE"
   *        type="string"
   *        length="64"
   *        not-null="false"
   */
  public String getElementType() {
    return elementType;
  }
  public void setElementType(String elementType) {
    this.elementType = elementType;
  }

  /**
   * @hibernate.property
   *        column="TAG_KEY"
   *        type="string"
   *        length="64"
   *        not-null="false"
   */
  public String getTag() {
    return tag;
  }
  public void setTag(String tag) {
    this.tag = tag;
  }

  /**
   * @hibernate.id
   *        column="OBJID"
   *        type="long"
   *        not-null="true"
   *        unsaved-value="any"
   *        generator-class="native"
   */
  public long getId() {
      return id;
  }
  public void setId(long _id) {
      id = _id;
  }
  
  /**
   * @hibernate.many-to-one
   *        column="invoice_id"
   *        class="br.com.auster.om.invoice.Invoice"
   * 
   * @return The associated invoice object. In case this object is an invoice, returns itself.
   */
  public Invoice getInvoice() {
    return (invoice != null) ? (Invoice) invoice.get() : null;
  }
  public void setInvoice(Invoice invoice) {
    this.invoice = new WeakReference(invoice);
  }

  /**
   * Returns an XML String representation for this object
   *  
   * @param ident prefix used in the begining of all tags.Usually a space sequence.
   * @param showEmptyValues true if you want to show empty attributes also
   * 
   * @return an XML String representation for this object
   */
  public String toXML(String ident, boolean showEmptyValues) {
    boolean subelements = false;
    StringBuffer buf = new StringBuffer();
    try {
      buf.append(ident);
      buf.append("<");
      buf.append(this.getClass().getName().substring(this.getClass().getPackage().getName().length()+1));
      buf.append(" ");
      
      Method[] methods = this.getClass().getMethods();
      
      // Prints all attributes
      for(int i=0; i<methods.length; i++) {
        if((methods[i].getName().startsWith("get") ||
            methods[i].getName().startsWith("is")) && 
          (methods[i].getParameterTypes().length == 0) &&
          (!methods[i].getName().equals("getClass")) &&
          (!Collection.class.isAssignableFrom(methods[i].getReturnType())) &&
          (!Map.class.isAssignableFrom(methods[i].getReturnType())) &&
          (!InvoiceModelObject.class.isAssignableFrom(methods[i].getReturnType()))) {
          Object o = methods[i].invoke(this, (Object[]) null);
          if((showEmptyValues) || ((o != null) && (!o.equals("")))) {
            String name = methods[i].getName();
            name = (name.startsWith("get")) ? name.substring(3) : name.substring(2);
            buf.append(name);
            buf.append("=\"");
            if((o != null) && (o instanceof Date)) {
              buf.append(((SimpleDateFormat) formatter.get()).format(o));
            } else {
              buf.append((o == null) ? "" : o.toString().replaceAll("&","&amp;"));
            }
            buf.append("\" ");
          }
        } else if(methods[i].getName().startsWith("get") && 
              (methods[i].getParameterTypes().length == 0) &&
              (!methods[i].getName().equals("getClass")) &&
              (
                (Collection.class.isAssignableFrom(methods[i].getReturnType())) ||
                (Map.class.isAssignableFrom(methods[i].getReturnType())) ||
                (BarCode.class.isAssignableFrom(methods[i].getReturnType()))
              )) {
          subelements = true;
        }
      }
      if(!subelements) {
        buf.append("/>\n");
      } else {
        buf.append(">\n");
        // prints all inner elements (collection attributes)
        for(int i=0; i<methods.length; i++) {
          if(methods[i].getName().startsWith("get") &&
             ((Collection.class.isAssignableFrom(methods[i].getReturnType())) ||
              (Map.class.isAssignableFrom(methods[i].getReturnType())))) {
            Collection col = null;
            if(methods[i].getReturnType().isAssignableFrom(Map.class)) {
              Map map = (Map) methods[i].invoke(this, (Object[]) null);
              if(map != null) {
                col = map.values();
              } 
            } else {
              col = (Collection) methods[i].invoke(this, (Object[]) null);
            }
            if(col != null) {
              for(Iterator it=col.iterator(); it.hasNext(); ) {
                Object entry = it.next();
                if(entry instanceof InvoiceModelObject) {
                  buf.append(((InvoiceModelObject)entry).toXML(ident+"    ", showEmptyValues));
                } else {
                  buf.append(ident);
                  buf.append(entry.toString());
                  buf.append("\n");
                }
              }
            }
          } else if((methods[i].getName().startsWith("get")) &&
        		    (BarCode.class.isAssignableFrom(methods[i].getReturnType()))) {
              InvoiceModelObject object = (InvoiceModelObject) methods[i].invoke(this, (Object[]) null);
              buf.append(((InvoiceModelObject)object).toXML(ident+"    ", showEmptyValues));
          }
        }
        buf.append(ident);
        buf.append("</");
        buf.append(this.getClass().getName().substring(this.getClass().getPackage().getName().length()+1));
        buf.append(">\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return buf.toString();
  }
  
  /**
   * Returns an XML String representation for this object. It will not show empty attributes.
   *  
   * @param ident prefix used in the begining of all tags.Usually a space sequence.
   * 
   * @return an XML String representation for this object
   */
  public String toXML(String ident) {
    return this.toXML(ident, false);
  }

  /**
   * Cleans up all inner references easying the GC work
   */
  public void cleanupReferences() {
    try {
      Method[] methods = this.getClass().getMethods();
      // Prints all attributes
      for(int i=0; i<methods.length; i++) {
        if((methods[i].getName().startsWith("get") ||
          (methods[i].getParameterTypes().length == 0) &&
          (!methods[i].getName().equals("getClass")) &&
          (!Collection.class.isAssignableFrom(methods[i].getReturnType())) &&
          (!Map.class.isAssignableFrom(methods[i].getReturnType())) &&
          (InvoiceModelObject.class.isAssignableFrom(methods[i].getReturnType())))) {
          Method setter = this.getClass().getMethod(
                  "set"+methods[i].getName().substring(3),
                  new Class[] {methods[i].getReturnType()});
          setter.invoke(this, new Object[] { null });
        } else if(methods[i].getName().startsWith("get") &&
            ((Collection.class.isAssignableFrom(methods[i].getReturnType())) ||
             (Map.class.isAssignableFrom(methods[i].getReturnType())))) {
           Collection col = null;
           if(methods[i].getReturnType().isAssignableFrom(Map.class)) {
             Map map = (Map) methods[i].invoke(this, (Object[]) null);
             if(map != null) {
               col = map.values();
             } 
           } else {
             col = (Collection) methods[i].invoke(this, (Object[]) null);
           }
           if(col != null) {
             for(Iterator it=col.iterator(); it.hasNext(); ) {
               Object entry = it.next();
               if(entry instanceof InvoiceModelObject) {
                 ((InvoiceModelObject)entry).cleanupReferences();
               }
             }
           }
         }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
    
  /**
   * Convert the objects in SAX events and call the appropriate methods 
   * in the given content handler
   *  
   * @param ident prefix used in the begining of all tags.Usually a space sequence.
   * @throws SAXException 
   * @throws InvocationTargetException 
   * @throws IllegalAccessException 
   */
  public void toSAX(ContentHandler ch, boolean callStartDocument) 
       throws SAXException, IllegalAccessException, InvocationTargetException {
	  
    boolean subelements = false;

    // if it is supposed to call start document, call it
    if(callStartDocument) {
  	  ch.startDocument();
    }

    // setting call parameters
    String uri = "";
    String localName = this.getClass().getName().substring(this.getClass().getPackage().getName().length()+1);
    String qname = uri+":"+localName;
    AttributesImpl attributes = new AttributesImpl();

    // getting object methods through reflection
	  Method[] methods = this.getClass().getMethods();
    
	  // populating attributes map and returning true in case there are subelements
   
	  subelements = this.createAttributesMap(methods, attributes, uri);
    
    // calling startElement
    ch.startElement(uri, localName, qname, attributes);
    
    // if there are subelements, recursivelly call them 
    if(subelements) {
      callSubelements(ch, methods);
    }
    
    // calling endElement
    ch.endElement(uri, localName, qname);

    // if it is supposed to call endDocument, call it
    if(callStartDocument) {
      ch.endDocument();
    }
    
  }
  
  
  /**
   * @param ch
   * @param methods
   * @throws IllegalAccessException
   * @throws InvocationTargetException
 * @throws SAXException 
   */
  private void callSubelements(ContentHandler ch, Method[] methods) 
      throws IllegalAccessException, InvocationTargetException, SAXException {
	  
	  // prints all inner elements (collection attributes)
	  for(int i=0; i<methods.length; i++) {
		  if(methods[i].getName().startsWith("get") &&
				  ((Collection.class.isAssignableFrom(methods[i].getReturnType())) ||
						  (Map.class.isAssignableFrom(methods[i].getReturnType())))) {
			  Collection col = null;
			  if(methods[i].getReturnType().isAssignableFrom(Map.class)) {
				  Map map = (Map) methods[i].invoke(this, (Object[]) null);
				  if(map != null) {
					  col = map.values();
				  } 
			  } else {
				  col = (Collection) methods[i].invoke(this, (Object[]) null);
			  }
			  if(col != null) {
				  for(Iterator it=col.iterator(); it.hasNext(); ) {
					  Object entry = it.next();
					  if(entry instanceof InvoiceModelObject) {
						  ((InvoiceModelObject)entry).toSAX(ch, false);
					  } 
				  }
			  }
		  }
	  }
  }
  
  /**
   * Populates attributes map
   * @param methods
   * @param attributes
   * @param uri
   * @return
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  private boolean createAttributesMap(Method[] methods, AttributesImpl attributes, String uri) 
        throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
	  
	  boolean subelements = false;
	  
	  // Prints all attributes
	  for(int i=0; i<methods.length; i++) {
		  if((methods[i].getName().startsWith("get") ||
				  methods[i].getName().startsWith("is")) && 
				  (methods[i].getParameterTypes().length == 0) &&
				  (!methods[i].getName().equals("getClass")) &&
				  (!Collection.class.isAssignableFrom(methods[i].getReturnType())) &&
				  (!Map.class.isAssignableFrom(methods[i].getReturnType())) &&
				  (!InvoiceModelObject.class.isAssignableFrom(methods[i].getReturnType()))) {
			  Object o = methods[i].invoke(this, (Object[]) null);
			  
			  String attname = methods[i].getName();
			  attname = (attname.startsWith("get")) ? attname.substring(3) : attname.substring(2);
			  
			  String attValue = null;
			  
			  if((o != null) && (o instanceof Date)) {
				  attValue = ((SimpleDateFormat) formatter.get()).format(o);
			  } else {
				  attValue = (o == null) ? "" : o.toString().replaceAll("&","&amp;");
			  }
			  
			  attributes.addAttribute(uri, attname, attname, "CDATA", attValue);
			  
		  } else if(methods[i].getName().startsWith("get") && 
				  (methods[i].getParameterTypes().length == 0) &&
				  (!methods[i].getName().equals("getClass")) &&
				  ((Collection.class.isAssignableFrom(methods[i].getReturnType())) |
						  (Map.class.isAssignableFrom(methods[i].getReturnType())))) {
			  subelements = true;
		  }
	  }
	  return subelements;
  }
  
  public Iterator iterator() {
    return new Iterator() {
      public boolean hasNext() {
        return false;
      }

      public Object next() {
        return null;
      }

      public void remove() {
        throw new UnsupportedOperationException("OM-Iterator does not support remove");
      }
    };
  }
  
}
