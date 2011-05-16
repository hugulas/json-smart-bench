package net.minidev.bench.json.impl;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

/**
 * http://code.google.com/p/json-simple/
 * http://code.google.com/p/json-test-suite/
 * 
 * @author uriel
 *
 */
public class Simple extends net.minidev.bench.json.JsonInter {
	Object obj;
	JSONParser p;
	
	public Simple() {
		init();
	}
	
	@Override
	public void init() {
		obj = null;
		p = new JSONParser();
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
		return ((JSONAware)obj).toJSONString();
	}
	@Override
	public String getSimpleName() {
		return "json-Simple";
	}
}
