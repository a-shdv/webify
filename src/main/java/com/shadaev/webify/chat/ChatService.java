package com.shadaev.webify.chat;

import com.github.rjeschke.txtmark.Processor;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.UserService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@RequiredArgsConstructor
public class ChatService {
    @Getter
    private final Queue<ChatMessage> messages = new ConcurrentLinkedQueue<>();
    private final ComponentEventBus eventBus = new ComponentEventBus(new Div());
    private final UserService userService;

    @Getter
    @AllArgsConstructor
    public static class ChatMessage {
        private String username;
        private String message;
    }

    public static class ChatEvent extends ComponentEvent<Div> {
        public ChatEvent() {
            super(new Div(), false);
        }
    }

    public void addRecord(User user, String message) {
        User userFromDb= userService.findUserByUsername(user.getUsername());
        messages.add(new ChatMessage(userFromDb.getUsername(), message));
        eventBus.fireEvent(new ChatEvent());
    }

    public void addRecordJoined(User user) {
        User userFromDb= userService.findUserByUsername(user.getUsername());
        messages.add(new ChatMessage("", userFromDb.getUsername()));
        eventBus.fireEvent(new ChatEvent());
    }

    public Registration attachListener(ComponentEventListener<ChatEvent> messageListener) {
        return eventBus.addListener(ChatEvent.class, messageListener);
    }

    public int size() {
        return messages.size();
    }
}