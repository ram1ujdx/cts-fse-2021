package com.cts.microservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.microservices.service.KafkaProducerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaProducerController {

	@Autowired
	KafkaProducerService producerService;
	
	@GetMapping("/send/{message}")
	public void sendMessage(@PathVariable String message) {
		log.info("Tring to Send a Message..");
		this.producerService.sendMessage(message);
	}
	
	
	
	
}
