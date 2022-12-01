package jpabasic.acl2.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role2 {
    @Id
    private String id;
    private String name;

    @ElementCollection //Set에 클래스를 넣어서 하는 것은 Set 제너릭에 클래스명을 넣어주고 저 클래스 파일에는 @Embeddable을 선언한다.
    @CollectionTable(
            name = "role_perm",
            joinColumns = @JoinColumn(name = "role_id")
    )
    private Set<GrantedPermission> permissions = new HashSet<>();

    protected Role2() {}

    public Role2(String id, String name, Set<GrantedPermission> permissions) {
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

    public Set<GrantedPermission> getPermissions() {
        return permissions;
    }

    public void revokeAll() {
//        this.permissions.clear();
        this.permissions = new HashSet<>();
    }

    public void setPermissions(Set<GrantedPermission> newPermissions) {
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