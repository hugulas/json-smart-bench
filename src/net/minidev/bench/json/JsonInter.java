package net.minidev.bench.json;

public abstract class JsonInter {
	abstract public Object parseObj(String json) throws Exception;
	abstract public Object parseArray(String json) throws Exception;
	abstract public String toJsonString() throws Exception;
	abstract public void init() throws Exception;

	public int testPass = 0;
	public int score = 0;
	abstract public String getSimpleName();
	public boolean canDead() { return false; }
}
