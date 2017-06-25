package com.site.Spider.Classes;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chrissheppard
 * Created by chrissheppard on 22/06/2017.
 */
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Website {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "website", cascade = CascadeType.ALL)
    private List<Page> webpage = new ArrayList<>();

}
