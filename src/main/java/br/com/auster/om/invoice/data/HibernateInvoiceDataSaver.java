/*
 * Copyright (c) 2004-2006 Auster Solutions do Brasil. All Rights Reserved.
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
 * Created on 30/01/2006
 */

package br.com.auster.om.invoice.data;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import br.com.auster.common.data.DataSaver;
import br.com.auster.common.data.DataSaverException;
import br.com.auster.common.io.IOUtils;
import br.com.auster.common.log.LogFactory;
import br.com.auster.common.xml.DOMUtils;
import br.com.auster.om.invoice.Account;
import br.com.auster.om.invoice.ChargedTax;
import br.com.auster.om.invoice.Invoice;
import br.com.auster.om.invoice.Receipt;
import br.com.auster.om.invoice.ReceiptDetail;
import br.com.auster.om.invoice.Section;
import br.com.auster.om.invoice.SectionDetail;
import br.com.auster.om.invoice.UsageDetail;

/**
 * <p><b>Title:</b> HibernateInvoiceDataSaver</p>
 * <p><b>Description:</b> A data saver that recursivelly persists an Invoice OM tree 
 * using hibernate</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> Auster Solutions</p>
 * 
 * <config>
 *   <hb-invoice-data-saver-config id='saver1'>
 *     <hibernate-config file-name='hibernate.conf.xml' encrypted='false'/>
 *     <root-om-tag name='account-list'/>
 *   </hb-invoice-data-saver-config>
 * </config>
 *
 * @author etirelli
 * @version $Id: HibernateInvoiceDataSaver.java 120 2006-03-01 19:32:35Z etirelli $
 */
public class HibernateInvoiceDataSaver implements DataSaver {
  public static final String CONFIG_ROOT_ELEM       = "hb-invoice-data-saver-config";
  public static final String CONFIG_ROOT_ID_ELEM    = "id";
  public static final String CONFIG_HIBERNATE_CONF  = "hibernate-config";
  public static final String CONFIG_HIBERNATE_FILE  = "file-name";
  public static final String CONFIG_HIBERNATE_ENCR  = "encrypted";
  public static final String CONFIG_TAG_ELEM        = "root-om-tag";
  public static final String CONFIG_TAG_NAME_ATTR   = "name";
  
  private static Logger log = LogFactory.getLogger(HibernateInvoiceDataSaver.class);
  
  private String omTag = null;

  private Session session;
  
  /**
   * @inheritDoc
   */
  public void configure(Element config, Connection connection)
      throws DataSaverException {
    try {
      log.debug("Configuring HibernateInvoiceDataSaver...");
      
      if(!CONFIG_ROOT_ELEM.equals(config.getLocalName())) {
        config = DOMUtils.getElement(config, CONFIG_ROOT_ELEM, true);
      }
      
      Element hibernateElem = DOMUtils.getElement(config, CONFIG_HIBERNATE_CONF, true);
      String  hibernateFile = DOMUtils.getAttribute(hibernateElem, CONFIG_HIBERNATE_FILE, true);
      boolean encrypted = DOMUtils.getBooleanAttribute(hibernateElem, CONFIG_HIBERNATE_ENCR, true);
      
      this.configureHibernate(hibernateFile, encrypted, connection);
      
      Element rootTagElem = DOMUtils.getElement(config, CONFIG_TAG_ELEM, true);
      omTag               = DOMUtils.getAttribute(rootTagElem, CONFIG_TAG_NAME_ATTR, true);
    } catch (Exception e) {
      throw new DataSaverException("Error configuring HibernateInvoiceDataSaver", e);
    }
  }

  /**
   * @inheritDoc
   */
  public void save(Map data) throws DataSaverException {
    try {
      Object root = data.get(omTag);
      if (root instanceof Collection) {
        Collection omBag = (Collection) root;
        for (Iterator i = omBag.iterator(); i.hasNext();) {
          Account account = (Account) i.next();
          this.saveOMTree(account);
        }
      } else if (root instanceof Account) {
        this.saveOMTree((Account) root);
      } else {
        log.error("Dont know how to save an instance of " + root.getClass());
        throw new DataSaverException("Dont know how to save an instance of "
            + root.getClass());
      }
    } catch (ClassCastException e) {
      throw new DataSaverException("Dont know how to save this object", e);
    } catch (HibernateException e) {
      throw new DataSaverException("Error saving object", e);
    } catch (SQLException e) {
      throw new DataSaverException("Error commiting object", e);
    } 
  }

  private void saveOMTree(Account account) 
       throws HibernateException, SQLException {
    this.saveAccount((Account) account);
    session.flush();
    session.connection().commit();
  }

  /**
   * A helper method to configure hibernate
   * @throws GeneralSecurityException 
   * @throws ParserConfigurationException 
   * @throws IOException 
   * @throws SAXException 
   */
  private void configureHibernate(String confFile, boolean encrypted,
          Connection connection) 
          throws HibernateException, SAXException, IOException, 
                 ParserConfigurationException, GeneralSecurityException {
    
    // Reading configuration file into an XML Document
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    Document config = dbf.newDocumentBuilder().parse(
        IOUtils.openFileForRead(confFile, encrypted));
    
    // Configuring hibernate and creating session
    Configuration cfg = new Configuration();
    cfg.configure(config);
    SessionFactory factory = cfg.buildSessionFactory();
    if(connection != null) {
      session = factory.openSession(connection);
    } else {
      session = factory.openSession();
    }
  }

  private void saveAccount(Account account) throws HibernateException {
    session.save(account);
    for(Iterator i = account.getIdentities().values().iterator(); i.hasNext(); ) {
      session.save(i.next());
    }
    for(Iterator i = account.getAddresses().values().iterator(); i.hasNext(); ) {
      session.save(i.next());
    }
    for(Iterator i = account.getInvoices().values().iterator(); i.hasNext(); ) {
      Invoice inv = (Invoice) i.next();
      saveInvoice(inv);
    }
  }

  /**
   * @param inv
   * @throws HibernateException
   */
  private void saveInvoice(Invoice inv) throws HibernateException {
    session.save(inv);
    session.save(inv.getBarCode());
    for(Iterator i2 = inv.getReceipts().iterator(); i2.hasNext(); ) {
      Receipt receipt = (Receipt) i2.next();
      saveReceipt(receipt);
    }
    for(Iterator i2 = inv.getSections().iterator(); i2.hasNext(); ) {
      Section section = (Section) i2.next();
      this.saveSection(section);
    }
  }

  /**
   * @param receipt
   * @throws HibernateException
   */
  private void saveReceipt(Receipt receipt) throws HibernateException {
    session.save(receipt);
    for(Iterator i3 = receipt.getDetails().iterator(); i3.hasNext(); ) {
      ReceiptDetail detail = (ReceiptDetail) i3.next();
      saveReceiptDetail(detail);
    }
    for(Iterator i3 = receipt.getTaxes().iterator(); i3.hasNext(); ) {
      ChargedTax tax = (ChargedTax) i3.next();
      saveChargedTax(tax);
    }
  }

  /**
   * @param detail
   * @throws HibernateException
   */
  private void saveReceiptDetail(ReceiptDetail detail) throws HibernateException {
    session.save(detail);
    for(Iterator i4 = detail.getTaxes().iterator(); i4.hasNext(); ) {
      ChargedTax tax = (ChargedTax) i4.next();
      saveChargedTax(tax);
    }
  }
  
  /**
   * @param tax
   * @throws HibernateException
   */
  private void saveChargedTax(ChargedTax tax) throws HibernateException {
    session.save(tax);
  }

  private void saveSection(Section section) throws HibernateException {
    session.save(section);
    for(Iterator i = section.getDetails().iterator(); i.hasNext(); ) {
      SectionDetail detail = (SectionDetail) i.next();
      saveSectionDetail(detail);
    }
    for(Iterator i = section.getSubSections().iterator(); i.hasNext(); ) {
      Section subsection = (Section) i.next();
      this.saveSection(subsection);
    }
  }

  /**
   * @param detail
   * @throws HibernateException
   */
  private void saveSectionDetail(SectionDetail detail) throws HibernateException {
    session.save(detail);
    if(detail instanceof UsageDetail) {
      UsageDetail usage = (UsageDetail) detail;
      for(Iterator i2 = usage.getParts().iterator(); i2.hasNext(); ) {
        SectionDetail part = (SectionDetail) i2.next();
        session.save(part);
      }
    }
  }
  
  
}
