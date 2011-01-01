package com.higginsthomas.android.weatheralert.alertreader;

import com.higginsthomas.android.weatheralert.util.Pair;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class NwsAtomCapAlertReader {
  static final String uri = "http://www.weather.gov/alerts-beta/us.php?x=1";

  public List<String> read() {
    List<String> alerts = new ArrayList<String>();
    
    try {
      SAXParserFactory saxFactory = SAXParserFactory.newInstance();
      SAXParser parser = saxFactory.newSAXParser();
      NwsAtomParserManager manager = new NwsAtomParserManager();
      manager.pushHandler(new DefaultHandler(), null);  // provides a catch for end-of-document
      manager.pushHandler(new NwsAtomDocumentParser(manager), 
                          new NwsAtomCallback() {
                            @Override @SuppressWarnings("unchecked")
                            public void end(Object result) {
                              List<Pair<String, ?>> doc = (List<Pair<String, ?>>)result;
                              System.out.println(doc);
                            }
                          });
      parser.parse(getInputStream(), manager);
    } catch ( Exception e ) {
      throw new RuntimeException("Unable to read alert feed.", e);
    }
    
    return alerts;
  }

  protected InputStream getInputStream() throws MalformedURLException, IOException {
    return new URL(uri).openStream();
  }
}
