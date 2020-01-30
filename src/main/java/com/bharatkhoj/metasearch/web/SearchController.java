package com.bharatkhoj.metasearch.web;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharatkhoj.metasearch.domain.GoogleSearchResults;
import com.google.gson.JsonObject;

@RestController
public class SearchController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/google")
	public String google(@RequestParam(value = "query", defaultValue = "Coffee") String query) {
		Map<String, String> parameter = new HashMap<>();

		parameter.put("q", query);
		parameter.put("location", "new delhi, India");
		parameter.put("hl", "en");
		parameter.put("gl", "us");
		parameter.put("google_domain", "google.co.in");
		parameter.put("api_key", "68c93ada023f9d62275f81ad610db347e4caa2811125648187962e91ace7bb81");

		GoogleSearchResults client = new GoogleSearchResults(parameter);
		try {
			JsonObject results = client.getJson();
			return results.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}