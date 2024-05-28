package kr.ge.kwdi.gep.platform.domain.main.dao;

import org.apache.ibatis.annotations.Mapper;
import kr.ge.kwdi.gep.platform.domain.main.dto.*;

import java.util.List;

@Mapper
public interface MainDAO {
	List<MultimediaVO.Result> selectCardImage();
	List<MultimediaVO.Result> selectPhotoImage();
	List<MultimediaVO.Result> selectWebtoonImage();


	List<MainVdoVO.Result> selectMainVdo();

	List<MainNewsVO.Result> selectMainNews();

	List<BySubjectVO.Result> selectDataBySubject(BySubjectVO.Param bySubjectParamVO);

	List<BySubjectVO.Result> selectDataBySubjectAndIntr(BySubjectVO.Param bySubjectParamVO);

	List<MainNewsConverageVO.Result> selectMainNewsConverage();

	// 공지사항 목록 조회
	List<MainNoticeVO.Result> selectMainNotice();
}