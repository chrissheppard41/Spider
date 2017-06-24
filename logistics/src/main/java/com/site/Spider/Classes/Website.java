package com.site.Spider.Classes;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrissheppard on 22/06/2017.
 */
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Setter
@Getter
public class Website {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "website", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Page> webpage = new ArrayList<>();

}
