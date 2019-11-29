package com.flowers.sample.project.flowersDemo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flowers.sample.project.flowersDemo.flower.Flower;
import com.flowers.sample.project.flowersDemo.flower.FlowerDaoService;
import com.flowers.sample.project.flowersDemo.flower.FlowerNotFoundException;

@RestController
public class FlowersController {

	@Autowired
	private FlowerDaoService service;

	@GetMapping(path = "/flowers")
	public List<Flower> retrieveFlowers() {
		return service.findAll();
	}

	@GetMapping("/flowers/{id}")
	public Flower retrieveFlower(@PathVariable int id) {
		Flower flower = service.retrieveFlowerById(id);

		if (flower == null)
			throw new FlowerNotFoundException("id-" + id);

		return flower;
	}

	@GetMapping(path = "/flowersCount")
	public Map retrieveFlowersCount() {
		return service.retrieveFlowersCount();
	}

	@GetMapping(path = "/uniqueFlowersCount")
	public Map retrieveUniqueFlowersCount() {
		return service.retrieveUniqueFlowersCount();
	}

	@GetMapping(path = "/uniqueFlowerIds")
	public Map retrieveUniqueFlowersIds() {
		return service.retrieveUniqueFlowersIds();
	}

	@PutMapping(path = "/flowers")
	public Flower updateFlowers(@RequestBody Flower flower) {
		Flower updatedFlower = service.updateFlowers(flower);
		return updatedFlower;

	}

}