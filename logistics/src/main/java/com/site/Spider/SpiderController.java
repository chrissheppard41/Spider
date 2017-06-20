package com.site.Spider;

import com.site.Spider.Classes.Page;
import com.site.Spider.Classes.Response;
import com.site.Spider.Operation.Spider;
import com.site.Spider.Repositories.IPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author chrissheppard
 * Created by chrissheppard on 17/06/2017.
 */
@RestController
@RequestMapping("/spider")
public class SpiderController {

    @Autowired
    IPageRepository pageRepository;

    @RequestMapping("/")
    public ResponseEntity<Response> index() {
        Response response = new Response(HttpStatus.OK, "");

        //@todo: if the site doesn't exist, handle it
        //@todo: check to see if the page exists?
        String website = "http://127.0.0.1:4000";

        Spider spider = new Spider(website);
        spider.cycle("/", "/");

        Map<String, Page> urls = spider.getUrls();
        urls.forEach((i, k) -> System.out.println(i + " called " + k));

        response.setData(urls);

        return new ResponseEntity<Response>(
                response,
                response.getStatus()
        );
    }

}
