package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JtedashboardGenerated {
	public static final String JTE_NAME = "dashboard.jte";
	public static final int[] JTE_LINE_INFO = {3,3,3,3,3,3,3,3,15,15,15,15,15,15};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<!DOCTYPE html>\n<html lang=\"en\">\n\t<head>\n\t\t");
		gg.jte.generated.ondemand.common.JteheadGenerated.render(jteOutput, jteHtmlInterceptor, "Dashboard");
		jteOutput.writeContent("\n\t</head>\n\n\t<body>\n\t\t<main class=\"container\">\n\t\t\t<article class=\"card\">\n\t\t\t\t<h3>Dashboard</h3>\n\t\t\t\t<a href=\"/api/admin/logout\" role=\"button\">Log Out</a>\n\t\t\t</article>\n\t\t</main>\n\t</body>\n</html>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
