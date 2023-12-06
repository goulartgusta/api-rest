package br.com.anhembi.apiresttests.services.exceptions;

public class DataIntegratyViolationException extends RuntimeException{

    public DataIntegratyViolationException(String message) {

        super(message);
    }
}
