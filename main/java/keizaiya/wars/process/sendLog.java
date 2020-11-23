package keizaiya.wars.process;

public class sendLog {
    public static void sendconsole(String type , String message){
        if(type != null){
            System.out.println("[" + type + "][" + System.currentTimeMillis() + "] " + message);
        }else {
            System.out.println("[ null ][" + System.currentTimeMillis() + "] " + message);
        }
    }
    
}
