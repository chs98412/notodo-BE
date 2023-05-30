package com.notodo.notodo.service;

import com.notodo.notodo.entity.Member;
import com.notodo.notodo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Optional<Member> findFriend(String email) {
        return memberRepository.findByEmail(email);
    }

    public Object findAll() {
        return memberRepository.findAll();
    }

    public void byebye(Member member) {
        memberRepository.delete(member);

    }

    public Optional<Member> findMember(String email) {
        return memberRepository.findByEmail(email);
    }
}
