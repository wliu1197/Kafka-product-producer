package com.kafka.ms.products.model.db;

import jakarta.persistence.*;

@Entity(name="created_product_event_message")
public class CreatedProductEventDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="message_id")
    private String messageId;
    @Column(name="event")
    private String event;
    public CreatedProductEventDetails() {
        super();
    }

    public CreatedProductEventDetails(String messageId, String event) {
        this.messageId = messageId;
        this.event = event;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
