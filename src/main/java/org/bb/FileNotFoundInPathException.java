package org.bb;

public class FileNotFoundInPathException extends Exception{
    String message;
    public FileNotFoundInPathException(String message){
        this.message = message;
    }
}
