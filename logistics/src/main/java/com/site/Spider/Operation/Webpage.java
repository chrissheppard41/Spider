package com.site.Spider.Operation;

import com.site.Spider.Classes.Website;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;
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
    private String path;

    @NonNull
    @NotEmpty
    private Website website;

    @Getter private int statusCode;

    public Webpage getWebpage() throws IOException {

        if(this.path.isEmpty()) throw new FileNotFoundException("File not Found");

        StringBuilder sb = new StringBuilder();
        URL url = new URL(this.website.getName() + this.path);

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
                .map(link -> this.formatPath(link.attr("href")))
                .collect(Collectors.toList());
    }

    private boolean exclude(String url) {
        return url.contains("mailto:")
                || (url.contains("http://") && !url.contains(this.website.getName()))
                || (url.contains("https://") && !url.contains(this.website.getName()))
                || url.charAt(0) == '#'
                || !url.contains(".html")
                || !url.contains(".htm");
    }

    private String formatPath(String href) {

        //strip any local website calls
        href = href.replaceAll(this.website.getName(), "");
        //Sometimes the url doesn't have trailing / for the homepage url, if it doesn't, add one, do we want to ignore
        //this type of url though?
        if(href.length() == 0) href = "/";
        //does the url have contain ../, fix the ../ to the previous parent section
        else if(href.contains("../")) {
            //find all the counts of ../, sometimes there are more than 1, handle it accordingly
            int count = StringUtils.countOccurrencesOf(href, "../");
            //the character length of ../ is 3, if there is more than 1 (count) times them together to catch everything
            href = href.substring((count * 3));
            //bounce back to the parent url of the count
            href = this.getPathParent(this.path, count + 1) + "/" + href;
        }
        //if no / or if ./ at the begining, this url will need fixed
        else if(href.contains("./")) {
            href = href.substring(2);
            href = this.path.substring(0, this.path.lastIndexOf("/")) + "/" + href;
        }
        else if(href.charAt(0) != '/') {
            href = this.path.substring(0, this.path.lastIndexOf("/")) + "/" + href;
        }

        //strip out the index.html (keeps everything tidy)
        if(href.contains("/index")) {
            href = href.substring(0, href.indexOf("/index") + 1);
        }

        return href;
    }

    private String getPathParent(String path, int position) {
        String[] path_sections = path.split("/");
        if(path_sections.length == 0) return "";

        return Arrays.stream(Arrays.copyOf(path_sections, path_sections.length - position))
                .map(String::toString)
                .collect(Collectors.joining( "/" ));
    }

}
