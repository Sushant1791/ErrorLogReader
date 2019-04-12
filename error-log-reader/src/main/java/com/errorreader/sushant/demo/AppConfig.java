//package com.errorreader.sushant.demo;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ResourceBundleMessageSource;
//
//@Configuration
//@ComponentScan(basePackages = { "com.errorreader.sushant.demo" })
//public class AppConfig {
//
//  @Bean(name = "messageSource")
//  public ResourceBundleMessageSource getMessageSource() {
//      ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//      messageSource.setBasename("config/messages");
//      messageSource.setDefaultEncoding("UTF-8");
//      messageSource.setUseCodeAsDefaultMessage(true);
//      return messageSource;
//  }
//}