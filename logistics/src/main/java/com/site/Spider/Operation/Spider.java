package com.site.Spider.Operation;

import com.site.Spider.Classes.Page;
import com.site.Spider.Classes.Website;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chrissheppard
 * Created by chrissheppard on 17/06/2017.
 */
@RequiredArgsConstructor
public class Spider {

    private static final Logger log = LoggerFactory.getLogger(Spider.class);

    @NonNull
    private Website website;
    @Getter private Map<String, Page> urls = new HashMap<>();

    //@todo: think about a better way of handling this maybe
    public void cycle(String url_path) {
        log.info("Looking at webpage :: " + url_path);

        String path = url_path;
        Webpage webpage = new Webpage(path, this.website);

        try {
            webpage.getWebpage().getWebpageLinks()
                    .forEach(link -> {
                        Page l = this.urls.get(link);
                        if(l == null) {
                            l = new Page(link, this.website);
                            l.addCaller(path);
                            this.urls.put(link, l);

                            this.cycle(link);

                        } else {
                            if(!l.getCallers().contains(path)) {
                                l.addCaller(path);
                            }
                        }
                    });
        } catch (IOException e) {
            log.error("Page :: " + path + " :: " + e.getMessage());

            Page l = this.urls.get(path);
            l.setDeadUrl(true);

        }
    }

    public List<Page> getUrlsFromMap() {
        return new ArrayList(this.urls.values());
    }
}
