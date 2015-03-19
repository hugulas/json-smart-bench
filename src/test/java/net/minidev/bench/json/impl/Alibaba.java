package net.minidev.bench.json.impl;

import com.alibaba.fastjson.*;
/**
 * http://sourceforge.net/projects/fastjson/
 * 
 */
public class Alibaba extends net.minidev.bench.json.JsonInter {
	Object obj;
	
	public Alibaba() {
		init();
	}
	
	@Override
	public void init() {
		obj = null;
	}

	@Override
	public Object parseObj(String json) throws Exception {
		obj = JSONObject.parse(json);
		return obj;
	}

	@Override
	public Object parseArray(String json) throws Exception {
		obj = JSONObject.parse(json);
		return obj;
	}

	@Override
	public String toJsonString() {
		return obj.toString();
	}

	@Override
	public String getSimpleName() {
		return "Json-fast";
	}
}
