package org.vanbart;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON DTO for Hipchat notification.
 * @since 01/07/2017.
 */
public class MessageDto {

    private String message;

    private String from;

    @JsonProperty("message_format")
    private String messageFormat = "text";

    private String color = "random";

    private String notify = "true";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }
}
