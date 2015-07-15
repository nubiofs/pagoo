/**
 * $Id: BigDecimalFormat.java,v 1.3 2002/01/29 20:06:19 murgul Exp $
 *
 * (c)1997 IoS Gesellschaft fr innovative Softwareentwicklung mbH
 * http://www.IoS-Online.de    mailto:info@IoS-Online.de
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  
 */

package br.com.infosolo.comum.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;

import javax.transaction.NotSupportedException;

/**
 * BigDecimalFormat is a Formatter for BigDecimals subclassing
 * DecimalFormat and resolving the rounding-problems occuring due
 * to use of Double in DecimalFormat.
 * WARNING: Not all super-Methods yet fully mapped (especially NumberFormat-Methods).
 * This is a first, a little dirty implementation...
 */
public class BigDecimalFormat extends DecimalFormat {
	private static final long serialVersionUID = -8852596230235650863L;
	
/** Precision-Format-String. */
  protected boolean hasFract;
  protected int     fractZeros;
  protected int     fractDCrosses;
  protected boolean autoScaleDown = false;
  

  /**
   * Create a DecimalFormat from the given pattern and the symbols
   * for the default locale.
   * @param pattern A non-localized pattern string.
 * @throws NotSupportedException 
   * @exception IllegalArgumentException if the given pattern is invalid.
   */
  public BigDecimalFormat(String pattern) throws NotSupportedException {
    this( pattern, pattern.indexOf('.') );
  }
  
  
  /**
   * Private Constructor for internal use.
 * @throws NotSupportedException 
   */
  public BigDecimalFormat(String pattern, int p) throws NotSupportedException {
    super( (p == -1) ? pattern : pattern.substring(0,p) );
    if (pattern.indexOf(';') > 0)
      throw new NotSupportedException( "Format-Lists not yet supported!" );
    setParseIntegerOnly( true );
    setFraction( (p == -1) ? "" : pattern.substring( p ) );
  }


  /**
   * Set the auto-scale-down-mode.
   */
  public BigDecimalFormat setAutoScaleDown( boolean auto ) {
    autoScaleDown = auto;
    return this;
  }

  /**
   * Set the Fraction-Informations.
   */
  protected void setFraction( String pattern ) {
    char sep = super.getDecimalFormatSymbols().getDecimalSeparator();

    hasFract = (pattern.length() > 0);
    fractZeros = 0;
    fractDCrosses = 0;
    if (hasFract)
      pattern = pattern.substring(1);
    while ( (fractZeros < pattern.length()) && (pattern.charAt(fractZeros) == '0') )
      fractZeros++;
    pattern = pattern.substring(fractZeros);
    while ( (fractDCrosses < pattern.length()) && (pattern.charAt(fractDCrosses) == '#') )
      fractDCrosses++;
    if (fractDCrosses != pattern.length())
      throw new IllegalArgumentException( "Illegal or unsupported characters at the fraction-part of the pattern (suffix not supported) or illegal format!" );
  }


  /**
   * Unsupported Method of a superclass.
   */
  public StringBuffer format(double number, StringBuffer result,
			     FieldPosition fieldPosition) {
    try {
		throw new NotSupportedException( "This Method is generally not supported on this class!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
  }


  /**
   * Unsupported Method of a superclass.
   */
  public StringBuffer format(long number, StringBuffer result,
			     FieldPosition fieldPosition) {
    try {
		throw new NotSupportedException( "This Method is generally not supported on this class!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
  }


  /**
   * Returns a BigDecimal.
   * Does not throw an exception; if no object can be parsed, index is
   * unchanged!
   * @see java.text.NumberFormat#isParseIntegerOnly
   * @see java.text.Format#parseObject
   */
  @SuppressWarnings("unused")
public Number parse(String text, ParsePosition parsePosition) {
    ParsePosition p2 = new ParsePosition( parsePosition.getIndex() );
    Long l1 = (Long)super.parse(text, p2);
    Long l2 = null;
    char sep = super.getDecimalFormatSymbols().getDecimalSeparator();
    BigDecimal b;
    String xsign;
    
    if (l1 == null)
      return null;
    if ( text.regionMatches( p2.getIndex(), ""+sep, 0, 1 ) ) {
      xsign = ( (l1.longValue() == 0) && text.trim().startsWith("-") ) ? "-" : "" ; // fix lost sign...
      try {
        b = new BigDecimal( xsign+l1.longValue()+"."+text.substring(p2.getIndex()+1) );
      } catch (NumberFormatException n) {
	return null;
      }
      parsePosition.setIndex( text.length() );
    } else {
      if (p2.getIndex() != text.length())
        return null;
      b = new BigDecimal( ""+l1.longValue() );
      parsePosition.setIndex( text.length() );
    }
    return b;
  }

  
  /** Too much 'final' at NumberFormat and DecimalFormat require this Method. */
  public String formatBD(Object obj) {
    return (obj == null)
      ? ""
      : format( (obj instanceof BigDecimal)
		? (BigDecimal)obj
		: new BigDecimal( obj.toString() ),
		new StringBuffer(), new FieldPosition(0)).toString();
  }



  /**
   * Formats an object to produce a string.
   * @param obj    The object to format
   * @param toAppendTo    where the text is to be appended
   * @param status    On input: an alignment field, if desired.
   * On output: the offsets of the alignment field.
   * @return       the value passed in as toAppendTo (this allows chaining,
   * as with StringBuffer.append())
   * @exception IllegalArgumentException when the Format cannot format the
   * given object.
   */
  @SuppressWarnings("unused")
public StringBuffer format( BigDecimal obj,
			      StringBuffer toAppendTo,
			      FieldPosition pos ) {
    char   sep = super.getDecimalFormatSymbols().getDecimalSeparator();
    String ts  = obj.toString();
    int    p   = ts.indexOf( '.' );
    int    l;
    int    p1, p2;

    p1 = toAppendTo.length();
    if (ts.startsWith( "-0." ))
      toAppendTo.append("-");
    super.format( obj.longValue(), toAppendTo, pos );
    p2 = toAppendTo.length();
    ts = (p == -1) ? "" : ts.substring(p+1);
    l = ts.length();
    if (hasFract)
      if (l < fractZeros)
        ts += "0000000000000000000000000000000000000000000000000000000000000000000000000000000000".substring(0, fractZeros-l); //  Is that enough?  :)
      else {
	if (l > (fractZeros+fractDCrosses)) {
          ts = obj.setScale( fractZeros+fractDCrosses, BigDecimal.ROUND_HALF_UP ).toString();
          p  = ts.indexOf( '.' );
          ts = (p == -1) ? "" : ts.substring(p+1);
	}
        if (autoScaleDown && (l > fractZeros)) {
	  l -= fractZeros;
          l = (l > fractDCrosses) ? fractDCrosses : l;
          while ((l > 0) && (ts.charAt(fractZeros+l-1) == '0'))
            l--;
	  ts = ts.substring(0, fractZeros+l);
        }
      }
    toAppendTo.append( ((hasFract && (ts.length() > 0)) ? (""+sep) : "")+ts );

    /* The following is forbidden by JDK - all ways for supporting are NOT ACCESSIBLE! So no 100% support for FieldPosition...
    if (pos.getField() == NumberFormat.INTEGER_FIELD) {  // This should already be done by super...
      pos.setBeginIndex(p1);
      pos.setEndIndex(p2);
    } else  if (pos.getField() == NumberFormat.FRACTION_FIELD) {
      pos.setBeginIndex( hasFract ? (p2+1) : p2 );
      pos.setEndIndex(   toAppendTo.length() );
    }
    */

    return toAppendTo;
  }
  

  /**
   * Unsupported Method of a superclass.
   */
  public String getPositiveSuffix () {
    try {
		throw new NotSupportedException( "This Method is generally not supported on this class!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
  }


  /**
   * Unsupported Method of a superclass.
   */
  public void setPositiveSuffix (String newValue) {
    try {
		throw new NotSupportedException( "This Method is generally not supported on this class!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }


  /**
   * Unsupported Method of a superclass.
   */
  public String getNegativeSuffix () {
    try {
		throw new NotSupportedException( "This Method is generally not supported on this class!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
  }


  /**
   * Unsupported Method of a superclass.
   */
  public void setNegativeSuffix (String newValue) {
    try {
		throw new NotSupportedException( "This Method is generally not supported on this class!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }


  /**
   * Unsupported Method of a superclass.
   */
  public int getMultiplier () {
    try {
		throw new NotSupportedException( "This Method is generally not supported on this class!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 0;
  }


  /**
   * Unsupported Method of a superclass.
   */
  public void setMultiplier (int newValue) {
    try {
		throw new NotSupportedException( "This Method is generally not supported on this class!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }


  // public boolean isDecimalSeparatorAlwaysShown() {


  /**
   * Clone this object.
   */
  public Object clone() {
    try {
      BigDecimalFormat c = (BigDecimalFormat)super.clone();
      c.hasFract      = hasFract;
      c.fractZeros    = fractZeros;
      c.fractDCrosses = fractDCrosses;
      return c;
    } catch (Exception e) {
      throw new InternalError();
    }
  }


  /**
   * Overrides equals.
   */
  public boolean equals(Object obj) {
    BigDecimalFormat b = (BigDecimalFormat)obj;
    return (obj == null)
      ? false
      : ( (!super.equals(obj))
	  ? false
	  : ((b.hasFract == hasFract) && (b.fractZeros == fractZeros) && (b.fractDCrosses == fractDCrosses)) );
  }


  /**
   * Synthesizes a pattern string that represents the current state
   * of this Format object (not yet implemented).
   * @see #applyPattern
   */
  public String toPattern() {
    try {
		throw new NotSupportedException( "Not yet implemented!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
  }


  /**
   * Synthesizes a localized pattern string that represents the current
   * state of this Format object (not yet implemented).
   * @see #applyPattern
   */
  public String toLocalizedPattern() {
    try {
		throw new NotSupportedException( "Not yet implemented!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
  }


  /**
   * Apply the given pattern to this Format object.  A pattern is a
   * short-hand specification for the various formatting properties.
   * These properties can also be changed individually through the
   * various setter methods.
   * <p>
   * There is no limit to integer digits are set
   * by this routine, since that is the typical end-user desire;
   * use setMaximumInteger if you want to set a real value.
   * For negative numbers, use a second pattern, separated by a semicolon
   * <P>Example "#,#00.0#" -> 1,234.56
   * <P>This means a minimum of 2 integer digits, 1 fraction digit, and
   * a maximum of 2 fraction digits.
   * <p>Example: "#,#00.0#;(#,#00.0#)" for negatives in parantheses.
   * <p>In negative patterns, the minimum and maximum counts are ignored;
   * these are presumed to be set in the positive pattern.
   */
  public void applyPattern( String pattern ) {
    int p = pattern.indexOf('.');
    super.applyPattern( (p == -1) ? pattern : pattern.substring(0,p) );
    if (pattern.indexOf(';') > 0)
		try {
			throw new NotSupportedException( "Format-Lists not yet supported!" );
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    setFraction( (p == -1) ? "" : pattern.substring( p ) );
  }


  /**
   * Apply the given pattern to this Format object (not yet implemented).  The pattern
   * is assumed to be in a localized notation. A pattern is a
   * short-hand specification for the various formatting properties.
   * These properties can also be changed individually through the
   * various setter methods.
   * <p>
   * There is no limit to integer digits are set
   * by this routine, since that is the typical end-user desire;
   * use setMaximumInteger if you want to set a real value.
   * For negative numbers, use a second pattern, separated by a semicolon
   * <P>Example "#,#00.0#" -> 1,234.56
   * <P>This means a minimum of 2 integer digits, 1 fraction digit, and
   * a maximum of 2 fraction digits.
   * <p>Example: "#,#00.0#;(#,#00.0#)" for negatives in parantheses.
   * <p>In negative patterns, the minimum and maximum counts are ignored;
   * these are presumed to be set in the positive pattern.
   */
  public void applyLocalizedPattern( String pattern ) {
    try {
		throw new NotSupportedException( "Not yet implemented!" );
	} catch (NotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }


}


/**
 * $Log: BigDecimalFormat.java,v $
 * Revision 1.3  2002/01/29 20:06:19  murgul
 * Final Euro update from Joachim Schaaf
 *
 * Revision 1.2  2001/12/04 07:00:52  murgul
 * Some bugfixes, added original CVS-comment
 *
 * Revision 1.1.1.1  2001/02/07 15:25:05  rtfm
 * initial
 *
 * Revision 1.7  2001/03/16 12:20:22  fw
 * reactivated setDecimalFormatSymbols
 *
 * Revision 1.6  2000/01/27 13:12:10  fw
 * BigDecimalFormat: bugfix (ArrayOutOfBounds-Exception)
 * StringTool: added new Method (minRString(...))
 * Template: bugfix at the .num(...) and .prec(...) Template-Functions (bad results) + extra optional parameter (precision-separator)
 *
 * Revision 1.5  2000/01/06 16:58:10  fw
 * another fix due to the lost sign on values between -1 and 0
 *
 * Revision 1.4  2000/01/06 16:15:58  fw
 * bugfix for negative values between -1 and 0 loosing their sign
 *
 * Revision 1.3  1999/07/13 08:18:31  fw
 * implemted dynamic scaling (=> setAutoScaleDown(...), default: off)
 *
 * Revision 1.2  1999/05/11 10:45:30  fw
 * BigDecimalFormat: formatBD now can handle null -> ""
 * SendMail: support for sender/recipient-default-domain (automatically added if available and required)
 *           support for colon-seperated Multi-Recipints
 *
 * Revision 1.1  1999/04/15 15:49:59  fw
 * initial
 *
 */
