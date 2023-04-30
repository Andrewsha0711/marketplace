package com.andrewsha.marketplace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.andrewsha.marketplace.user.User;
import com.andrewsha.marketplace.user.resource.UserDTOBuilder;
import com.andrewsha.marketplace.utils.DTOBuilder;

@Configuration
public class AppBean {
    
    @Bean
    public DTOBuilder<User> userBuilder(){
        return new UserDTOBuilder();
    }
}
