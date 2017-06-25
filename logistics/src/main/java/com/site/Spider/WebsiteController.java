package com.site.Spider;

import com.site.Spider.Classes.Response;
import com.site.Spider.Classes.Website;
import com.site.Spider.Repositories.IWebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author chrissheppard
 * Created by chrissheppard on 22/06/2017.
 */
@RestController
@RequestMapping("/website")
public class WebsiteController {

    @Autowired
    private IWebsiteRepository websiteRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<Response> post(@RequestBody @Valid Website website) {
        Response response = new Response(HttpStatus.OK, "");

        long id = websiteRepository.save(website).getId();
        response.setData(id);

        return new ResponseEntity<>(
                response,
                response.getStatus()
        );
    }


    @RequestMapping(value = "/pages/", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Response> getPages(@RequestParam("website") long website) {
        Response response = new Response(HttpStatus.OK, "");

        response.setData(websiteRepository.findAll());

        return new ResponseEntity<>(
                response,
                response.getStatus()
        );
    }
}
