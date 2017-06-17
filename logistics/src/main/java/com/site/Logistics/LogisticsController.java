package com.site.Logistics;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chrissheppard
 * Created by chrissheppard on 17/06/2017.
 */
@RestController
@RequestMapping("/logistics")
public class LogisticsController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
