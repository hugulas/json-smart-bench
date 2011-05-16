//package net.minidev.bench.json.impl;
//
//import java.util.HashMap;
//
//import com.twolattes.json.*;
///**
// * http://code.google.com/p/jsonmarshaller/
// * 
// */
//public class JsonMarshaller extends net.minidev.bench.json.JsonInter {
//	Object obj;
//	Marshaller<HashMap> p = TwoLattes.createMarshaller(HashMap.class);
//	
//	@Override
//	public Object parseObj(String json) throws Exception {
//		p. (json);
//		obj = JSONObject.parse(json);
//		return obj;
//	}
//
//	@Override
//	public Object parseArray(String json) throws Exception {
//		obj = JSONObject.parse(json);
//		return obj;
//	}
//
//	@Override
//	public String toJsonString() {
//		return obj.toString();
//	}
//
//}
