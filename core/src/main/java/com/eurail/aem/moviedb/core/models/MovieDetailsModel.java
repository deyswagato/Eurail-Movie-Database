package com.eurail.aem.moviedb.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eurail.aem.moviedb.core.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Model(adaptables = SlingHttpServletRequest.class)
public class MovieDetailsModel {

	@Inject
	SlingHttpServletRequest request;
	
	@Inject
	ApiService apiService;
	
	private static final Logger LOG = LoggerFactory.getLogger(MovieDetailsModel.class);
	
	ObjectMapper mapper = new ObjectMapper();

	private Map<String, Object> movieDetalsMap;
	
	private Map<String, Object> youtubeMap;
	
	public Map<String, Object> getMovieDetalsMap() {
		return movieDetalsMap;
	}

	public Map<String, Object> getYoutubeMap() {
		return youtubeMap;
	}

	@PostConstruct
	void init() {
		String id = request.getParameter("id");
		if(id != null) {
			
			  try { 
				  movieDetalsMap = mapper.readValue(apiService.getAPIData("https://imdb-api.com/en/API/Title/", id), Map.class);
				  youtubeMap = mapper.readValue(apiService.getAPIData("https://imdb-api.com/en/API/YouTubeTrailer/", id), Map.class);
			  } catch (IOException e) {
				  e.printStackTrace();
			  }
		}
	}
}