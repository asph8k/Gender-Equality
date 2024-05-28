package kr.ge.kwdi.gep.platform.domain.notice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.ge.kwdi.gep.platform.domain.file.service.FileService;
import kr.ge.kwdi.gep.platform.domain.notice.dao.NoticeDAO;
import kr.ge.kwdi.gep.platform.domain.notice.dto.NoticeDTO;
import kr.ge.kwdi.gep.platform.domain.notice.dto.NoticeVO;
import kr.ge.kwdi.gep.platform.domain.paing.PageInfo;
import kr.ge.kwdi.gep.platform.domain.paing.PagingModule;
import kr.ge.kwdi.gep.platform.global.common.countevent.ViewEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeDAO noticeDAO;
	private final FileService fileService;

	/** 공지사항 목록 */
	public PageInfo<NoticeDTO.Response> selectNoticeList(NoticeDTO.Request request) {
		NoticeVO.Param noticeVO = NoticeVO.Param.builder()
			.pageIndex(request.getPageIndex() < 1 ? 0 : (request.getPageIndex() - 1) * 10)
			.rowCount(request.getRowCount())
			.q(request.getQ())
			.searchType(request.getSearchType())
			.build();

		List<NoticeDTO.Response> responses = getResponseList(noticeDAO.selectNoticeList(noticeVO));
		int count = noticeDAO.selectNoticeCount(noticeVO);

		PagingModule.PagingHelper<NoticeDTO.Response> pagingHelper = new PagingModule.PagingHelper<>();
		PageInfo<NoticeDTO.Response> pageInfo = pagingHelper.getPageInfo(responses, request.getPageIndex(), count);

		return pageInfo;
	}

	/** 공지사항 상세 */
	@ViewEventHandler
	public NoticeDTO.Detail selectNoticeDetail(Long noticeNo) {
		log.info("NoticeService :: selectNoticeDetail :: noticeNo = {}", noticeNo);
		NoticeVO.Detail noticeVo = noticeDAO.selectNoticeDetail(noticeNo);

		if (noticeVo == null) {
			throw new IllegalArgumentException(
				"noticeNo=" + noticeNo + "에 해당하는 공지사항이 존재하지 않습니다.");
		}
		// 이전글 조회
		NoticeVO.Previous previous = noticeDAO.selectNoticePrevious(noticeVo.getNtcMttrNo());
		// 다음글 조회
		NoticeVO.Next next = noticeDAO.selectNoticeNext(noticeVo.getNtcMttrNo());

		return getResponse(noticeVo, previous, next);
	}

	private List<NoticeDTO.Response> getResponseList(List<NoticeVO.NoticeList> results) {
		return results.stream()
			.map(vo -> NoticeDTO.Response.builder()
				.noticeNo(vo.getNtcMttrNo())
				.postTitle(vo.getPstTtl())
				.registrationDate(vo.getFrstJobDt())
				.build())
			.collect(Collectors.toList());
	}

	private NoticeDTO.Detail getResponse(NoticeVO.Detail noticeVO, NoticeVO.Previous previous, NoticeVO.Next next) {
		return NoticeDTO.Detail.builder()
			.noticeNo(noticeVO.getNtcMttrNo())
			.postTitle(noticeVO.getPstTtl())
			.postContent(noticeVO.getPstCn())
			.registrationDate(noticeVO.getFrstJobDt())
			.postAttachedFileYn(noticeVO.getPstAtchFileYn())
			.atchFileList(fileService.getNoticeAtchFileList(noticeVO.getNtcMttrNo()))
			.previous(previous)
			.next(next)
			.build();
	}

}
