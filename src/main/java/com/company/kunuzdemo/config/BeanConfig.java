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

        // Configure ModelMapper to retain old values when the source value is null
        modelMapper.getConfiguration().setPropertyCondition(
                new NotNullPropertyCondition()
        );

        return modelMapper;
    }

    private static class NotNullPropertyCondition implements Condition<Object, Object> {
        @Override
        public boolean applies(MappingContext<Object, Object> context) {
            return context.getSource() != null;
        }
    }

}