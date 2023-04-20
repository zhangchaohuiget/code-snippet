package com.app.server;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/webSocket/{userId}")
@Component
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static AtomicInteger onlineNum = new AtomicInteger();

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
     */
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 发送消息
     */
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 给指定用户发送信息
     */
    public void sendInfo(String userId, String message) {
        Session session = sessionPools.get(userId);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     */
    public void broadcast(String message) {
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 建立连接成功调用
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        sessionPools.put(userId, session);
        addOnlineCount();
        System.out.println(userId + "加入webSocket！当前人数为" + onlineNum);
    }

    /**
     * 关闭连接时调用
     */
    @OnClose
    public void onClose(@PathParam(value = "userId") String userId) {
        sessionPools.remove(userId);
        subOnlineCount();
        System.out.println(userId + "断开webSocket连接！当前人数为" + onlineNum);
    }

    /**
     * 收到客户端信息
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("server get" + message);
    }

    /**
     * 错误时调用
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    public static AtomicInteger getOnlineNumber() {
        return onlineNum;
    }

    public static ConcurrentHashMap<String, Session> getSessionPools() {
        return sessionPools;
    }
}
