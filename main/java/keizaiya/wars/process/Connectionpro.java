package keizaiya.wars.process;

import com.google.gson.Gson;
import keizaiya.wars.file.Yamlfile;
import keizaiya.wars.object.ConnectionIndo;
import keizaiya.wars.object.Country;
import keizaiya.wars.object.botdata;
import keizaiya.wars.object.serverinfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connectionpro {
    public static boolean stop = false;
    private static OutputStream out;
    private static InputStream in;
    public static Socket socket;
    public static boolean kidou = false;
    public static List<String> bot = new ArrayList<>();

    public static Thread startcom(){
        System.out.println("情報通信サーバーに接続を開始。" + CConfig.TCPconfig.getString("ServerAdd"));
        YamlConfiguration yml = CConfig.TCPconfig;
        bot = yml.getStringList("bot");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (stop == false) {
                    long Time =  System.currentTimeMillis();
                    try {
                        Socket socket = new Socket(yml.getString("ServerAdd"),yml.getInt("Serverpoat"));
                        in = socket.getInputStream();
                        out = socket.getOutputStream();
                        sendserver("server&close@");
                        Gson gson = new Gson();
                        while (true) {
                            String data = "";
                            while (true) {
                                int c = in.read();
                                String string = String.valueOf((char) c);
                                if (string.equalsIgnoreCase("@")) {
                                    break;
                                }
                                if (System.currentTimeMillis() - Time >= 10000) {
                                    data = "";
                                }
                                data = data + string;
                                Time = System.currentTimeMillis();
                            }
                            if(data != null) {
                                System.out.println("[WARScommunication][Recive]: " +data);
                                String[] yeah = data.split("&");
                                if(yeah.length >= 1){
                                    if(yeah[0].equalsIgnoreCase("server")){
                                        if(yeah.length == 2){
                                            if(yeah[1].equalsIgnoreCase("account.request")){
                                                System.out.println("yeah");
                                                sendserver("{name: '"+ yml.getString("account") +"' , pass: '"+ yml.getString("pass") +"'}@");
                                            }else if(yeah[1].equalsIgnoreCase("pass clear")){
                                                System.out.println("サーバーとの接続しました。");
                                                kidou = true;
                                            }else if(yeah[1].equalsIgnoreCase("close")){
                                                System.out.println("サーバーへの認証が失敗しました。");
                                                kidou = false;
                                            }else if(yeah[1].equalsIgnoreCase("used account")){
                                                System.out.println("アカウントが使われてます。");
                                                kidou = false;
                                                close();
                                            }
                                        }
                                    }else if(bot.contains(yeah[0])){
                                        botdata datas = gson.fromJson(yeah[1],botdata.class);
                                        System.out.println(datas);
                                        if(datas != null){
                                            System.out.println(datas.getRequest());
                                            if(datas.getRequest().equalsIgnoreCase("serverinfo")){
                                                Map<String,String> data1 = new HashMap<>();
                                                data1.put("TPS",String.valueOf(Simpletab.getNtps()));
                                                data1.put("Time", String.valueOf(System.currentTimeMillis()));
                                                data1.put("Member",gson.toJson(Simpletab.getMember()));
                                                ConnectionIndo info = new ConnectionIndo(datas.getid(),"serverinfo",data1);
                                                sendserver(getBotsend(gson.toJson(info),yeah[0]));
                                            }else if(datas.getRequest().equalsIgnoreCase("whitelist")){
                                                Map<String,String> data1 = new HashMap<>();
                                                for(OfflinePlayer player : Bukkit.getWhitelistedPlayers()){
                                                    System.out.println(player);
                                                    data1.put(player.getName(),gson.toJson(player.serialize()));
                                                }
                                                ConnectionIndo info = new ConnectionIndo(datas.getid(),"whitelist",data1);
                                                sendserver(getBotsend(gson.toJson(info),yeah[0]));
                                            }
                                             String[] yeahs = datas.getRequest().split(":");
                                            if(yeahs.length >= 1){
                                                if(yeahs[0].equals("whitelist_add")){
                                                    if(yeahs.length == 2){
                                                        OfflinePlayer player = Bukkit.getOfflinePlayer(yeahs[1]);
                                                        Map<String,String> data1 = new HashMap<>();
                                                        if(player != null){
                                                            player.setWhitelisted(true);
                                                            data1.put("result","clear");
                                                        }else {
                                                            data1.put("result","damedesu-");
                                                        }
                                                        ConnectionIndo info = new ConnectionIndo(datas.getid(),"whitelist_add",data1);
                                                        sendserver(getBotsend(gson.toJson(info),yeah[0]));
                                                    }
                                                }else if(yeahs[0].equalsIgnoreCase("getCountry")){
                                                    Country country = Countrypro.getCountry(yeahs[1]);
                                                    Map<String,String> data1 = new HashMap<>();
                                                    if(country != null){
                                                        data1.put("country",gson.toJson(country));
                                                    }else {
                                                        data1.put("country","null");
                                                    }
                                                    ConnectionIndo info = new ConnectionIndo(datas.getid(),"getCountry",data1);
                                                    sendserver(getBotsend(gson.toJson(info),yeah[0]));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("停止。");
                        kidou = false;
                        try {
                            socket.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        });
        return thread;
    }

    public static void sendserver(String data){
        if(out != null){
            try {
                out.write(data.getBytes("UTF-8"));
                System.out.println("[WARScommunication][Send]: " + data);
            } catch (IOException e) {
                System.out.println("送信に失敗しました。");
                e.printStackTrace();
            }
        }
    }

    public static void close(){
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static String getBotsend(String data , String User){
        if(User.equalsIgnoreCase("ALL")){
            String str = "";
            for(String use : bot){
                str = str + use + "&" + data + "@";
            }
            return str;
        }else {
            String str = User + "&" + data + "@";
            return str;
        }
    }

    public static int checkTPS(Float data){
        if(data <= 15){
            return 1;
        }else if(data <= 10){
            return 2;
        }
        return 0;
    }
}
