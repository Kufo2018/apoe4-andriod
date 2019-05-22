package hng.tech.apoe_4.retrofit.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hng.tech.apoe_4.models.Answer;
import hng.tech.apoe_4.models.CategoryData;

public class AnswerResponse {

    @SerializedName("Answer")
    @Expose
    private Answer answer;
    @SerializedName("categoryData")
    @Expose
    private CategoryData categoryData;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private Integer code;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public CategoryData getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(CategoryData categoryData) {
        this.categoryData = categoryData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}


