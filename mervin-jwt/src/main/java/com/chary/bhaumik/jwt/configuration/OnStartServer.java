package com.chary.bhaumik.jwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.chary.bhaumik.jwt.service.PersonService;

@Component
public class OnStartServer implements ApplicationListener<ContextRefreshedEvent>
{
	
	@Autowired
	PersonService personService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) 
	{
		//personService.insertData();
		
	}

}
