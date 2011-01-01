package com.higginsthomas.android.weatheralert;

import org.apache.abdera.Abdera;
import org.apache.abdera.protocol.client.*;
import org.apache.abdera.protocol.Response.ResponseType;
import org.apache.abdera.model.*;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class WeatherAlertReadCAP {
  static final String uri = "http://www.weather.gov/alerts-beta/us.php?x=1";
  final Abdera abdera;

  WeatherAlertReadCAP()  {
    try {
      abdera = new Abdera();
      System.out.println(abdera);
    } catch ( RuntimeException e ) {
      System.err.println(e);
      throw e;
    }
  }
  
  List<String> read() {
    List<String> result = new ArrayList<String>();
    
  	AbderaClient client = new AbderaClient(abdera);
    ClientResponse response = client.get(uri);
    if ( ResponseType.SUCCESS == response.getType() ) {
      Document<Feed> doc = response.getDocument();
      Feed feed = doc.getRoot();
      List<Entry> alerts = feed.getEntries();
      for ( Entry entry : alerts ) {
        StringWriter item = new StringWriter(4000);
        item.write(entry.toString());
        item.append("\n\n*****\n\n");
        result.add(item.toString());
      }
    }
    
    return result;
  }
  
  public void main(String argv[]) {
    System.out.println(read().toString());
  }
}
