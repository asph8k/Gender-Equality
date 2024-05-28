package kr.ge.kwdi.gep.admin.domain.banner.dao;

import kr.ge.kwdi.gep.admin.domain.banner.dto.BannerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerDAO {

    // 배너 관리 목록 조회
    List<BannerVO.BannerListResult> selectBannerList(BannerVO.SearchParam searchParam);

    // 배너 관리 상세 조회
    BannerVO.BannerDetailResult selectBannerDetail(Long entityId);

    // 배너 우선순위 1씩 증가
    BannerVO.BannerOrdrCountResult selectBannerOrdrCount(String positionCode);

    // 배너 이미지 파일 조회
    BannerVO.BannerImgFileResult selectBannerImgFile(Long entityId);

    // 배너 이미지 파일 리스트 조회
    List<BannerVO.BannerImgFileResult> selectBannerImgFileList(BannerVO.DeleteParam deleteParam);

    // 배너 위치 공통코드 조회
    List<BannerVO.BannerPositionCodeResult> selectBannerPositionCode();

    // 배너 타겟 공통코드 조회
    List<BannerVO.BannerTargetCodeResult> selectBannerTargetCode();

    // 배너 관리 등록
    int insertBannerAndImg(BannerVO.InsertParam insertParam);

    // 배너 관리 등록(기간 제외)
    int insertBannerStartDtAndEndDtNull(BannerVO.InsertParam insertParam);

    // 배너 관리 수정(이미지 포함)
    int updateBannerAndImg(BannerVO.UpdateParam updateParam);

    // 배너 관리 수정(이미지 미포함)
    int updateBanner(BannerVO.UpdateParam updateParam);

    // 배너 관리 삭제
    int updateBannerDelYn(BannerVO.DeleteParam deleteParam);

    // 우선 순위 + 1 증가
    int updateOrdrPlus(BannerVO.OrdrParam ordrParam);

    // 우선 순위 - 1 감소
    int updateOrdrMinus(BannerVO.OrdrParam ordrParam);
}
