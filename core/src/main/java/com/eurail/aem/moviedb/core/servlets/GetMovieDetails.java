package com.eurail.aem.moviedb.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eurail.aem.moviedb.core.models.MovieDetailModel;
import com.eurail.aem.moviedb.core.models.MovieListModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(
		resourceTypes="moviedb/getmoviedetails",
		methods=HttpConstants.METHOD_GET,
		extensions="txt")
public class GetMovieDetails extends SlingSafeMethodsServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(GetMovieDetails.class);

	private static final String API_KEY = "k_8ruxma70";

	MovieListModel listModel = new MovieListModel();
	List<MovieDetailModel> list;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOG.error("Inside Servlet");
		String searchText = request.getParameter("searchText");
		LOG.error("searchText : " + searchText);
		// Create a neat value object to hold the URL
		URL url = new URL("https://imdb-api.com/en/API/SearchMovie/" + API_KEY + "/" + searchText);

		/*
		 * JSONTokener tokener; JSONTokener tokenerYoutube; try { tokener = new
		 * JSONTokener(url.openStream()); list = new ArrayList<>(); JSONObject root =
		 * new JSONObject(tokener); JSONArray resultsObject =
		 * root.getJSONArray("results"); for (int i = 0; i < resultsObject.length();
		 * i++) { JSONObject item = resultsObject.getJSONObject(i); MovieDetailModel
		 * movieDetailModel = new MovieDetailModel();
		 * movieDetailModel.setMovieID(item.getString("id"));
		 * movieDetailModel.setMovieTitle(item.getString("title"));
		 * movieDetailModel.setMovieImage(item.getString("image")); url = new
		 * URL("https://imdb-api.com/en/API/YouTubeTrailer/" + API_KEY + "/" +
		 * item.getString("id")); tokenerYoutube = new JSONTokener(url.openStream());
		 * JSONObject rootYoutube = new JSONObject(tokenerYoutube);
		 * movieDetailModel.setYoutubeID(rootYoutube.getString("videoId"));
		 * list.add(movieDetailModel); } listModel.setMovieList(list);
		 * 
		 * } catch (JSONException | IOException e) { e.printStackTrace(); }
		 */
	}
}
