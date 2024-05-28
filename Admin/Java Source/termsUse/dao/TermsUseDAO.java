package kr.ge.kwdi.gep.admin.domain.termsUse.dao;

import kr.ge.kwdi.gep.admin.domain.termsUse.dto.TermsUseListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TermsUseDAO {
    //이용약관 목록 조회
    List<TermsUseListVO.ListResult> selectTermsUseList(TermsUseListVO.SearchParam searchParam);

    //이용약관 상세 조회
    TermsUseListVO.DetailResult selectTermsUseDetail(Long entityId);

    //이용약관 Pk Max값 조회
    TermsUseListVO.MngNoResult selectUtztnMngNo();

    //이용약관 Pk List 조회
    List<TermsUseListVO.TermsPkListResult> selectTermsPkList();

    //이전 날짜 조회
    TermsUseListVO.BeforeDateResult selectBeforeDate(Long entityId);

    //이전 이용약관 데이터 조회
    TermsUseListVO.BeforeDataInfoResult selectBeforeDataInfo();

    //최근 이용약관 시행일자 조회
    TermsUseListVO.FirstBeginDtResult selectFirstBeginDt(Long entityId);

    //이용약관 데이터 체크
    List<TermsUseListVO.AllResult> selectAll();

    //PK MAX - 1 한 PK 조회
    TermsUseListVO.MinusPKResult selectMinusPK();

    //이전 종료일자 하루 더하기
    TermsUseListVO.BeforeDatePlusResult selectPlusBeforeEndDt(Long entityId);

    //최신 등록 이전 데이터 조회
    TermsUseListVO.NewBeforeDataResult selectNewBeforeData();

    //최신 등록된 이용약관 조회
    TermsUseListVO.FirstInsertTermsResult selectFirstInsertTerms();

    //이전 시행일자 조회
    TermsUseListVO.PrvsStartDateResult selectPrvsStartDate();

    //이전 종료일자 Null 수정
    int updateBeforeEndDtNull(Long entityId);

    //이용약관 등록
    int insertTermsUse(TermsUseListVO.InsertParam insertParam);

    //이전 날짜 수정
    int updateBeforeDate(TermsUseListVO.BeforeDateParam beforeDateParam);

    //이용약관 수정
    int updateTermsUse(TermsUseListVO.UpdateParam updateParam);

    //이용약관 삭제
    int deleteTermsUse(TermsUseListVO.DeleteParam deleteParam);

    //종료일자 하루 더하기
    int updateEndDtPlus(TermsUseListVO.UpdateParam updateParam);
}
