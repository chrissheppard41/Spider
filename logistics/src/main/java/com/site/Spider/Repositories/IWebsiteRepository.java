package com.site.Spider.Repositories;

import com.site.Spider.Classes.Website;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author chrissheppard
 * Created by chrissheppard on 20/06/2017.
 */
public interface IWebsiteRepository extends CrudRepository<Website, Long> {

    List<Website> findAll();

    Website findById(long id);
}
