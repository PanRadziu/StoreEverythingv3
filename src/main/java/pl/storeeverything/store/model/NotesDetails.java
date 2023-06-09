package pl.storeeverything.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "notes")
public class NotesDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;
    @NotBlank(message = "Title is required")
    @Size(min =3,max = 30, message = "Title cannot exceed 50 characters")
    private String title;
    @Size(min =3,max = 500, message = "Title cannot exceed 500 characters")
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    @Column(name = "link")
    private String link;
//    @Column(name = "date", nullable = false)
//    @NotNull
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;
    @Column(name = "remind_date",nullable = false)
    private String remind_date;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_name")

    private CategoryDetails category;

    public NotesDetails(Long id, String title, String description, String link, Date date, String remind_date, CategoryDetails category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        date = Calendar.getInstance().getTime();
        this.remind_date = remind_date;
        this.category = category;
    }

    public NotesDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public void setDate(String date) {
        dateFormat.format(date);
    }

    public String getRemind_date() {
        return remind_date;
    }

    public void setRemind_date(String remind_date) {
        this.remind_date = remind_date;
    }

    public CategoryDetails getCategory() {
        return category;
    }

    public void setCategory(CategoryDetails category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "NotesDetails{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", date='" + date + '\'' +
                ", remind_date='" + remind_date + '\'' +
                ", category='" + category + '\'' +
                '}';
    }


}
