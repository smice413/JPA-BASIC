package jpabasic.reserve.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "best_pick")
public class BestPick {
    @Id
    @Column(name = "user_email")
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "user_email") //식별자 공유 방식1-1 단방향 연관 매핑
    private User user;
    private String title;

    protected BestPick() {
    }

    public BestPick(User user, String title) {
        this.email = user.getEmail();
        this.user = user;
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }
}