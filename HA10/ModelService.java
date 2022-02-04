package de.uniks.pmws2122.icp.model;

import kong.unirest.json.JSONArray;

public class ModelService {
    private Chat currentChatSession;

    public Chat getCurrentChatSession() {
        return currentChatSession;
    }

    public void buildChat(String nickname) {
        // TODO: Create a new chat with the nickname
    }

    public void buildAllChatMessages(JSONArray messages) {
        // TODO: Create for each json object in the json array a chat message object and at it to the current chat session
    }

    public void clearAllChatMessages() {
        // TODO: Remove all chat messages from the current chat session
        // TODO: Hint: Look at the removeYou() Method from the Chat
    }
}
