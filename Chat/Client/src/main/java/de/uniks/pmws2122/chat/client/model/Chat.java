package de.uniks.pmws2122.chat.client.model;

import java.beans.PropertyChangeSupport;
import java.util.*;

public class Chat
{
   public static final String PROPERTY_CURRENT_NICKNAME = "currentNickname";
   public static final String PROPERTY_ALL_MESSAGES = "allMessages";
   public static final String PROPERTY_ONLINE_USER = "onlineUser";
   private String currentNickname;
   private List<ChatMessage> allMessages;
   private List<User> onlineUser;
   protected PropertyChangeSupport listeners;

   public String getCurrentNickname()
   {
      return this.currentNickname;
   }

   public Chat setCurrentNickname(String value)
   {
      if (Objects.equals(value, this.currentNickname))
      {
         return this;
      }

      final String oldValue = this.currentNickname;
      this.currentNickname = value;
      this.firePropertyChange(PROPERTY_CURRENT_NICKNAME, oldValue, value);
      return this;
   }

   public List<ChatMessage> getAllMessages()
   {
      return this.allMessages != null ? Collections.unmodifiableList(this.allMessages) : Collections.emptyList();
   }

   public Chat withAllMessages(ChatMessage value)
   {
      if (this.allMessages == null)
      {
         this.allMessages = new ArrayList<>();
      }
      if (!this.allMessages.contains(value))
      {
         this.allMessages.add(value);
         value.setChat(this);
         this.firePropertyChange(PROPERTY_ALL_MESSAGES, null, value);
      }
      return this;
   }

   public Chat withAllMessages(ChatMessage... value)
   {
      for (final ChatMessage item : value)
      {
         this.withAllMessages(item);
      }
      return this;
   }

   public Chat withAllMessages(Collection<? extends ChatMessage> value)
   {
      for (final ChatMessage item : value)
      {
         this.withAllMessages(item);
      }
      return this;
   }

   public Chat withoutAllMessages(ChatMessage value)
   {
      if (this.allMessages != null && this.allMessages.remove(value))
      {
         value.setChat(null);
         this.firePropertyChange(PROPERTY_ALL_MESSAGES, value, null);
      }
      return this;
   }

   public Chat withoutAllMessages(ChatMessage... value)
   {
      for (final ChatMessage item : value)
      {
         this.withoutAllMessages(item);
      }
      return this;
   }

   public Chat withoutAllMessages(Collection<? extends ChatMessage> value)
   {
      for (final ChatMessage item : value)
      {
         this.withoutAllMessages(item);
      }
      return this;
   }

   public List<User> getOnlineUser()
   {
      return this.onlineUser != null ? Collections.unmodifiableList(this.onlineUser) : Collections.emptyList();
   }

   public Chat withOnlineUser(User value)
   {
      if (this.onlineUser == null)
      {
         this.onlineUser = new ArrayList<>();
      }
      if (!this.onlineUser.contains(value))
      {
         this.onlineUser.add(value);
         value.setChat(this);
         this.firePropertyChange(PROPERTY_ONLINE_USER, null, value);
      }
      return this;
   }

   public Chat withOnlineUser(User... value)
   {
      for (final User item : value)
      {
         this.withOnlineUser(item);
      }
      return this;
   }

   public Chat withOnlineUser(Collection<? extends User> value)
   {
      for (final User item : value)
      {
         this.withOnlineUser(item);
      }
      return this;
   }

   public Chat withoutOnlineUser(User value)
   {
      if (this.onlineUser != null && this.onlineUser.remove(value))
      {
         value.setChat(null);
         this.firePropertyChange(PROPERTY_ONLINE_USER, value, null);
      }
      return this;
   }

   public Chat withoutOnlineUser(User... value)
   {
      for (final User item : value)
      {
         this.withoutOnlineUser(item);
      }
      return this;
   }

   public Chat withoutOnlineUser(Collection<? extends User> value)
   {
      for (final User item : value)
      {
         this.withoutOnlineUser(item);
      }
      return this;
   }

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (this.listeners != null)
      {
         this.listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public PropertyChangeSupport listeners()
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      return this.listeners;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(' ').append(this.getCurrentNickname());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutAllMessages(new ArrayList<>(this.getAllMessages()));
      this.withoutOnlineUser(new ArrayList<>(this.getOnlineUser()));
   }
}
