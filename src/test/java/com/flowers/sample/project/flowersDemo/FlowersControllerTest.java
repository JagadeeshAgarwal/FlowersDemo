package com.flowers.sample.project.flowersDemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.flowers.sample.project.flowersDemo.flower.Flower;
import com.flowers.sample.project.flowersDemo.flower.FlowerDaoService;


@RunWith(SpringRunner.class)
@WebMvcTest
public class FlowersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FlowerDaoService flowerDaoService;

	@Test
	public void retrieveFlowersTest() throws Exception {

		List flowersList = new ArrayList<Flower>();
		Flower f1 = new Flower("1", 1, "rose", "rose");
		Flower f2 = new Flower("2", 2, "lilly", "lilly");
		flowersList.add(f1);
		flowersList.add(f2);

		Mockito.when(flowerDaoService.findAll()).thenReturn(flowersList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/flowers").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"userId\":\"1\",\"id\":1,\"title\":\"rose\",\"body\":\"rose\"},{\"userId\":\"2\",\"id\":2,\"title\":\"lilly\",\"body\":\"lilly\"}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void retrieveFlowerTest() throws Exception {

		Flower f1 = new Flower("1", 1, "rose", "rose");
		Mockito.when(flowerDaoService.retrieveFlowerById(Mockito.anyInt())).thenReturn(f1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/flowers/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"userId\":\"1\",\"id\":1,\"title\":\"rose\",\"body\":\"rose\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void retrieveFlowersCountTest() throws Exception {

		List flowersList = new ArrayList<Flower>();
		Flower f1 = new Flower("1", 1, "rose", "rose");
		Flower f2 = new Flower("2", 2, "lilly", "lilly");
		flowersList.add(f1);
		flowersList.add(f2);

		Map count = new HashMap<String, Integer>();
		count.put("flowersCount", flowersList.size());

		Mockito.when(flowerDaoService.retrieveFlowersCount()).thenReturn(count);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/flowersCount").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{\"flowersCount\":2}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void retrieveUniqueFlowersCountTest() throws Exception {

		List<Flower> flowersList = new ArrayList<Flower>();
		Flower f1 = new Flower("1", 1, "rose", "rose");
		Flower f2 = new Flower("2", 2, "lilly", "lilly");
		flowersList.add(f1);
		flowersList.add(f2);

		Map uniqueCount = new HashMap<String, Integer>();
		Set uniqueIds = new HashSet<Integer>();
		for (Flower flower : flowersList) {
			uniqueIds.add(flower.getUserId());
		}
		uniqueCount.put("uniqueFlowersCount", uniqueIds.size());
		Mockito.when(flowerDaoService.retrieveUniqueFlowersCount()).thenReturn(uniqueCount);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uniqueFlowersCount")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"uniqueFlowersCount\":2}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void retrieveUniqueFlowersIdsTest() throws Exception {

		List<Flower> flowersList = new ArrayList<Flower>();
		Flower f1 = new Flower("1", 1, "rose", "rose");
		Flower f2 = new Flower("2", 2, "lilly", "lilly");
		flowersList.add(f1);
		flowersList.add(f2);

		Set uniqueIds = new HashSet<Integer>();
		for (Flower flower : flowersList) {
			uniqueIds.add(flower.getUserId());
		}
		Map uniqueUserIds = new HashMap<String, Set>();
		uniqueUserIds.put("uniqueUserIds", uniqueIds);
		Mockito.when(flowerDaoService.retrieveUniqueFlowersIds()).thenReturn(uniqueUserIds);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/uniqueFlowerIds")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"uniqueUserIds\":[\"1\",\"2\"]}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	
	@Test
	public void updateFlowersTest() throws Exception {

		Flower f1 = new Flower("1", 1, "1008Flowers", "1008Flowers");

		Mockito.when(flowerDaoService.updateFlowers(ArgumentMatchers.any())).thenReturn(f1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/flowers").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\":\"1\",\"id\":1,\"title\":\"rose\",\"body\":\"rose\"}");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"userId\":\"1\",\"id\":1,\"title\":\"1008Flowers\",\"body\":\"1008Flowers\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
