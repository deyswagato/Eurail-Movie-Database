package com.eurail.aem.moviedb.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

@Model(adaptables = SlingHttpServletRequest.class)
public class PendingRequestTrailerModel {
	
	@Inject
	ResourceResolver resourceResolver;
	
	private List<RequestTrailerModel> requestTrailerList;

	public List<RequestTrailerModel> getRequestTrailerList() {
		return requestTrailerList;
	}
	
	@PostConstruct
	void init() {
		Map<String, String> predicates = new HashMap<String, String>();
		requestTrailerList = new ArrayList<>();
		String userGenPath = "/content/usergenerated";
		Resource userGenRes = resourceResolver.getResource(userGenPath);
		if(userGenRes != null) {
			Resource movieDbReqRes = resourceResolver.getResource(userGenRes.getPath() + "/moviedbReq");
			if(movieDbReqRes != null) {
				predicates.put("path", movieDbReqRes.getPath());
			}
		}

		QueryBuilder qb = resourceResolver.adaptTo(QueryBuilder.class);
		Session session = resourceResolver.adaptTo(Session.class);

		Query query = qb.createQuery(PredicateGroup.create(predicates), session);
		SearchResult result = query.getResult();
		for(Hit hit: result.getHits()) {
			Node hitNode;
			try {
				hitNode = hit.getNode();
				RequestTrailerModel requestTrailerModel  = new RequestTrailerModel();
				requestTrailerModel.setFname(hitNode.getProperty("fname").getString());
				requestTrailerModel.setLname(hitNode.getProperty("lname").getString());
				requestTrailerModel.setMovieName(hitNode.getProperty("movieName").getString());
				requestTrailerModel.setRequestDescription(hitNode.getProperty("requestDescription").getString());
				requestTrailerList.add(requestTrailerModel);
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
		}
	}
}
