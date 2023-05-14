package com.notodo.notodo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // Table 생성
@Data // getter setter 함수 안 만들어도 됨 !!
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    @Id //PK
    @GeneratedValue
    @Column(name = "friend_id") // 컬럼 이름 지정
    private Long friendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private String email;


}
