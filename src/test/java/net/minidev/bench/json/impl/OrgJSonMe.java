package net.minidev.bench.json.impl;

import org.json.me.*;

/**
 * https://github.com/upictec/org.json.me
 * 
 */
public class OrgJSonMe extends net.minidev.bench.json.JsonInter {
	Object obj;
	
	public OrgJSonMe() {
		init();
	}
	
	@Override
	public void init() {
		obj = null;
	}

	@Override
	public String getSimpleName() {
		return "JsonMe";
	}
	
	@Override
	public Object parseObj(String json) throws Exception {
		obj = new JSONObject(json);
		return obj;
	}

	@Override
	public Object parseArray(String json) throws Exception {
		obj = new JSONArray(json);
		return obj;
	}

	@Override
	public String toJsonString() {
		return obj.toString();
	}

}
