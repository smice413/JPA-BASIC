package jpabasic.acl.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    private String id;
    private String name;

    //@ElementCollection 조회가 기본적으로 lazy이다. lazy는 role테이블을 조회하고 role_perm조회가 필요할 때 그 시점에 조회 즉, find는 role만 조회하고 ,getPermission()할 때 lazy로 role_perm테이블 조회함
    //@ElementCollection(fetsh = FetchType.EAGER)는 find할 떄 left join하여 role_perm까지 한번에 조회함.
    @ElementCollection
    @CollectionTable( 
            name = "role_perm", //콜랙션 테이블명
            joinColumns = @JoinColumn(name = "role_id") //join할 때 사용할 컬럼
    )
    @Column(name = "perm") //role_perm테이블에 값을 지닌 컬럼
    private Set<String> permissions = new HashSet<>();

    protected Role() {}

    public Role(String id, String name, Set<String> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void revokeAll() {
        this.permissions.clear(); // SELECT -> DELETE
        // this.permissions = new HashSet<>(); // DELETE
    }

    public void setPermissions(Set<String> newPermissions) {
        this.permissions = newPermissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}