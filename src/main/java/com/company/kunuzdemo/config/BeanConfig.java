package com.company.kunuzdemo.config;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configure ModelMapper to retain old values when the source value is not null or not blank
        modelMapper.getConfiguration().setPropertyCondition(
                new NotNullAndNotBlankPropertyCondition()
        );

        return modelMapper;
    }

    private static class NotNullAndNotBlankPropertyCondition implements Condition<Object, Object> {
        @Override
        public boolean applies(MappingContext<Object, Object> context) {
            Object source = context.getSource();
            if (source == null) {
                return false;
            }
            if (source instanceof String) {
                // Check if the source string is blank (empty or contains only whitespace)
                return !((String) source).trim().isEmpty();
            }
            return true;
        }
    }
}