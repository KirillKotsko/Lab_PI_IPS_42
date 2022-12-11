package ua.kotsko.project.Examinator.repository;

import org.springframework.core.env.Environment;

public abstract class AbstractRepository {

    private Environment environment;

    protected String URL;

    public static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    public static final String USER_KEY = "?user=";
    public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    public static final String PASSWORD_KEY = "&password=";

    public AbstractRepository(Environment environment) {
        this.environment = environment;
        buildURL(environment);
    }

    private void buildURL(Environment env) {
        StringBuilder urlBuilder = new StringBuilder(env.getProperty(SPRING_DATASOURCE_URL));
        urlBuilder.append(USER_KEY).append(env.getProperty(SPRING_DATASOURCE_USERNAME));
        urlBuilder.append(PASSWORD_KEY).append(env.getProperty(SPRING_DATASOURCE_PASSWORD));
        URL = urlBuilder.toString();
    }

}
