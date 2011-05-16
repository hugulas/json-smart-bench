package net.minidev.bench.json.impl;

import jsonij.json.*;

/**
 * http://projects.plural.cc/projects/jsonij
 */
public class JSonIJ extends net.minidev.bench.json.JsonInter {
	JSON obj;
	
	public JSonIJ() {
		init();
	}
	
	@Override
	public void init() {
		obj = null;
	}

	@Override
	public Object parseObj(String json) throws Exception {
		obj= JSON.parse(json);
		return obj;
	}

	@Override
	public Object parseArray(String json) throws Exception {
		obj= JSON.parse(json);
		return obj;
	}

	@Override
	public String toJsonString() {
		return obj.toJSON();
	}
	
	@Override
	public String getSimpleName() {
		return "JsonIJ";
	}

}
