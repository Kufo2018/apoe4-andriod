package hng.tech.apoe_4.retrofit.responses;

import com.google.gson.annotations.SerializedName;

public class Notif {
    @SerializedName("message")
    private String notification;
    @SerializedName("notif_type")
    private String type;
    @SerializedName("web_url")
    private String weburl;

    public Notif(String notification, String type, String weburl) {
        this.notification = notification;
        this.type = type;
        this.weburl = weburl;
    }

    public String getNotification() {
        return notification;
    }

    public String getType() {
        return type;
    }

    public String getWeburl() {
        return weburl;
    }
}
