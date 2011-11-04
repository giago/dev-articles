package com.devarticles.cms.shared;

public class ServerException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public ServerException() {
        
    }
    
    public ServerException(String message) {
        super(message);
    }

}
