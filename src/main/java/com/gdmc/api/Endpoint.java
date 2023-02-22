package com.gdmc.api;

import java.util.ArrayList;

import com.gdmc.api.EndpointBuilder.Casts;

public class Endpoint {
	String name;
	ArrayList<Casts> casts;
	ArrayList<String> names;
	ArrayList<Object> defaultValues;

	Endpoint(String name) {
		this.name = name;
		this.casts = new ArrayList<>();
		this.names = new ArrayList<>();
		this.defaultValues = new ArrayList<>();
	}
}
