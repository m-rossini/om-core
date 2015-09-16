package br.com.auster.om.invoice;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import br.com.auster.om.invoice.util.InvoiceXMLLoaderCH;

public class ObjectReferenceTest extends TestCase {

  private static final String DATA_FILE = "/ACCOUNT_0100729400.xml";
  
  private Account account;

  public ObjectReferenceTest() {
    Logger logger = Logger.getRootLogger();
    logger.setLevel(Level.WARN);
  }
  
  protected void setUp() throws Exception {
    super.setUp();
    account = this.loadTestData(DATA_FILE);
  }
  
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testInvoiceReferences() {
    Assert.assertNull("Account reference to invoice should be null", account.getInvoice());
    for(Iterator i = account.getIdentities().values().iterator(); i.hasNext(); ) {
      Identity id = (Identity) i.next();
      Assert.assertNull("Address reference to invoice should be null", id.getInvoice());
    }
    for(Iterator i = account.getAddresses().values().iterator(); i.hasNext(); ) {
      Address addr = (Address) i.next();
      Assert.assertNull("Address reference to invoice should be null", addr.getInvoice());
    }
    for(Iterator i = account.getInvoices().values().iterator(); i.hasNext(); ) {
      Invoice inv = (Invoice) i.next();
      Assert.assertNotNull("Invoice reference to invoice should NOT be null", inv.getInvoice());
      Assert.assertNotNull("Barcode reference to invoice should NOT be null", inv.getBarCode().getInvoice());
      for(Iterator i2 = inv.getReceipts().iterator(); i2.hasNext(); ) {
        Receipt receipt = (Receipt) i2.next();
        Assert.assertNotNull("Receipt reference to invoice should NOT be null", receipt.getInvoice());
        for(Iterator i3 = receipt.getDetails().iterator(); i3.hasNext(); ) {
          ReceiptDetail detail = (ReceiptDetail) i3.next();
          Assert.assertNotNull("ReceiptDetail reference to invoice should NOT be null", detail.getInvoice());
          for(Iterator i4 = detail.getTaxes().iterator(); i4.hasNext(); ) {
            ChargedTax tax = (ChargedTax) i4.next();
            Assert.assertNotNull("ChargedTaxDetail reference to invoice should NOT be null", tax.getInvoice());
          }
        }
        for(Iterator i3 = receipt.getTaxes().iterator(); i3.hasNext(); ) {
          ChargedTax tax = (ChargedTax) i3.next();
          Assert.assertNotNull("ChargedTax reference to invoice should NOT be null", tax.getInvoice());
        }
      }
      for(Iterator i2 = inv.getSections().iterator(); i2.hasNext(); ) {
        Section section = (Section) i2.next();
        Assert.assertNotNull("Section reference to invoice should NOT be null", section.getInvoice());
        this.checkSubSection(section);
      }
    }
  }
  
  public void checkSubSection(Section section) {
    for(Iterator i2 = section.getSubSections().iterator(); i2.hasNext(); ) {
      Section subsection = (Section) i2.next();
      Assert.assertNotNull("SubSection reference to invoice should NOT be null", subsection.getInvoice());
      this.checkSubSection(subsection);
    }
    this.checkSectionDetail(section);
  }
  
  public void checkSectionDetail(Section section) {
    for(Iterator i2 = section.getDetails().iterator(); i2.hasNext(); ) {
      SectionDetail detail = (SectionDetail) i2.next();
      Assert.assertNotNull("SectionDetail reference to invoice should NOT be null", detail.getInvoice());
      if(detail instanceof UsageDetail) {
        UsageDetail usage = (UsageDetail) detail;
        if(usage.getParts() != null) {
          for(Iterator i3 = usage.getParts().iterator(); i3.hasNext(); ) {
            UsageDetail part = (UsageDetail) i3.next();
            Assert.assertNotNull("UsageDetail reference to invoice should NOT be null", part.getInvoice());
          }
        }
      }
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

}
