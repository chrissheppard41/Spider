package com.site.Spider.Repositories;

import com.site.Spider.Classes.Page;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by chrissheppard on 20/06/2017.
 */
public interface IPageRepository extends CrudRepository<Page, Long> {

    List<Page> findAll();

    List<Page> findByWebsiteId(long id);

}
