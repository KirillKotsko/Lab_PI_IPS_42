package ua.kotsko.project.Examinator.models;

public enum MediaType {
    IMAGE("image"),VIDEO("video");

    private String type;

    MediaType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
