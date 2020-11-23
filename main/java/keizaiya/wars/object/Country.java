package keizaiya.wars.object;


import keizaiya.wars.command.Countrycommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.util.*;

public class Country {
    private String name;
    private String nickname;
    private String head;
    private String tag;
    private Integer act; //現在のアクト
    private Integer Cumact; //累計アクト数
    private Integer Effective; //実行権
    private List<String> member;
    private List<String> reader;
    private List<String> actPa;
    private Double X;
    private Double Y;
    private Double Z;
    private String world;
    private Boolean end;
    private List<String> request;
    private List<String> province;
    private Map<Integer,String> Facility;

    public Country(String name , String nickname , String head , String tag ){
        this.name = name;
        this.nickname = nickname;
        this.head = head;
        this.tag = tag;
        act = 0;
        Cumact = 0;
        Effective = 1;
        member = new ArrayList<>();
        member.add(head);
        reader = new ArrayList<>();
        request = new ArrayList<>();
        X = 0d;
        Y = 0d;
        Z = 0d;
        world = Bukkit.getWorlds().get(0).getName();
        province = new ArrayList<>();
        end = false;
        actPa = new ArrayList<>();
        Facility = new HashMap<>();
    }
    public Boolean getEnd() {
        return end;
    }
    public Integer getAct() {
        return act;
    }
    public List<String> getMember() {
        return member;
    }
    public Location getLocation() {
        Location location = new Location(Bukkit.getWorld(world),X,Y,Z);
        return location;
    }
    public List<String> getReader() {
        return reader;
    }
    public String getHead() {
        return head;
    }
    public String getName() {
        return name;
    }
    public String getNickname() {
        return nickname;
    }
    public String getTag() {
        return tag;
    }
    public List<String> getRequest(){return request;}
    public void setAct(Integer act) {
        this.act = act;
    }
    public void setEnd(Boolean end) {
        this.end = end;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public void setMember(List<String> member) {
        this.member = member;
    }
    public Boolean addUser(String user){
        if(member.contains(user) == false){
            member.add(user);
            return true;
        }
        return false;
    }
    public Boolean removeUser(String user){
        if(member.contains(user)){
            member.remove(user);
            return true;
        }
        return false;
    }
    public Boolean checkUser(String user){
        return member.contains(user);
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public void setReader(List<String> reader) {
        this.reader = reader;
    }
    public void setRequest(List<String> data){ this.request = data;}
    public Boolean addreader(String user){
        if(reader.contains(user) == false){
            reader.add(user);
            return true;
        }
        return false;
    }
    public Boolean removereader(String user){
        if(reader.contains(user)){
            reader.remove(user);
            return true;
        }
        return false;
    }
    public Boolean checkreader(String user){
        return reader.contains(user);
    }
    public void setLocation(Location location) {
        this.world = location.getWorld().getName();
        this.X = location.getX();
        this.Y = location.getY();
        this.Z = location.getZ();
    }

    public String getvalue(){
        String message = "name: " + name + ",nickname: " + nickname + ",head: " + head + ",tag: " + tag
                + ",act: " + act + ",member: " + getmembetoString() + ",reader: " + getreadertoString() + ",location: " + getLocationtoString() + ",end: " + end.toString();
        return message;
    }
    public String getmembetoString(){
        String members = "";
        for(String data : member){
            members = members + data + ",";
        }
        return members;
    }
    public String getreadertoString(){
        String members = "";
        for(String data : reader){
            members = members + data + ",";
        }
        return members;
    }
    public String getLocationtoString(){
        String message = X  + "," + Y  + "," + Z  + ","  + world;
        return message;
    }
    public Boolean checkRequest(String Uuid){
        return request.contains(Uuid);
    }
    public void addRequest(String Uuid){
        request.add(Uuid);
    }
    public Boolean removeRequest(String Uuid){
        if(checkRequest(Uuid)){
            request.remove(Uuid);
            return true;
        }else {
            return false;
        }
    }
    public List<String> getProvince() {
        return province;
    }
    public void setProvince(List<String> province) {
        this.province = province;
    }

    public Integer getCumact() {
        return Cumact;
    }

    public void setCumact(Integer cumact) {
        Cumact = cumact;
    }

    public Integer getEffective() {
        return Effective;
    }

    public void setEffective(Integer effective) {
        Effective = effective;
    }

    public List<String> getActPa() {
        return actPa;
    }

    public Boolean addActPa(String user){
        if(actPa.contains(user) == false){
            actPa.add(user);
            return true;
        }
        return false;
    }
    public Boolean removeActPa(String user){
        if(actPa.contains(user)){
            actPa.remove(user);
            return true;
        }
        return false;
    }

    public void setActPa(List<String> actPa) {
        this.actPa = actPa;
    }

    public Map<Integer, String> getFacility() {
        return Facility;
    }

    public void setFacility(Map<Integer, String> facility) {
        Facility = facility;
    }
}
