package kr.ge.kwdi.gep.admin.domain.privacypolicy.dao;

import kr.ge.kwdi.gep.admin.domain.privacypolicy.dto.PrivacyPolicyListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrivacyPolicyDAO {
    //개인정보처리방침 리스트 조회
    List<PrivacyPolicyListVO.ListResult> selectPrvcPlcyList(PrivacyPolicyListVO.SearchParam searchParam);

    //개인정보처리방침 상세 조회
    PrivacyPolicyListVO.DetailResult selectPrvcPlcyDetail(Long entityId);

    //이전 날짜 조회
    PrivacyPolicyListVO.BeforeDateResult selectBeforeDate(Long entityId);

    //개인정보처리방침 Pk List 조회
    List<PrivacyPolicyListVO.PrvcPlcyPkListResult> selectPrvcPlcyPkList();

    //개인정보처리방침 Pk Max값 조회
    PrivacyPolicyListVO.PrvcPlcyPkMaxResult selectPrvcPlcyPkMax();

    //이전 개인정보처리방침 데이터 조회
    PrivacyPolicyListVO.BeforeDataInfoResult selectBeforeDataInfo();

    //최근 개인정보처리방침 시행일자 조회
    PrivacyPolicyListVO.FirstBeginDtResult selectFirstBeginDt(Long entityId);

    //개인정보처리방침 데이터 체크
    List<PrivacyPolicyListVO.AllResult> selectAll();

    //PK MAX - 1 한 PK 조회
    PrivacyPolicyListVO.MinusPKResult selectMinusPK();

    //이전 종료일자 하루 더하기
    PrivacyPolicyListVO.BeforeDatePlusResult selectPlusBeforeEndDt(Long entityId);

    //최신 등록 이전 데이터 조회
    PrivacyPolicyListVO.NewBeforeDataResult selectNewBeforeData();

    //최신 등록된 개인정보 조회
    PrivacyPolicyListVO.FirstInsertPrcyResult selectFirstInsertPrcy();

    //이전 시행일자 조회
    PrivacyPolicyListVO.PrvsStartDateResult selectPrvsStartDate();

    //개인정보처리방침 등록
    int insertPrvcPlcy(PrivacyPolicyListVO.InsertParam insertParam);

    //이전 종료일자 Null 수정
    int updateBeforeEndDtNull(Long entityId);

    //이전날짜 수정
    int updateBeforeDate(PrivacyPolicyListVO.BeforeDateParam beforeDateParam);

    //개인정보처리방침 수정
    int updatePrvcPlcy(PrivacyPolicyListVO.UpdateParam updateParam);

    //개인정보처리방침 삭제
    int deletePrvcPlcy(PrivacyPolicyListVO.DeleteParam deleteParam);

    //종료일자 하루 더하기
    int updateEndDtPlus(PrivacyPolicyListVO.UpdateParam updateParam);
}
