package com.notodo.notodo.controller;


import com.notodo.notodo.dto.NotodoPutResponseDTO;
import com.notodo.notodo.dto.NotodoRequestDTO;
import com.notodo.notodo.dto.NotodoResponseDTO;
import com.notodo.notodo.dto.NotodoSetDTO;
import com.notodo.notodo.entity.Member;
import com.notodo.notodo.entity.Notodo;
import com.notodo.notodo.service.NotodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("notodo")
public class NotodoController {

    @Autowired
    private NotodoService notodoService;

    //낫투두 생성 메소드
    @PostMapping("/create")
    public ResponseEntity createNotodo(@AuthenticationPrincipal Member member,@RequestBody NotodoRequestDTO dto) {

        notodoService.notodoCreate(member, dto);

        return new ResponseEntity("success", HttpStatus.CREATED);
    }

    //낫투두 조회 메소드
    @GetMapping("/view")
    public ResponseEntity viewNotodo(@AuthenticationPrincipal Member member,@RequestParam String date) {
        List<Notodo> notodos = notodoService.notodoView(member, date);
        List<NotodoResponseDTO> responseDTOS = new ArrayList<>();
        for (Notodo notodo : notodos) {
            NotodoResponseDTO responseDTO = new NotodoResponseDTO(notodo.getNotodoId(),notodo.getContent(), notodo.getAdded(), notodo.getStatus());
            responseDTOS.add(responseDTO);
        }
        return new ResponseEntity(responseDTOS, HttpStatus.OK);
    }

    //낫투두 전체 조회 메소드 (테스트용)
    @GetMapping("/viewall")
    public ResponseEntity viewAllNotodo(@AuthenticationPrincipal Member member) {
        List<Notodo> notodos = notodoService.notodoViewAll(member);
        List<NotodoResponseDTO> responseDTOS = new ArrayList<>();
        for (Notodo notodo : notodos) {
            NotodoResponseDTO responseDTO = new NotodoResponseDTO(notodo.getNotodoId(),notodo.getContent(), notodo.getAdded(), notodo.getStatus());
            responseDTOS.add(responseDTO);
        }
        return new ResponseEntity(responseDTOS, HttpStatus.OK);
    }
    //낫투두 성공 체크
    @PostMapping("/setsuccess")
    public ResponseEntity setsuccessNotodo(@AuthenticationPrincipal Member member,@RequestBody NotodoSetDTO dto) {
          notodoService.notodoSetSuccess(dto.getNotodoId());
        return new ResponseEntity("success", HttpStatus.OK);
    }
    //낫투두 실패 체크
    @PostMapping("/setFail")
    public ResponseEntity setFailNotodo(@AuthenticationPrincipal Member member,@RequestBody NotodoSetDTO dto) {
        notodoService.notodoSetFail(dto.getNotodoId());
        return new ResponseEntity("success", HttpStatus.OK);
    }

    //낫투두 삭제
    @DeleteMapping("/delete")
    public ResponseEntity deleteNotodo(@AuthenticationPrincipal Member member,@RequestBody NotodoSetDTO dto) {
        notodoService.notodoDelete(dto.getNotodoId());
        return new ResponseEntity("success", HttpStatus.OK);
    }


    //낫투두 수정
    @PutMapping("/put")
    public ResponseEntity putNotodo(@AuthenticationPrincipal Member member,@RequestBody NotodoPutResponseDTO dto) {
        notodoService.notodoPut(dto);
        return new ResponseEntity("success", HttpStatus.OK);
    }

}
