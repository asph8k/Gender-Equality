package kr.ge.kwdi.gep.admin.domain.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.ge.kwdi.gep.admin.domain.notice.dto.NoticeListVO;

@Mapper
public interface NoticeListDAO {
	//공지사항 목록 조회
	List<NoticeListVO.ListResult> selectNoticeList(NoticeListVO.SearchParam searchParam);

	//공지사항 상세 조회
	NoticeListVO.DetailResult selectNoticeDetail(Long entityId);

	//공지사항 첨부파일 상세 조회
	List<NoticeListVO.FileResult> selectNoticeFlies(Long entityId);

	//공지사항 리스트 생성
	int insertNoticeList(NoticeListVO.InsertParam insertParam);

	//공지사항 첨부파일 등록
	int insertNoticeFile(NoticeListVO.FileInsertParam fileInsertParam);

	//공지사항 리스트 수정
	int updateNoticeList(NoticeListVO.UpdateParam updateParam);

	//공지사항 리스트 삭제
	int deleteNoticeList(NoticeListVO.DeleteParam deleteParam);

	//첨부파일 삭제
	int deleteNoticeFlie(List<Long> fileIdList);
}
