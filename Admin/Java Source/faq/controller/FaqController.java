package kr.ge.kwdi.gep.admin.domain.faq.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.admin.domain.faq.dto.FaqDTO;
import kr.ge.kwdi.gep.admin.domain.faq.service.FaqService;
import kr.ge.kwdi.gep.admin.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "Faq 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/faqs", produces = MediaType.APPLICATION_JSON_VALUE)
public class FaqController {
	private final FaqService faqService;

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'faqs', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
	@ApiOperation(value = "Faq 목록 조회 API")
	@GetMapping("")
	public ApiResult<List<FaqDTO.ListResponse>> selectFaqList(FaqDTO.SearchRequest searchRequest) {
		List<FaqDTO.ListResponse> listResponses = faqService.getFaqList(searchRequest);

		return ApiResult.success(listResponses);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'faqs', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
	@ApiOperation(value = "Faq 상세 조회 API")
	@GetMapping("/{entityId}")
	public ApiResult<FaqDTO.DetailResponse> selectFaqDetail(@PathVariable Long entityId) {
		FaqDTO.DetailResponse detailResponse = faqService.getFaqDetail(entityId);

		return ApiResult.success(detailResponse);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'faqs', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_CREATE)")
	@ApiOperation(value = "Faq 생성 API")
	@PostMapping("")
	public ApiResult<Integer> saveFaq(@RequestBody FaqDTO.InsertRequest insertRequest) {
		int insertFaqResult = faqService.saveFaq(insertRequest);

		return ApiResult.success(insertFaqResult);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'faqs', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_UPDATE)")
	@ApiOperation(value = "Faq 수정 API")
	@PutMapping("/{entityId}")
	public ApiResult<Integer> updateFaq(@PathVariable Long entityId, @RequestBody FaqDTO.UpdateRequest updateRequest) {
		int updateFaqResult = faqService.updateFaq(entityId, updateRequest);

		return ApiResult.success(updateFaqResult);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'faqs', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_DELETE)")
	@ApiOperation(value = "Faq 삭제 API")
	@DeleteMapping("")
	public ApiResult<Integer> deleteFaq(@RequestBody FaqDTO.deleteList deleteList) {
		int deleteFaqResult = faqService.deleteFaq(deleteList);

		return ApiResult.success(deleteFaqResult);
	}
}
