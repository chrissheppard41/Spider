package com.site.Spider.Operation;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chrissheppard
 * Created by chrissheppard on 17/06/2017.
 */
@RequiredArgsConstructor
public class Webpage {

    private String webpage = "";

    @NonNull
    @NotEmpty
    private String url;

    @Getter private int statusCode;

    //@todo: pages that start with no / find the parent that calls them, remove their end path and add in the page path
    public Webpage getWebpage() throws IOException {

        if(url.isEmpty()) throw new FileNotFoundException("File not Found");

        StringBuilder sb = new StringBuilder();
        URL url = new URL(this.url);

        if(this.checkWebpage(url)) {

            URLConnection con = url.openConnection();

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            con.getInputStream()
                    )
            )) {
                in.lines()
                        .forEach(sb::append);

                in.close();

                this.webpage = sb.toString();
            }


        }
        return this;
    }

    private boolean checkWebpage(URL url) throws IOException {
        HttpURLConnection huc =  (HttpURLConnection)  url.openConnection();
        huc.setRequestMethod ("GET");
        huc.connect () ;
        this.statusCode = huc.getResponseCode();
        return this.statusCode == 200;
    }

    public List<String> getWebpageLinks() throws FileNotFoundException {
        if(this.webpage.isEmpty()) throw new FileNotFoundException("Webpage is empty");

        Document doc = Jsoup.parse(this.webpage);

        Elements a = doc.getElementsByTag("a");

        return a.stream()
                .filter(link -> !this.exclude(link.attr("href")))
                .map(link -> link.attr("href"))
                .collect(Collectors.toList());
    }

    //@todo: don't exclude https/http calls to itself, only exclude external website calls
    private boolean exclude(String url) {
        return url.contains("mailto:") || url.contains("http://") || url.contains("https://") || url.charAt(0) == '#';
    }

}
