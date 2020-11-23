package keizaiya.wars.object;

import com.google.gson.Gson;
import org.bukkit.Location;

import java.util.Map;


public class Facility {
    private Map<String, Object> location;
    private String name;
    private Integer type;

    public Facility(Location location , String name , Integer type){
        this.location = location.serialize();
        this.name = name;
        this.type = type;
    }

    public Map<String, Object> getLocation() {
        return location;
    }

    public void setLocation(Map<String, Object> location) {
        this.location = location;
    }
    public void setLocation(Location location){
        this.location = location.serialize();
    }

    public Location getLocationL(){
        return Location.deserialize(location);
    }

    public String getLocationS(){
        Location target = Location.deserialize(location);
        return "X: " + target.getBlockX() + " Y: " + target.getBlockY() + " Z: " + target.getBlockZ();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
