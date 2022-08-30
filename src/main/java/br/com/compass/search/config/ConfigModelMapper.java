package br.com.compass.search.config;

import org.modelmapper.AbstractCondition;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigModelMapper {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Condition<?, ?> isStringBlank = new AbstractCondition<Object, Object>() {
            @Override
            public boolean applies(MappingContext<Object, Object> context) {
                if(context.getSource() instanceof String) {
                    return null!=context.getSource() && !"".equals(context.getSource());
                } else {
                    return context.getSource() != null;
                }
            }
        };

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setPropertyCondition(isStringBlank);
        return modelMapper;
    }
}
