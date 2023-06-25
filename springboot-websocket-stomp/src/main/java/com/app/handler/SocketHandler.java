package com.app.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * SocketHandler
 *
 * @author ch
 * @date 2023/6/25 14:50
 */
@Component
public class SocketHandler extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("handleTextMessage start");
        String msg = message.getPayload();
        logger.info("msg = " + msg);
        // 给所有用户群发消息
        sendMessagesToUsers(message);
        // 给指定用户群发消息
        //sendMessageToUser(userId, msg);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connected ... " + session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        sessions.remove(session);
        logger.info(String.format("Session %s closed because of %s", session.getId(), status.getReason()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        logger.error("error occured at sender " + session, throwable);
    }

    /**
     * 给所有的用户发送消息
     */
    public void sendMessagesToUsers(TextMessage message) {
        for (WebSocketSession user : sessions) {
            try {
                // isOpen()在线就发送
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息给指定的用户
     */
    private void sendMessageToUser(WebSocketSession user, TextMessage message) {
        try {
            // 在线就发送
            if (user.isOpen()) {
                user.sendMessage(message);
            }
        } catch (IOException e) {
            logger.error("发送消息给指定的用户出错", e);
        }
    }
}
