package com.galaxy.bean;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Map;

public class HttpBean {

    private String pathInfo;
    private Cookie[] cookies;
    private Map<String, String> header;


    public HttpBean(String pathInfo, Cookie[] cookies, Map<String, String> header) {
        this.pathInfo = pathInfo;
        this.cookies = cookies;
        this.header = header;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }


    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "HttpBean{" +
                "pathInfo='" + pathInfo + '\'' +
                ", cookies=" + Arrays.toString(cookies) +
                ", header=" + header +
                '}';
    }
}
