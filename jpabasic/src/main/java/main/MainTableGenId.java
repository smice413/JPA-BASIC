
package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.reserve.domain.AccessLog;
import jpabasic.reserve.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class MainTableGenId { //테이블 저장 방식
    private static Logger logger = LoggerFactory.getLogger(MainTableGenId.class);

    public static void main(String[] args) {
        EMF.init();
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            AccessLog log = new AccessLog("/path01", LocalDateTime.now());
            logger.info("persist 실행 전");
            em.persist(log); //id-seq 테이블에서 시퀀스 값을 구해오고 nextval시퀀스값 증가시킴
            logger.info("persist 실행 함");
            logger.info("생성한 식별자: {}", log.getId()); //id가 시퀀스인 식별자 구해옴
            logger.info("커밋하기 전");
            tx.commit(); //insert 쿼리 실행됨
            logger.info("커밋함");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        EMF.close();
    }
}