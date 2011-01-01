package com.higginsthomas.android.weatheralert.test.alertreader;

import com.higginsthomas.android.weatheralert.alertreader.NwsAtomCapAlertReader;

import java.util.List;

import android.test.AndroidTestCase;


public class NwsAtomCapAlertReaderTest extends AndroidTestCase {
  private NwsAtomCapAlertReader sut = null;
  
  public final void setUp() {
    sut = new NwsAtomCapAlertReader();
  }
  
  
  public final void testRead() {
    List<String> result = sut.read();
    assertNotNull(result);
    assertEquals(0, result.size());
  }
}
