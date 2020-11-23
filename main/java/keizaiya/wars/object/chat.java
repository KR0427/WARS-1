package keizaiya.wars.object;

import org.bukkit.entity.Player;

import java.sql.Time;

public class chat {
    private String name;
    private String uuid;
    private String message;
    private Time time;
    private Integer number;

    public void chat(Player player , String message , Integer number , Time time){
        name = player.getName();
        uuid = player.getUniqueId().toString();
        this.message = message;
        this.number = number;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
