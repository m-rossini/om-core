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
 * Created on Jan 27, 2005
 */
package br.com.auster.om.util;

import java.text.MessageFormat;
import java.text.ParseException;


/**
 * <p><b>Title:</b> UnitCounter</p>
 * <p><b>Description:</b> Implements a generic unit counter, capable of counting time, data or simple units</p>
 * <p><b>Copyright:</b> Copyright (c) 2004</p>
 * <p><b>Company:</b> Auster Solutions</p>
 *
 * @author etirelli
 * @version $Id: UnitCounter.java 67 2005-03-23 12:37:19Z etirelli $
 */
public class UnitCounter implements Cloneable {
  public static final String TIME_COUNTER = "TIME";
  public static final String DATA_COUNTER = "DATA";
  public static final String UNIT_COUNTER = "UNIT";
  
  private static final ThreadLocal formatter = new ThreadLocal() {
    protected Object initialValue() {
      return new MessageFormat("{0,number,00}h{1,number,00}m{2,number,00}s");
    }
  };
  
  
  private String   counterType;
  private long     unitCounter; // a counter for single units and data volumes
  
  public UnitCounter() {
    this(UNIT_COUNTER);
  }
  
  public UnitCounter(String type) {
    this.setType(type);
  }

  
  /**
   * @hibernate.property
   *                type="string"
   *                
   * @hibernate.column
   *                name="TYPE"                
   *                length="32"
   *                not-null="false"
   */
  public void setType(String type) {
    if(type.equals(TIME_COUNTER)) {
      this.unitCounter = 0;
    } else if (type.equals(DATA_COUNTER)) {
      this.unitCounter = 0;
    } else if (type.equals(UNIT_COUNTER)) {
      this.unitCounter = 0;
    } else {
      throw new IllegalArgumentException("Unknown type = "+type);
    }
    this.counterType = type;
  }
  
  public String getType() {
    return this.counterType;
  }
  
  public void reset() {
    this.unitCounter=0;
  }
  
  public void addUnits(long units) {
    this.unitCounter += units;
  }
  
  public void addBytes(long bytes) {
    if(this.getType().equals(DATA_COUNTER)) {
      this.unitCounter += bytes;
    } else {
      throw new IllegalArgumentException("Can't add data units to a "+this.getType()+" counter");
    }
  }

  public void addKBytes(double kbytes) {
    if(this.getType().equals(DATA_COUNTER)) {
      long aux = (long)((kbytes*100) + 0.005); // correcting rounding problems
      this.unitCounter += (long)(aux*1024)/100;
    } else {
      throw new IllegalArgumentException("Can't add data units to a "+this.getType()+" counter");
    }
  }

  public void addMBytes(double mbytes) {
    if(this.getType().equals(DATA_COUNTER)) {
      long aux = (long)((mbytes*100) + 0.005); // correcting rounding problems
      this.unitCounter += (long) (aux*1024*1024)/100;
    } else {
      throw new IllegalArgumentException("Can't add data units to a "+this.getType()+" counter");
    }
  }
  
  public void addSeconds(long seconds) {
    if(this.getType().equals(TIME_COUNTER)) {
      this.unitCounter += seconds;
    } else {
      throw new IllegalArgumentException("Can't add time units to a "+this.getType()+" counter");
    }
  }

  public void addMinutes(double minutes) {
    if(this.getType().equals(TIME_COUNTER)) {
      long aux = (long)((minutes*100) + 0.005); // correcting rounding problems
      this.unitCounter += (aux*60)/100;
    } else {
      throw new IllegalArgumentException("Can't add time units to a "+this.getType()+" counter");
    }
  }

  public void addHours(double hours) {
    if(this.getType().equals(TIME_COUNTER)) {
      long aux = (long)((hours*100) + 0.005); // correcting rounding problems
      this.unitCounter += (aux*3600)/100;
    } else {
      throw new IllegalArgumentException("Can't add time units to a "+this.getType()+" counter");
    }
  }
  
  public long getSeconds() {
    return this.unitCounter;
  }
  
  public double getMinutes() {
    return ((double) this.unitCounter / 60.0);
  }
  
  public long getBytes() {
    return this.unitCounter;
  }
  
  public double getKBytes() {
    return ((double) this.unitCounter / 1024.0);
  }
  
  /**
   * @hibernate.property
   *                type="long"
   *                
   * @hibernate.column
   *                name="UNIT"                
   *                not-null="false"
   */
  public long getUnits() {
    return this.unitCounter;
  }
  public void setUnits(long _units) {
      this.unitCounter = _units;
  }

  /***
   * MT.
   * This method adds the UNITS from newCounter to this Unit Counter.
   * If UNITS are not Compatible, throws an IllegalArgumentException
   * 
   * If they are the same it just SUM both COUNTERS.
   * 
   * If they are compatible, first convert, then add.
   * 
   * @since 2.0.2
   * 
   * @param newCounter
   */
  public void addCounter(UnitCounter newCounter) {
  	if (newCounter.getType().equals(this.getType())) {
  		this.setUnits(getUnits() + newCounter.getUnits());
  	} else {
  		throw new IllegalArgumentException("Can't add different and not compatible UNITS. Current UC:" + 
  				this.toString() + ".To be added UC:" + newCounter.toString() );
  	}
  }
  
  public String toString() {
    String ret = null;
    if(this.getType().equals(UNIT_COUNTER)) {
      ret = this.unitCounter + " unidade(s)";
    } else if(this.getType().equals(TIME_COUNTER)) {
      Object[] param = {new Integer((int) this.unitCounter/3600),
          				new Integer((int) (this.unitCounter%3600)/60),
          				new Integer((int) this.unitCounter%60)};
      ret = ((MessageFormat) formatter.get()).format(param);
    } else if(this.getType().equals(DATA_COUNTER)) {
      ret = this.unitCounter + " bytes";
    }
    return ret;
  }
  
  public void parse(String val) {
    long value = 0;
    String type = UnitCounter.UNIT_COUNTER;
    if(val.endsWith(" unidade(s)")) {
      String aux = val.substring(0, val.length()-11);
      value = Long.parseLong(aux);
      type = UnitCounter.UNIT_COUNTER;
    } else if (val.endsWith(" bytes")) {
      String aux = val.substring(0, val.length()-6);
      value = Long.parseLong(aux);
      type = UnitCounter.DATA_COUNTER;
    } else if ((val.charAt(2) == 'h') && (val.charAt(5)=='m') && (val.charAt(8)=='s')) {
      //MessageFormat formatter = new MessageFormat("{0,number,00}h{1,number,00}m{2,number,00}s");
      try {
        Object[] result = ((MessageFormat)formatter.get()).parse(val);
        value = ((Number)result[0]).intValue() * 3600 +
                ((Number)result[1]).intValue() * 60 +
                ((Number)result[2]).intValue();
      } catch (ParseException e) {
        e.printStackTrace();
      }
      type = UnitCounter.TIME_COUNTER;
    }
    this.setType(type);
    this.addUnits(value);
  }

  public static String format(String type, long units) {
    String ret = null;
    if(type.equals(UNIT_COUNTER)) {
      ret = units + " unidade(s)";
    } else if(type.equals(TIME_COUNTER)) {
      Object[] param = {new Integer((int) units/3600),
                  new Integer((int) (units%3600)/60),
                  new Integer((int) units%60)};
      ret = ((MessageFormat) formatter.get()).format(param);
    } else if(type.equals(DATA_COUNTER)) {
      ret = units + " bytes";
    }
    return ret;
  }
  
  public Object clone() {
	  UnitCounter clone = new UnitCounter(this.getType());
	  clone.setUnits(this.getUnits());
	  return clone;
  }

  public boolean equalsType(Object obj) {
  	if (obj instanceof UnitCounter) {
  		UnitCounter input = (UnitCounter) obj;
  		if (input.getType().equals(this.getType())) {
  			return true;
  		}
  	}
  	return false;
  }
    
	public boolean equals(Object obj) {
		if (obj instanceof UnitCounter) {
			UnitCounter input = (UnitCounter) obj;
			if (input.getType().equals(this.getType())) {
				if (input.getUnits() == this.getUnits()) {
					return true;
				}
			}			
		}
		return false;		
	}

	public int hashCode() {
		int result = this.getType().hashCode() * 37;
		result = result*37 + (int)this.getUnits();
		return result;
	}
  
}
