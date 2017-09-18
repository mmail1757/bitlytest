package com.avbx;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiTests {

    String token = "d017e649f51215b089047f020083084dad668e47";
    String apiKey = "R_3addff974ca14c4db7b959ee528d6e9c";

    String shortenUrl = "https://api-ssl.bitly.com/v3/shorten?access_token=";
    String userInfoUrl = "https://api-ssl.bitly.com/v3/user/info?access_token=";
    String linkHistoryUrl = "https://api-ssl.bitly.com/v3/user/link_history?access_token=";

    String link = "http://yahoo.com/";
    String login = "usern2";

    List<String> links = asList("http://google.com/", "http://bing.com/");

    @Test
    public void testShortenUrl() throws UnsupportedEncodingException {
        String encodedURL = URLEncoder.encode(link, "UTF-8");
        List<JSONObject> list = getJSONList(shortenUrl + token + "&longUrl=" + encodedURL);
        list.forEach(item -> {
            assertEquals( ((Map<String, String>)item.get("data")).get("long_url"), link);
        });
    }

    @Test
    public void testUserInfo() throws UnsupportedEncodingException {
        List<JSONObject> list = getJSONList(userInfoUrl + token);
        list.forEach(item -> {
            assertEquals( ((Map<String, String>)item.get("data")).get("login"), login);
            assertEquals( ((Map<String, String>)item.get("data")).get("apiKey"), apiKey);
        });
    }

    @Test
    public void testLinkHistory() throws UnsupportedEncodingException {
        List<JSONObject> list = getJSONList(linkHistoryUrl + token);
        List<String> linksList = new ArrayList<>();
        list.forEach(item -> {
            List<Map<String, String>> mapsList = (List<Map<String, String>>) ((Map<String, Object>) item.get("data")).get("link_history");
            mapsList.forEach(t -> {
                linksList.add(t.get("long_url"));
            });
        });
        links.forEach(it -> assertTrue(linksList.contains(it)));
    }

    private List<JSONObject> getJSONList(String url) {

        List<JSONObject> result = new ArrayList<>();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpGet httpGet = new HttpGet(url);
            HttpResponse response1 = httpClient.execute(httpGet);
            String json = EntityUtils.toString(response1.getEntity(), "UTF-8");

            try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);

                if (resultObject instanceof JSONArray) {
                    JSONArray array = (JSONArray) resultObject;
                    for (Object object : array) {
                        result.add( (JSONObject) object);
                    }

                } else if (resultObject instanceof JSONObject) {
                    result.add( (JSONObject) resultObject);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

}
