package net.minidev.bench.json.impl;

import argo.format.*;
import argo.jdom.*;

/**
 * http://argo.sourceforge.net/
 */
public class Agro extends net.minidev.bench.json.JsonInter {
	JdomParser p;
	JsonFormatter w;
	Object obj;
	
	public Agro() {
		init();
	}
	
	@Override
	public void init() {
		p = new JdomParser();
		w = new CompactJsonFormatter();
		obj = null;
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
		return w.format((JsonRootNode)obj);
	}

	@Override
	public String getSimpleName() {
		return "Agro";
	}
}
