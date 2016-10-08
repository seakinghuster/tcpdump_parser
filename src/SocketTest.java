import org.junit.Test;

import static org.junit.Assert.*;

public class SocketTest {

    String sample1 = "17.248.133.169.https";
    String sample2 = "23.44.7.39.80";
    String sample3 = "132.245.72.114.https";
    String sample4 = "192.168.14.113.58076";
    String sample5 = "192.168.14.112.61028";
    String sample6 = "10.33.44.33.43114:";

    @Test
    public void toString_includes_host_and_port() {
        assertToString(sample1,"17.248.133.169:https");
        assertToString(sample2,"23.44.7.39:80");
        assertToString(sample3,"132.245.72.114:https");
        assertToString(sample4,"192.168.14.113:58076");
        assertToString(sample5,"192.168.14.112:61028");
        assertToString(sample6,"10.33.44.33:43114");
    }

    @Test
    public void equal() {
        assertEqual(sample1);
        assertEqual(sample2);
        assertEqual(sample3);
        assertEqual(sample4);
        assertEqual(sample5);
        assertEqual(sample6);
    }

    @Test
    public void not_equal() {
        String[] samples = new String[]{sample1,sample2,sample3,sample4,sample5,sample6};
        for (String a : samples) {
            for (String b : samples) {
                if (a!=b) {
                    assertNotEqual(a,b);
                }
            }
        }
    }

    void assertNotEqual(String samplea, String sampleb) {
        Socket a = parse(samplea);
        Socket b = parse(sampleb);
        assertNotEquals(a,b);
        assertNotEquals(a.hashCode(),b.hashCode());
    }

    void assertEqual(String sample) {
        Socket a = parse(sample);
        Socket b = parse(sample);
        assertEquals(a,b);
        assertEquals(a.hashCode(),b.hashCode());
    }

    @Test
    public void parse_returns_socket_when_sample_is_valid() {
        assertNotNull(parse(sample1));
        assertNotNull(parse(sample2));
        assertNotNull(parse(sample3));
        assertNotNull(parse(sample4));
        assertNotNull(parse(sample5));
        assertNotNull(parse(sample6));
    }

    @Test
    public void parse_returns_null_when_sample_is_NOT_valid() {
        assertNull(parse(""));
        assertNull(parse("this is"));
        assertNull(parse("not.the.key.we.are.looking:for"));
        assertNull(parse("IP"));
        assertNull(parse("IPv4"));
    }

    @Test
    public void host() {
        assertHost(sample1,"17.248.133.169");
        assertHost(sample2,"23.44.7.39");
        assertHost(sample3,"132.245.72.114");
        assertHost(sample4,"192.168.14.113");
        assertHost(sample5,"192.168.14.112");
        assertHost(sample6,"10.33.44.33");
    }

    @Test
    public void port() {
        assertPort(sample1,"https");
        assertPort(sample2,"80");
        assertPort(sample3,"https");
        assertPort(sample4,"58076");
        assertPort(sample5,"61028");
        assertPort(sample6,"43114");
    }

    void assertHost(String sample, String host) {
        assertEquals(host,parse(sample).host);
    }

    void assertPort(String sample, String port) {
        assertEquals(port, parse(sample).port);
    }

    void assertToString(String sample, String string) {
        assertEquals(string, parse(sample).toString());
    }

    Socket parse(String sample) {
        return Socket.parse(sample);
    }

}
