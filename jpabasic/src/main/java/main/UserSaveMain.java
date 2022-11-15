package main;

import jakarta.persistence.*;
import jpabasic.reserve.domain.User;

import java.time.LocalDateTime;

public class UserSaveMain {
    public static void main(String[] args) {
    	//EntityManagerFactory는 영속단위기준으로 구분하여 즉 식별자인 'jpabegin'기준으로 생성
    	//식별자는 persistence.xml에 있음
    	//factory는 db연동과 관련된 자원을 생상하게함.
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpabegin");

        EntityManager entityManager = emf.createEntityManager(); //entitymanager 생성
        EntityTransaction transaction = entityManager.getTransaction(); // entitytransaction 생성
        try {
            transaction.begin(); // 트랜잭션 시작
            User user = new User("user@user.com", "user", LocalDateTime.now());
            entityManager.persist(user); 
            // insert 쿼리는 위의 식별자를 직접 적용하는 경우 커밋메소드 호출로 커밋 시점에 실행된다. 즉, 식별자가 따로 없다면 persist메소드 실행시 insert실행
            // 이는 영속컨텍스트 떄문이다. insert하는 영속객체 데이터가 영속컨텍스트에서 보관되고 db의 데이터가 비교되어 변화된것이 있는지 추적 후 커밋 시점에 쿼리 실행된다.
            transaction.commit(); // 커밋 
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback(); // 롤백
        } finally {
            entityManager.close(); //factory를 통해 사용한 자원을 반환하게 됨.
        }

        emf.close();
    }
}