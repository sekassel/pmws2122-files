package de.uniks.pmws2122.icp.model;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import static de.uniks.pmws2122.icp.Constants.JSON_FROM;
import static de.uniks.pmws2122.icp.Constants.JSON_MSG;

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
            // Build one message
            this.buildAllChatMessage(jsonObject);
        }
    }

    public void buildAllChatMessage(JSONObject message) {
        // Create a new chat message and add it to the current chat session
        new ChatMessage().setFrom(message.getString(JSON_FROM)).setMsg(message.getString(JSON_MSG)).setChat(this.currentChatSession);
    }

    public void buildUsers(JSONArray users) {
        // TODO: Create for each string in the json array a user object with the string as name and add it to the current chat session
    }

    public void buildUser(String username) {
        // TODO: Create a new user and add it to the current chat session
    }

    public void removeUser(String username) {
        // TODO: Remove the user with the given username from the current chat session
    }
}
