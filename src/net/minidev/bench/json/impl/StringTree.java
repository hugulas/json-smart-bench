package net.minidev.bench.json.impl;

import org.stringtree.json.*;

/**
 * http://sourceforge.net/projects/stringtree
 */
public class StringTree extends net.minidev.bench.json.JsonInter {
	Object obj;
	JSONReader jreader;
	JSONWriter w;

	public StringTree() {
		init();
	}

	@Override
	public void init() {
		jreader = new JSONReader();
		w = new JSONWriter();
		obj = null;
	}

	@Override
	public Object parseObj(String json) throws Exception {
		obj = jreader.read(json);
		return obj;
	}

	@Override
	public Object parseArray(String json) throws Exception {
		obj = jreader.read(json);
		return obj;
	}

	@Override
	public String toJsonString() {
		return w.write(obj);
	}

	@Override
	public String getSimpleName() {
		return "StringTree";
	}

	@Override
	public boolean canDead() {
		return true;
	}
}
