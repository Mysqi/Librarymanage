package com.mysql.qi_fu.librarymanage.base;

import java.io.Serializable;

/**
 * Created by qi_fu on 2017/4/13.
 */

public class BaseProtocolBean extends BaseBean implements Serializable {

    /**
     * 服务器的返回代码
     */
    public int code;

    /**
     * 服务器的返回消息
     */
    public String msg;
}
