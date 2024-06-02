package org.race.loko.config.security.registration;

import org.race.loko.config.security.filter.AdminFilter;
import org.race.loko.config.security.filter.EquipeFilter;
import org.race.loko.config.security.filter.UserFilter;
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

    @Value("${url.user}")
    private String userURL;



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


    // Autorisation EQUIPE et ADMIN
    @Bean
    public FilterRegistrationBean<UserFilter> userFilterFilterRegistrationBean() {
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter());
        registrationBean.addUrlPatterns(userURL+"/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}

