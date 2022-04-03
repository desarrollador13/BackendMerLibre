package com.BackendMerLibre.constante;

import lombok.Builder;

@Builder
public class Endpoint {
	
	private Endpoint() {}
	
	public static final String TOPSECRET = "/topsecret/";
	public static final String TOPSECRETSPLIT = "/topsecret_split/";

}
