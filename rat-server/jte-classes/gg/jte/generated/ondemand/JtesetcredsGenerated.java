package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JtesetcredsGenerated {
	public static final String JTE_NAME = "set-creds.jte";
	public static final int[] JTE_LINE_INFO = {3,3,3,3,3,3,3,3,19,19,19,19,19,19};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<!DOCTYPE html>\n<html lang=\"en\">\n\t<head>\n\t\t");
		gg.jte.generated.ondemand.common.JteheadGenerated.render(jteOutput, jteHtmlInterceptor, "Set Credentials");
		jteOutput.writeContent("\n\t</head>\n\n\t<body>\n\t\t<main class=\"container\">\n\t\t\t<article class=\"card\">\n\t\t\t\t<h3>Set Credentials</h3>\n\t\t\t\t<form action=\"/api/admin/set-creds\" method=\"POST\">\n\t\t\t\t\t<input type=\"text\" placeholder=\"Username\" name=\"username\" required>\n\t\t\t\t\t<input type=\"password\" placeholder=\"Password\" name=\"password\" required>\n\t\t\t\t\t<button type=\"submit\">Done</button>\n\t\t\t\t</form>\n\t\t\t</article>\n\t\t</main>\n\t</body>\n</html>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
