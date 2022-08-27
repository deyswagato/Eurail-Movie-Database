package com.eurail.aem.moviedb.core.models;

import java.util.List;

public class MovieDetailsResultsModel {
	private String searchType;
	private String expression;
	private List<MovieDetailModel> results;
	private String errorMessage;
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public List<MovieDetailModel> getResults() {
		return results;
	}

	public void setResults(List<MovieDetailModel> results) {
		this.results = results;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
