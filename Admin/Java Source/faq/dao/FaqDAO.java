package kr.ge.kwdi.gep.admin.domain.faq.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import kr.ge.kwdi.gep.admin.domain.faq.dto.FaqVO;

@Mapper
public interface FaqDAO {
	//Faq 목록 조회
	List<FaqVO.ListResult> selectFaqList(FaqVO.SearchParam searchParam);

	//Faq 상세 조회
	FaqVO.DetailResult selectFaqDetail(Long entityId);

	//Faq순서 + 1 증가값 조회
	FaqVO.OrdrCountResult selectOrdrCount();

	//Faq 리스트 생성
	int insertFaq(FaqVO.InsertParam insertParam);

	//Faq 리스트 수정
	int updateFaq(FaqVO.UpdateParam updateParam);

	//Faq 리스트 삭제
	int deleteFaq(FaqVO.DeleteParam deleteParam);
}
