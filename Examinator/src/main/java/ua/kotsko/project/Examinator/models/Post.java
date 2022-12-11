package ua.kotsko.project.Examinator.models;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "posts")
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Date from;

    private Date to;

    @Column(name = "creation_date")
    private Date creationDate;

    private String restriction;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne()
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne()
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Media image;

}
