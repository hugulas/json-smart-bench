package net.minidev.bench.json.impl;

import org.json.*;

/**
 * http://www.json.org/java/index.html
 * 
 * @author uriel
 *
 */
public class OrgJSon extends net.minidev.bench.json.JsonInter {
	Object obj;
	
	public OrgJSon() {
		init();
	}
	
	@Override
	public void init() {
		obj = null;
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

	@Override
	public String getSimpleName() {
		return "org.json";
	}

}
