package jpabasic.reserve.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMF {
	private static EntityManagerFactory emf;
	
	public static void init() { //초기화
		emf = Persistence.createEntityManagerFactory("jpabegin");
	}
	
	public static EntityManager createEntityManager() { //엔티티 매니처 생성
		return emf.createEntityManager();
	}
	
	public static void close() { //엔티티 팩토리 닫기
		emf.close();
	}
}
