package kr.ge.kwdi.gep.platform.domain.notice.controlloer;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.platform.domain.notice.dto.NoticeDTO;
import kr.ge.kwdi.gep.platform.domain.notice.service.NoticeService;
import kr.ge.kwdi.gep.platform.domain.paing.PageInfo;
import kr.ge.kwdi.gep.platform.domain.paing.PagingModule;
import kr.ge.kwdi.gep.platform.global.common.ApiResult;
import kr.ge.kwdi.gep.platform.global.util.PageSizeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "공지사항 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/notices", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeController {

	private final NoticeService noticeService;

	@ApiOperation(value = "공지사항 목록 조회 API")
	@GetMapping("")
	public ApiResult<PageInfo<NoticeDTO.Response>> getNoticeList(NoticeDTO.Request request) {
		PageInfo<NoticeDTO.Response> responses = noticeService.selectNoticeList(request);

		return ApiResult.success(responses);
	}

	@ApiOperation(value = "공지사항 상세 조회 API")
	@GetMapping("/{noticeNo}")
	public ApiResult<NoticeDTO.Detail> getNoticeDetail(@PathVariable Long noticeNo) {
		log.info("NoticeController API detail :: getNoticeDetail = {}", noticeNo);
		NoticeDTO.Detail getNoticeDetail = noticeService.selectNoticeDetail(noticeNo);
		return ApiResult.success(getNoticeDetail);
	}

}
