package keizaiya.wars.object;


import keizaiya.wars.WARS;
import org.bukkit.Chunk;

import java.util.HashMap;
import java.util.Map;

public class Province {
    private Integer X;
    private Integer Z;
    private String owner;
    private String name;
    private Map<Integer,String> Facility;

    public Province(Chunk chunk , String tag){
        Integer X = 0;
        Integer Z = 0;
        Integer data = WARS.config.getInt("chanksize", 8);
        if(chunk.getX() >= 0) {
            X = (chunk.getX() + WARS.config.getInt("chankX", 0)) / data;
        }else {
            X = (chunk.getX() + WARS.config.getInt("chankX", 0) + 1) / data -1;
        }
        if(chunk.getZ() >= 0) {
            Z = (chunk.getZ() + WARS.config.getInt("chankZ", 0)) / data;
        }else{
            Z = (chunk.getZ() + WARS.config.getInt("chankZ", 0) + 1) / data -1;
        }
        this.X = X;
        this.Z = Z;
        this.owner = tag;
        Facility = new HashMap<>();
        name = "null";
    }
    public Province(Integer X , Integer Z , String tag){
        this.X = X;
        this.Z = Z;
        this.owner = tag;
        Facility = new HashMap<>();
    }

    public Integer getX() {
        return X;
    }

    public Integer getZ() {
        return Z;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String gettag(){
        String data = X + "," + Z;
        return data;
    }

    public Map<Integer, String> getFacility() {
        return Facility;
    }

    public void setFacility(Map<Integer, String> facility) {
        Facility = facility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
