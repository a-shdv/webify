package com.shadaev.webify.chat;

import com.github.rjeschke.txtmark.Processor;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.UserService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;


@Route("")
@Push
public class ChatView extends VerticalLayout {
    private final ChatService chatService;
    private final UserService userService;
    private Registration registration;
    private Grid<ChatService.ChatMessage> grid;
    private VerticalLayout chat;
    private VerticalLayout login;
    private User user;

    @Autowired
    public ChatView(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        user = (User) authentication.getPrincipal();

        buildLogin(userService.findUserByUsername(user.getUsername()));
        buildChat();
    }

    private void buildLogin(User user) {
        User userFromDb = userService.findUserByUsername(user.getUsername());
        login = new VerticalLayout() {{
            TextField field = new TextField();
            field.setPlaceholder("Пожалуйста, представьтесь");
            add(
                    new H3("Вы уверены, что хотите войти в чат?"),
                    new HorizontalLayout(
                            new Button("Выйти") {{
                                addThemeVariants(ButtonVariant.LUMO_ERROR);
                                addClickListener(click -> UI.getCurrent().getPage().setLocation("/home"));
                            }},
                            new Button("Войти") {{
                                addClickListener(click -> {
                                    login.setVisible(false);
                                    chat.setVisible(true);
                                    field.setValue(userFromDb.getUsername());
                                    chatService.addRecordJoined(user);
                                });
                                addClickShortcut(Key.ENTER);
                            }}
                    )
            );
        }};
        add(login);
    }

    private void buildChat() {
        chat = new VerticalLayout();
        add(chat);
        chat.setVisible(false);

        grid = new Grid<>();
        grid.setItems(chatService.getMessages());
        grid.addColumn(new ComponentRenderer<>(message -> new Html(renderRow(message))))
                .setAutoWidth(true);

        TextField field = new TextField();

        chat.add(
                new H3("Чат"),
                grid,
                new HorizontalLayout() {{
                    add(
                            field,
                            new Button("Отправить") {{
                                addClickListener(click -> {
                                    if (!field.getValue().isEmpty()) {
                                        chatService.addRecord(user, field.getValue());
                                    } else {
                                        Notification notification = new Notification("Пожалуйста, введите сообщение.", 3000);
                                        notification.open();
                                    }
                                    field.clear();
                                });
                                addClickShortcut(Key.ENTER);
                            }}
                    );
                }},
                new Button("Выйти") {{
                    addThemeVariants(ButtonVariant.LUMO_ERROR);
                    addClickListener(click -> UI.getCurrent().getPage().setLocation("/home"));
                }}
        );
    }

    public void onMessage(ChatService.ChatEvent event) {
        if (getUI().isPresent()) {
            UI ui = getUI().get();
            ui.getSession().lock();
            ui.beforeClientResponse(grid, ctx -> grid.scrollToEnd());
            ui.access(() -> grid.getDataProvider().refreshAll());
            ui.getSession().unlock();
        }
    }

    private String renderRow(ChatService.ChatMessage message) {
        if (message.getUsername().isEmpty()) {
            return Processor.process(String.format("Пользователь **%s** только что присоединился!", message.getMessage()));
        } else {
            return Processor.process(String.format("**%s**: %s", message.getUsername(), message.getMessage()));
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        registration = chatService.attachListener(this::onMessage);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
    }
}
