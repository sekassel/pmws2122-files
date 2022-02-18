package de.uniks.pmws2122.chat.client.model;

import java.beans.PropertyChangeSupport;
import java.util.Objects;

public class ChatMessage
{
   public static final String PROPERTY_FROM = "from";
   public static final String PROPERTY_MSG = "msg";
   public static final String PROPERTY_CHAT = "chat";
   public static final String PROPERTY_SENDER = "sender";
   private String from;
   private String msg;
   protected PropertyChangeSupport listeners;
   private Chat chat;
   private User sender;

   public String getFrom()
   {
      return this.from;
   }

   public ChatMessage setFrom(String value)
   {
      if (Objects.equals(value, this.from))
      {
         return this;
      }

      final String oldValue = this.from;
      this.from = value;
      this.firePropertyChange(PROPERTY_FROM, oldValue, value);
      return this;
   }

   public String getMsg()
   {
      return this.msg;
   }

   public ChatMessage setMsg(String value)
   {
      if (Objects.equals(value, this.msg))
      {
         return this;
      }

      final String oldValue = this.msg;
      this.msg = value;
      this.firePropertyChange(PROPERTY_MSG, oldValue, value);
      return this;
   }

   public Chat getChat()
   {
      return this.chat;
   }

   public ChatMessage setChat(Chat value)
   {
      if (this.chat == value)
      {
         return this;
      }

      final Chat oldValue = this.chat;
      if (this.chat != null)
      {
         this.chat = null;
         oldValue.withoutAllMessages(this);
      }
      this.chat = value;
      if (value != null)
      {
         value.withAllMessages(this);
      }
      this.firePropertyChange(PROPERTY_CHAT, oldValue, value);
      return this;
   }

   public User getSender()
   {
      return this.sender;
   }

   public ChatMessage setSender(User value)
   {
      if (this.sender == value)
      {
         return this;
      }

      final User oldValue = this.sender;
      if (this.sender != null)
      {
         this.sender = null;
         oldValue.withoutMessages(this);
      }
      this.sender = value;
      if (value != null)
      {
         value.withMessages(this);
      }
      this.firePropertyChange(PROPERTY_SENDER, oldValue, value);
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
      result.append(' ').append(this.getFrom());
      result.append(' ').append(this.getMsg());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.setChat(null);
      this.setSender(null);
   }
}
