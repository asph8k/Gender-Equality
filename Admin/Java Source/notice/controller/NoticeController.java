package kr.ge.kwdi.gep.admin.domain.notice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.admin.domain.notice.dto.NoticeListDTO;
import kr.ge.kwdi.gep.admin.domain.notice.service.NoticeListService;
import kr.ge.kwdi.gep.admin.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.multipart.MultipartFile;

@Api(tags = "공지사항 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/notices", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeController {
	private final NoticeListService noticeService;

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'notices', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
	@ApiOperation(value = "공지사항 목록 조회 API")
	@GetMapping("")
	public ApiResult<List<NoticeListDTO.ListResponse>> selectNoticeList(NoticeListDTO.SearchRequest searchRequest) {
		List<NoticeListDTO.ListResponse> listResponses = noticeService.getNoticeList(searchRequest);

		return ApiResult.success(listResponses);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'notices', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
	@ApiOperation(value = "공지사항 상세 조회 API")
	@GetMapping("/{entityId}")
	public ApiResult<NoticeListDTO.DetailResponse> selectNoticeDetail(@PathVariable Long entityId) {
		NoticeListDTO.DetailResponse detailResponse = noticeService.getNoticeDetail(entityId);

		return ApiResult.success(detailResponse);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'notices', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_CREATE)")
	@ApiOperation(value = "공지사항 생성 API")
	@PostMapping("")
	public ApiResult<Integer> saveNoticeList(NoticeListDTO.InsertRequest insertRequest,
											 @RequestPart(value = "newFiles", required = false) List<MultipartFile> newFiles) throws IOException {
		int getNoticeList = noticeService.saveNoticeList(insertRequest, newFiles);

		return ApiResult.success(getNoticeList);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'notices', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_UPDATE)")
	@ApiOperation(value = "공지사항 수정 API")
	@PutMapping("/{entityId}")
	public ApiResult<Integer> updateNoticeList(@PathVariable Long entityId, NoticeListDTO.UpdateRequest updateRequest,
											   @RequestPart(value = "newFiles", required = false) List<MultipartFile> newFiles) throws IOException {
		int getNoticeList = noticeService.updateNoticeList(entityId, updateRequest, newFiles);

		return ApiResult.success(getNoticeList);
	}

	@PreAuthorize("@userAccessAuthorization.authorization(principal, 'notices', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_DELETE)")
	@ApiOperation(value = "공지사항 삭제 API")
	@DeleteMapping("")
	public ApiResult<Integer> deleteNoticeList(@RequestBody NoticeListDTO.deleteList deleteList) {
		int deleteResult = noticeService.deleteNoticeList(deleteList);

		return ApiResult.success(deleteResult);
	}
}
