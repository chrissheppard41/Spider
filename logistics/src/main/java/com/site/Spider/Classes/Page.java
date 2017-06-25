package com.site.Spider.Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrissheppard on 17/06/2017.
 */
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Page {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ElementCollection
    private List<String> callers = new ArrayList<>();

    @NonNull
    private String href;

    private boolean deadUrl = false;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "website_id")
    @JsonIgnore
    private Website website;

    public void addCaller(String caller) {
        this.callers.add(caller);
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", callers=" + callers +
                ", href='" + href + '\'' +
                ", deadUrl=" + deadUrl +
                ", website=" + website +
                '}';
    }
}
