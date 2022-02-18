package de.uniks.pmws2122.chat.client.model;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import static de.uniks.pmws2122.chat.client.Constants.JSON_FROM;
import static de.uniks.pmws2122.chat.client.Constants.JSON_MSG;

public class ModelService {
    private Chat currentChatSession;

    public Chat getCurrentChatSession() {
        return currentChatSession;
    }

    public void buildChat(String nickname) {
        // Create a new chat with the nickname
        this.currentChatSession = new Chat().setCurrentNickname(nickname);
    }

    public void buildAllChatMessages(JSONArray messages) {
        // Create for each json object in the json array a chat message object and at it to the current chat session
        for (Object o : messages) {
            JSONObject jsonObject = (JSONObject) o;
            // Use the new buildChatMessage function
            this.buildAllChatMessage(jsonObject);
        }
    }

    public void buildAllChatMessage(JSONObject message) {
        // Create a new chat message and add it to the current chat session
        new ChatMessage().setFrom(message.getString(JSON_FROM)).setMsg(message.getString(JSON_MSG)).setChat(this.currentChatSession);
    }

    public void buildUsers(JSONArray users) {
        // Create for each string in the json array a user object with the string as name and add it to the current chat session
        for(Object o : users) {
            String username = (String) o;
            // Use the new buildUser function
            this.buildUser(username);
        }
    }

    public void buildUser(String username) {
        // Create a new user and add it to the current chat session
        new User().setName(username).setChat(this.currentChatSession);
    }

    public void removeUser(String username) {
        // Remove the user with the given username from the current chat session
        User toRemove = this.currentChatSession.getOnlineUser().stream()
                .filter((u) -> u.getName().equals(username))
                .findFirst()
                .orElse(null);
        this.currentChatSession.withoutOnlineUser(toRemove);
    }
}
