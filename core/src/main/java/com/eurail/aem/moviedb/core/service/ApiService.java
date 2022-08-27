package com.eurail.aem.moviedb.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

@Component(service = ApiService.class)
public class ApiService {
	
	private static final String API_KEY = "k_8ruxma70";
	
	public InputStream getAPIData(String path, String searchText) {
		URL url;
		JSONObject jsonObject = null;
		InputStream inputStream = null;
		try {
			url = new URL(path + API_KEY + "/" + searchText);
			// Open a connection(?) on the URL(??) and cast the response(???)
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// Now it's "open", we can set the request method, headers etc.
			connection.setRequestProperty("accept", "application/json");
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

}
