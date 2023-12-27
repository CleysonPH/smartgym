package dev.cleysonph.smartgym.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExerciseNotFoundExceptionTest {

    @Test
    void whenInstantiateExerciseNotFoundException_thenItShouldHaveTheRightMessage() {
        var exception = new ExerciseNotFoundException();
        assertEquals("Exercise not found", exception.getMessage());
    }

    @Test
    void whenInstantiateExerciseNotFoundExceptionWithMessage_thenItShouldHaveTheRightMessage() {
        var exception = new ExerciseNotFoundException("Exercise not found");
        assertEquals("Exercise not found", exception.getMessage());
    }

}