package net.minidev.bench.json;

import java.util.ArrayList;

import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;

public abstract class JsonInter extends JapexDriverBase {
	abstract public Object parseObj(String json) throws Exception;

	abstract public Object parseArray(String json) throws Exception;

	abstract public String toJsonString() throws Exception;

	abstract public void init() throws Exception;

	public int testPass = 0;
	public int score = 0;

	abstract public String getSimpleName();

	public boolean canDead() {
		return false;
	}

	ArrayList<String> tests;

	@Override
	public void prepare(TestCase testCase) {
		super.prepare(testCase);
		String type = testCase.getParam("japex.testType");
		tests = BenchData.getTestMessages(type, true);
	}

	@Override
	public void run(TestCase testCase) {
		String s = "---";
		try {
			int len = tests.size();
			for (int i=0; i< len; i++) {
				s = tests.get(i);
				parseObj(s);
			}
		} catch (Exception e) {
			throw new RuntimeException(s, e);
		}
	}

	@Override
	public void warmup() {
		super.warmup();
	}

	@Override
	public void warmup(TestCase testCase) {
		run(testCase);
	}

	@Override
	public void finish(TestCase testCase) {
		super.finish(testCase);
	}

}
