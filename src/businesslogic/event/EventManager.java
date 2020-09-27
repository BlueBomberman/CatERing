package businesslogic.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventManager {
    public ObservableList<EventInfo> getEventInfo() {
        return FXCollections.unmodifiableObservableList(EventInfo.loadAllEventInfo());
    }

    public ObservableList<EventInfo> getMyEvent(int sid) {
        return FXCollections.unmodifiableObservableList(EventInfo.loadMyEvent(sid));
    }
}
