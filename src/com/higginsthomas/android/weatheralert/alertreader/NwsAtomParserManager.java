package com.higginsthomas.android.weatheralert.alertreader;

import com.higginsthomas.android.weatheralert.util.Pair;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;


public class NwsAtomParserManager extends DefaultHandler {
  private final ArrayList<Pair<DefaultHandler, NwsAtomCallback>> stack 
      = new ArrayList<Pair<DefaultHandler, NwsAtomCallback>>();
  private int index = -1;

  
  /**
   * Push new handler on the stack, recording the callback of the original
   * 
   * @param handler
   * @param callback
   */
  public void pushHandler(DefaultHandler handler, NwsAtomCallback callback) {
    stack.add(++index, new Pair<DefaultHandler, NwsAtomCallback>(handler, callback));
  }
  
  /**
   * Pop the handler stack, passing through the result to the prior handler
   * 
   * @param result
   */
  public void popHandler(Object result) {
    stack.get(index).y.end(result);       // initiate callback, passing through result
    stack.remove(index--);                // pop stack
  }

  /* All the following methods will be delegated to the active handler (at the top
   * of the stack).
   */
  
  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    stack.get(index).x.characters(ch, start, length);
  }

  @Override
  public void endDocument() throws SAXException {
    stack.get(index).x.endDocument();
  }

  @Override
  public void endElement(String uri, String localName, String qName)
          throws SAXException {
    stack.get(index).x.endElement(uri, localName, qName);
  }

  @Override
  public void startDocument() throws SAXException {
    stack.get(index).x.startDocument();
  }

  @Override
  public void startElement(String uri, String localName, String qName,
          Attributes attributes) throws SAXException {
    stack.get(index).x.startElement(uri, localName, qName, attributes);
  }
}
