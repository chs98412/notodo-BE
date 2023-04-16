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
public class Notodo {

    @Id //PK
    @GeneratedValue
    @Column(name = "notodo_id") // 컬럼 이름 지정
    private Long notodoId;

    private String content;
    private Integer status;
}
