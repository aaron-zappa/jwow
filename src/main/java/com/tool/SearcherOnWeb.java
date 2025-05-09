package com.tool;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
public class SearcherOnWeb {

    /**
     * Performs a web search using DuckDuckGo for the given words.
     *
     * @param words The search query words.
     * @return The search results as a string.
     */
    public String casearch(String words) {
        try {
            String encodedWords = URLEncoder.encode(words, StandardCharsets.UTF_8.toString());
            String searchUrl = "https://duckduckgo.com/?q=" + encodedWords;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(searchUrl);

                try (CloseableHttpResponse response = httpClient.execute(request)) {
                    String htmlResponse = EntityUtils.toString(response.getEntity());

                    Document doc = Jsoup.parse(htmlResponse);
                    Elements results = doc.select(".nrn-react-div"); // This selector might need adjustment based on DuckDuckGo's HTML structure

                    StringBuilder searchResults = new StringBuilder("Search Results for '").append(words).append("':\\n");

                    if (results.isEmpty()) {
                        searchResults.append("No results found or selector needs adjustment.");
                    } else {
                        for (int i = 0; i < Math.min(results.size(), 5); i++) { // Limit to top 5 results
                            Elements titleElement = results.get(i).select(".Wo6ZA_Vf1KpdIuIe5mVs a"); // Selector for title and link
                            String title = titleElement.text();
                            String url = titleElement.attr("href");
                            searchResults.append("- ").append(title).append(" (").append(url).append(")\\n");
                        }
                    }
                    return searchResults.toString();
                }
            }
        } catch (Exception e) {
            return "Error performing search: " + e.getMessage();
        }
    }
}