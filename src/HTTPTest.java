import org.junit.Test;

import static org.junit.Assert.*;

public class HTTPTest {

    String sample1 = "There will length 194: HTTP";
    String sample2 = "be a *RANDOM* length 1460: HTTP";
    String sample3 = "number of tokens before length 237: HTTP: HTTP/1.1 200 OK";
    String sample4 = "the one that we length 229: HTTP: GET /generate_204/?unused=1475603635753 HTTP/1.1";
    String sample5 = "use to find the length 386: HTTP: POST /devhand/devicehandler.ashx HTTP/1.1";
    String sample6 = "length 105: HTTP: HTTP/1.1 204 No Content";

    @Test
    public void length() {
        assertLength(sample1,194);
        assertLength(sample2,1460);
        assertLength(sample3,237);
        assertLength(sample4,229);
        assertLength(sample5,386);
        assertLength(sample6,105);
    }

    @Test
    public void verb() {
        assertVerb(sample1,null);
        assertVerb(sample2,null);
        assertVerb(sample3,null);
        assertVerb(sample4,"GET");
        assertVerb(sample5,"POST");
        assertVerb(sample6,null);
    }

    void assertLength(String sample, int length) {
        assertEquals(length, parse(sample).length);
    }

    void assertVerb(String sample, String verb) {
        assertEquals(verb, parse(sample).verb);
    }

    HTTP parse(String sample) {
        return HTTP.parse(sample.split(" "));
    }
}