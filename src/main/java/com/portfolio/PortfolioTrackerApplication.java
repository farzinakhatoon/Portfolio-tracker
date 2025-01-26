package com.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = {"https://example1.com", "https://example2.com","*"})
 
    
public class PortfolioTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioTrackerApplication.class, args);
    }
}
