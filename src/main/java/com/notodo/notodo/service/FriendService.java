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


    public boolean friendAdd(Member member, Member friend) {
        Friend newFriend = Friend.builder().member(member).email(friend.getEmail()).build();
        Optional<Friend> optFr =  friendRepository.findByMemberAndEmail(member, friend.getEmail());
        if (optFr.isPresent()) {
            return false;
        }
        if (member.getEmail().equals(friend.getEmail())) {
            return false;
        }
        friendRepository.save(newFriend);
        return true;
    }

    public boolean friendDelete(Member member, Member friend) {
        Optional<Friend> optFr = friendRepository.findByMemberAndEmail(member, friend.getEmail());
        if (optFr.isEmpty()) {
            return false;
        }
        Friend fr = optFr.get();
        friendRepository.delete(fr);

        return true;
    }

    public List<Friend> findFriends(Member member) {
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

    public List<Friend> findFollwers(Member member) {
        return friendRepository.findByEmail(member.getEmail());
    }

    public void deleteFollwer(Member member, String email) {
        Friend friend = friendRepository.findByMemberAndEmail(member, email).get();
        friendRepository.delete(friend);
        return;
    }

    public boolean isFollwerCheck(Member member, String email) {
        Optional<Friend> friend = friendRepository.findByMemberAndEmail(member, email);
        if (friend.isEmpty()) {
            return false;
        }
        return true;


    }

    public boolean isFollowingCheck(Member member, String email) {
        Optional<Friend> friend = friendRepository.findByMemberAndEmail(member, email);
        if (friend.isEmpty()) {
            return false;
        }
        return true;
    }
}
