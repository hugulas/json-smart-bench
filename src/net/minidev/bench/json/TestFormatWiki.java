package net.minidev.bench.json;

import java.io.PrintStream;

public class TestFormatWiki implements TestFormat {
	PrintStream out;
	
	public TestFormatWiki(PrintStream out) {
		this.out = out;
	}

	public void title(String text)
	{
		out.print(text);
		out.print("||");
	}
	
	public void formatTextData(String text) {
		out.append(text);
	}

	@Override
	public void START_ARRAY() {
	}

	@Override
	public void STOP_ARRAY() {
	}

	@Override
	public void START_LINE() {
		out.print("||");
	}

	@Override
	public void STOP_LINE() {
		out.println("");
	}

	@Override
	public void START_CELL() {
	}

	@Override
	public void STOP_CELL() {
		out.print("||");
	}

	@Override
	public void cellOk() {
		out.append("Ok");
	}

	@Override
	public void cellError(String error) {
		out.append("Error");
	}

	@Override
	public void cellEx(Exception e) {
		out.append("Ex");
	}

	@Override
	public void cellDead() {
		out.append("Dead");
	}

	@Override
	public void fileStart() {
		out.append("");
	}

	@Override
	public void fileStop() {
		out.append("\r\n");
		out.close();
	}

	public void formatTestData(String text){
		out.append(" {{{ ").append(text).append(" }}} ");
	}
	
	public void setTestCost(int value) {
	}
}
