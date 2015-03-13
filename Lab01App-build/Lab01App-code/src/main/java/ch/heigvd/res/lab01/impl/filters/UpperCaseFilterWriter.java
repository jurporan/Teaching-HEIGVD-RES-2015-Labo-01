package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
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
  public void write(int c) throws IOException {
    if(c >= 'a' && c <= 'z')
    {
        super.write(c-32);
    }
    else
    {
        super.write(c);
    }
  }

}
