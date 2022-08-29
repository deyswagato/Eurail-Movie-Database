package com.eurail.aem.moviedb.core.service;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "API Key Configuration")
public @interface ApiKeyConfiguration {
	
	@AttributeDefinition(name = "API Key", description = "API Key Value")
	String api_key() default "k_0e8ta7h5";
}
