package com.notodo.notodo.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    private LocalDate added;
    private Integer status;


}
