package com.shra1.biznes.webservices;

class WebUrls {

    public static final String WS_CONTENT_TYPE_STR = "Content-Type";
    public static final String WS_CONTENT_TYPE_VALUE_JSON = "application/json";
    public static final String WS_SET_COOKIE_STR = "set-cookie";
    public static final String WS_COOKIE_STR = "Cookie";

    private static final String IP = "192.168.1.5";
    private static final String PORT = "8092";
    private static final String BASE_URL = "http://" + IP + ":" + PORT + "/sample-jaxrs/";

    public static final String REGISTER_USER = BASE_URL + "api/user/p/register-user";
    public static final String LOGIN = BASE_URL + "login";
}
