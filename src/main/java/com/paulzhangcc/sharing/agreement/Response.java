package com.paulzhangcc.sharing.agreement;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <B>模块名称：</B>平台公共模块<BR>
 * <B>中文类名：</B>用请求响应对象<BR>
 * <B>概要说明：</B><BR>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    //响应头
    private ResponseHeader header;

    //响应体
    private T body;

    public Response(){
        header = new ResponseHeader();
    }

    public static <T> Response<T> success(T body){
        Response response = new Response();
        response.getHeader().setCode(RetCode.SUCCESS.getCode());
        response.setBody(body);
        return response;
    }

    public static Response error(String message){
        Response response = new Response();
        response.getHeader().setCode(RetCode.INTERNALEXCEP.getCode());
        response.getHeader().setMessage(message);
        return response;
    }

    public static Response exception(Throwable e){
        Response response = new Response();
        response.getHeader().setCode(RetCode.INTERNALEXCEP.getCode());
        response.getHeader().setMessage(e.getMessage());
        return response;
    }

    public static <T> Response<T> response(String code, String message, T body){
        Response response = new Response();
        response.getHeader().setCode(code);
        response.getHeader().setMessage(message);
        response.setBody(body);
        return response;
    }

    public static <T> Response<T> response(String code, String message){
        Response response = new Response();
        response.getHeader().setCode(code);
        response.getHeader().setMessage(message);
//        response.setBody(body);
        return response;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public static void main(String[] args){
        System.out.println(Response.error("wer").getHeader().getMessage());
    }

}
