package com.chary.bhaumik;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MurracoJwtApplication
{
	public static void main(String[] args) 
	{
		SpringApplication.run(MurracoJwtApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() 
	{
	 return new ModelMapper();
	}
}
