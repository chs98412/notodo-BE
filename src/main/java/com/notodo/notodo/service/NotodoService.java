package com.notodo.notodo.service;


import com.notodo.notodo.dto.DateDTO;
import com.notodo.notodo.dto.NotodoRequestDTO;
import com.notodo.notodo.entity.Member;
import com.notodo.notodo.entity.Notodo;
import com.notodo.notodo.repository.NotodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotodoService {

    @Autowired
    private NotodoRepository notodoRepository;

    @Autowired
    private MemberService memberService;

    public void notodoCreate(Member member, NotodoRequestDTO dto) {
        Notodo notodo = Notodo.builder().added(dto.getAdded()).status(0).content(dto.getContent()).member(member).build();
        notodoRepository.save(notodo);
    }

    public List<Notodo> notodoView(Member member, DateDTO dto) {
        return notodoRepository.findByMenberAndAdded(member, dto.getAdded());
    }

//    public Notodo createNotodo(NotodoRequezstDTO notodoRequestDTO) {
//
//        Member member = memberService.getMember(notodoRequestDTO.getEmail());//예외처리
//
//
//    }
}
