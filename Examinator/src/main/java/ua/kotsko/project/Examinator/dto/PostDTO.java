package ua.kotsko.project.Examinator.dto;

import lombok.Builder;
import lombok.Data;

@Builder
public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private String firstNameCreator;
    private String lastNameCreator;
    private String dateFrom;
    private MediaDTO image;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstNameCreator() {
        return firstNameCreator;
    }

    public void setFirstNameCreator(String firstNameCreator) {
        this.firstNameCreator = firstNameCreator;
    }

    public String getLastNameCreator() {
        return lastNameCreator;
    }

    public void setLastNameCreator(String lastNameCreator) {
        this.lastNameCreator = lastNameCreator;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public MediaDTO getImage() {
        return image;
    }

    public void setImage(MediaDTO image) {
        this.image = image;
    }
}
