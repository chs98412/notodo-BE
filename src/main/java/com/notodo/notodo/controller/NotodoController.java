package com.notodo.notodo.controller;


import com.notodo.notodo.service.NotodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notodo")
public class NotodoController {

    @Autowired
    private NotodoService notodoService;

    //낫투두 생성 메소드
    public ResponseEntity createNotodo() {

        return new ResponseEntity("success", HttpStatus.CREATED);
    }
    //낫투두 조회 메소드
    //낫투두 전체 조회 메소드 (테스트용)
    //낫투두 성공 체크
    //낫투두 실패 체크


}
