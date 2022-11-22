
package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.reserve.domain.ActivityLog;
import jpabasic.reserve.jpa.EMFOracle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainSequence {
    private static Logger logger = LoggerFactory.getLogger(MainSequence.class);

    public static void main(String[] args) { //시퀀스 사용 방식
        EMFOracle.init();
        EntityManager em = EMFOracle.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ActivityLog log = new ActivityLog("U01", "VISIT"); //식별자 없음
            logger.info("persist 실행 전");
            em.persist(log); //식별자 생성함, 따라서 시퀀스를 구해오는 select 쿼리 돈다.
            logger.info("persist 실행 함");
            logger.info("생성한 식별자: {}", log.getId());
            logger.info("커밋하기 전");
            tx.commit(); //insert 쿼리가 실생됨
            logger.info("커밋함");
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        EMFOracle.close();
    }
}