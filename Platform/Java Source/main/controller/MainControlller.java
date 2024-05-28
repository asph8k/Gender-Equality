package kr.ge.kwdi.gep.platform.domain.main.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.platform.domain.main.dto.*;
import kr.ge.kwdi.gep.platform.domain.main.service.MainService;
import kr.ge.kwdi.gep.platform.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "메인 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/main", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainControlller {

	private final MainService mainService;

	@ApiOperation(value = "메인 뉴스 조회 API")
	@GetMapping("/newses")
	public ApiResult<List<MainNewsDTO.Response>> getMainNewsData() {
		List<MainNewsDTO.Response> getMainNewsData = mainService.getMainNewsData();

		return ApiResult.success(getMainNewsData);
	}

	@ApiOperation(value = "메인 멀티미디어 조회 API")
	@GetMapping("/medias")
	public ApiResult<List<MultimediaDTO.Response>> getMuiltimediaData() {
		List<MultimediaDTO.Response> getMuiltimediaData = mainService.getMuiltimediaData();

		return ApiResult.success(getMuiltimediaData);
	}

	@ApiOperation(value = "메인 멀티미디어 카드 조회 API")
	@GetMapping("/card")
	public ApiResult<List<MultimediaDTO.Response>> getCardImage() {
		List<MultimediaDTO.Response> getMuiltimediaData = mainService.getCardImage();

		return ApiResult.success(getMuiltimediaData);
	}

	@ApiOperation(value = "메인 멀티미디어 사진 조회 API")
	@GetMapping("/photo")
	public ApiResult<List<MultimediaDTO.Response>> getPhoto() {
		List<MultimediaDTO.Response> getMuiltimediaData = mainService.getPhotoImage();

		return ApiResult.success(getMuiltimediaData);
	}

	@ApiOperation(value = "메인 영상자료 조회 API")
	@GetMapping("/videos")
	public ApiResult<List<MainVdoDTO.Response>> getMainVdo() {
		List<MainVdoDTO.Response> getMainVdo = mainService.getMainVdoData();

		return ApiResult.success(getMainVdo);
	}

	@ApiOperation(value = "메인 주제별 자료 조회 API")
	@GetMapping("/topics")
	public ApiResult<List<BySubjectDTO.Response>> getDataBySubject(BySubjectDTO.Request bySubjectReqDTO) {
		List<BySubjectDTO.Response> getDataBySubject = mainService.getDataBySubject(bySubjectReqDTO);

		return ApiResult.success(getDataBySubject);
	}

	@ApiOperation(value = "메인 보도 자료 조회 API")
	@GetMapping("/articles")
	public ApiResult<List<MainNewsConverageDTO.Response>> getMainNewsConverage() {
		List<MainNewsConverageDTO.Response> getMainNewsConverage = mainService.getMainNewsConverage();

		return ApiResult.success(getMainNewsConverage);
	}

	@ApiOperation(value = "주제 목록")
	@GetMapping("/subjects")
	public ApiResult<List<BySubjectDTO.SubjectsResponse>> getSubjects() {
		List<BySubjectDTO.SubjectsResponse> responses = mainService.getSubjects();
		return ApiResult.success(responses);
	}

	@ApiOperation(value = "메인 공지사항 목록 조회 API")
	@GetMapping("/notices")
	public ApiResult<List<MainNoticeDTO.Response>> selectMainNotice() {
		List<MainNoticeDTO.Response> responses = mainService.selectMainNotice();

		return ApiResult.success(responses);
	}
}
