package kr.ge.kwdi.gep.platform.domain.notice.dao;

import org.apache.ibatis.annotations.Mapper;
import kr.ge.kwdi.gep.platform.domain.notice.dto.NoticeVO;

import java.util.List;

@Mapper
public interface NoticeDAO {

	// 공지사항 목록
	List<NoticeVO.NoticeList> selectNoticeList(NoticeVO.Param noticeVO);
	int selectNoticeCount(NoticeVO.Param noticeVO);

	// 공지사항 상세
	NoticeVO.Detail selectNoticeDetail(Long noticeNo);

	// 공지사항 이전 글
	NoticeVO.Previous selectNoticePrevious(Long noticeNo);

	// 공지사항 다음 글
	NoticeVO.Next selectNoticeNext(Long noticeNo);

}
