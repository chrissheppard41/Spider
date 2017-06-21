package com.site.Spider.Operation;

import com.site.Spider.Classes.Page;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chrissheppard on 17/06/2017.
 */
@RequiredArgsConstructor
public class Spider {

    private static final Logger log = LoggerFactory.getLogger(Spider.class);

    @NonNull
    private String website;
    @Getter private Map<String, Page> urls = new HashMap<String, Page>();

    //@todo: think about a better way of handling this maybe?
    public void cycle(String parent_url, String url) {
        log.info("Looking at webpage :: " + url);

        Webpage webpage = new Webpage(this.website + url);

        try {
            webpage.getWebpage().getWebpageLinks().forEach(link -> {
                Page l = this.urls.get(link.getHref());
                if(l == null) {
                    log.info("Not been found, looking for links :: " + this.website + link.getHref());

                    this.urls.put(link.getHref(), link);

                    this.cycle(url, link.getHref());
                } else {
                    l.addCaller(url);
                    log.warn("Already found ignoring :: " + this.website + link.getHref());
                }
            });
        } catch (Exception e) {
            log.error("Dead url :: " + this.website + url + " :: " + e.toString());
            //@todo: could this be written better? Of course it can
            Page l = this.urls.get(url);
            if(l == null) {
                l = new Page(url);
                l.addCaller(parent_url);
                l.setDeadUrl(true);
                this.urls.put(url, l);
            } else {
                l.addCaller(parent_url);
                l.setDeadUrl(true);
            }
        }
    }

    public List<Page> getUrlsFromMap() {
        return new ArrayList(this.urls.values());
    }
}
