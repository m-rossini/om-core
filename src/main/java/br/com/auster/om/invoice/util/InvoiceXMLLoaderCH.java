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
 * Created on Mar 17, 2005
 */
package br.com.auster.om.invoice.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import br.com.auster.common.log.LogFactory;
import br.com.auster.om.invoice.BarCode;
import br.com.auster.om.invoice.ChargedTax;
import br.com.auster.om.invoice.Identity;
import br.com.auster.om.invoice.Invoice;
import br.com.auster.om.invoice.InvoiceModelObject;
import br.com.auster.om.invoice.InvoiceObjectModelLoader;
import br.com.auster.om.invoice.Receipt;
import br.com.auster.om.invoice.ReceiptDetail;
import br.com.auster.om.invoice.Section;
import br.com.auster.om.invoice.SectionDetail;
import br.com.auster.om.invoice.UsageDetail;
import br.com.auster.om.util.UnitCounter;

import com.sun.jndi.cosnaming.IiopUrl.Address;

/**
 * <p><b>Title:</b> InvoiceXMLLoader</p>
 * <p><b>Description:</b> </p>
 * <p><b>Copyright:</b> Copyright (c) 2004-2005</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: InvoiceXMLLoaderCH.java 62 2005-10-26 19:40:52Z framos $
 */

public class InvoiceXMLLoaderCH implements ContentHandler, InvoiceObjectModelLoader {
  private static Logger log = LogFactory.getLogger(InvoiceXMLLoaderCH.class);
  private static final String OM_PKG_NAME = InvoiceObjectModelLoader.class.getPackage().getName()+".";
  
  private List accountList = null;
  private Stack context = null;
  private SimpleDateFormat dtParser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

  public void setDocumentLocator(Locator locator) {}
  public void startPrefixMapping(String prefix, String uri) throws SAXException {}
  public void endPrefixMapping(String prefix) throws SAXException {}
  public void characters(char[] ch, int start, int length) throws SAXException {}
  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
  public void processingInstruction(String target, String data) throws SAXException {}
  public void skippedEntity(String name) throws SAXException { }


  public void startDocument() throws SAXException {
    accountList = new ArrayList();
    context = new Stack();

  }

  public void endDocument() throws SAXException {
  }

  public void startElement(String uri, String localName, String qName,
      Attributes atts) throws SAXException {
    if(!localName.equals("SIRS")) {
      String className = OM_PKG_NAME+localName;
      try {
        InvoiceModelObject obj = (InvoiceModelObject) Class.forName(className).newInstance();
        Method[] setMethods = obj.getClass().getMethods();
        for(int i=0; i<setMethods.length; i++) {
          Method set = setMethods[i];
          if(set.getName().startsWith("set")) {
            String attName = set.getName().substring(3);
            String attVal  = atts.getValue(attName);
            if(attVal != null) {
              setAttributeValue(obj, set, attVal);
            }
          }
        }
        log.debug("Adding object ["+localName+"] to the structure.");
        addObjectToStructure(localName, obj);
        context.push(obj);
        
      } catch (InstantiationException e) {
        log.error("Error loading XML",e); 
        throw new SAXException(e);
      } catch (IllegalAccessException e) {
        log.error("Error loading XML",e); 
        throw new SAXException(e);
      } catch (ClassNotFoundException e) {
        log.error("Error loading XML",e); 
        throw new SAXException(e);
      } catch (IllegalArgumentException e) {
        log.error("Error loading XML",e); 
        throw new SAXException(e);
      } catch (InvocationTargetException e) {
        log.error("Error loading XML",e); 
        throw new SAXException(e);
      } catch (ParseException e) {
        log.error("Error loading XML",e); 
        throw new SAXException(e);
      } catch (SecurityException e) {
        log.error("Error loading XML",e); 
        throw new SAXException(e);
      } catch (NoSuchMethodException e) {
        log.error("Error loading XML",e); 
        throw new SAXException(e);
      } 
    }
  }
  
  public void endElement(String uri, String localName, String qName)
      throws SAXException {
    if(!localName.equals("SIRS")) {
      context.pop();
    }
  }

  /**
   * @return Returns the accountList.
   */
  public List getAccountList() {
    return accountList;
  }
  
  /**
   * @param obj
   * @param set
   * @param attVal
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws ParseException
   */
  private void setAttributeValue(InvoiceModelObject obj, Method set, String attVal) throws IllegalAccessException, InvocationTargetException, ParseException {
    Class param = set.getParameterTypes()[0]; // it is supposed to have only one parameter
    if(String.class.isAssignableFrom(param)) {
      set.invoke(obj, new Object[] { attVal });
    } else if(int.class.isAssignableFrom(param)) {
      set.invoke(obj, new Object[] { new Integer(attVal) });
    } else if(long.class.isAssignableFrom(param)) {
      set.invoke(obj, new Object[] { new Long(attVal) });
    } else if(double.class.isAssignableFrom(param)) {
      set.invoke(obj, new Object[] { new Double(attVal) });
    } else if(boolean.class.isAssignableFrom(param)) {
      set.invoke(obj, new Object[] { new Boolean(attVal) });
    } else if(Date.class.isAssignableFrom(param)) {
      set.invoke(obj, new Object[] { dtParser.parse(attVal) });
    } else if(UnitCounter.class.isAssignableFrom(param)) {
      UnitCounter counter = new UnitCounter();
      counter.parse(attVal);
      set.invoke(obj, new Object[] { counter });
    } else {
      throw new RuntimeException("Parameter type not found: ["+param+"]. Impossible to set attribute value for attribute ["+set.getName().substring(3)+"]");
    }
  }
  
  /**
   * @param localName
   * @param obj
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  private void addObjectToStructure(String localName, InvoiceModelObject obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    if(context.size() > 0) {
      InvoiceModelObject oContext = (InvoiceModelObject) context.peek();
      Method add = null;
      if(UsageDetail.class.isAssignableFrom(obj.getClass()) &&
         UsageDetail.class.isAssignableFrom(oContext.getClass())) {
          add = oContext.getClass().getMethod("addDetail", 
              new Class[] {UsageDetail.class});
      } else if(SectionDetail.class.isAssignableFrom(obj.getClass())) {
          add = oContext.getClass().getDeclaredMethod("addDetail", 
              new Class[] {SectionDetail.class});
      } else if(Section.class.isAssignableFrom(obj.getClass())) {
        add = oContext.getClass().getDeclaredMethod("addSection", 
            new Class[] {Section.class});
      } else if(Invoice.class.isAssignableFrom(obj.getClass())) {
        add = oContext.getClass().getDeclaredMethod("addInvoice", 
            new Class[] {Invoice.class});
      } else if(Address.class.isAssignableFrom(obj.getClass())) {
        add = oContext.getClass().getDeclaredMethod("addAddress", 
            new Class[] {Address.class});
      } else if(Identity.class.isAssignableFrom(obj.getClass())) {
        add = oContext.getClass().getDeclaredMethod("addIdentity", 
            new Class[] {Identity.class});
      } else if(Receipt.class.isAssignableFrom(obj.getClass())) {
        add = oContext.getClass().getDeclaredMethod("addReceipt", 
            new Class[] {Receipt.class});
      } else if(ReceiptDetail.class.isAssignableFrom(obj.getClass())) {
        add = oContext.getClass().getDeclaredMethod("addDetail", 
            new Class[] {ReceiptDetail.class});
      } else if(ChargedTax.class.isAssignableFrom(obj.getClass())) {
        add = oContext.getClass().getDeclaredMethod("addTax", 
            new Class[] {ChargedTax.class});
      } else if(BarCode.class.isAssignableFrom(obj.getClass())) {
          add = oContext.getClass().getDeclaredMethod("setBarCode", 
              new Class[] {BarCode.class});
      } else {
        add = oContext.getClass().getDeclaredMethod("add"+localName, 
            new Class[] {obj.getClass()});
      }
      add.invoke(oContext, new Object[] {obj});
    } else if(localName.equals("Account")){
      this.accountList.add(obj);
    } else {
      throw new RuntimeException("UNEXPECTED Element without context:"+obj.toXML(""));
    }
  }
  
  public void configure(Element arg0) throws Exception {
  }

  public List getObjects() {
    return this.getAccountList();
  }

  public void cleanup() {
    this.accountList.clear();
  }

}
