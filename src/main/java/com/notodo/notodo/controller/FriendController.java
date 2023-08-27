package com.notodo.notodo.controller;

import com.notodo.notodo.dto.*;
import com.notodo.notodo.entity.Friend;
import com.notodo.notodo.entity.Member;
import com.notodo.notodo.entity.Notodo;
import com.notodo.notodo.exception.NotExsistFriendException;
import com.notodo.notodo.exception.UserNotFoundException;
import com.notodo.notodo.service.FriendService;
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
@RequestMapping("friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private NotodoService notodoService;

    @GetMapping("all")
    public ResponseEntity all() {
        return new ResponseEntity(memberService.findAll(), HttpStatus.OK);
    }
    @GetMapping("allfr")
    public ResponseEntity allfr() {
        return new ResponseEntity(friendService.findAll(), HttpStatus.OK);
    }

    //email로 친구 검색
    @GetMapping("search")
    public ResponseEntity serchFriend(@AuthenticationPrincipal Member member,@RequestParam String email) {
        FindFriendDTO response = friendService.friendSearch(member, email);
       return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("add")
    public ResponseEntity addFriend(@AuthenticationPrincipal Member member,@RequestBody SetFriendDTO setFriendDTO) {
        friendService.friendAdd(member, setFriendDTO);
        return new ResponseEntity( HttpStatus.CREATED);
    }

    //친구 삭제ㅊ
    @PostMapping("delete")
    public ResponseEntity deleteFriend(@AuthenticationPrincipal Member member,@RequestBody SetFriendDTO setFriendDTO) {
        friendService.friendDelete(member,setFriendDTO);
        return new ResponseEntity( HttpStatus.OK);
    }

    //팔로잉 목록
    @GetMapping("list")
    public ResponseEntity listFollowing(@AuthenticationPrincipal Member member) {
        List<FollowingDTO> response = friendService.followingFriend(member);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    //팔로잉 노토도 조회
    @GetMapping("view")
    public ResponseEntity viewFollowingNotodo(@RequestParam String email, @RequestParam String date) {
        List<NotodoResponseDTO> response = friendService.frNotodoView(email, date);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    //팔로워 조회
    @GetMapping("viewfollwers")
    public ResponseEntity viewFollwers(@AuthenticationPrincipal Member member) {

        List<Friend> friendList = friendService.findFollwers(member);
        List<FollwerDTO> dtos = new ArrayList<>();
        for (Friend fr : friendList) {
            Optional<Member> optFriend = memberService.findFriend(fr.getMember().getEmail());
            if (optFriend.isPresent()) {
                Member friend = optFriend.get();
                boolean isFollwing = friendService.isFollwerCheck(member, friend.getEmail());
                FollwerDTO dto = new FollwerDTO(friend.getEmail(), friend.getNickname(), friend.getThumbnail(),isFollwing);
                dtos.add(dto);
            }


    }
        return new ResponseEntity(dtos, HttpStatus.OK);
    }

    //팔로워 삭제
    @PostMapping("deletefollwer")
    public ResponseEntity deleteFollwers(@AuthenticationPrincipal Member member, @RequestBody SetFriendDTO setFriendDTO) {
        Optional<Member > optMe= memberService.findFriend(setFriendDTO.getEmail());
        if (optMe.isEmpty()) {
            throw new UserNotFoundException(String.format("email[%s] ㅅㅏ용자를 찾을 수 없습니다", setFriendDTO.getEmail()));
        }
        Member me = optMe.get();

        friendService.deleteFollwer(me,member.getEmail());

        return new ResponseEntity("success", HttpStatus.OK);

    }

    @GetMapping("count")
    //친구 노토도 개수 출력
    public ResponseEntity countNotodo(@RequestParam String email) {
        Member member = memberService.findMember(email).get();
        Integer count= notodoService.countNotodo(member);
        return new ResponseEntity(count, HttpStatus.OK);
    }

}
