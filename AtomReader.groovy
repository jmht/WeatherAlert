@Grab("org.apache.abdera:abdera-client:1.1.1")
import org.apache.abdera.Abdera
import org.apache.abdera.protocol.client.*
import org.apache.abdera.protocol.Response.ResponseType
import org.apache.abdera.model.*
Abdera abdera = new Abdera()
AbderaClient client = new AbderaClient(abdera)
def uri = "http://www.weather.gov/alerts-beta/us.php?x=1"
def reader = new BufferedReader(new InputStreamReader(new URL(uri).openStream()))
ClientResponse response = client.get(uri)
if ( ResponseType.SUCCESS == response.getType() ) {
 Document<Feed> doc = response.getDocument()
 def feed = doc.getRoot()
 List<Entry> alerts = feed.getEntries()
 alerts.each {
        println it
        println ""
        println "*****"
        println ""
 }
} else {
 println "*** Unable to obtain feed"
}