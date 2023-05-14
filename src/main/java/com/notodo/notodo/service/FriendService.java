package com.notodo.notodo.service;

import com.notodo.notodo.entity.Friend;
import com.notodo.notodo.entity.Member;
import com.notodo.notodo.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;


    public void friendAdd(Member member, Member friend) {
        Friend newFriend = Friend.builder().member(member).email(friend.getEmail()).build();
        friendRepository.save(newFriend);
        return;
    }

    public void friendDelete(Member member, Member friend) {
        Friend fr = friendRepository.findByMemberAndEmail(member, friend.getEmail()).get();
        friendRepository.delete(fr);

        return;
    }

    public List<Member> findFriends(Member member) {
        return friendRepository.findAllByMember(member);

    }

    public boolean isFriend( Member member,Member friend) {
        Optional<Friend> fr = friendRepository.findByMemberAndEmail(member, friend.getEmail());
        if (fr.isPresent()) {
            return true;
        }
        return false;

    }

    public Object findAll() {
        return friendRepository.findAll();
    }
}
