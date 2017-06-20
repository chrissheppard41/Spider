package com.site.Spider.Operation;

import com.site.Spider.Classes.Page;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrissheppard on 17/06/2017.
 */
@RequiredArgsConstructor
public class Webpage {

    private String webpage;

    @NonNull
    @NotEmpty
    private String url;

    public Webpage getWebpage() throws FileNotFoundException, MalformedURLException, IOException {

        if(url.isEmpty()) throw new FileNotFoundException("File not Found");

        StringBuilder sb = new StringBuilder();
        URL url = new URL(this.url);
        URLConnection con = url.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        con.getInputStream()
                )
        );
        in.lines()
                .forEach(line -> sb.append(line));

        in.close();

        this.webpage = sb.toString();

        return this;
    }

    public List<Page> getWebpageLinks() throws FileNotFoundException {
        List<Page> links = new ArrayList<>();

        if(this.webpage.isEmpty()) throw new FileNotFoundException("Webpage is empty");

        Document doc = Jsoup.parse(this.webpage.toString());

        Elements a = doc.getElementsByTag("a");

        a.stream()
                .forEach(link -> {
                    String href = link.attr("href");

                    if(!this.exclude(href)) {

                        Page l = new Page(href);

                        links.add(l);
                    }
                });

        return links;
    }

    private boolean exclude(String url) {
        boolean exc = false;

        if(url.contains("mailto:")
                || url.contains("http://")
                || url.contains("https://")) {
            return true;
        }

        return exc;
    }

}
