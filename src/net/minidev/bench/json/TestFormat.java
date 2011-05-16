package net.minidev.bench.json;

/**
 * @see TestFormatWiki
 * @see TestFormatWikiColor
 * @see TestFormatHTML
 * @author Uriel Chemouni <uchemouni@gmail.com>
 *
 */
public interface TestFormat {
	public void START_ARRAY();
	public void STOP_ARRAY();
	public void START_LINE();
	public void STOP_LINE();
	public void START_CELL();
	public void STOP_CELL();
	
	public void title(String text);
	
	public void cellOk();
	public void cellError(String error);
	public void cellEx(Exception e);
	public void cellDead();
	public void fileStart();
	public void fileStop();
	public void formatTestData(String text);
	public void formatTextData(String text);
}
