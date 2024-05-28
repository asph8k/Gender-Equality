package kr.ge.kwdi.gep.platform.domain.faq.service;

import kr.ge.kwdi.gep.platform.domain.faq.dao.FaqDAO;
import kr.ge.kwdi.gep.platform.domain.faq.dto.FaqDTO;
import kr.ge.kwdi.gep.platform.domain.faq.dto.FaqVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqService {

	private final FaqDAO faqDAO;
	private final ModelMapper modelMapper;

	/** FAQ 목록 */
	public List<FaqDTO.Response> selectFaqList(FaqDTO request) {
		log.info("FaqService :: selectFaqList :: request = {}", request);
		FaqVO faqVo = modelMapper.map(request, FaqVO.class);
		return getResponseList(faqDAO.selectFaqList(faqVo));
	}

	/** FAQ 상세 */
	public FaqDTO.Response selectFaqDetail(Long faqNo) {
		log.info("FaqService :: selectFaqDetail :: faqNo = {}", faqNo);
		FaqVO.Result faqVo = faqDAO.selectFaqDetail(faqNo);

		if (faqVo == null) {
			throw new IllegalArgumentException(
				"faqNo=" + faqNo + "에 해당하는 FAQ가 존재하지 않습니다.");
		}

		return getResponse(faqVo);
	}

	private List<FaqDTO.Response> getResponseList(List<FaqVO.Result> results) {
		return results.stream()
			.map(vo -> FaqDTO.Response.builder()
				.faqNo(vo.getFaqNo())
				.faqTitle(vo.getFaqTtl())
				.faqContent(vo.getFaqCn())
				.faqOrder(vo.getFaqOrdr())
				.build())
			.collect(Collectors.toList());
	}

	private FaqDTO.Response getResponse(FaqVO.Result noticeVO) {
		return FaqDTO.Response.builder()
			.faqNo(noticeVO.getFaqNo())
			.faqTitle(noticeVO.getFaqTtl())
			.faqContent(noticeVO.getFaqCn())
			.faqOrder(noticeVO.getFaqOrdr())
			.build();
	}

}
