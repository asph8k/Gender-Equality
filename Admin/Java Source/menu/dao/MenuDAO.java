package kr.ge.kwdi.gep.admin.domain.menu.dao;

import kr.ge.kwdi.gep.admin.domain.menu.dto.MenuVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDAO {

	// 메뉴 관리 목록 조회
	List<MenuVO.MenuListResult> selectMenuList(MenuVO.SearchParam searchParam);

	// 메뉴 관리 상세 조회
	MenuVO.DetailResult selectMenuDetail(Long entityId);

	// 저작권 유형 목록 조회
	List<MenuVO.PblcNuriLvlListResult> selectPblcNuriLvlList();

	// 저작권 담당 목록 조회
	List<MenuVO.PblcNuriMngListResult> selectPblcNuriMngList();

	// 메뉴 이미지 파일 조회
	MenuVO.MenuImgFileResult selectMenuImgFile(Long entityId);

	// 메뉴 그룹 리스트 조회
	List<MenuVO.MenuGrpListResult> selectMenuGroupList();

	// 메뉴 리스트 조회
	List<MenuVO.MenuClsList> selectMenuClsList();

	// 메뉴 리스트 전체 조회
	List<MenuVO.MenuListAllResult> selectMenuListAll();

	// 상위 아이디 조회
	MenuVO.UpperIdResult selectUpperId(Long entityId);

	MenuVO.CountOrdrResult selectCountOrdr(Long entityId);

	// 메뉴 사용, 미사용 리스트 조회
	List<MenuVO.UseOrNotMenuListResult> selectUseOrNotMenuList(MenuVO.DeleteParam deleteParam);

	// 메뉴 그룹 등록
	int insetMenuGroup(MenuVO.InsertParam insertParam);

	// 메뉴 관리 등록
	int insertMenu(MenuVO.InsertParam insertParam);

	// 대분류 메뉴 수정
	int updateMenuGroup(MenuVO.UpdateParam updateParam);

	// 메뉴 관리 수정
	int updateMenu(MenuVO.UpdateParam updateParam);

	// 메뉴 관리 수정 이미지 포함
	int updateMenuImg(MenuVO.UpdateParam updateParam);

	// 정렬 순서 + 1 증가
	int updateOrdrPlus(MenuVO.OrdrParam ordrParam);

	// 정렬 순서 - 1 감소
	int updateOrdrMinus(MenuVO.OrdrParam ordrParam);

	//메뉴 관리 삭제(list)
	int deleteMenuList(MenuVO.DeleteParam deleteParam);
}
