package ua.kotsko.project.Examinator.models;

public enum Subject {
	MATH("Math"),
    SCIENCE("Science"),
    NATURE("Nature"),
    HISTORY("History"),
    CULTURE("Culture"),
    IT("IT");

    private String title;

    Subject(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}