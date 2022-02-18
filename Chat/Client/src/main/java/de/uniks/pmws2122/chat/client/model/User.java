package de.uniks.pmws2122.chat.client.model;

import java.beans.PropertyChangeSupport;
import java.util.*;

public class User
{
   public static final String PROPERTY_NAME = "name";
   public static final String PROPERTY_MESSAGES = "messages";
   public static final String PROPERTY_CHAT = "chat";
   private String name;
   private List<ChatMessage> messages;
   private Chat chat;
   protected PropertyChangeSupport listeners;

   public String getName()
   {
      return this.name;
   }

   public User setName(String value)
   {
      if (Objects.equals(value, this.name))
      {
         return this;
      }

      final String oldValue = this.name;
      this.name = value;
      this.firePropertyChange(PROPERTY_NAME, oldValue, value);
      return this;
   }

   public List<ChatMessage> getMessages()
   {
      return this.messages != null ? Collections.unmodifiableList(this.messages) : Collections.emptyList();
   }

   public User withMessages(ChatMessage value)
   {
      if (this.messages == null)
      {
         this.messages = new ArrayList<>();
      }
      if (!this.messages.contains(value))
      {
         this.messages.add(value);
         value.setSender(this);
         this.firePropertyChange(PROPERTY_MESSAGES, null, value);
      }
      return this;
   }

   public User withMessages(ChatMessage... value)
   {
      for (final ChatMessage item : value)
      {
         this.withMessages(item);
      }
      return this;
   }

   public User withMessages(Collection<? extends ChatMessage> value)
   {
      for (final ChatMessage item : value)
      {
         this.withMessages(item);
      }
      return this;
   }

   public User withoutMessages(ChatMessage value)
   {
      if (this.messages != null && this.messages.remove(value))
      {
         value.setSender(null);
         this.firePropertyChange(PROPERTY_MESSAGES, value, null);
      }
      return this;
   }

   public User withoutMessages(ChatMessage... value)
   {
      for (final ChatMessage item : value)
      {
         this.withoutMessages(item);
      }
      return this;
   }

   public User withoutMessages(Collection<? extends ChatMessage> value)
   {
      for (final ChatMessage item : value)
      {
         this.withoutMessages(item);
      }
      return this;
   }

   public Chat getChat()
   {
      return this.chat;
   }

   public User setChat(Chat value)
   {
      if (this.chat == value)
      {
         return this;
      }

      final Chat oldValue = this.chat;
      if (this.chat != null)
      {
         this.chat = null;
         oldValue.withoutOnlineUser(this);
      }
      this.chat = value;
      if (value != null)
      {
         value.withOnlineUser(this);
      }
      this.firePropertyChange(PROPERTY_CHAT, oldValue, value);
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
      result.append(' ').append(this.getName());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutMessages(new ArrayList<>(this.getMessages()));
      this.setChat(null);
   }
}
