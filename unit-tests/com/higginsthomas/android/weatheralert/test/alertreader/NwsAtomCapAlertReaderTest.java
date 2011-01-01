package com.higginsthomas.android.weatheralert.test.alertreader;

import static org.junit.Assert.*;

import com.higginsthomas.android.weatheralert.alertreader.NwsAtomCapAlertReader;

import org.junit.*;

import java.util.List;


public class NwsAtomCapAlertReaderTest {
  private NwsAtomCapAlertReader sut = null;
  
  @Before
  public final void setUp() {
    sut = new NwsAtomCapAlertReader();
  }
  
  
  @Test
  public final void testRead() {
    List<String> result = sut.read();
    assertNotNull(result);
    assertEquals(0, result.size());
  }
}
