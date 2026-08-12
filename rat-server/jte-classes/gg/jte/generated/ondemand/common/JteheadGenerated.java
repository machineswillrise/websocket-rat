package gg.jte.generated.ondemand.common;
@SuppressWarnings("unchecked")
public final class JteheadGenerated {
	public static final String JTE_NAME = "common/head.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,11,11,11,11,12,12,12,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String title) {
		jteOutput.writeContent("\n<head>\n\t<meta charset=\"UTF-8\">\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n\t<meta name=\"color-scheme\" content=\"light dark\">\n\t<link\n\t\trel=\"stylesheet\"\n\t\thref=\"https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.orange.min.css\"\n\t>\n\t<link rel=\"stylesheet\" href=\"/style.css\">\n\t<title>");
		jteOutput.setContext("title", null);
		jteOutput.writeUserContent(title);
		jteOutput.writeContent("</title>\n</head>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String title = (String)params.get("title");
		render(jteOutput, jteHtmlInterceptor, title);
	}
}
