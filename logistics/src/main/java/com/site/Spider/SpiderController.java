package com.site.Spider;

import com.site.Spider.Operation.Spider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

        String website = "http://127.0.0.1:4000";

        Spider spider = new Spider(website);
        spider.cycle("/", "/");

        spider.getUrls().forEach((i, k) -> System.out.println(i + " called " + k));

        return "Greetings from Spring Boot!";
    }

}
