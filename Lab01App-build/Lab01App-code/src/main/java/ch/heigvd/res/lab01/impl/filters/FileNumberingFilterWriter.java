package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private boolean lastCharWasReturnCarriage;
  private int numberOfLines = 1;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = 0; i < len; ++i)
    {
        write(cbuf[off + i]);
    }
  }

  @Override
    public void write(int c) throws IOException
    {
        // It's the beginning of the file, write the number for the first line
        if(numberOfLines == 1)
        {
            super.write((numberOfLines++) + "\t");
        }
        
        // If the last character was a '\r'
        if(lastCharWasReturnCarriage)
        {
            // If the current character is a '\n' (Windows) write it and the 
            // the beginning of the next line.
            if(c == '\n')
            {
                lastCharWasReturnCarriage = false;
                super.write(c);
                super.write((numberOfLines++) + "\t");               
            }
            
            // Two return carriage write two empty lines (suppose it's MacOS)
            else if (c == '\r')
            {
                super.write((numberOfLines++) + "\t");
                super.write(c);
                super.write((numberOfLines++) + "\t");
            }
            
            // Other characters. We write it on a new line. (MacOS)
            else
            {
                lastCharWasReturnCarriage = false;
                super.write((numberOfLines++) + "\t");
                super.write(c);
            }
        }
        
        // The last characher was not a return carriage.
        
        // The current char is a '\r'
        else if (c == '\r')
        {
            lastCharWasReturnCarriage = true;  
            super.write(c);
        }
        
        // The current char is '\n' (Linux)
        else if (c == '\n')
        {
            super.write(c);
            super.write((numberOfLines++) + "\t");
        }
        
        else
        {
            super.write(c);
        }
    }
}
