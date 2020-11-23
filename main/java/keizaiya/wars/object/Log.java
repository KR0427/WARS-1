package keizaiya.wars.object;

import java.sql.Time;

public class Log {
    private Time time;
    private String type;
    private String data;
    private String value;
    private Integer number;
    private String sender;

    public Log(Time time, String type, String data, String value, Integer number, String sender) {
        this.time = time;
        this.type = type;
        this.data = data;
        this.value = value;
        this.number = number;
        this.sender = sender;
    }
    public Log(Time time, String type, String data, String value, Integer number) {
        this.time = time;
        this.type = type;
        this.data = data;
        this.value = value;
        this.number = number;
        this.sender = "null";
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
