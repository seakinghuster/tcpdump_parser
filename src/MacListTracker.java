import java.util.List;
import java.util.stream.Collectors;

final class MacListTracker implements MacTracker {

    final List<SingleMacTracker> trackers;

    private MacListTracker(List<Mac> macs, MacTracker.Listener listener) {
        trackers = macs.stream().map(m -> SingleMacTracker.of(m,listener)).collect(Collectors.toList());
    }

    static MacListTracker of(List<Mac> macs, MacTracker.Listener listener) {
        return new MacListTracker(macs,listener);
    }

    @Override
    public void accept(Packet packet) {
        for (SingleMacTracker tracker : trackers) {
            tracker.accept(packet);
        }
    }
}