package com.notodo.notodo.service;


import com.notodo.notodo.dto.NotodoPutResponseDTO;
import com.notodo.notodo.dto.NotodoRequestDTO;
import com.notodo.notodo.entity.Member;
import com.notodo.notodo.entity.Notodo;
import com.notodo.notodo.repository.NotodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<Notodo> notodoView(Member member, String date) {
        LocalDate localDate = LocalDate.parse(date);
        return notodoRepository.findByMemberAndAdded(member, localDate);
    }

    public List<Notodo> notodoViewAll(Member member) {
        return notodoRepository.findByMember(member);
    }


    public void notodoSetSuccess(Long notodoId) {
        Notodo notodo = notodoRepository.findById(notodoId).get();
        notodo.setStatus(1);
        notodoRepository.save(notodo);
    }

    public void notodoSetFail(Long notodoId) {
        Notodo notodo = notodoRepository.findById(notodoId).get();
        notodo.setStatus(2);
        notodoRepository.save(notodo);
    }

    public void notodoDelete(Long notodoId) {
        Notodo notodo = notodoRepository.findById(notodoId).get();
        notodoRepository.delete(notodo);
    }

    public void notodoPut(NotodoPutResponseDTO dto) {
        Notodo notodo = notodoRepository.findById(dto.getNotodoId()).get();
        notodo.setContent(dto.getContent());
        notodoRepository.save(notodo);
    }

    public Integer countNotodo(Member member) {
        return notodoRepository.countByMember(member);
    }
}
