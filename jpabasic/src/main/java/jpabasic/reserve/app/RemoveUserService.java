package jpabasic.reserve.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.reserve.domain.User;
import jpabasic.reserve.jpa.EMF;

public class RemoveUserService {
	public void removeUser(String email) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class, email); // 객체 찾아옴
            if (user == null) {
                throw new NoUserException();
            }
            em.remove(user); //삭제 트랜젝션 안에서 해야한다. 그리고 find메소드로 찾아온 객체여야 삭제가 가능하다.그렇지 않으면 예외처리 발생
            //---> 지금 이시점 커밋 전에 다른 프로세스가 데이터를 db에서 삭제하면 예외처리 발생	
            tx.commit();
        } catch(Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
