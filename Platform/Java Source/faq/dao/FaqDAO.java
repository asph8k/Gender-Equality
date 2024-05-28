package kr.ge.kwdi.gep.platform.domain.faq.dao;

import org.apache.ibatis.annotations.Mapper;
import kr.ge.kwdi.gep.platform.domain.faq.dto.FaqVO;

import java.util.List;

@Mapper
public interface FaqDAO {

	// FAQ 목록
	List<FaqVO.Result> selectFaqList(FaqVO faqVo);

	// FAQ 상세
	FaqVO.Result selectFaqDetail(Long faqNo);

}
