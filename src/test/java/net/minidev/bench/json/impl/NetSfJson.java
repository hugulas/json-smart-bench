package net.minidev.bench.json.impl;

import net.sf.json.*;

/**
 * http://json-lib.sourceforge.net/
 * 
 * @author uriel
 *
 */
public class NetSfJson extends net.minidev.bench.json.JsonInter {
	Object obj;
	
	public NetSfJson() {
		init();
	}
	
	@Override
	public void init() {
		obj = null;
	}

	@Override
	public Object parseObj(String json) throws Exception {
		obj = JSONObject.fromObject(json);
		return obj;
	}

	@Override
	public Object parseArray(String json) throws Exception {
		obj = JSONArray.fromObject(json);
		return obj;
	}

	@Override
	public String toJsonString() {
		return obj.toString();
	}

	@Override
	public String getSimpleName() {
		return "json-lib";
	}
}
