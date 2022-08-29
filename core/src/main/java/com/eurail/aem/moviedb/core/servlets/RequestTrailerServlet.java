package com.eurail.aem.moviedb.core.servlets;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eurail.aem.moviedb.core.models.MovieDetailModel;
import com.eurail.aem.moviedb.core.models.MovieListModel;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(
		resourceTypes="moviedb/requestTrailer",
		methods=HttpConstants.METHOD_GET,
		extensions="txt")
public class RequestTrailerServlet extends SlingSafeMethodsServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(RequestTrailerServlet.class);
	
	Node usergeneratedNode;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		LOG.error("Inside Servlet");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String movieName = request.getParameter("movieName");
		String requestDescription = request.getParameter("requestDescription");
		
		ResourceResolver resolver = request.getResourceResolver();
		Session session = resolver.adaptTo(Session.class);
		
		Resource usergeneratedResource = resolver.getResource("/content/usergenerated");
		if(usergeneratedResource != null) {
			Resource moviedbReqResource = resolver.getResource("/content/usergenerated/moviedbReq");
			if(moviedbReqResource == null) {
				usergeneratedNode = usergeneratedResource.adaptTo(Node.class);
				try {
					usergeneratedNode.addNode("moviedbReq");
					session.save();
				} catch (RepositoryException e) {
					e.printStackTrace();
				}
			}
			moviedbReqResource = resolver.getResource("/content/usergenerated/moviedbReq");
			saveData(moviedbReqResource, session, fname, lname, movieName, requestDescription);
		}
	}
	
	void saveData(Resource moviedbReqResource, Session session, String fname, String lname, String movieName, String requestDescription) {
		Node moviedbReqNode = moviedbReqResource.adaptTo(Node.class);
		if(moviedbReqNode != null) {
			try {
				LocalDateTime time = LocalDateTime.now();
				String timeString = time.toString().replace(".", "").replace("-", "").replace(":", "");
				Node randomNode = moviedbReqNode.addNode(timeString);
				randomNode.setPrimaryType("nt:unstructured");
				randomNode.setProperty("fname", fname);
				randomNode.setProperty("lname", lname);
				randomNode.setProperty("movieName", movieName);
				randomNode.setProperty("requestDescription", requestDescription);
				session.save();
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
		}
	}
}
