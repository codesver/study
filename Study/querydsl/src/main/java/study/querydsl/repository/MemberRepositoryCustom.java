package study.querydsl.repository;

import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDTO;

import java.awt.print.Pageable;
import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDTO> search(MemberSearchCondition condition);

    List<MemberTeamDTO> searchPageSimple(MemberSearchCondition condition, Pageable pageable);

    List<MemberTeamDTO> searchPageComplex(MemberSearchCondition condition, Pageable pageable);
}
