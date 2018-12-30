package gui;

public class RecordException extends Exception {
    RecordException(){
        super("Failed to load the record.");
    }
}
