package com.mysql.qi_fu.librarymanage.util;

/**
 * Created by qi_fu on 2017/4/13.
 */

public class Config {

    /**
     * 默认超时时间
     */
    public static final int HTTP_TIMEOUT = 60 * 1000;
    /**
     * 加密密钥
     */
    public static final String APP_KEY = "library1314source";

    /**
     *
     数组分隔符
     */
    public static final char CHARKEY = 7;

    /*
 * 服务端地址
 */
//    public static final String SERVER_URL = "http://120.76.78.6:4071";
    public static final String SERVER_URL = "http://192.31.81:8086";

    public static final String VERSION_CHECK = " /json/getVersion";
    /*
    * 请求参数编码
     */
    public static final String HTTP_DEFAULT_PARAMS_ENCODING = "UTF-8";

    //是否为测试
    public static boolean ISTEST = true;

}
