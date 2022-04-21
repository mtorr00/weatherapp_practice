package com.tts.weatherapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//class will be responsible for handling our web pages requests
@Controller
public class WeatherController {
	
	@Autowired
	WeatherService weatherService;
	
	//@RequestMapping(value="/", method= {RequestMethod.GET, RequestMethod.POST})
	
	@GetMapping(value="/") //only does GET
	public String getIndex(Model model) {

		Request request = new Request();
		model.addAttribute("request", request);
		model.addAttribute("lastRequests", weatherService.getLastRequests());
		return "index";
	}
	@PostMapping(value="/")
	public String postIndex(Request request,Model model) {
		Response data = weatherService.getForecast(request.getZipCode());
		model.addAttribute("data", data);
		model.addAttribute("lastRequests", weatherService.getLastRequests());
		return "index";
	}
}
