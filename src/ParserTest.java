import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.time.*;

import static org.junit.Assert.*;

public class ParserTest  {

    String line1 = "07:21:41.535679 91423200us tsft short preamble 6.0 Mb/s 5200 MHz 11a -70dB signal -99dB noise antenna 1 BSSID:d8:bb:bb:68:ad:bb (oui Unknown) DA:Broadcast SA:5a:5a:5a:5a:ad:be (oui Unknown) Beacon (acmevisitor) [6.0* 9.0 12.0* 18.0 24.0* 36.0 48.0 54.0 Mbit] ESS[|802.11]";
    String line2 = "11:37:22.811107 92698955us tsft short preamble 24.0 Mb/s 5200 MHz 11a -74dB signal -99dB noise antenna 1 BSSID:d8:bb:bb:69:ad:bb (oui Unknown) SA:5a:5a:5a:5a:fe:fa (oui Unknown) DA:da:da:da:da:ad:bc (oui Unknown)";
    String line3 = "08:58:14.793335 33575772us tsft short preamble 24.0 Mb/s 5240 MHz 11a -65dB signal -99dB noise antenna 1 RA:4a:4a:4a:4a:e4:4d (oui Unknown) Clear-To-Send";
    String line4 = "08:58:14.793409 33575885us tsft short preamble 24.0 Mb/s 5240 MHz 11a -65dB signal -99dB noise antenna 1 RA:4a:4a:4a:4a:e4:4d (oui Unknown) BA";
    String line5 = "08:58:14.795504 33577940us tsft short preamble 24.0 Mb/s 5240 MHz 11a -76dB signal -99dB noise antenna 1 (H) Unknown Ctrl SubtypeUnknown Ctrl Subtype";
    String line6 = "08:58:14.792914 33575259us tsft -65dB signal -99dB noise antenna 1 5240 MHz 11a ht/20 [bit 20] CF +QoS DA:da:da:da:da:e4:4d (oui Unknown) BSSID:bb:bb:bb:bb:d8:7b (oui Unknown) SA:5a:5a:5a:5a:1f:c4 (oui Unknown) LLC, dsap SNAP (0xaa) Individual, ssap SNAP (0xaa) Command, ctrl 0x03: oui Ethernet (0x000000), ethertype IPv4 (0x0800): 17.248.133.169.https > 192.168.14.113.58076: Flags [P.], seq 0:699, ack 1, win 832, options [nop,nop,TS val 828748516 ecr 798386358], length 699";
    String line7 = "08:58:14.782486 33564956us tsft short preamble 24.0 Mb/s 5240 MHz 11a -75dB signal -99dB noise antenna 1 RA:4a:4a:4a:4a:d8:7b (oui Unknown) TA:2a:2a:2a:2a:e4:4d (oui Unknown) Request-To-Send\n";

    @Test
    public void parse_returns_a_packet() {
        assert(parse(line1) instanceof Packet);
    }

    Packet parse(String line) {
        return Parser.parse(line);
    }

    @Test
    public void localTime() {
        assertEquals(LocalTime.of(07,21,41,535679000),parse(line1).localTime);
        assertEquals(LocalTime.of(11,37,22,811107000),parse(line2).localTime);
    }

    @Test
    public void BSSID() {
        assertBSSID(line1, "d8:bb:bb:68:ad:bb");
        assertBSSID(line2, "d8:bb:bb:69:ad:bb");
        assertBSSID(line3, null);
        assertBSSID(line4, null);
        assertBSSID(line5, null);
        assertBSSID(line6, "bb:bb:bb:bb:d8:7b");
        assertBSSID(line7, null);
    }

    @Test
    public void SA() {
        assertSA(line1, "5a:5a:5a:5a:ad:be");
        assertSA(line2, "5a:5a:5a:5a:fe:fa");
        assertSA(line3, null);
        assertSA(line4, null);
        assertSA(line5, null);
        assertSA(line6, "5a:5a:5a:5a:1f:c4");
        assertSA(line7, null);
    }

    @Test
    public void DA() {
        assertDA(line1, "Broadcast");
        assertDA(line2, "da:da:da:da:ad:bc");
        assertDA(line3, null);
        assertDA(line4, null);
        assertDA(line5, null);
        assertDA(line6, "da:da:da:da:e4:4d");
        assertDA(line7, null);
    }

    @Test
    public void RA() {
        assertRA(line1, null);
        assertRA(line2, null);
        assertRA(line3, "4a:4a:4a:4a:e4:4d");
        assertRA(line4, "4a:4a:4a:4a:e4:4d");
        assertRA(line5, null);
        assertRA(line6, null);
        assertRA(line7, "4a:4a:4a:4a:d8:7b");
    }

    @Test
    public void TA() {
        assertTA(line1, null);
        assertTA(line2, null);
        assertTA(line3, null);
        assertTA(line4, null);
        assertTA(line5, null);
        assertTA(line6, null);
        assertTA(line7, "2a:2a:2a:2a:e4:4d");
    }

    void assertBSSID(String line, String mac) {
        assertMac(parse(line).BSSID, mac(mac));
    }

    void assertSA(String line, String mac) {
        assertMac(parse(line).SA, mac(mac));
    }

    void assertDA(String line, String mac) {
        assertMac(parse(line).DA, mac(mac));
    }

    void assertRA(String line, String mac) {
        assertMac(parse(line).RA, mac(mac));
    }

    void assertTA(String line, String mac) {
        assertMac(parse(line).TA, mac(mac));
    }

    Mac mac(String mac) {
        return mac == null ? null : Mac.of(mac);
    }

    void assertMac(Mac actual, Mac expected) {
        assertEquals(expected,actual);
    }

    @Test
    public void empty_stream_has_no_packets() {
        Stream stream = Parser.parse(stream());
        assertEquals(stream.count(),0);
    }

    @Test
    public void stream_with_1_line_has_a_packet() {
        Stream stream = Parser.parse(stream(line1));
        assertEquals(stream.count(),1);
    }

    InputStream stream(String... lines) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(bytes);
        for (String line : lines) {
            writer.write(line + System.lineSeparator());
        }
        writer.flush();
        return new ByteArrayInputStream(bytes.toByteArray());
    }
}
