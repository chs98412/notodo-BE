package com.notodo.notodo.service;

import com.notodo.notodo.dto.FindFriendDTO;
import com.notodo.notodo.dto.FollowingDTO;
import com.notodo.notodo.dto.NotodoResponseDTO;
import com.notodo.notodo.dto.SetFriendDTO;
import com.notodo.notodo.entity.Friend;
import com.notodo.notodo.entity.Member;
import com.notodo.notodo.entity.Notodo;
import com.notodo.notodo.exception.AlreadyFriendException;
import com.notodo.notodo.exception.NotExsistFriendException;
import com.notodo.notodo.exception.SelfFriendException;
import com.notodo.notodo.exception.UserNotFoundException;
import com.notodo.notodo.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private NotodoService notodoService;


    public void friendAdd(Member member, SetFriendDTO setFriendDTO) {

        Optional<Member > optFriend = memberService.findFriend(setFriendDTO.getEmail());
        if (optFriend.isEmpty()) {
            throw new UserNotFoundException(String.format("email[%s] ㅅㅏ용자를 찾을 수 없습니다", setFriendDTO.getEmail()));
        }
        Member friend = optFriend.get();

        Friend newFriend = Friend.builder().member(member).email(friend.getEmail()).build();
        Optional<Friend> optFr =  friendRepository.findByMemberAndEmail(member, friend.getEmail());
        if (optFr.isPresent() || member.getEmail().equals(friend.getEmail()) ) {
            throw new AlreadyFriendException(String.format("email[%s] ㅅㅏ용자가 이미 친구로 등록되어 있습니다.", setFriendDTO.getEmail()));
        }
        if (member.getEmail().equals(friend.getEmail()) ) {
            throw new SelfFriendException(String.format("본인은 친구로 추가할 수 없습니다."));
        }

        friendRepository.save(newFriend);
    }

    public void friendDelete(Member member, SetFriendDTO setFriendDTO) {

        Optional<Member > optFriend = memberService.findMember(setFriendDTO.getEmail());
        if (optFriend.isEmpty()) {
            throw new UserNotFoundException(String.format("email[%s] ㅅㅏ용자를 찾을 수 없습니다", setFriendDTO.getEmail()));
        }
        Member friend = optFriend.get();

        Optional<Friend> optFr = friendRepository.findByMemberAndEmail(member, friend.getEmail());
        if (optFr.isEmpty()) {
            throw  new NotExsistFriendException(String.format("email[%s] 에 해당하는 친구를 찾을 수 없습니다", setFriendDTO.getEmail()));
        }
        Friend fr = optFr.get();
        friendRepository.delete(fr);
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

    public FindFriendDTO friendSearch(Member member, String email) {

        Optional<Member> optFriend = memberService.findFriend(email);
        if (optFriend.isEmpty()) {
            throw new UserNotFoundException(String.format("email[%s] ㅅㅏ용자를 찾을 수 없습니다", email));
        }
        Member friend = optFriend.get();
        boolean isMe = false;
        if (member.equals(friend)) {
            isMe = true;
        }
        FindFriendDTO response = new FindFriendDTO(friend.getEmail(), friend.getNickname(), friend.getThumbnail(), isFriend(member,friend),isMe);

        return response;
    }

    public List<FollowingDTO> followingFriend(Member member) {
        List<Friend> friendList = friendRepository.findAllByMember(member);
        List<FollowingDTO> response = new ArrayList<>();
        for (Friend fr : friendList) {
            Optional<Member> optFriend = memberService.findFriend(fr.getEmail());
            if (optFriend.isPresent()) {
                Member friend = optFriend.get();
                boolean isFollwer = isFollwerCheck(friend, member.getEmail());
                FollowingDTO dto = new FollowingDTO(friend.getEmail(), friend.getNickname(), friend.getThumbnail(), isFollwer);
                response.add(dto);
            } else {
                throw new UserNotFoundException(String.format("email[%s] ㅅㅏ용자를 찾을 수 없습니다", fr.getEmail()));
            }
        }
        return response;
    }

    public List<NotodoResponseDTO> frNotodoView(String email, String date) {
        Optional<Member > optFriend = memberService.findFriend(email);
        if (optFriend.isEmpty()) {
            throw new UserNotFoundException(String.format("email[%s] ㅅㅏ용자를 찾을 수 없습니다", email));
        }
        Member friend = optFriend.get();
        List<Notodo> notodos = notodoService.notodoView(friend, date);
        List<NotodoResponseDTO> response = new ArrayList<>();
        for (Notodo notodo : notodos) {
            NotodoResponseDTO responseDTO = new NotodoResponseDTO(notodo.getNotodoId(),notodo.getContent(), notodo.getAdded(), notodo.getStatus());
            response.add(responseDTO);
        }
        return response;
    }

}
