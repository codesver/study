package study.querydsl.repository;

import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDTO;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    @Override
    public List<MemberTeamDTO> search(MemberSearchCondition condition) {
        return null;
    }
}
