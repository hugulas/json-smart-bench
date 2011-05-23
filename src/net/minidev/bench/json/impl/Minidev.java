package net.minidev.bench.json.impl;

import net.minidev.json.*;
import net.minidev.json.parser.*;

/**
 * http://code.google.com/p/json-smart/
 * 
 * @author uriel
 *
 */
public class Minidev extends net.minidev.bench.json.JsonInter {
	JSONParser p;
	Object obj;
	
	public Minidev() {
		init();
	}
	
	@Override
	public void init() {
		obj = null;
		p = new JSONParser(JSONParser.MODE_RFC4627);
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
		return "Json-Smart";
	}

}
