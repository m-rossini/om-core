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
 * Created on 09/01/2006
 */

package br.com.auster.om.invoice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import br.com.auster.om.invoice.util.InvoiceXMLLoaderCH;

/**
 * <p><b>Title:</b> AccountTest</p>
 * <p><b>Description:</b> A test case for Hibernate mappings</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: AccountTest.java 120 2006-03-01 19:32:35Z etirelli $
 */

public class AccountTest extends TestCase {
  private static final String DATA_FILE = "/ACCOUNT_0100729400.xml";
  
  private Session session;
  private Account account;
  
  public AccountTest() {
    Logger logger = Logger.getRootLogger();
    logger.setLevel(Level.WARN);
  }

  protected void setUp() throws Exception {
    super.setUp();
    this.configureHibernate();
    account = this.loadTestData(DATA_FILE);
  }
  
  protected void tearDown() throws Exception {
    super.tearDown();
    if(session != null) {
      session.close();
    }
  }
  
  public void testLifeCycle() {
    this.saveOMStructure(account);
    try {
      this.loadOMStructure(account.getId());
      //Account loadedAccount = this.loadOMStructure(account.getId());
      //System.out.println(loadedAccount.toXML(""));
    } catch (HibernateException e) {
      e.printStackTrace();
      Assert.fail("Error loading account structure from DB");
    }
  }
  
  private Account loadOMStructure(long id) throws HibernateException {
    return (Account) session.load(Account.class, new Long(id));
  }

  /**
   * Save all objects in the account
   * 
   * @param account
   */
  private void saveOMStructure(Account account) {
    try {
      session.save(account);
      for(Iterator i = account.getIdentities().values().iterator(); i.hasNext(); ) {
        session.save(i.next());
      }
      for(Iterator i = account.getAddresses().values().iterator(); i.hasNext(); ) {
        session.save(i.next());
      }
      for(Iterator i = account.getInvoices().values().iterator(); i.hasNext(); ) {
        Invoice inv = (Invoice) i.next();
        session.save(inv);
        session.save(inv.getBarCode());
        for(Iterator i2 = inv.getReceipts().iterator(); i2.hasNext(); ) {
          Receipt receipt = (Receipt) i2.next();
          session.save(receipt);
          for(Iterator i3 = receipt.getDetails().iterator(); i3.hasNext(); ) {
            ReceiptDetail detail = (ReceiptDetail) i3.next();
            session.save(detail);
            for(Iterator i4 = detail.getTaxes().iterator(); i4.hasNext(); ) {
              ChargedTax tax = (ChargedTax) i4.next();
              session.save(tax);
            }
          }
          for(Iterator i3 = receipt.getTaxes().iterator(); i3.hasNext(); ) {
            ChargedTax tax = (ChargedTax) i3.next();
            session.save(tax);
          }
        }
        for(Iterator i2 = inv.getSections().iterator(); i2.hasNext(); ) {
          Section section = (Section) i2.next();
          this.saveSection(section);
        }
      }
      session.flush();
      session.connection().commit();
    } catch (HibernateException e) {
      e.printStackTrace();
      Assert.fail("Error saving objects to database");
    } catch (SQLException e) {
      e.printStackTrace();
      Assert.fail("Error commiting objects to database");
    } finally {
    }
  }
  
  private void saveSection(Section section) throws HibernateException {
    session.save(section);
    for(Iterator i = section.getDetails().iterator(); i.hasNext(); ) {
      SectionDetail detail = (SectionDetail) i.next();
      session.save(detail);
      if(detail instanceof UsageDetail) {
        UsageDetail usage = (UsageDetail) detail;
        for(Iterator i2 = usage.getParts().iterator(); i2.hasNext(); ) {
          SectionDetail part = (SectionDetail) i2.next();
          session.save(part);
        }
      }
    }
    for(Iterator i = section.getSubSections().iterator(); i.hasNext(); ) {
      Section subsection = (Section) i.next();
      this.saveSection(subsection);
    }
  }
  
  /**
   * A helper method to load test data
   * @throws ParserConfigurationException 
   * @throws SAXException 
   * @throws IOException 
   *
   */
  private Account loadTestData(String dataFile) throws ParserConfigurationException, 
                                                       SAXException, IOException {
    try {
      // getting parser
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setNamespaceAware(true);
      SAXParser parser = factory.newSAXParser();
      XMLReader reader = parser.getXMLReader();
      
      // getting loader
      InvoiceXMLLoaderCH loader = new InvoiceXMLLoaderCH();
      
      // getting input file
      InputSource input = new InputSource(this.getClass().getResourceAsStream(dataFile));
      
      // setting content handler and parsing file
      reader.setContentHandler(loader);
      reader.parse(input);
      
      return (Account) loader.getAccountList().get(0);
      
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
      throw e;
    } catch (SAXException e) {
      e.printStackTrace();
      throw e;
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * A helper method to configure hibernate
   */
  private void configureHibernate() throws HibernateException {
    try {
      Configuration cfg = new Configuration();
      cfg.configure("/hibernate-configuration.xml");
      SessionFactory factory = cfg.buildSessionFactory();
      session = factory.openSession();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } 
  }

}
