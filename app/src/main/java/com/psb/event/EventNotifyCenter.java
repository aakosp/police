package com.psb.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Handler;

/**
 * @Description 消息通知中心
 * @date 2014-7-12
 */
public class EventNotifyCenter {

    private static EventNotifyCenter instance = null;
    private static Map<Integer, List<Handler>> eventHandlers = new HashMap<>();

    public static EventNotifyCenter getInstance() {
        if (null == instance) {
            instance = new EventNotifyCenter();
        }
        return instance;
    }

    private EventNotifyCenter() {
    }

    /**
     * 清空所有注册的事件通知
     */
    public void clear() {
        eventHandlers.clear();
    }

    /**
     * 通知
     */
    public void doNotify(Integer event) {
        if (0 == eventHandlers.size()) {
            return;
        }
        List<Handler> eventHandlerList = eventHandlers.get(event);
        if (null == eventHandlerList) {
            return;
        }
        for (Handler handler : eventHandlerList) {
            handler.sendEmptyMessage(event);
        }
    }

    /**
     * 注册一个事件消息通知
     */
    public void register(Handler handler, Integer event) {
        if(!eventHandlers.containsKey(event)){
            List<Handler> handlers = new ArrayList<>();
            eventHandlers.put(event, handlers);
        }
        List<Handler> handlers = eventHandlers.get(event);
        if(!handlers.contains(handler)){
            handlers.add(handler);
        }
    }

    /**
     * 注册一组事件消息通知
     */
    public void register(Handler handler, Integer... events) {
        for(int event : events){
            if(!eventHandlers.containsKey(event)){
                List<Handler> handlers = new ArrayList<>();
                eventHandlers.put(event, handlers);
            }
            List<Handler> handlers = eventHandlers.get(event);
            if(!handlers.contains(handler)){
                handlers.add(handler);
            }
        }
    }

    /**
     * 反注册一个事件消息通知
     */
    public void unregister(Integer event, Handler handler) {
        if(eventHandlers.containsKey(event)){
            List<Handler> handlers = eventHandlers.get(event);
            handlers.remove(handler);
            if(handlers.size() == 0){
                eventHandlers.remove(event);
            }
        }
    }

}
