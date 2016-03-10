package com.web.base.session;

/**
 * 会话不存在
 * 
 * @since 
 * @author
 */
public class SessionNotExistsException extends RuntimeException {
    public static enum Code {
        NOT_LOGIN, LOGIN_EXPIRED,NETWORK_ERROR;
    }

    private static final long serialVersionUID = 8191826124292493551L;

    private Code code;
    
    public SessionNotExistsException(Code code, String message) {
        super(message);
    }
    
    public Code getCode() {
        return code;
    }
}
