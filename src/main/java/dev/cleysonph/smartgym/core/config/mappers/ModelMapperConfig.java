package dev.cleysonph.smartgym.core.config.mappers;

import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.cleysonph.smartgym.api.v1.exercises.dtos.ExerciseResponse;
import dev.cleysonph.smartgym.core.models.Exercise;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Exercise.class, ExerciseResponse.class)
            .addMappings(mapper -> mapper.using(stringToListSplitSemicolon())
                .map(Exercise::getInstructions, ExerciseResponse::setInstructions)
            );

        return modelMapper;
    }

    private Converter<String, List<String>> stringToListSplitSemicolon() {
        return ctx -> ctx.getSource() == null ? null : List.of(ctx.getSource().split(";"));
    }
    
}
