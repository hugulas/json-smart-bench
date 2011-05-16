package net.minidev.bench.json.impl;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * http://jackson.codehaus.org/
 */
public class Jackson extends net.minidev.bench.json.JsonInter {
	ObjectMapper p;
	JsonNode obj;
	
	public Jackson() {
		init();
	}
	
	@Override
	public void init() {
		p = new ObjectMapper();
		obj = null;
	}

	@Override
	public Object parseObj(String json) throws Exception {
		obj = p.readTree(json);
		return obj;
	}

	@Override
	public Object parseArray(String json) throws Exception {
		JsonNode n = p.readTree(json);
		// text = n.toString();
		return n;
		// ArrayList n = p.readValue(json, ArrayList.class);
		// text = n.toString();
		// return n;
	}

	@Override
	public String toJsonString() {
		return obj.toString();
	}

	@Override
	public String getSimpleName() {
		return "Jackson";
	}
}
