package com.lyun.laboratorymanagement.dao;

import com.lyun.laboratorymanagement.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao extends BaseDao<Member>{
    void addMember(Member member);
}
