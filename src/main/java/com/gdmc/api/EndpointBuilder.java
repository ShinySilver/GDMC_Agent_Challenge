package com.gdmc.api;

public class EndpointBuilder {

	enum Casts {
		CAST_INT, CAST_BLOCK, CAST_STRING
	}

	private Endpoint end;

	public EndpointBuilder(String name) {
		end = new Endpoint(name);
	}

	public EndpointBuilder addString(String name) {
		end.names.add(name);
		end.casts.add(Casts.CAST_STRING);
		end.defaultValues.add(null);
		return this;
	}

	public EndpointBuilder addOptionalString(String name, String defaultValue) {
		end.names.add(name);
		end.casts.add(Casts.CAST_STRING);
		end.defaultValues.add(defaultValue);
		return this;
	}

	public EndpointBuilder addInt(String name) {
		end.names.add(name);
		end.casts.add(Casts.CAST_INT);
		end.defaultValues.add(null);
		return this;
	}

	public EndpointBuilder addOptionalInt(String name, String defaultValue) {
		end.names.add(name);
		end.casts.add(Casts.CAST_INT);
		end.defaultValues.add(defaultValue);
		return this;
	}

	public EndpointBuilder addBlock(String name) {
		end.names.add(name);
		end.casts.add(Casts.CAST_BLOCK);
		end.defaultValues.add(null);
		return this;
	}

	public EndpointBuilder addOptionalBlock(String name, String defaultBlock) {
		end.names.add(name);
		end.casts.add(Casts.CAST_BLOCK);
		end.defaultValues.add(defaultBlock);
		return this;
	}
	
	public Endpoint build() {
		Endpoint out = end;
		end = null;
		return out;
	}
}
