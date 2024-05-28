package kr.ge.kwdi.gep.platform.domain.faq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.platform.domain.faq.dto.FaqDTO;
import kr.ge.kwdi.gep.platform.domain.faq.service.FaqService;
import kr.ge.kwdi.gep.platform.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "FAQ API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/faqs", produces = MediaType.APPLICATION_JSON_VALUE)
public class FaqController {

	private final FaqService faqService;

	@ApiOperation(value = "FAQ 목록 조회 API")
	@GetMapping("")
	public ApiResult<List<FaqDTO.Response>> getFaqList(FaqDTO request) {
		log.info("FaqController API list :: getFaqList");
		List<FaqDTO.Response> getFaqList = faqService.selectFaqList(request);
		return ApiResult.success(getFaqList);
	}

	@ApiOperation(value = "FAQ 상세 조회 API")
	@GetMapping("/{faqNo}")
	public ApiResult<FaqDTO.Response> getFaqDetail(@PathVariable Long faqNo) {
		log.info("FaqController API detail :: getFaqDetail = {}", faqNo);
		FaqDTO.Response getFaqDetail = faqService.selectFaqDetail(faqNo);
		return ApiResult.success(getFaqDetail);
	}

}
