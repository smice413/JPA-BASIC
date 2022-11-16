package jpabasic.reserve.app;

import jakarta.persistence.EntityManager;
import jpabasic.reserve.domain.User;
import jpabasic.reserve.jpa.EMF;

public class GetUserService {
	public User getUser(String email) {
		EntityManager em = EMF.createEntityManager();
		try {
			User user = em.find(User.class, email); //조회 find메소드는 값이 없으면 null 리턴함. 엔티티타입과 id가 일치해야함
			if(user == null) {
				throw new NoUserException();
			}
			return user;
		}finally {
			
		}
	}
}
