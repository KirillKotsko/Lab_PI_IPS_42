package ua.kotsko.project.Examinator.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "medias")
public class Media {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    private Integer width;

    private Integer height;
}
