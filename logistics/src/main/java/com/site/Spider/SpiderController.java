package com.site.Spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author chrissheppard
 * Created by chrissheppard on 17/06/2017.
 */
@RestController
@RequestMapping("/spider")
public class SpiderController {


    @RequestMapping("/")
    public String index() {
        //Attempt to get the website data
        //look for all the urls on the web page
        //store and check to see if they link somewhere
        //save that result as a pass or fail
        //return that result

        StringBuilder sb = new StringBuilder();
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:4000/");
            URLConnection con = url.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            con.getInputStream()
                    )
            );
            in.lines()
                    .forEach(line -> sb.append(line));

            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(sb.length() != 0) {
            Document doc = Jsoup.parse(sb.toString());

            Elements a = doc.getElementsByTag("a");

            a.stream()
                    .forEach(link -> {
                        System.out.println(link.attr("href"));
                    });
        }

        return "Greetings from Spring Boot!";
    }

}
