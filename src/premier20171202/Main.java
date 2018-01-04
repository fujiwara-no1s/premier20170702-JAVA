package premier20171202;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  private static final String GOOGLE_URL = 
      "https://www.google.co.jp/search?q=%E6%B2%96%E7%B8%84%E3%80%80%E9%AB%98%E7%B4%9A%E3%83%9B%E3%83%86%E3%83%AB";
  private static final String CHROME_USERAGENT = 
      "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
  private static final String regexPattern = 
      "<h3 class=\"r\"><a href=\"([^\"]*?)\"[^>]*?>(.+?)</a></h3>";

  public static void main(String[] args) {
    // get html
    String html = getHtml();
    // parse html with regex and display results
    showResult(html);
  }

  public static String getHtml() {
    BufferedReader reader = null;
    String html = "";
    try {
      URLConnection connection = new URL(GOOGLE_URL).openConnection();
      connection.setDoInput(true);
      connection.setRequestProperty("User-Agent", CHROME_USERAGENT);
      reader = new BufferedReader(
          new InputStreamReader(connection.getInputStream()));
      String line = "";
      StringBuilder sb = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
      html = sb.toString();
    } catch (IOException ioe) {
      System.out.println(ioe.toString());
    } catch (Exception ex) {
      System.out.println(ex.toString());
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException ioe) {
          System.out.println(ioe.toString());
        }
      }
    }
    return html;
  }

  public static void showResult(String html) {
    Matcher matcher = Pattern.compile(regexPattern).matcher(html);
    while (matcher.find()) {
      String href = matcher.group(1);
      String title = matcher.group(2);
      System.out.println("<<< " + title + " >>>");
      System.out.println(href);
      System.out.println("---------------");
    }
  }
}
