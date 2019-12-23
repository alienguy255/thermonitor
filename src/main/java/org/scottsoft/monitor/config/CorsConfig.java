package org.scottsoft.monitor.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    //@Value("${api.cors.origin}")
    //private String apiAllowedOrigin;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // if the configuration value has a space, it is a list of origins.
        // In this case, we'll split and add each.  Otherwise, only a single item will be added.
        //Arrays.stream(apiAllowedOrigin.split(" ")).forEach(config::addAllowedOrigin);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("https://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
