package com.gdmc.api;

import java.util.HashMap;

public class EndpointRegister {
	public static final EndpointRegister instance = new EndpointRegister();

	private HashMap<String, Endpoint> map;

	private EndpointRegister() {
		map = new HashMap<>();
	}

	void register(Endpoint endpoint) {
		map.put(endpoint.name, endpoint);
	}

	void parse(String[] line) {
		Endpoint e = map.get(line[0]);
		for(int i = 0; i<e.names.size(); i++) {
		}
	}
}
