package com.rabobank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/*
 * @author Anitha C
 */

@SpringBootApplication
@ComponentScan("com.rabobank")
public class RaboBankStmtApp extends SpringBootServletInitializer{

	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RaboBankStmtApp.class);
    }
	
	 public static void main(String[] args) {
	        SpringApplication.run(RaboBankStmtApp.class, args);
	    }

}





