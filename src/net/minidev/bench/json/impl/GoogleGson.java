package net.minidev.bench.json.impl;

import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * http://code.google.com/p/google-gson
 * 
 */
public class GoogleGson extends net.minidev.bench.json.JsonInter {
	Object obj;
	Gson p;
	Type type;

	public GoogleGson() {
		init();
	}
	
	@Override
	public void init() {
		p = new Gson();
		type = null;
		obj = null;
	}
	
	@Override
	public Object parseObj(String json) throws Exception {
		obj = p.fromJson(json, java.util.HashMap.class);
		type = java.util.HashMap.class;
		return obj;
	}

	@Override
	public Object parseArray(String json) throws Exception {
		obj = p.fromJson(json, java.util.ArrayList.class);
		type = java.util.ArrayList.class;
		return obj;
	}

	@Override
	public String toJsonString() {
		return p.toJson(obj, type);
	}

	@Override
	public String getSimpleName() {
		return "GSon";
	}
}
