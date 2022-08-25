package com.eurail.aem.moviedb.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOG.error("Inside Servlet");
		String searchText = request.getParameter("searchText");
		LOG.error("searchText : " + searchText);
		// Create a neat value object to hold the URL
		URL url = new URL("https://imdb-api.com/en/API/SearchMovie/" + API_KEY + "/" + searchText);

		// Open a connection(?) on the URL(??) and cast the response(???)
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		// Now it's "open", we can set the request method, headers etc.
		connection.setRequestProperty("accept", "application/json");

		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		
		LOG.error("Response : " + content.toString());

	}
}
