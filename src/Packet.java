import java.time.LocalTime;
import java.util.*;

final class Packet {

    final LocalTime localTime;
    final Mac BSSID;
    final Mac DA;
    final Mac RA;
    final Mac SA;
    final Mac TA;
    final String signal;
    final String noise;
    final Microseconds offset;
    final Microseconds duration;
    final Integer length;
    final RadioTap radioTap;
    final HTTP http;

    private Packet(Builder builder) {
        localTime = builder.localTime;
        BSSID     = builder.BSSID;
        DA        = builder.DA;
        RA        = builder.RA;
        SA        = builder.SA;
        TA        = builder.TA;
        signal    = builder.signal;
        noise     = builder.noise;
        offset    = builder.offset;
        duration  = builder.duration;
        length    = builder.length;
        radioTap  = builder.radioTap;
        http      = builder.http;
    }

    static class Builder {
        LocalTime localTime;
        Mac BSSID;
        Mac DA;
        Mac RA;
        Mac SA;
        Mac TA;
        String signal;
        String noise;
        Microseconds offset;
        Microseconds duration;
        RadioTap radioTap;
        Integer length;
        HTTP http;

        Packet build() {
            return new Packet(this);
        }
    }

    static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        Map map = new TreeMap();
        if (localTime != null) { map.put("localTime", localTime.toString());}
        if (BSSID != null)     { map.put("BSSID",BSSID); }
        if (SA != null)        { map.put("SA",SA); }
        if (TA != null)        { map.put("TA",TA); }
        if (RA != null)        { map.put("RA",RA); }
        if (DA != null)        { map.put("DA",DA); }
        if (length != null)    { map.put("length",length); }
        if (duration != null)  { map.put("duration",duration); }
        if (signal != null)    { map.put("signal",signal); }
        if (noise != null)     { map.put("noise",noise); }
        if (offset != null)    { map.put("offset",offset); }
        return "Packet:" + map;
    }
}
