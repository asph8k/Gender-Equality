package kr.ge.kwdi.gep.admin.domain.relatedInst.dao;

import kr.ge.kwdi.gep.admin.domain.relatedInst.dto.RelatedInstVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelatedInstDAO {

    // 유관기관 목록 조회
    List<RelatedInstVO.RltdInstListResult> selectRltdInstList(RelatedInstVO.SearchParam searchParam);

    // 유관기관 상세 조회
    RelatedInstVO.RltdInstDetailResult selectRltdInstDtl(Long entityId);

    // 유관기관 이미지 파일 조회
    RelatedInstVO.ImgFileDtlResult selectImgFileDtl(Long entityId);

    // 유관기관 등록
    int insertRltdInst(RelatedInstVO.RltdInstInsertParam rltdInstInsertParam);

    // 유관기관 수정
    int updateRltdInst(RelatedInstVO.RltdInstUpdateParam rltdInstUpdateParam);

    // 유관기관 수정 이미지 포함
    int updateRltdImg(RelatedInstVO.RltdInstUpdateParam rltdInstUpdateParam);

    // 유관기관 삭제
    int deleteRltdInst(RelatedInstVO.RltdInstDeleteParam rltdInstDeleteParam);
}
