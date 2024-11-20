package com.hxb.cookfood.entry.pojo;

import lombok.Data;

@Data
public class R {
    private int code;
    private String msg;
    private Object data;

    public static R ok(Object data){
    	R r = new R();
    	r.setCode(200);
        r.setMsg("success");
    	r.setData(data);
    	return r;
    }

    public static R error(){
    	R r = new R();
    	r.setCode(500);
    	return r;
    }

    public static R error(String msg){
    	R r = new R();
    	r.setCode(500);
    	r.setMsg(msg);
    	return r;
    }

    public static R error(int code,String msg){
    	R r = new R();
    	r.setCode(code);
    	r.setMsg(msg);
    	return r;
    }

    public static R error(int code,String msg,Object data){
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
