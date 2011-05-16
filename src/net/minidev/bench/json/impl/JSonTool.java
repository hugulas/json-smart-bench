package net.minidev.bench.json.impl;
//package net.minidev.bench.json.impl;
//
//import java.io.StringReader;
//
//import net.minidev.bench.json.JsonInter;
//
//import com.sdicons.json.parser.*;
//
///**
// * http://prdownload.berlios.de/jsontools/
// * 
// * @author uriel
// *
// */
//public class JsonInterJSonTool extends JsonInter {
//	String text;
//	
//	@Override
//	public Object parseObj(String json) throws Exception {
//		JSONParser p = new JSONParser(new StringReader(json));
//		p.nextValue();
//		//JSONWriter w = new JSONWriter();
//		//w.write(object)
//		return obj;
//	}
//
//	@Override
//	public Object parseArray(String json) throws Exception {
//		JSONArray obj = new JSONArray(json);
//		text = obj.toString();
//		return obj;
//	}
//
//	@Override
//	public String toJsonString() {
//		return text;
//	}
//
//
//}
