package org.race.loko.config.security.registration;

import org.race.loko.config.security.filter.AdminFilter;
import org.race.loko.config.security.filter.EquipeFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfig {

    @Value("${url.equipe}")
    private String equipeURL;

    @Value("${url.admin}")
    private String adminURL;


    // Autorisation ADMIN
    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilterFilterRegistrationBean() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns(adminURL+"/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }


    // Autorisation EQUIPE
    @Bean
    public FilterRegistrationBean<EquipeFilter> equipeFilterFilterRegistrationBean() {
        FilterRegistrationBean<EquipeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new EquipeFilter());
        registrationBean.addUrlPatterns(equipeURL+"/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}

