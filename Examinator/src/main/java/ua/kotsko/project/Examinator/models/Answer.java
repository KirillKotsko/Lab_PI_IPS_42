package ua.kotsko.project.Examinator.models;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_text")
    private String content;

    @Column(name = "answer_iscorrect")
    private boolean correct;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_questionid")
    private Question question;

    public Answer() {
    	
    }

    public Answer(String content, boolean correct) {
        this.content = content;
        this.correct = correct;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.id && correct == answer.correct && Objects.equals(content, answer.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, correct);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", correct=" + correct +
                '}';
    }
}
