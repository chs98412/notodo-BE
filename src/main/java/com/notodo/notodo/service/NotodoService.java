package com.notodo.notodo.service;


import com.notodo.notodo.dto.NotodoRequezstDTO;
import com.notodo.notodo.entity.Member;
import com.notodo.notodo.entity.Notodo;
import com.notodo.notodo.repository.MemberRepository;
import com.notodo.notodo.repository.NotodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotodoService {

    @Autowired
    private NotodoRepository notodoRepository;

    @Autowired
    private MemberService memberService;

//    public Notodo createNotodo(NotodoRequezstDTO notodoRequestDTO) {
//
//        Member member = memberService.getMember(notodoRequestDTO.getEmail());//예외처리
//
//
//    }
}
