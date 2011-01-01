package com.higginsthomas.android.weatheralert.alertreader;

import com.higginsthomas.android.weatheralert.util.Pair;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class NwsAtomEntryParser extends DefaultHandler {
  public static final String ELEMENT = "entry";
  private final NwsAtomParserManager manager;
  private final StringBuilder content = new StringBuilder();
  private final List<Pair<String, String>> entry = new ArrayList<Pair<String, String>>();
  private boolean inElement = false;
  
  
  public NwsAtomEntryParser(NwsAtomParserManager manager) {
    this.manager = manager;
  }

  
  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if ( inElement ) {
      content.append(ch, start, length);
    }
  }

  @Override
  public void startElement(String uri, String localName, 
                           String qName, Attributes attributes) throws SAXException {
    inElement = true;
  }
  
  @Override
  public void endElement(String uri, String localName, String qName)
          throws SAXException {
    if ( ELEMENT.equals(qName) ) {
      manager.popHandler(entry);
      return;
    }
    entry.add(new Pair<String, String>(qName, content.toString()));
    content.delete(0, content.length());
    inElement  = false;
  }
}
