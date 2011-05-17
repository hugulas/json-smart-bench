package net.minidev.bench.json;

import java.io.PrintStream;

public class TestFormatWikiColor implements TestFormat {
	PrintStream out;
	
	public TestFormatWikiColor(PrintStream out) {
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
		out.append("<font color=\"#33FF33\">*Ok*</font>");
		
	}

	@Override
	public void cellError(String error) {
		out.append("<font color=\"#FF3333\">*Fail*</font>");
	}

	@Override
	public void cellEx(Exception e) {
		out.append("<font color=\"#FF6600\">throw</font>");
	}

	@Override
	public void cellDead() {
		out.append("<font color=\"#AAAAAA\">DEAD</font>");
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

	int prevScore = 100;
	public void setTestCost(int value) {
		if (prevScore != value) {
			if (value == 10) {
				out.append("= NON RFC 4627 Tests =\r\n");
				out.append("*Following tests not of interest to use standard*\r\n");
				out.append("\r\n");
			}
			if (value == 1) {
				out.append("= trap tests =\r\n");
				out.append("\r\n");
			}
			prevScore = value;
		}
	}
}
