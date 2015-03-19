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


	public void START_ARRAY() {
	}


	public void STOP_ARRAY() {
	}


	public void START_LINE() {
		out.print("||");
	}


	public void STOP_LINE() {
		out.println("");
	}


	public void START_CELL() {
	}


	public void STOP_CELL() {
		out.print("||");
	}


	public void cellOk() {
		out.append("Ok");
	}


	public void cellError(String error) {
		out.append("Error");
	}


	public void cellEx(Exception e) {
		out.append("Ex");
	}


	public void cellDead() {
		out.append("Dead");
	}


	public void fileStart() {
		out.append("");
	}


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
