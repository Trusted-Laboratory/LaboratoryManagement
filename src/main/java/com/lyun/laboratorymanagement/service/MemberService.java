package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.entity.Member;

import java.util.List;

public interface MemberService {
    void save(Member member);
    List<Member> findAll();
    void addMember(Member member);
}
