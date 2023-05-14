package com.notodo.notodo.service;

import com.notodo.notodo.entity.Member;
import com.notodo.notodo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member findFriend(String email) {
        return memberRepository.findByEmail(email).get();
    }

    public Object findAll() {
        return memberRepository.findAll();
    }
}
