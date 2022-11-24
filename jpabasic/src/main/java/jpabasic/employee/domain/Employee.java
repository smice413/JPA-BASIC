package jpabasic.employee.domain;

import jakarta.persistence.*;
import jpabasic.common.Address;

@Entity
public class Employee {
    @Id
    private String id;
    //@Embedded 타입 필드가 2개면 오류남 따라서 @AttributeOverrides를 쓰면된다.
    @Embedded
    private Address homeAddress;

    @AttributeOverrides({ //Address entity의 address1 필드명을 workAddress의 컬럼 waddr1에 매핑한다는 의미
    	//이상태로 저장을 하면 insert into Employee(addr1, addr2, zipcode, waddr1, waddr2, wzipcode, i) values(?,?...)으로 함께 저장가능해진다.
            @AttributeOverride(name = "address1", column = @Column(name = "waddr1")),
            @AttributeOverride(name = "address2", column = @Column(name = "waddr2")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "wzipcode"))
    })
    @Embedded
    private Address workAddress;

    protected Employee() {
    }

    public Employee(String id, Address homeAddress, Address workAddress) {
        this.id = id;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
    }

    public String getId() {
        return id;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public Address getWorkAddress() {
        return workAddress;
    }
}