package com.tass.productservice.model;


import com.tass.productservice.database.entities.Category;
import lombok.Data;

@Data
public class BaseResponse {
    private int code;
    private String message;

    private Object data;

    public BaseResponse(){
        this.code = 1;
        this.message = "SUCCESS";
    }

    public BaseResponse(ERROR error){
        this.code = error.code;
        this.message = error.message;
    }

    public BaseResponse(int code, String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public BaseResponse(Category category){
        this.code = 1;
        this.message = "SUCCESS";
        this.data = category;

    }


    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
