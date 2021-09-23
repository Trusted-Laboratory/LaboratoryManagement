package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.dao.MemberDao;
import com.lyun.laboratorymanagement.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberDao memberDao;

    @Override
    public void save(Member member) {

    }

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public void addMember(Member member) {
        memberDao.addMember(member);
    }

}
