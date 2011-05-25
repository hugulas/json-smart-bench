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

		t = new TestData("Support simple Object float value");
		t.setData("{ \"PI\":3.141E-10}");
		t.setSolution("{\"PI\":3.141E-10}", "{\"PI\":3.141e-10}");
		tests.add(t);

		t = new TestData("Support lowcase float value");
		t.setData("{ \"PI\":3.141e-10}");
		t.setSolution("{\"PI\":3.141E-10}", "{\"PI\":3.141e-10}");
		tests.add(t);

		tests.add(new TestData("{ \"v\":12345123456789}", "{\"v\":12345123456789}", "Long number support"));
		tests.add(new TestData("{ \"v\":123456789123456789123456789}", "{\"v\":123456789123456789123456789}",
				"Bigint number support"));

		tests.add(new TestData("[ 1,2,3,4]", "[1,2,3,4]", "Support simple digit array"));
		tests.add(new TestData("[ \"1\",\"2\",\"3\",\"4\"]", "[\"1\",\"2\",\"3\",\"4\"]", "Support simple string array"));

		tests.add(new TestData("[ { }, { },[]]", "[{},{},[]]", "Array of empty Object"));

		t = new TestData("Support lowercase Unicode Text");
		t.setData("{ \"v\":\"\\u2000\\u20ff\"}");
		t.setSolution("{\"v\":\"\\u2000\\u20ff\"}", "{\"v\":\"\\u2000\\u20FF\"}");
		tests.add(t);

		t = new TestData("Support uppercase Unicode Text");
		t.setData("{ \"v\":\"\\u2000\\u20FF\"}");
		t.setSolution("{\"v\":\"\\u2000\\u20ff\"}", "{\"v\":\"\\u2000\\u20FF\"}");
		tests.add(t);

		t = new TestData("Support non protected / text");
		t.setData("{ \"a\":\"hp://foo\"}");
		t.setSolution("{\"a\":\"hp:\\/\\/foo\"}", "{\"a\":\"hp://foo\"}");

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
