package jpabasic.reserve.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_log")
public class AccessLog { //테이블 생성 방식
    @Id
    @TableGenerator(
            name = "accessIdGen",
            table = "id_seq", //id를 생성할 때 사용할 테이블 이름
            pkColumnName = "entity", //entity이름을 보관할 컬럼
            pkColumnValue = "accesslog", //entity의 이름 where절에 사용
            valueColumnName = "nextval", //다음 식별자를 구할 때 사용할 컬럼
            initialValue = 0, //저장할 때 다음 식별자가 1부터 시작함
            allocationSize = 1
    )
    @GeneratedValue(generator = "accessIdGen")
    private Long id;
    private String path;
    private LocalDateTime accessed;

    protected AccessLog() {
    }

    public AccessLog(String path, LocalDateTime accessed) {
        this.path = path;
        this.accessed = accessed;
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getAccessed() {
        return accessed;
    }
}