package com.notodo.notodo.controller;

import com.notodo.notodo.dto.FindFriendDTO;
import com.notodo.notodo.dto.MemberDTO;
import com.notodo.notodo.dto.NotodoResponseDTO;
import com.notodo.notodo.dto.SetFriendDTO;
import com.notodo.notodo.entity.Friend;
import com.notodo.notodo.entity.Member;
import com.notodo.notodo.entity.Notodo;
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
import java.util.Set;

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
        Member friend = memberService.findFriend(email);
        boolean isMe = false;
        if (member.equals(friend)) {
            isMe = true;
        }
        FindFriendDTO dto = new FindFriendDTO(friend.getEmail(), friend.getNickname(), friend.getThumbnail(), friendService.isFriend(member,friend),isMe);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    //친구 추가
    @PostMapping("add")
    public ResponseEntity addFriend(@AuthenticationPrincipal Member member,@RequestBody SetFriendDTO setFriendDTO) {
        Member friend = memberService.findFriend(setFriendDTO.getEmail());
        friendService.friendAdd(member, friend);

        return new ResponseEntity("success", HttpStatus.CREATED);
    }

    //친구 삭제
    @DeleteMapping("delete")
    public ResponseEntity deleteFriend(@AuthenticationPrincipal Member member,@RequestBody SetFriendDTO setFriendDTO) {
        Member friend = memberService.findFriend(setFriendDTO.getEmail());
        friendService.friendDelete(member, friend);

        return new ResponseEntity("success", HttpStatus.OK);
    }

    //친구 목록
    @GetMapping("list")
    public ResponseEntity listFriend(@AuthenticationPrincipal Member member) {
        List<Friend> friendList = friendService.findFriends(member);
        List<MemberDTO> dtos = new ArrayList<>();
        for (Friend fr : friendList) {
            Member friend = memberService.findFriend(fr.getEmail());
            MemberDTO dto = new MemberDTO(friend.getEmail(), friend.getNickname(), friend.getThumbnail());
            dtos.add(dto);
        }
        return new ResponseEntity(dtos, HttpStatus.OK);
    }

    //친구 노토도 조회
    @GetMapping("view")
    public ResponseEntity viewFriendNotodo(@RequestParam String email, @RequestParam String date) {
        Member friend = memberService.findFriend(email);
        List<Notodo> notodos = notodoService.notodoView(friend, date);
        List<NotodoResponseDTO> responseDTOS = new ArrayList<>();
        for (Notodo notodo : notodos) {
            NotodoResponseDTO responseDTO = new NotodoResponseDTO(notodo.getNotodoId(),notodo.getContent(), notodo.getAdded(), notodo.getStatus());
            responseDTOS.add(responseDTO);
        }
        return new ResponseEntity(responseDTOS, HttpStatus.OK);
    }

    //팔로워 조회
    @GetMapping("viewfollwers")
    public ResponseEntity viewFollwers(@AuthenticationPrincipal Member member) {
        List<Friend> friendList = friendService.findFollwers(member);
        List<MemberDTO> dtos = new ArrayList<>();
        for (Friend fr : friendList) {
            Member friend = memberService.findFriend(fr.getEmail());
            MemberDTO dto = new MemberDTO(friend.getEmail(), friend.getNickname(), friend.getThumbnail());
            dtos.add(dto);
        }
        return new ResponseEntity(dtos, HttpStatus.OK);

    }

    //팔로워 삭제
    @DeleteMapping("deletefollwer")
    public ResponseEntity deleteFollwers(@AuthenticationPrincipal Member member, @RequestBody SetFriendDTO setFriendDTO) {
        Member me = memberService.findFriend(setFriendDTO.getEmail());

        friendService.deleteFollwer(me,member.getEmail());

        return new ResponseEntity("success", HttpStatus.OK);

    }

}
