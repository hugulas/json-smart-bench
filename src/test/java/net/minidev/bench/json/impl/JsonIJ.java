package net.minidev.bench.json.impl;

import cc.plural.jsonij.JSON;


/**
 * http://projects.plural.cc/projects/jsonij
 */
public class JsonIJ extends net.minidev.bench.json.JsonInter {
	JSON obj;
	
	public JsonIJ() {
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
