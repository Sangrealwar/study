package study.concurrency.test.ThisEscape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author; weiqian
 * @Date: 2021/4/1 6:19 下午
 * @Version:
 */
public class EventSource {
    public List<EventListener> listeners = new ArrayList<>();

    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    public void clear() {
        listeners.clear();
        listeners = new ArrayList<>();
    }

    public long run() {
        long a = 0;
        if(listeners != null) {
            for (EventListener listener : listeners) {
                if (listener != null) {
                    a = listener.run();
                }
            }
        }
        return a;
    }
}
