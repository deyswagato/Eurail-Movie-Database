package com.eurail.aem.moviedb.core.models;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eurail.aem.moviedb.core.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Model(adaptables = SlingHttpServletRequest.class)
public class MovieListModel {

	@Inject
	SlingHttpServletRequest request;
	
	@Inject
	ApiService apiService;

	private static final Logger LOG = LoggerFactory.getLogger(MovieListModel.class);
	
	ObjectMapper mapper = new ObjectMapper();
	
	MovieDetailsResultsModel movieList;
	
	public MovieDetailsResultsModel getMovieList() {
		return movieList;
	}

	@PostConstruct
	void init() {
		String searchText = request.getParameter("searchText");
		LOG.error("searchText : " + searchText);

		if(searchText != null) {
			try {
				movieList = mapper.readValue(apiService.getAPIData("https://imdb-api.com/en/API/SearchMovie/", searchText), MovieDetailsResultsModel.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
