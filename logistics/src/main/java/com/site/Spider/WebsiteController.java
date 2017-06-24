package com.site.Spider;

import com.site.Spider.Classes.Response;
import com.site.Spider.Classes.Website;
import com.site.Spider.Repositories.IWebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chrissheppard
 * Created by chrissheppard on 22/06/2017.
 */
@RestController
@RequestMapping("/website")
public class WebsiteController {

    @Autowired
    IWebsiteRepository websiteRepository;

    //@todo: swap to POST
    //@todo: validation
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Response> get(@RequestParam("website") String name) {
        Response response = new Response(HttpStatus.OK, "");

        Website website = new Website(name);

        long id = websiteRepository.save(website).getId();

        response.setData(id);

        return new ResponseEntity<Response>(
                response,
                response.getStatus()
        );
    }
}
