package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.acl.domain.Role;
import jpabasic.reserve.jpa.EMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class MainRole {
    private static Logger logger = LoggerFactory.getLogger(MainRole.class);

    public static void main(String[] args) throws Exception {
        clearAll();
        EMF.init();
        String roleId = "R07";
        saveRole(roleId);
        readRole(roleId);
        updateRoleByModifyingCollection(roleId);
        String roleId2 = "R19";
        saveRole(roleId2);
        updateRoleByAssigningNewSet(roleId2);
        revokeRole(roleId2);
    }

    private static void clearAll() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jpabegin?characterEncoding=utf8",
                "jpauser",
                "jpapass");
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate("delete from role_perm where role_id != ''");
            stmt.executeUpdate("delete from role where id != ''");
        }
    }

    private static void saveRole(String roleId) {
        logger.info("saveRole");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = new Role(roleId, "관리자", Set.of("F1", "F2"));
            em.persist(role);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void readRole(String roleId) {
        logger.info("readRole");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = em.find(Role.class, roleId); //lazy일때 role테이블만 select함. eager일때는 select쿼리에 role_perm테이블이 left join되어 한번에 조회함.
            logger.info("role id: {}", role.getId());
            for (String perm : role.getPermissions()) { //lazy이면 getPermissions()할때 role_perm테이블을 select돈다.
                logger.info("perm: {}", perm);
            }
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void updateRoleByModifyingCollection(String roleId) {
        logger.info("updateRoleByModifyingCollection");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = em.find(Role.class, roleId);
            role.getPermissions().add("F3");  //set 수정 insert됨
            role.getPermissions().remove("F1"); //set 수정 delete됨
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void updateRoleByAssigningNewSet(String roleId) {
        logger.info("updateRoleByAssigningNewSet");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = em.find(Role.class, roleId);
            role.setPermissions(Set.of("F4", "F5")); //setPermissions()로 값을 할당하면 delete쿼리로 id값에 맞춰 다지우고, insert함
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    private static void revokeRole(String roleId) {
        logger.info("revokeRole");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Role role = em.find(Role.class, roleId);
            role.revokeAll(); //Set clear() revokeAll()메소드에서 this.permissions.clear()할 때 role_perm Set 데이터 접근하여 select 쿼리 돌고(lazy로 돔), delete함
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}