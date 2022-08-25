package com.eurail.aem.moviedb.core.models;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eurail.aem.moviedb.core.servlets.GetMovieDetails;

@Model(adaptables = SlingHttpServletRequest.class)
public class MovieListModel {

	@Inject
	SlingHttpServletRequest request;

	private static final Logger LOG = LoggerFactory.getLogger(MovieListModel.class);
	private static final String API_KEY = "k_s0m4cb5v";

	private List<MovieDetailModel> movieList;

	public List<MovieDetailModel> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<MovieDetailModel> movieList) {
		this.movieList = movieList;
	}

	@PostConstruct
	void init() {

		LOG.error("Hello World ==========" + request.getParameter("searchText"));
		String searchText = request.getParameter("searchText");
		LOG.error("searchText : " + searchText);

		if(searchText != null) {
			try {
				// Create a neat value object to hold the URL
				URL url = new URL("https://imdb-api.com/en/API/SearchMovie/" + API_KEY + "/" + searchText);

				// Open a connection(?) on the URL(??) and cast the response(???)
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				// Now it's "open", we can set the request method, headers etc.
				connection.setRequestProperty("accept", "application/json");

				JSONTokener tokener;
				JSONTokener tokenerYoutube;

				tokener = new JSONTokener(url.openStream());
				movieList = new ArrayList<>();
				JSONObject root = new JSONObject(tokener);
				JSONArray resultsObject = root.getJSONArray("results");
				for (int i = 0; i < resultsObject.length(); i++) {
					JSONObject item = resultsObject.getJSONObject(i); 
					MovieDetailModel movieDetailModel = new MovieDetailModel();
					movieDetailModel.setMovieID(item.getString("id"));
					movieDetailModel.setMovieTitle(item.getString("title"));
					movieDetailModel.setMovieImage(item.getString("image"));
					movieDetailModel.setDescription(item.getString("description"));

					if(item.getString("resultType").equalsIgnoreCase("title")) {
						url = new URL("https://imdb-api.com/en/API/YouTubeTrailer/" + API_KEY + "/" + item.getString("id"));
						tokenerYoutube = new JSONTokener(url.openStream());
						JSONObject rootYoutube = new JSONObject(tokenerYoutube);
						//LOG.error("Hello ===============" + rootYoutube.get("videoId"));
						//if(rootYoutube.get("videoId").equals("null"))
							movieDetailModel.setYoutubeID(rootYoutube.getString("videoId"));
					}
					movieList.add(movieDetailModel);
				}

			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}
		}
	}

}
