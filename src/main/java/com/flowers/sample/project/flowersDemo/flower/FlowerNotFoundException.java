package com.flowers.sample.project.flowersDemo.flower;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FlowerNotFoundException extends RuntimeException {
	public FlowerNotFoundException(String message) {
		super(message);
	}
} 