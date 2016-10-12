import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class TimelineTest {

    Packet packet1 = parse("00:00:00.000000 0us IP 192.168.0.114.43114 > 10.9.8.7.https");

    @Test
    public void timeline_of_no_packets() {
        Timeline timeline = Timeline.of(Packets.of(() -> Collections.EMPTY_LIST.stream()));
        assertEquals(timeline.channels.size(),0);
    }

    @Test
    public void timeline_of_one_packet() {
        Timeline timeline = Timeline.of(Packets.of(() -> Collections.singleton(packet1).stream()));
        assertEquals(timeline.channels.size(),1);
        Channel channel = timeline.channels.get(0);
        assertEquals(channel.packets.size(),1);
        assertSame(channel.packets.get(0),packet1);
    }

    static Packet parse(String line) {
        return Parser.parse(line);
    }
}