package com.flowers.sample.project.flowersDemo.flower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FlowerDaoService {

	@Value("${flowersRestPath}")
	private String flowersRestPath;

	private static List<Flower> flowers = new ArrayList<Flower>();

	public List<Flower> findAll() {

		if (flowers.isEmpty()) {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<List<Flower>> response = restTemplate.exchange(flowersRestPath, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Flower>>() {
					});
			flowers = response.getBody();
		}
		return flowers;

	}

	public Map retrieveFlowersCount() {
		Map count = new HashMap<String, Integer>();
		count.put("flowersCount", flowers.size());
		return count;
	}

	public Map retrieveUniqueFlowersCount() {
		Map uniqueCount = new HashMap<String, Integer>();
		Set uniqueIds = new HashSet<Integer>();
		for (Flower flower : flowers) {
			uniqueIds.add(flower.getUserId());
		}
		uniqueCount.put("uniqueFlowersCount", uniqueIds.size());
		return uniqueCount;
	}

	public Map retrieveUniqueFlowersIds() {
		Set uniqueIds = new HashSet<Integer>();
		for (Flower flower : flowers) {
			uniqueIds.add(flower.getUserId());
		}
		Map uniqueUserIds = new HashMap<String, Set>();
		uniqueUserIds.put("uniqueUserIds", uniqueIds);

		return uniqueUserIds;
	}

	public Flower updateFlowers(Flower flower) {
		// TODO Auto-generated method stub

		for (Flower flowersList : flowers) {
			if (flowersList.getId() == flower.getId()) {
				flowersList.setBody(flower.getBody());
				flowersList.setTitle(flower.getTitle());
				return flower;
			}
		}
		return null;

	}

	public Flower retrieveFlowerById(int id) {
		for (Flower flower : flowers) {
			if (flower.getId() == id) {
				return flower;
			}
		}
		return null;
	}
}
