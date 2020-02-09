package com.bharatkhoj.metasearch.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharatkhoj.metasearch.domain.GoogleSearchResults;
import com.google.gson.JsonObject;
import com.microsoft.azure.cognitiveservices.search.autosuggest.BingAutoSuggestSearchAPI;
import com.microsoft.azure.cognitiveservices.search.autosuggest.BingAutoSuggestSearchManager;
import com.microsoft.azure.cognitiveservices.search.autosuggest.models.SearchAction;
import com.microsoft.azure.cognitiveservices.search.autosuggest.models.Suggestions;

@RestController
public class SearchController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private final String subscriptionKey = "d26dc7d94bc8496d83204c65aecb609a";
	private BingAutoSuggestSearchAPI bingClient;
	
	@PostConstruct
	public void init() {
		bingClient  = BingAutoSuggestSearchManager.authenticate(subscriptionKey);
	}

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
	
	@GetMapping("/bing")
	public ResponseEntity<Object> getSeachSuggestions(@RequestParam(value = "suggest", defaultValue = "Tiger") String suggest){
		
		List<String> retValue = new ArrayList<String>();
		try {
			
			 Suggestions suggestions = bingClient.bingAutoSuggestSearch().autoSuggest()
		                .withQuery(suggest)
		                .execute();
			 
			 if (suggestions != null && suggestions.suggestionGroups() != null && suggestions.suggestionGroups().size() > 0) {
	                System.out.println("Found the following suggestions:");
	                for (SearchAction suggestion: suggestions.suggestionGroups().get(0).searchSuggestions()) {
	                    System.out.println("....................................");
	                    System.out.println(suggestion.query());
	                    System.out.println(suggestion.displayText());
	                    System.out.println(suggestion.url());
	                    System.out.println(suggestion.searchKind());
	                    retValue.add(suggestion.displayText());
	                }
	            } else {
	                System.out.println("Didn't see any suggestion...");
	            }
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return new ResponseEntity<>(retValue, HttpStatus.OK);
	}
	
	

}