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

        String path = this.rootIndexFile(url_path);
        Webpage webpage = new Webpage(this.website.getName() + path);

        try {
            webpage.getWebpage().getWebpageLinks()
                    .forEach(link -> {
                        String formatted_link = this.rootIndexFile(link);

                        Page l = this.urls.get(formatted_link);
                        if(l == null) {
                            l = new Page(formatted_link, this.website);
                            l.addCaller(path);
                            this.urls.put(formatted_link, l);

                            this.cycle(formatted_link);

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

    private String rootIndexFile(String url) {
        //index and anchor id's are not required so you can remove them
        url = this.cleanUrl(url, "/index");
        url = this.cleanUrl(url, "#");
        //../ is not needed either and should be removed, bad url pathing
        if(url.contains("../")) {
            url = url.replaceFirst("../", "/");
        }

        return url;
    }

    private String cleanUrl(String url, String pattern) {
        if(url.contains(pattern)) {
            int position = url.lastIndexOf(pattern) + 1;
            url = url.substring(0, position);
        }

        return url;
    }

    public List<Page> getUrlsFromMap() {
        return new ArrayList(this.urls.values());
    }
}
