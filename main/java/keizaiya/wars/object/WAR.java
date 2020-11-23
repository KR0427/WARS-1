package keizaiya.wars.object;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class WAR {
    private String Attaker;
    private String Defense;
    private Map<String, Object> DFA;
    private Map<String, Object> DFB;
    private String AHead;
    private String DHead;
    private String Province;

    public WAR(String attaker, String defense, String province) {
        Attaker = attaker;
        Defense = defense;
        this.DFA = new HashMap<>();
        this.DFB = new HashMap<>();
        this.AHead = "";
        this.DHead = "";
        Province = province;
    }

    public String getAttaker() {
        return Attaker;
    }

    public void setAttaker(String attaker) {
        Attaker = attaker;
    }

    public String getDefense() {
        return Defense;
    }

    public void setDefense(String defense) {
        Defense = defense;
    }


    public Location getDFAL(){
        try {
            Location location = Location.deserialize(DFA);
            return location;
        }catch (Exception e){
            return null;
        }
    }

    public void setDFA(Location location){
        this.DFA = location.serialize();
    }

    public Location getDFBL(){
        try {
            Location location = Location.deserialize(DFB);
            return location;
        }catch (Exception e){
            return null;
        }
    }

    public void setDFB(Location location){
        this.DFB = location.serialize();
    }

    public Map<String, Object> getDFA() {
        return DFA;
    }

    public void setDFA(Map<String, Object> DFA) {
        this.DFA = DFA;
    }

    public Map<String, Object> getDFB() {
        return DFB;
    }

    public void setDFB(Map<String, Object> DFB) {
        this.DFB = DFB;
    }


    public String getAHead() {
        return AHead;
    }

    public void setAHead(String AHead) {
        this.AHead = AHead;
    }

    public String getDHead() {
        return DHead;
    }

    public void setDHead(String DHead) {
        this.DHead = DHead;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }
}
