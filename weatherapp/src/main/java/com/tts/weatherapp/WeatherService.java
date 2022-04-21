package com.tts.weatherapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

//utility class to make api call
//common when making springboot app to create service classes
//essentially just utility class
@Service
public class WeatherService {
	@Autowired
	RequestRepository zipCodeRepository;
	
	
	
	
	@Value("${api_key}")
	private String apiKey;
	
	public Response getForecast(String zipCode) {
		//String url = "https://api.openweathermap.org/data/2.5/weather";
		//url +="?zip=" + zipCode + "&units=imperial&appid=" + apiKey;
		
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("api.openweathermap.org")
				.path("/data/2.5/weather")
				.queryParam("zip", zipCode)
				.queryParam("units","imperial")
				.queryParam("appid", apiKey)
				.build();
		String url = uriComponents.toUriString();
		//any time you want to make a rest api call or just generally any request over
		//http via spring boot you create a rest template
		RestTemplate restTemplate = new RestTemplate();
		
		Response response; 
		
		try {
			response = restTemplate.getForObject(url, Response.class);
			
			Request newRequest = new Request(zipCode);
			zipCodeRepository.save(newRequest);
		}
		catch(HttpClientErrorException e) {
			response = new Response();
			response.setName("error");
		}
			
		return response;
	}
	public List<Request> getLastRequests() {
		return zipCodeRepository.findTop10ByOrderByCreatedAt();
	}
}






















