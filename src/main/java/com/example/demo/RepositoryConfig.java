//package com.example.demo;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
//import org.springframework.http.MediaType;
//
//@Configuration
//public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
//
//  @Override
//  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//    config.getProjectionConfiguration().addProjection(InlineRecordsDepartment.class);
//    
//    config.getProjectionConfiguration().addProjection(InlineRecordsEmployee.class);
//    
//    //
//    config.setDefaultMediaType(MediaType.APPLICATION_JSON);
//    //
//  }
//}
