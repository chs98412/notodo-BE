package com.notodo.notodo.controller;


import com.notodo.notodo.dto.*;
import com.notodo.notodo.entity.Member;
import com.notodo.notodo.entity.Notodo;
import com.notodo.notodo.exception.NoNotodoException;
import com.notodo.notodo.exception.UserNotFoundException;
import com.notodo.notodo.repository.MemberRepository;
import com.notodo.notodo.service.MemberService;
import com.notodo.notodo.service.NotodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("notodo")
public class NotodoController {


    @Autowired
    private NotodoService notodoService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/memberinfo")
    public ResponseEntity infoMember(@AuthenticationPrincipal Member member) {

        MemberDTO memberDTO = new MemberDTO(member.getEmail(), member.getNickname(), member.getThumbnail());

        return new ResponseEntity(memberDTO, HttpStatus.OK);
    }
    @GetMapping("/memberinfotest")
    public ResponseEntity infoMemberTest(@RequestParam String email) {
        Optional<Member> optMember =memberService.findMember(email);
        if (optMember.isPresent()) {
            Member member = optMember.get();
            MemberDTO memberDTO = new MemberDTO(member.getEmail(), member.getNickname(), member.getThumbnail());

            return new ResponseEntity(memberDTO, HttpStatus.OK);
        } else {
            throw new UserNotFoundException(String.format("email[%s] ㅅㅏ용자를 찾을 수 없습니다", email));
        }

    }



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
//        if (notodos.isEmpty()) {
//            throw new NoNotodoException(String.format("data[] 날짜에 해당하는 노토도가 없습니다.", date));
//        }
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
    @PostMapping("/delete")
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

    //탈퇴
    @PostMapping("/bye")
    public ResponseEntity byebye(@AuthenticationPrincipal Member member) {
        memberService.byebye(member);
        return new ResponseEntity("success", HttpStatus.OK);
    }

}
