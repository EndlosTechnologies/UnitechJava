package com.unitechApi.exception.ExceptionService;

public class TimeExtendException extends RuntimeException{
   public TimeExtendException(){
        super();
    }
    public TimeExtendException(String msg){
        super(msg);
    }
    public TimeExtendException(String msg,Throwable throwable){
        super(msg,throwable);
    }
}
