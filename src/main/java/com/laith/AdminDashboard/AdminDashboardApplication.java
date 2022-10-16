package com.laith.AdminDashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AdminDashboardApplication extends SpringBootServletInitializer{

   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(AdminDashboardApplication.class);
   }
	   
	public static void main(String[] args) {
		SpringApplication.run(AdminDashboardApplication.class, args);
	}

}
