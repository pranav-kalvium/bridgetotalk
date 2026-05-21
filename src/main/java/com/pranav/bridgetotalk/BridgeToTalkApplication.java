package com.pranav.bridgetotalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("com.pranav.bridgetotalk")
@SpringBootApplication
public class BridgeToTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BridgeToTalkApplication.class, args);
	}

}
