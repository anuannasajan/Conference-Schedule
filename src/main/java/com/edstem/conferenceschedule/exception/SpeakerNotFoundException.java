package com.edstem.conferenceschedule.exception;

public class SpeakerNotFoundException extends RuntimeException{
    public SpeakerNotFoundException(String message){
        super(message);
    }
}

