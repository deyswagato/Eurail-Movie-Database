package com.eurail.aem.moviedb.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = ApiService.class)
@Designate(ocd = ApiKeyConfiguration.class)
public class ApiService {
	
	private String API_KEY;
	
	@Activate
	protected void activate(ApiKeyConfiguration apiKeyConfiguration) {
		this.API_KEY = apiKeyConfiguration.api_key();
	}
	
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
