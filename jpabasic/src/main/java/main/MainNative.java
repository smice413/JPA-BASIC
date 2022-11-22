package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.reserve.domain.Review;
import jpabasic.reserve.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainNative { //식별 칼럼 방식
    private static Logger logger = LoggerFactory.getLogger(MainNative.class);

    public static void main(String[] args) {
        EMF.init();
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Review review = new Review(5, "H-01", "작성자", "댓글");
            logger.info("persist 실행 전");
            em.persist(review); //persist() 호출시점에 insert쿼리 실행되고 식별자 할당됨
            logger.info("persist 실행 함");
            logger.info("생성한 식별자: {}", review.getId()); //식별자가 할당되었기 때문에 getId()식별자 사용가능
            logger.info("커밋하기 전");
            tx.commit();
            logger.info("커밋함");
        } catch (Exception ex) {
            tx.rollback();
        } finally {
            em.close();
        }
        EMF.close();
    }
}