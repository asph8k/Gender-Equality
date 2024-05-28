package kr.ge.kwdi.gep.admin.domain.apiApply.dao;

import kr.ge.kwdi.gep.admin.domain.apiApply.dto.ApiApplyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiApplyDAO {
    //API 신청 목록 조회
    List<ApiApplyVO.ListResult> selectApiApplyList(ApiApplyVO.SearchParam searchParam);

    //API 신청 상세 조회
    ApiApplyVO.DetailResult selectApiApplyDetail(Long entityId);

    //반려 조회
    ApiApplyVO.RjctResult selectRjct(Long entityId);

    //API 신청 허용 상태 수정
    int updateApiApplySttsCd(ApiApplyVO.UpdateParam updateParam);

    //반려 등록
    int updateRjct(ApiApplyVO.RjctParam rjctParam);

    //API 신청 삭제 여부 수정
    int updateApiApplyDelYn(ApiApplyVO.DeleteParam deleteParam);

    //승인 등록
    int updateAprv(List<Long> entityId);
}
