package net.minidev.bench.json.impl;

import net.sf.sojo.interchange.json.*;

/**
 * http://sojo.sourceforge.net/
 */
public class Sojo extends net.minidev.bench.json.JsonInter {
	Object obj;
	JsonParser p;
	JsonSerializer w;
	
	public Sojo() {
		init();
	}
	
	@Override
	public void init() {
		obj = null;
		p = new JsonParser();
		w = new JsonSerializer();
	}

	@Override
	public Object parseObj(String json) throws Exception {
		obj = p.parse(json);
		return obj;
	}

	@Override
	public Object parseArray(String json) throws Exception {
		obj = p.parse(json);
		return obj;
	}

	@Override
	public String toJsonString() {
		return (String)w.serialize(obj);
	}
	
	@Override
	public String getSimpleName() {
		return "Sojo";
	}
}
