package com.higginsthomas.android.weatheralert.test.alertreader;

import static org.easymock.EasyMock.*;

import com.higginsthomas.android.weatheralert.alertreader.NwsAtomCallback;
import com.higginsthomas.android.weatheralert.alertreader.NwsAtomParserManager;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class NwsAtomParserManagerTest {
  NwsAtomParserManager sut;

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Before
  public void setUp() throws Exception {
    sut = new NwsAtomParserManager();
  }

  /**
   * Initially, there is no handler, so calls will fail.
   */
  @Test
  public final void testExceptionIfNotInitiated() throws Exception {
    exception.expect(Exception.class);
    sut.startDocument();
  }
  
  /**
   * Push a second handler and now calls will be delegated to new handler
   * and no longer to original.
   */
  @Test
  public final void testPushSecondHandler() throws Exception {
    DefaultHandler mock1 = createMock(DefaultHandler.class);
    replay(mock1);
    DefaultHandler mock2 = createMock(DefaultHandler.class);
    mock2.startDocument();
    replay(mock2);
    
    sut.pushHandler(mock1, null);
    sut.pushHandler(mock2, null);
    sut.startDocument();
    
    verify(mock2);
    verify(mock1);
  }

  /**
   * Popping a handler passes result back to original handler's callback
   */
  @Test
  public final void testPopHandlerReturnsResult() {
    String testResult = "our result";
    NwsAtomCallback mock = createMock(NwsAtomCallback.class);
    mock.end(testResult);
    replay(mock);
    sut.pushHandler(null, mock);
    
    sut.popHandler(testResult);
    
    verify(mock);
  }

  /**
   * Popping a handler restores original handler
   */
  @Test
  public final void testPopHandlerRestoresOriginalHandler() throws Exception {
    DefaultHandler mock1 = createMock(DefaultHandler.class);
    mock1.startDocument();
    replay(mock1);
    DefaultHandler mock2 = createMock(DefaultHandler.class);
    replay(mock2);
    String testResult = "our result";
    NwsAtomCallback mock3 = createMock(NwsAtomCallback.class);
    mock3.end(testResult);
    replay(mock3);
    sut.pushHandler(mock1, null);
    sut.pushHandler(mock2, mock3);
    
    sut.popHandler(testResult);
    sut.startDocument();
    
    verify(mock2);
    verify(mock1);
  }

  /**
   * Trying to continue after popping last handler will result in exception
   */
  @Test
  public final void testContinuingAfterPoppingLastHandlerThrowsException() throws Exception {
    String testResult = "our result";
    NwsAtomCallback mock = createMock(NwsAtomCallback.class);
    mock.end(testResult);
    replay(mock);
    sut.pushHandler(null, mock);
    
    sut.popHandler(testResult);
    exception.expect(Exception.class);
    sut.startDocument();
    
    verify(mock);
  }

  /**
   * In all of the following tests, we're verifying the manager properly delegates
   * to the active handler all supported SAX events. 
   */
  
  @Test
  public final void testStartDocument() throws Exception {
    DefaultHandler mock = createMock(DefaultHandler.class);
    mock.startDocument();
    replay(mock);
    
    sut.pushHandler(mock, null);
    sut.startDocument();
    
    verify(mock);
  }

  @Test
  public final void testEndDocument() throws Exception {
    DefaultHandler mock = createMock(DefaultHandler.class);
    mock.endDocument();
    replay(mock);
    
    sut.pushHandler(mock, null);
    sut.endDocument();
    
    verify(mock);
  }

  @Test
  public final void testCharacters() throws Exception {
    final char[] expectedArg0 = new char[1];
    final int expectedArg1 = 1;
    final int expectedArg2 = 2;
    DefaultHandler mock = createMock(DefaultHandler.class);
    mock.characters(expectedArg0, expectedArg1, expectedArg2);
    replay(mock);
    
    sut.pushHandler(mock, null);
    sut.characters(expectedArg0, expectedArg1, expectedArg2);
    
    verify(mock);
  }

  @Test
  public final void testEndElement() throws Exception {
    final String expectedArg0 = "0";
    final String expectedArg1 = "1";
    final String expectedArg2 = "2";
    DefaultHandler mock = createMock(DefaultHandler.class);
    mock.endElement(expectedArg0, expectedArg1, expectedArg2);
    replay(mock);
    
    sut.pushHandler(mock, null);
    sut.endElement(expectedArg0, expectedArg1, expectedArg2);
    
    verify(mock);
  }

  @Test
  public final void testStartElement() throws Exception {
    final String expectedArg0 = "0";
    final String expectedArg1 = "1";
    final String expectedArg2 = "2";
    final Attributes expectedArg3 = null;
    DefaultHandler mock = createMock(DefaultHandler.class);
    mock.startElement(expectedArg0, expectedArg1, expectedArg2, expectedArg3);
    replay(mock);
    
    sut.pushHandler(mock, null);
    sut.startElement(expectedArg0, expectedArg1, expectedArg2, expectedArg3);
    
    verify(mock);
  }
}
