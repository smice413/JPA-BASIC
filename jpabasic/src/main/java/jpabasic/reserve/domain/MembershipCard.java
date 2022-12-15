package jpabasic.reserve.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "membership_card")
public class MembershipCard {
    @Id
    @Column(name = "card_no")
    private String cardNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email") //참조키 방식 1-1 단방향 연관 매핑
    private User owner;
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    private boolean enabled;

    protected MembershipCard() {
    }

    public MembershipCard(String cardNumber, User owner, LocalDate expiryDate) {
        this.cardNumber = cardNumber;
        this.owner = owner;
        this.expiryDate = expiryDate;
        this.enabled = false;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public User getOwner() {
        return owner;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isEnabled() {
        return enabled;
    }
}