package com.gabriel.messms;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@SpringBootApplication
public class MessageMSApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(MessageMSApplication.class, args);
	}
}
