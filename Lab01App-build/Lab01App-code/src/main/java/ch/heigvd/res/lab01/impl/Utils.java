package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;
import java.util.*;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) 
  {
      int returnCarriage = lines.indexOf('\r');
      int newLine = lines.indexOf('\n');
      String[] output = new String[2];
      // Windows and Unix
      if(newLine > -1)
      {
          output[0] = lines.substring(0, newLine + 1);
          output[1] = lines.substring(newLine + 1);
          return output;
      }
      // MacOS
      else if (returnCarriage > -1)
      {
          output[0] = lines.substring(0, returnCarriage + 1);
          output[1] = lines.substring(returnCarriage + 1);
          return output;
      }
      
      // No end of line character
      else
      {
          output[0] = "";
          output[1] = lines;
          return output;
      }
  }

}
