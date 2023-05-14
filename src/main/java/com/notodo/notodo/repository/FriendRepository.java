package com.notodo.notodo.repository;

import com.notodo.notodo.entity.Friend;
import com.notodo.notodo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByMemberAndEmail(Member member, String email);

    List<Friend> findAllByMember(Member member);
}
