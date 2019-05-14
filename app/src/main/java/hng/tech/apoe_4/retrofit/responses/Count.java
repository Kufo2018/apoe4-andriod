package hng.tech.apoe_4.retrofit.responses;

import com.google.gson.annotations.SerializedName;

public class Count {
    @SerializedName("count")
    private String count;

    public String getCount() {
        return count;
    }
}
