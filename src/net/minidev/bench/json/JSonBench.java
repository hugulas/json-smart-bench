package net.minidev.bench.json;

import java.io.File;

import java.io.IOException;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import net.minidev.bench.json.impl.*;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.apache.commons.io.FileUtils;

/**
 * Json Bench erntry point
 * 
 * @author Uriel Chemouni <uchemouni@gmail.com>
 * 
 */
public class JSonBench {
	static int threadPause = 40;
	static boolean preload;
	public final static int LOOP_COUNT = 500000;

	@SuppressWarnings("rawtypes")
	static Class[] clazz = new Class[] { StringTree.class, JSonIJ.class, Jackson.class, Agro.class, Simple.class,
			Sojo.class, Alibaba.class, NetSfJson.class, OrgJSonMe.class, OrgJSon.class, JSonSmartStrict.class,
			JSonSmart.class };

	// GoogleGson.class,
	public static void main(String[] args) throws Exception {
		// start(new String[] { "test" });
		// start(new String[] { "showResult" });
		// start(new String[] { "showScript" });
		// start(new String[] { "benchPreload", "int", "0"});
		start(args);
	}

	public static void start(String[] args) throws Exception {
		// args = new String[] { "test"};
		if (args.length == 0) {
			System.out.println("bench / benchPreload / test");
			System.out.println("Help bench [text|unicode|float|int|mixte|boolean] [1..11]");
			System.out.println("Help benchPreload [text|unicode|float|int|mixte|boolean] [1..11]");
			System.out.println("Help test");
			return;
		}
		String cmd = args[0];

		if (cmd.equals("test")) {
			test();
			System.exit(0);
			return;
		} else if (cmd.startsWith("bench")) {
			int impId = 0;
			String testName = "mixte";

			if (cmd.endsWith("Preload"))
				preload = true;

			if (args.length == 3) {
				testName = args[1].toLowerCase();
				impId = Integer.parseInt(args[2]);
			} else {
				impId = Integer.parseInt(args[1]);
			}
			TestData.changeTest(testName);
			if (impId >= clazz.length)
				return;
			String apiName = ((JsonInter) (clazz[impId].newInstance())).getSimpleName();

			long ms = -1;
			try {
				ms = bench(impId);
				addResult(cmd, testName, apiName, ms);
			} catch (Exception e) {
			}
		} else if (cmd.startsWith("showResult")) {
			formatResult();
		} else if (cmd.startsWith("showScript")) {
			showScript();
		}
	}

	public static void showScript() throws IOException {
		File startBench = new File("runbench.bat");

		StringBuilder sb = new StringBuilder();
		sb.append("@echo off\r\n");
		sb.append("set CLASSPATH=");
		File dir = new File("lib");
		for (String s : dir.list()) {
			if (s.endsWith(".jar"))
				sb.append("lib").append(File.separator).append(s).append(';');
		}
		sb.append("bin");
		sb.append("\r\n");

		String[] allbench = "benchPreload|bench".split("\\|");
		String[] allTest = "text|unicode|float|int|mixte|boolean".split("\\|");

		for (int pass = 0; pass < 10; pass++) {
			sb.append("echo '**************'\r\n");
			sb.append("echo 'pass " + pass + "'\r\n");
			sb.append("echo '**************'\r\n");
			for (String bench : allbench) {
				System.out.println("bench : " + bench);
				for (String test : allTest) {
					for (int id = 0; id < 11; id++) {
						sb.append("java ").append(JSonBench.class.getName());
						sb.append(" ").append(bench);
						sb.append(" ").append(test);
						sb.append(" ").append(id);
						sb.append("\r\n");
					}
				}
			}
		}

		sb.append("java ").append(JSonBench.class.getName());
		sb.append(" showResult > result.wiki").append("\r\n");
		sb.append("pause\r\n");
		FileUtils.writeStringToFile(startBench, sb.toString());
	}

	public static void formatResult() throws Exception {
		NumberFormat nf = NumberFormat.getPercentInstance();

		PrintStream ps = System.out;
		File file = new File("result.json");
		String text = FileUtils.readFileToString(file);
		JSONObject root = (JSONObject) JSONValue.parse(text);

		for (String bench : root.keySet()) {
			ps.println("");
			ps.println("== BenchMark : " + bench + "==");

			JSONObject allTest = (JSONObject) root.get(bench);
			Iterable<String> allApiName = getApiNames(allTest);
			Iterable<String> allTestName = allTest.keySet();

			TreeMap<String, Integer> total = new TreeMap<String, Integer>();
			TreeMap<String, Integer> bests = new TreeMap<String, Integer>();
			ArrayList<String> sortedApi = new ArrayList<String>();

			for (String api : allApiName) {
				int tot = 0;
				for (String test : allTestName) {
					JSONObject obj = (JSONObject) allTest.get(test);
					Number n = (Number) obj.get(api);

					if (n == null || n.intValue() == -1) {
						tot = -1;
						break;
					} else {
						tot += n.intValue();
						Number best = bests.get(test);
						if (best == null || best.intValue() > n.intValue())
							bests.put(test, n.intValue());
					}
				}
				total.put(api, tot);
				Number best = bests.get("total");
				if (tot > 0)
					if (best == null || best.intValue() > tot)
						bests.put("total", tot);

				String key_;
				api = tot + api;
				if (tot > 100000)
					key_ = api;
				else if (tot > 10000)
					key_ = "0" + api;
				else if (tot > 1000)
					key_ = "00" + api;
				else if (tot > 100)
					key_ = "000" + api;
				else if (tot > 10)
					key_ = "0000" + api;
				else if (tot == -1)
					key_ = "9000" + api;
				else
					key_ = "00000" + api;
				sortedApi.add(key_);
			}

			Collections.sort(sortedApi);
			Collections.reverse(sortedApi);
			for (int i = 0; i < sortedApi.size(); i++) {
				sortedApi.set(i, sortedApi.get(i).substring(6));
			}

			ps.print("|| Test || ");
			for (String testName : sortedApi) {
				ps.print(" *");
				ps.print(testName);
				ps.print("* ||");
			}
			ps.println("");

			for (String testName : allTestName) {
				ps.print("|| *");
				ps.print(testName);
				ps.print("* ||");
				JSONObject allApi = (JSONObject) allTest.get(testName);
				// for (String apiName : allApi.keySet()) {
				// }
				for (String apiName : sortedApi) {
					Number value = (Number) allApi.get(apiName);
					if (value == null || value.intValue() == -1)
						ps.print("N/A");
					else {

						ps.print(nf.format(value.floatValue() / bests.get(testName).floatValue()));
					}
					ps.print("||");
				}
				ps.println("");
			}

			ps.print("|| *TOTAL* ||");
			for (String apiName : sortedApi) {
				Number value = (Number) total.get(apiName);
				if (value == null || value.intValue() == -1)
					ps.print("N/A");
				else
					ps.print(nf.format(value.floatValue() / bests.get("total").floatValue()));
				// ps.print(nf.format(value));
				ps.print("||");
			}
			ps.println("");

		}
	}

	public static Iterable<String> getApiNames(JSONObject obj) throws Exception {
		for (String k : obj.keySet()) {
			obj = (JSONObject) obj.get(k);
			return obj.keySet();
		}
		return null;
	}

	public static void addResult(String bench, String testName, String apiName, long time) throws Exception {
		if (time == -1L)
			return;
		JSONObject root;
		File file = new File("result.json");

		if (file.exists()) {
			String text = FileUtils.readFileToString(file);
			root = (JSONObject) JSONValue.parse(text);
		} else {
			root = new JSONObject();
		}

		JSONObject current = root;
		// bench / benchPreload
		JSONObject tmp = (JSONObject) current.get(bench);
		if (tmp == null) {
			tmp = new JSONObject();
			current.put(bench, tmp);
		}
		current = tmp;

		// mixte, text, int, float ...
		tmp = (JSONObject) current.get(testName);
		if (tmp == null) {
			tmp = new JSONObject();
			current.put(testName, tmp);
		}
		current = tmp;

		// API name
		Number n = (Number) current.get(apiName);
		if (n == null || n.longValue() > time) {
			current.put(apiName, time);
			FileUtils.writeStringToFile(file, root.toJSONString());
			System.out.println("Score Updated");
		} else {
			System.out.println("Best : " + n);
		}

	}

	@SuppressWarnings("rawtypes")
	public static void test() throws Exception {
		/**
		 * build Test List
		 */
		final TestFormat formater = new TestFormatWikiColor(System.out);
		// final TestFormat formater = new TestFormatWiki(System.out);
		// final TestFormat formater = new TestFormatHTML(System.out);

		formater.fileStart();
		ArrayList<TestData> tests = TestData.getCompTests();
		ArrayList<JsonInter> apis = new ArrayList<JsonInter>();
		for (Class c : clazz) {
			try {
				JsonInter api = (JsonInter) c.newInstance();
				apis.add(api);
			} catch (Exception e) {
			}
		}

		formater.START_ARRAY();
		for (final TestData test : tests) {
			formater.setTestCost(test.score);
			formater.START_LINE();

			formater.START_CELL();
			if (test.data != null) {
				formater.formatTextData(test.message + "<br/>");
				String text = test.data.replace("\r\n", "\\r\\n");
				formater.formatTestData(text);
			} else if (test.message.equals("score")) {
				formater.formatTextData("pass count<br/>Score");
			} else if (test.message.equals("title")) {
				formater.formatTextData(" ");
			}
			formater.STOP_CELL();

			for (final JsonInter api : apis) {
				System.gc();
				if (test.data == null) {

					if (test.message.equals("title")) {
						// line num 1
						String name = api.getSimpleName();
						formater.title(name);
					} else if (test.message.equals("score")) {
						// last line
						String score = api.testPass + "/" + (tests.size() - 2) + "<br/>" + api.score;
						formater.title(score);
					}
					continue;
				}

				Runnable r = new Runnable() {
					@Override
					public void run() {
						TestData data = test;
						String output;
						JsonInter parsser = api;
						try {
							parsser.init();
							// Object obj =
							if (data.data.startsWith("["))
								parsser.parseArray(data.data);
							else
								parsser.parseObj(data.data);
							output = parsser.toJsonString();
							if (data.isValid(output)) {
								formater.cellOk();
								api.testPass++;
								api.score += data.score;
							} else {
								if (data.shouldStack) {
									formater.cellError(output);
								} else {
									formater.cellError(output);
								}
							}
						} catch (Exception e) {
							if (data.shouldStack) {
								formater.cellOk();
								api.testPass++;
							} else {
								formater.cellEx(e);
							}
						}
						formater.STOP_CELL();
					}
				};
				if (test.canKill && api.canDead()) {
					formater.START_CELL();
					formater.cellDead();
					// formater.cellError(null);
					formater.STOP_CELL();
				} else {
					Thread t = new Thread(r);
					t.start();
					for (int i = 0; i < threadPause; i++) {
						if (!t.isAlive()) {
							break;
						}
						Thread.sleep(20);
					}
					if (t.isAlive()) {
						t.interrupt();
						formater.cellDead();
						t.interrupt();
					}
				}
			}
			Thread.sleep(100);
			formater.STOP_LINE();
		}
		formater.STOP_ARRAY();
		formater.fileStop();
	}

	@SuppressWarnings("rawtypes")
	static void bench() throws Exception {
		ArrayList<String> msgs = TestData.genTestMessages();
		for (Class c : clazz) {
			bench(c, msgs);
		}
	}

	static long bench(int classId) throws Exception {
		if (classId >= clazz.length)
			return -1;
		return bench(clazz[classId], TestData.genTestMessages());
	}

	@SuppressWarnings("rawtypes")
	static long bench(Class c, ArrayList<String> msgs) throws Exception {
		int nbText = msgs.size();
		JsonInter p = (JsonInter) c.newInstance();
		String text = null;

		if (preload) {
			p.parseObj("{\"preload\":true, \"int\":1, \"float\":1.4, \"String\":\"text\"}").toString();
			System.gc();
			System.gc();
			System.gc();
			Thread.sleep(600L);
		}

		try {
			long T1 = System.currentTimeMillis();
			for (int i = 0; i < LOOP_COUNT; i++) {
				text = msgs.get(i % nbText);
				p.parseObj(text);
			}
			T1 = System.currentTimeMillis() - T1;
			System.out.println(TestData.testMode + "> " + p.getSimpleName() + " : " + T1 + "ms");
			return T1;
		} catch (Exception e) {
			// System.out.println("Parser error " + e);
			// System.out.println("failed on " + text);
			// e.printStackTrace();
		}
		return -1;
	}
}
