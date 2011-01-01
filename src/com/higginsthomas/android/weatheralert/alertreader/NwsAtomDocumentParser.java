package com.higginsthomas.android.weatheralert.alertreader;

import com.higginsthomas.android.weatheralert.util.Pair;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

public class NwsAtomDocumentParser extends org.xml.sax.helpers.DefaultHandler {
  private final String DOCUMENT = "feed";
  private final List<Pair<String, ?>> document = new ArrayList<Pair<String, ?>>();
  private final NwsAtomParserManager manager;
  private final StringBuilder content = new StringBuilder();
  private boolean inElement;
  
  
  NwsAtomDocumentParser(NwsAtomParserManager manager) {
    this.manager = manager;
  }

  
  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if ( inElement ) {
      content.append(ch, start, length);
    }
  }

  @Override
  public void startElement(String uri, String localName, String qName,
          Attributes attributes) throws SAXException {
    if ( NwsAtomEntryParser.ELEMENT.equals(qName) ) {
      manager.pushHandler(new NwsAtomEntryParser(manager), 
                          new NwsAtomCallback() {
                            @Override @SuppressWarnings("unchecked")
                            public void end(Object result) {
                              List<Pair<String, ?>> entry = (List<Pair<String, ?>>)result;
                              document.add(new Pair<String, List<Pair<String, ?>>>("entry", entry));
                            }
                          });
      return;
    }
    inElement = true;
  }

  @Override
  public void endElement(String uri, String localName, String qName)
          throws SAXException {
    if ( DOCUMENT.equalsIgnoreCase(qName) ) {
      manager.popHandler(document);
      return;
    }
    document.add(new Pair<String, String>(qName, content.toString()));
    content.delete(0, content.length());
    inElement = false;
  }
}
