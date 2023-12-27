package dev.cleysonph.smartgym.core.exceptions;

public class ExerciseNotFoundException extends ModelNotFoundException {

    public ExerciseNotFoundException() {
        super("Exercise not found");
    }
    
    public ExerciseNotFoundException(String message) {
        super(message);
    }
    
}
