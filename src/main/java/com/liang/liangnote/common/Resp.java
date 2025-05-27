package com.liang.liangnote.common;

import lombok.Data;

/**
 * 统一请求响应
 * @author liang
 * @version 1.0.0
 * @date 2025/5/27 16:07
 */
@Data
public class Resp<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> Resp<T> success(T data) {
        Resp<T> resp = new Resp<>();
        resp.setCode(200);
        resp.setMsg("success");
        resp.setData(data);
        return resp;
    }

    public static <T> Resp<T> error(String code, String msg) {
        Resp<T> resp = new Resp<>();
        resp.setCode(500);
        resp.setMsg(msg);
        return resp;
    }

    public static <T> Resp<T> failed(String msg) {
        Resp<T> resp = new Resp<>();
        resp.setCode(-1);
        resp.setMsg(msg);
        return resp;
    }
}
