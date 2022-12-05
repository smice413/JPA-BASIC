package jpabasic.question.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    private String id;
    private String text;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "question_choice", //컬랙션 테이블 이름
            joinColumns = @JoinColumn(name = "question_id")//조인할 아이디컬럼
    )
    @OrderColumn(name = "idx") //Set과 다른점으로 List에서 index값을 저장할 칼럼
    @Column(name = "text")//값이 들어갈 컬럼
    private List<String> choices = new ArrayList<>();

    protected Question() {}

    public Question(String id, String text, List<String> choices) {
        this.id = id;
        this.text = text;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}