package com.site.Spider;

import com.site.Spider.Classes.Page;
import com.site.Spider.Classes.Response;
import com.site.Spider.Operation.Spider;
import com.site.Spider.Repositories.IPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/spider", method = RequestMethod.GET)
    public ResponseEntity<Response> spider(@RequestParam("website") String website) {
        Response response = new Response(HttpStatus.OK, "");

        Spider spider = new Spider(website);
        spider.cycle("/", "/");

        Map<String, Page> urls = spider.getUrls();
        response.setData(urls);

        pageRepository.save(spider.getUrlsFromMap());

        return new ResponseEntity<Response>(
                response,
                response.getStatus()
        );
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Response> get(@RequestParam("website") String website) {
        Response response = new Response(HttpStatus.OK, "");

        //@todo: website should be by Id so create a website controller and link
        response.setData(pageRepository.findAll());

        return new ResponseEntity<Response>(
                response,
                response.getStatus()
        );
    }

}
