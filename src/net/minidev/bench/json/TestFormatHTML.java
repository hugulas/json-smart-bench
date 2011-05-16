package net.minidev.bench.json;

import java.io.PrintStream;

public class TestFormatHTML implements TestFormat {
	PrintStream out;
	
	public TestFormatHTML(PrintStream out) {
		this.out = out;
	}
	
	public void title(String text)
	{
		out.print("<TD>");
		out.print(text);
		out.print("</TD>");
	}
	
	public void formatTextData(String text) {
		out.append(text);
	}
	
	public void START_ARRAY() {
		out.print("<table>"); 
	}
	public void STOP_ARRAY() {
		out.print("</table>");
	}

	public void START_LINE() {
		out.print("<TR>");
	}
	public void STOP_LINE() {
		out.print("</TR>");
	}

	public void START_CELL() {
		out.print("<TD>");
	}
	public void STOP_CELL() {
		out.print("</TD>");
	}

	public void cellOk() {
		out.println("<TD class='ok'>Pass</TD>");
	}

	public void cellError(String error) {
		out.print("<TD class='error'>Fail");
		out.print("<br/><span class='debug'>" + error + "</span>");
		out.println("</TD>");
	}

	public void cellEx(Exception e) {
		out.println("<TD class='stack'><small>throw</small></TD>");
	}

	public void cellDead() {
		out.println("<TD class='dead'>DEAD</TD>");
	}

	public void fileStart() {
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		out.println("<title>Results</title>");
		out.println("<style type=\"text/css\"><!--");
		out.println("#tab, #tab caption");
		out.println("{margin: auto;}");

		// out.println("td {  }");
		String common = "text-align: center;width:8%;";
		out.println(".ok {background-color: #AAFFAA;" + common + "}");
		out.println(".error {background-color: #FFAAAA;" + common + "}");
		out.println(".stack {background-color: #FFAAAA;font-size:0.9em;" + common + "}");
		out.println(".dead {background-color: #111111; color:#FFFFFF;" + common + "}");

		out.println(".debug {font-size:0.4em}");
		out.println(".result {" + common + "}");

		// width: 50px;
		// height: 30px;
		// background-image: url(images/bubble_arrow_quad.png);
		// background-repeat: no-repeat;
		// background-position: 0px -30px;
		// }

		out.println("--></style>");
		out.println("<body>");
		out.println("<BR/>");
		out.println("<BR/>");
	}

	public void fileStop() {
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
	
	public void formatTestData(String text){
		out.append("<code>").append(text).append("</code>");
	}
}
