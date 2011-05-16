package net.minidev.bench.json;

import java.util.ArrayList;

public class TestData {
	public String data;
	public String[] r;
	public String message;
	public boolean shouldStack;
	public boolean canKill = false;
	public int score;

	public TestData(String message) {
		this(null, null, message);
	}

	public TestData(String data, String message) {
		this(data, data, message);
	}

	public TestData(String data, String message, boolean shouldStack) {
		this(data, data, message);
		this.shouldStack = shouldStack;
	}

	public TestData(String data, String r1, String message) {
		this.data = data;
		this.message = message;
		this.score = DEFAULT_SCORE;
		setSolution(r1);
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setSolution(String... sols) {
		this.r = sols;
	}

	public boolean isValid(String result) {
		if (shouldStack)
			return false;
		for (String s : r)
			if (s.equals(result))
				return true;
		return false;
	}

	static ArrayList<String> testMsgs;

	/**
	 * 17 key, 17 is a prim number
	 */
	static String[] keys = new String[] { "firstname", "lastname", "date", "len", "shape", "gate", "foo", "bar",
			"city", "site", "url", "age", "action", "level", "password", "color", "case" };

	/**
	 * 13 values, 13 is a prim number
	 */
	static String[] valueMixt = new String[] { "\"jean\"", "120445", "12", "\"Paris\"", "\"pacifica\"",
			"\"44reg(a{[|)@\"", "true", "false", "null", "1.1245E-12", "{\"Sub\":5}", "[\"DFG\", \"ABC\"]",
			"\"AnnotherValue\"" };

	static String[] valueText = new String[] { "jean", "jinna", "paris", "city", "pacifica", "abc", "jeanjouuul" };
	static String[] valueTextProtected = new String[] { "\"jean\"", "\"jinna\"", "\"paris\"", "\"city\"",
			"\"pacifica\"" };
	static String[] valueBoolean = new String[] { "true", "true", "false", "true", "false", "false" };
	static String[] valueInt = new String[] { "1442", "15005", "44", "58", "4253478", "72352284", "55433456",
			"332211223" };
	static String[] valueFloat = new String[] { "1442.12", "15005.12", "44.12", "58.10", "478.2", "2284.5", "33456.12",
			"332211.12" };
	static String[] valueUnicode = new String[] { "\"\\u44FF\\u44ff\"", "\"\\u44ff\"", "\"\\u44aa\\u44ff\"",
			"\"\\u44AA\"", "\"\\u4425\"", "\"\\u4426\"", "\"\\u4427\\u44FF\"" };

	static String[] value = null;
	static public String testMode = null;

	public static void changeTest(String name) {
		testMode = name;
		testMsgs = null;
		if (name.equals("mixte"))
			value = valueMixt;
		else if (name.equals("int"))
			value = valueInt;
		else if (name.equals("float"))
			value = valueFloat;
		else if (name.equals("boolean"))
			value = valueBoolean;
		else if (name.equals("bool"))
			value = valueBoolean;
		else if (name.equals("text"))
			value = valueTextProtected;
		else if (name.equals("string"))
			value = valueText;
		else if (name.equals("unicode"))
			value = valueUnicode;
		else
			System.err.println("non supported Test Type:" + name);
	}

	public static ArrayList<String> genTestMessages() {
		if (testMsgs != null)
			return testMsgs;

		testMsgs = new ArrayList<String>();
		int nbValue = keys.length * value.length;
		for (int i = 0; i < nbValue; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append("{\"");
			sb.append(keys[i % keys.length]).append("\":");
			sb.append(value[i % value.length]);

			sb.append(",\"");
			sb.append(keys[(i + 1) % keys.length]).append("\":");
			sb.append(value[(i + i) % value.length]);

			sb.append(",\"");
			sb.append(keys[(i + 2) % keys.length]).append("\":");
			sb.append(value[(i + 2) % value.length]);

			sb.append("}");
			testMsgs.add(sb.toString());
		}
		return testMsgs;
	}

	static public int DEFAULT_SCORE = 100;

	static public ArrayList<TestData> tests;

	public static ArrayList<TestData> getCompTests() {
		if (tests != null)
			return tests;
		TestData t;
		tests = new ArrayList<TestData>();
		tests.add(new TestData("title"));

		// fack test
		t = new TestData("Support Empty Object");
		t.setData("{}");
		t.setSolution("{}");
		tests.add(t);

		DEFAULT_SCORE = 100;
		tests.add(new TestData("{ \"v\":\"1\"}", "{\"v\":\"1\"}", "Support simple Object String value"));
		tests.add(new TestData("{\t\"v\":\"1\"\r\n}", "{\"v\":\"1\"}", "Space Tester"));
		tests.add(new TestData("{ \"v\":1}", "{\"v\":1}", "Support simple Object int value"));
		tests.add(new TestData("{ \"v\":\"ab'c\"}", "{\"v\":\"ab'c\"}", "Support simple Quote in String"));

		tests.add(new TestData("{ \"PI\":3.141E-10}", "{\"PI\":3.141E-10}", "Support simple Object float value"));
		tests.add(new TestData("{ \"PI\":3.141e-10}", "{\"PI\":3.141E-10}", "Support lowcase float value"));

		tests.add(new TestData("{ \"v\":12345123456789}", "{\"v\":12345123456789}", "Long number support"));
		tests.add(new TestData("{ \"v\":123456789123456789123456789}", "{\"v\":123456789123456789123456789}",
				"Bigint number support"));

		tests.add(new TestData("[ 1,2,3,4]", "[1,2,3,4]", "Support simple digit array"));
		tests.add(new TestData("[ \"1\",\"2\",\"3\",\"4\"]", "[\"1\",\"2\",\"3\",\"4\"]", "Support simple string array"));

		tests.add(new TestData("[ { }, { },[]]", "[{},{},[]]", "Array of empty Object"));

		t = new TestData("Support Unicode Text");
		t.setData("{ \"v\":\"\\u2000\\u20ff\"}");
		t.setSolution("{\"v\":\"\\u2000\\u20ff\"}", "{\"v\":\"\\u2000\\u20FF\"}");
		tests.add(t);

		t = new TestData("Support non protected / text");
		t.setData("{ \"a\":\"hp://foo\"}");
		t.setSolution("{\"a\":\"hp:\\/\\/foo\"}");

		tests.add(t);

		// non string value
		tests.add(new TestData("{ \"a\":null}", "{\"a\":null}", "Support null"));
		tests.add(new TestData("{ \"a\":true}", "{\"a\":true}", "Support boolean"));
		tests.add(new TestData("{ \"a\" : true }", "{\"a\":true}", "Support non trimed data"));

		t = new TestData("Double precision floating point");
		t.setData("{ \"v\":" + Double.MAX_VALUE + "}");
		t.setSolution("{\"v\":" + Double.MAX_VALUE + "}");
		tests.add(t);

		// trunctated datas
		DEFAULT_SCORE = 100;
		t = new TestData("{'X':'s", "trucated value", true);
		t.canKill = true;
		tests.add(t);
		t = new TestData("{'X", "trucated Key", true);
		t.canKill = true;
		tests.add(t);

		// string
		DEFAULT_SCORE = 10;
		t = new TestData("{ \"v\":'ab\"c'}", "{\"v\":\"ab\\\"c\"}", "Support Double Quote in Simple Quoted Text");
		t.canKill = true;
		tests.add(t);

		// non protected String values
		DEFAULT_SCORE = 10;
		t = new TestData("{ \"v\":str}", "{\"v\":\"str\"}", "Support non protected String value");
		t.canKill = true;
		tests.add(t);

		t = new TestData("Support simple quote in non protected string value");
		t.setData("{ \"v\":It's'Work}");
		t.setSolution("{\"v\":\"It's'Work\"}", "{\"v\":\"ItsWork\"}");
		t.canKill = true;
		tests.add(t);

		// non protected keys test in object
		DEFAULT_SCORE = 10;
		t = new TestData("{ a:1234}", "{\"a\":1234}", "Support non protected keys");
		t.canKill = true;
		tests.add(t);

		// non protected values in arrays
		DEFAULT_SCORE = 10;
		t = new TestData("[ a,bc]", "[\"a\",\"bc\"]", "Support non protected array value");
		t.canKill = true;
		tests.add(t);

		DEFAULT_SCORE = 10;
		t = new TestData("Support non protected String value with space");
		t.setData("{ \"v\":s1 s2}");
		t.setSolution("{\"v\":\"s1 s2\"}");
		t.canKill = true;
		tests.add(t);

		DEFAULT_SCORE = 10;
		t = new TestData("Support non protected String value having special spaces");
		t.setData("{ \"v\":s1\ts2\t}");
		t.setSolution("{\"v\":\"s1\\ts2\"}", "{\"v\":\"s1\ts2\"}");
		t.canKill = true;
		tests.add(t);

		DEFAULT_SCORE = 10;
		tests.add(new TestData("{ \"a\":\"foo.bar\"}#toto", "{\"a\":\"foo.bar\"}", "Support gardbage tailling comment"));
		t = new TestData("{ 'value':'string'}", "{\"value\":\"string\"}", "Support Simple Quote Stings");
		t.canKill = true;
		tests.add(t);

		DEFAULT_SCORE = 10;
		t = new TestData("Support non protected start like text value");
		t.setData("{v:15-55}");
		t.setSolution("{\"v\":\"15-55\"}");
		t.canKill = true;
		tests.add(t);

		t = new TestData("Support non protected start like text value");
		t.setData("{v:15%}");
		t.setSolution("{\"v\":\"15%\"}");
		t.canKill = true;
		tests.add(t);

		t = new TestData("Support non protected start like text value");
		t.setData("{v:15.06%}");
		t.setSolution("{\"v\":\"15.06%\"}");
		t.canKill = true;
		tests.add(t);

		// Idle Feature Tests

		DEFAULT_SCORE = 1;
		t = new TestData("Support non protected String value having quote 1");
		t.setData("{ \"v\":s1' s2}");
		t.setSolution("{\"v\":\"s1' s2\"}");
		t.canKill = true;
		tests.add(t);

		DEFAULT_SCORE = 1;
		t = new TestData("Support non protected String value having quote 2");
		t.setData("{ \"v\":s1\" \"s2}");
		t.setSolution("{\"v\":\"s1\\\" \\\"s2\"}", "{\"v\":\"s1 s2\"}");
		t.canKill = true;
		tests.add(t);

		DEFAULT_SCORE = 1;
		// extanding Tests
		t = new TestData("{ \"NaN\":NaN}", "{\"NaN\":NaN}", "NaN Test Value");
		t.canKill = true;
		tests.add(t);

		DEFAULT_SCORE = 1;
		t = new TestData("[ a},b]", "[\"a}\",\"b\"]", "Support non protected array value contains }");
		t.canKill = true;
		tests.add(t);
		
		t = new TestData("[ a:,b]", "[\"a:\",\"b\"]", "Support non protected array value contains :");
		t.canKill = true;
		tests.add(t);
		
		DEFAULT_SCORE = 1;
		t = new TestData("{ a,b:123}", "{\"a,b\":123}", "Support non protected keys contains ,");
		tests.add(t);
		t.canKill = true;
		t = new TestData("{ a]b:123}", "{\"a]b\":123}", "Support non protected keys contains ]");
		t.canKill = true;
		tests.add(t);
		tests.add(new TestData("score"));
		return tests;
	}

}
