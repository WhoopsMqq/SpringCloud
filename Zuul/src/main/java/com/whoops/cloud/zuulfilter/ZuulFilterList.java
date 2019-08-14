package com.whoops.cloud.zuulfilter;

import org.springframework.context.annotation.Bean;

public class ZuulFilterList {
    @Bean
    public NameFilter nameFilter(){
        return new NameFilter();
    }
}
