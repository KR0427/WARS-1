package keizaiya.wars.object;

import java.util.Map;

public class ConnectionIndo {
    private Integer id;
    private String type;
    private Map<String,String> data;

    public ConnectionIndo(Integer id, String type, Map<String, String> data) {
        this.id = id;
        this.type = type;
        this.data = data;
    }
}
