package de.uniks.pmws2122.chat.client.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.builder.reflect.Link;

import java.util.List;

public class GenModel implements ClassModelDecorator {
    class Chat {
        String currentNickname;

        @Link("chat")
        List<User> onlineUser;
        @Link("chat")
        List<ChatMessage> allMessages;
    }

    class User {
        String name;

        @Link("sender")
        List<ChatMessage> messages;

        @Link("onlineUser")
        Chat chat;
    }

    class ChatMessage {
        String from;
        String msg;

        @Link("allMessages")
        Chat chat;
        @Link("messages")
        User sender;
    }

    @Override
    public void decorate(ClassModelManager m) {
        m.haveNestedClasses(GenModel.class);
    }
}
