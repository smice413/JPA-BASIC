package jpabasic.acl2.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

//@Embeddable 타입 클래스는 반드시 아래의 equals메소드와 hashCode 메소드가 필요하다.
//equals는 Set으로 데이터를 주고 받기 때문에 비교할때 필요하고, new HashSet<>()코드이기 때문에 hashCode메소드 필요하다. 
@Embeddable 
public class GrantedPermission {
    @Column(name = "perm")
    private String permission;
    private String grantor;

    protected GrantedPermission() {}

    public GrantedPermission(String permission, String grantor) {
        this.permission = permission;
        this.grantor = grantor;
    }

    public String getPermission() {
        return permission;
    }

    public String getGrantor() {
        return grantor;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrantedPermission that = (GrantedPermission) o;
        return Objects.equals(permission, that.permission) && Objects.equals(grantor, that.grantor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission, grantor);
    }

    @Override
    public String toString() {
        return "GrantedPermission{" +
                "permission='" + permission + '\'' +
                ", grantor='" + grantor + '\'' +
                '}';
    }
}