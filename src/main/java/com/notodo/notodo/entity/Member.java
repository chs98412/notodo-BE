package com.notodo.notodo.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // Table 생성
@Data // getter setter 함수 안 만들어도 됨 !!
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id //PK
    @GeneratedValue
    @Column(name = "member_id") // 컬럼 이름 지정
    private Long memberId;

    private String email;
    private String nickname;
}
