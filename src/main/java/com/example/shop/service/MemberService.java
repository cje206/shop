package com.example.shop.service;

import com.example.shop.common.ResponseEntity;
import com.example.shop.member.Member;
import com.example.shop.member.MemberRepository;
import com.example.shop.member.MemberRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<List<Member>> findAll() {
        return new ResponseEntity<>(HttpStatus.OK.value(), memberRepository.findAll(), memberRepository.count());
    }

    public ResponseEntity<Member> save(MemberRequest request) {
        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );
        Member member1 =  memberRepository.save(member);
        AtomicInteger cnt = new AtomicInteger();
        if(member1 instanceof List) {
            cnt.set(((List<?>) member1).size());
        } else{
            cnt.set(1);
        }
        return new ResponseEntity<>(HttpStatus.OK.value(), member1, cnt.get());
    }

    public ResponseEntity<Member> update(String id, MemberRequest request) {
        Member member = new Member(
                id,
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );
        Member member1 = memberRepository.save(member);
        AtomicInteger cnt = new AtomicInteger();
        if(member1 instanceof List) {
            cnt.set(((List<?>) member1).size());
        } else{
            cnt.set(1);
        }

        return new ResponseEntity<>(HttpStatus.OK.value(), member1, cnt.get());
    }

    public ResponseEntity<Void> deleteById(String id) {
        memberRepository.deleteById(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.OK.value(), null, 0);
    }
}
