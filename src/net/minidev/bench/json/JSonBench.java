package net.minidev.bench.json;

import java.util.ArrayList;
import net.minidev.bench.json.impl.*;
/**
 * Json Bench erntry point
 * 
 * @author Uriel Chemouni <uchemouni@gmail.com>
 *
 */
public class JSonBench {
	static int threadPause = 40;
	public final static int LOOP_COUNT = 500000;

	@SuppressWarnings("rawtypes")
	static Class[] clazz = new Class[] { StringTree.class, JSonIJ.class, Jackson.class, Agro.class, Simple.class,
			Sojo.class, Alibaba.class, NetSfJson.class, OrgJSonMe.class, OrgJSon.class, Minidev.class }; // ,

	// GoogleGson.class,
	public static void main(String[] args) throws Exception {
		args = new String[] { "test" };

		if (args.length == 0) {
			System.out.println("Help bench [text|unicode|float|int|mixte|boolean] [1..11]");
			System.out.println("Help test");
			return;
		}
		String cmd = args[0];
		if (cmd.equals("test")) {
			test();
			System.exit(0);
			return;
		}

		if (cmd.equals("bench")) {
			int impId = 0;
			String test = "mixte";

			if (args.length == 3) {
				test = args[1].toLowerCase();
				impId = Integer.parseInt(args[2]);
			} else {
				impId = Integer.parseInt(args[1]);
			}
			TestData.changeTest(test);
			bench(impId);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void test() throws Exception {
		/**
		 * build Test List
		 */
		//final TestFormat formater = new TestFormatWikiColor(System.out);
		//final TestFormat formater = new TestFormatWiki(System.out);
		final TestFormat formater = new TestFormatHTML(System.out);
		
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
		System.gc();
		Thread.sleep(1000);
		System.out.println("start");

		ArrayList<String> msgs = TestData.genTestMessages();
		for (Class c : clazz) {
			bench(c, msgs);
		}
	}

	static void bench(int classId) throws Exception {
		if (classId >= clazz.length)
			return;
		bench(clazz[classId], TestData.genTestMessages());
	}

	@SuppressWarnings("rawtypes")
	static void bench(Class c, ArrayList<String> msgs) throws Exception {
		int nbText = msgs.size();
		JsonInter p = (JsonInter) c.newInstance();
		String text = null;
		try {
			long T1 = System.currentTimeMillis();
			for (int i = 0; i < LOOP_COUNT; i++) {
				text = msgs.get(i % nbText);
				p.parseObj(text);
			}
			T1 = System.currentTimeMillis() - T1;
			System.out.println(TestData.testMode + "> " + p.getSimpleName() + " : " + T1 + "ms");
		} catch (Exception e) {
			System.out.println("Parser error " + e);
			System.out.println("failed on " + text);
			e.printStackTrace();
		}
		System.gc();
		Thread.sleep(500);
	}

}
