import java.time.LocalTime;

final class Packet {

    final LocalTime localTime;
    final Mac BSSID;
    final Mac DA;
    final Mac RA;
    final Mac SA;

    private Packet(Builder builder) {
        localTime = builder.localTime;
        BSSID     = builder.BSSID;
        DA        = builder.DA;
        RA        = builder.RA;
        SA        = builder.SA;
    }

    static class Builder {
        LocalTime localTime;
        Mac BSSID;
        Mac DA;
        Mac RA;
        Mac SA;

        Packet build() {
            return new Packet(this);
        }
    }

    static Builder builder() {
        return new Builder();
    }
}
