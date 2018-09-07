package com.syscloud.provider.auth.exception;

public class JwtSignatureException extends Exception {
    public JwtSignatureException(String s) {
        super(s);
    }
}
