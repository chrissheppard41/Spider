package com.site.Spider.Classes;

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
@Data
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "website_id")
    private Website website;

    public Page(String href, boolean deadUrl) {
        this.callers.add(href);
        this.href = href;
        this.deadUrl = deadUrl;
    }

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
