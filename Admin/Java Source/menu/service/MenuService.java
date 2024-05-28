package kr.ge.kwdi.gep.admin.domain.menu.service;

import egovframework.rte.fdl.property.impl.EgovPropertyServiceImpl;
import kr.ge.kwdi.gep.admin.domain.account.dto.AccountVO;
import kr.ge.kwdi.gep.admin.domain.menu.dao.MenuDAO;
import kr.ge.kwdi.gep.admin.domain.menu.dto.MenuDTO;
import kr.ge.kwdi.gep.admin.domain.menu.dto.MenuVO;
import kr.ge.kwdi.gep.admin.global.common.error.ErrorCode;
import kr.ge.kwdi.gep.admin.global.common.error.GlobalException;
import kr.ge.kwdi.gep.admin.global.constant.ConstantValue;
import kr.ge.kwdi.gep.admin.global.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.regex.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService extends EgovPropertyServiceImpl {

	private final MenuDAO menuDAO;

	@Value("${file.path}")
	private String filePath;

	// 메뉴 관리 목록 조회
	public List<MenuDTO.MenuListResponse> selectMenuList(MenuDTO.SearchRequest searchRequest) {
		MenuVO.SearchParam searchParam = MenuVO.SearchParam.builder()
				.q(searchRequest.getQ())
				.searchType(searchRequest.getSearchType())
				.build();
		// 조회
		List<MenuVO.MenuListResult> results = menuDAO.selectMenuList(searchParam);
		// 변환
		List<MenuDTO.MenuListResponse> menuListResponses = results.stream()
				.map(result -> MenuDTO.MenuListResponse.builder()
						.id(result.getId())
						.groupName(result.getGroupName())
						.name(result.getName())
						.upperId(result.getUpperId())
						.url(result.getUrl())
						.levelName(result.getLevelName())
						.ordr(result.getOrdr())
						.visible(TextUtil.convertStringToBoolean(result.getVisible()))
						.build())
				.collect(Collectors.toList());

		return menuListResponses;
	}

	// 메뉴 관리 상세 조회
	public MenuDTO.DetailResponse selectMenuDetail(Long entityId) {
		// 조회
		MenuVO.DetailResult detailResult = menuDAO.selectMenuDetail(entityId);

		// 변환
		MenuDTO.DetailResponse detailResponse = MenuDTO.DetailResponse.builder()
				.id(detailResult.getId())
				.upperId(detailResult.getUpperId())
				.groupName(detailResult.getGroupName())
				.name(detailResult.getName())
				.menuId(detailResult.getMenuId())
				.url(detailResult.getUrl())
				.nuriLevelId(detailResult.getNuriLevelId())
				.levelName(detailResult.getLevelName())
				.content(detailResult.getContent())
				.nuriMangerId(detailResult.getNuriMangerId())
				.department(detailResult.getDepartment())
				.manager(detailResult.getManager())
				.ordr(detailResult.getOrdr())
				.ordrCount(detailResult.getOrdrCount())
				.visible(TextUtil.convertStringToBoolean(detailResult.getVisible()))
				.rmrk(detailResult.getRmrk())
				.imageName(detailResult.getOriginalName())
				.image(ConstantValue.IMAGE_LOAD_API + detailResult.getStorageName())
				.build();

		return detailResponse;
	}

	// 저작권 유형 목록 조회
	public List<MenuDTO.PblcNuriLvlListResponse> selectPblcNuriLvlList() {
		// 조회
		List<MenuVO.PblcNuriLvlListResult> pblcNuriLvlListResult = menuDAO.selectPblcNuriLvlList();

		// 변환
		List<MenuDTO.PblcNuriLvlListResponse> pblcNuriLvlListResponses = pblcNuriLvlListResult.stream()
				.map(result -> MenuDTO.PblcNuriLvlListResponse.builder()
						.id(result.getId())
						.levelId(result.getLevelId())
						.levelName(result.getLevelName())
						.content(result.getContent())
						.build())
				.collect(Collectors.toList());

		return pblcNuriLvlListResponses;
	}

	// 저작권 담당 목록 조회
	public List<MenuDTO.PblcNuriMngListResponse> selectPblcNuriMngList() {
		// 조회
		List<MenuVO.PblcNuriMngListResult> pblcNuriMngListResult = menuDAO.selectPblcNuriMngList();

		// 변환
		List<MenuDTO.PblcNuriMngListResponse> pblcNuriMngListResponses = pblcNuriMngListResult.stream()
				.map(result -> MenuDTO.PblcNuriMngListResponse.builder()
						.id(result.getId())
						.department(result.getDepartment())
						.manager(result.getManager())
						.build())
				.collect(Collectors.toList());

		return pblcNuriMngListResponses;
	}

	// 메뉴 그룹 리스트 조회
	public MenuDTO.MenuGrpListResponse selectMenuGroupList() {
		// 메뉴 그룹명 리스트 조회
		List<MenuVO.MenuGrpListResult> menuGrpListResults = menuDAO.selectMenuGroupList();
		// 메뉴명 리스트 조회
		List<MenuVO.MenuClsList> menuClsLists = menuDAO.selectMenuClsList();

		MenuDTO.MenuGrpListResponse menuGrpListResponse = MenuDTO.MenuGrpListResponse.builder()
				.menuGroup(menuGrpListResults.stream().filter(menuGrpListResult -> Objects.equals("groupCode", menuGrpListResult.getCodeType())).collect(
						Collectors.toList()))
				.menu(menuClsLists.stream().filter(menuClsList -> Objects.equals(menuClsList.getCodeType(), "cd")).collect(Collectors.toList()))
				.build();

		return menuGrpListResponse;
	}

	// 메뉴 관리 등록
	@Transactional
	public int insertMenu(MenuDTO.InsertRequest insertRequest, MultipartFile newFiles) throws IOException {
		// 반환값 설정
		int result = 0;

		// 로그인 중인 사용자 정보
		AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Url 등록 시 "/gep/" 가 아닌 문자열 검증
		String urlCheck = insertRequest.getUrl();
		String check = urlCheck.substring(0, 5);

		// /gep/ 뒤에 특수문자 검증
		String regex = "^/gep/[a-zA-Z0-9]*$";
		Pattern pattern = Pattern.compile(regex);

		boolean isMatch = pattern.matcher(urlCheck).matches();

		// 기존에 등록되어 있는 메뉴 리스트 조회
		List<MenuVO.MenuListAllResult> menuListAllResults = menuDAO.selectMenuListAll();

		String imgFileActlStrgNm = uploadImgFile(newFiles);

		// 기존에 있던 메뉴 그룹을 등록 하는게 아니라면 작동
		if(!ObjectUtils.isEmpty(insertRequest.getUpperId())) {
			// 직접 입력한 메뉴명과 기존에 등록 되어 있는 메뉴명이 동일한지 검증
			for(MenuVO.MenuListAllResult menuListAllResult : menuListAllResults) {
				if(insertRequest.getName().equals(menuListAllResult.getName())) {
					throw new GlobalException(ErrorCode.SAME_MENU, "same menu");
				}
			}

			// 입력한 URL이 /gep/가 맞는지 검증
			if(!check.equals("/gep/")) {
				throw new GlobalException(ErrorCode.BAD_URL_INSERT, "bad url");
			}

			// URL에 특수문자가 포함되어 있는지 검증
			if(!isMatch) {
				throw new GlobalException(ErrorCode.NOT_SPECIAL_SYMBOL, "do not special symbol");
			}

			// 정렬순서 자동 1씩 증가 값 조회
			MenuVO.CountOrdrResult countOrdrResult = menuDAO.selectCountOrdr(insertRequest.getUpperId());

			MenuVO.InsertParam insertParam = MenuVO.InsertParam.builder()
					.upperId(insertRequest.getUpperId())
					.name(insertRequest.getName())
					.levelId(insertRequest.getLevelId())
					.mngrId(insertRequest.getMngrId())
					.url(insertRequest.getUrl())
					.visible(TextUtil.convertBooleanToString(insertRequest.getVisible()))
					.rmrk(insertRequest.getRmrk())
					.level(Long.valueOf(2))
					.ordr(countOrdrResult.getOrdrCount())
					.originalName(newFiles.getOriginalFilename())
					.storageName(imgFileActlStrgNm)
					.imgSize(newFiles.getSize())
					.frstJobObj(accountVO.getJobObject())
					.build();

			int menuInsertCheck = menuDAO.insertMenu(insertParam);

			if(menuInsertCheck == 1) {
				result = 1;
			}
		} else { // 새로 등록하는 메뉴라면 작동
			// 직접 입력한 메뉴명과 기존에 등록 되어 있는 메뉴명이 동일한지 검증
			for(MenuVO.MenuListAllResult menuListAllResult : menuListAllResults) {
				if(insertRequest.getName().equals(menuListAllResult.getName()) || insertRequest.getGroupName().equals(menuListAllResult.getName())) {
					throw new GlobalException(ErrorCode.SAME_MENU, "same menu");
				}
			}

			if(!check.equals("/gep/")) {
				throw new GlobalException(ErrorCode.BAD_URL_INSERT, "bad url");
			}

			if(!isMatch) {
				throw new GlobalException(ErrorCode.NOT_SPECIAL_SYMBOL, "do not special symbol");
			}

			// 대분류 메뉴 Insert
			MenuVO.InsertParam insertParam = MenuVO.InsertParam.builder()
					.name(insertRequest.getGroupName())
					.url(insertRequest.getUrl())
					.level(Long.valueOf(1))
					.frstJobObj(accountVO.getJobObject())
					.build();

			int groupMenuInsertCheck = menuDAO.insetMenuGroup(insertParam);

			MenuVO.CountOrdrResult countOrdrResult = menuDAO.selectCountOrdr(insertParam.getEntityId());

			// 소분류 메뉴 Insert
			MenuVO.InsertParam InsertMenuParam = MenuVO.InsertParam.builder()
					.upperId(insertParam.getEntityId())
					.name(insertRequest.getName())
					.url(insertRequest.getUrl())
					.levelId(insertRequest.getLevelId())
					.mngrId(insertRequest.getMngrId())
					.visible(TextUtil.convertBooleanToString(insertRequest.getVisible()))
					.rmrk(insertRequest.getRmrk())
					.level(Long.valueOf(2))
					.ordr(countOrdrResult.getOrdrCount())
					.originalName(newFiles.getOriginalFilename())
					.storageName(imgFileActlStrgNm)
					.imgSize(newFiles.getSize())
					.frstJobObj(accountVO.getJobObject())
					.build();

			int menuInsertCheck = menuDAO.insertMenu(InsertMenuParam);

			if(groupMenuInsertCheck == 1 && menuInsertCheck == 1) {
				result = 1;
			}
		}

		return result;
	}

	// 메뉴 관리 수정
	public int updateMenu(Long entityId, MenuDTO.UpdateRequest updateRequest, MultipartFile newFiles) throws IOException {
		// 반환값 설정
		int result = 0;

		// 로그인 중인 사용자 정보
		AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 메뉴 그룹명에 필요한 상위아이디를 조회
		MenuVO.UpperIdResult upperIdResult = menuDAO.selectUpperId(entityId);

		// 정렬 순서 업데이트 시 현재 가지고 있는 순서를 파악하기 위해 상세 조회 재호출
		MenuVO.DetailResult detailResult = menuDAO.selectMenuDetail(entityId);
		int ordr = detailResult.getOrdr();

		if(ObjectUtils.isEmpty(newFiles)) {
			// 정렬 순서 업데이트
			if(updateRequest.getOrdr() > ordr) {
				MenuVO.OrdrParam ordrParam = MenuVO.OrdrParam.builder()
						.entityId(detailResult.getUpperId())
						.exstnOrdr(ordr)
						.changeOrdr(updateRequest.getOrdr())
						.build();

				menuDAO.updateOrdrMinus(ordrParam);
			}

			if(updateRequest.getOrdr() < ordr) {
				MenuVO.OrdrParam ordrParam = MenuVO.OrdrParam.builder()
						.entityId(detailResult.getUpperId())
						.exstnOrdr(ordr)
						.changeOrdr(updateRequest.getOrdr())
						.build();

				menuDAO.updateOrdrPlus(ordrParam);
			}

			// 대분류 메뉴 수정
			MenuVO.UpdateParam updateParam = MenuVO.UpdateParam.builder()
					.entityId(upperIdResult.getUpperId())
					.name(updateRequest.getGroupName())
					.lastJobObj(accountVO.getJobObject())
					.build();

			int updateGroupMenuResult = menuDAO.updateMenuGroup(updateParam);

			// 소분류 메뉴 수정
			MenuVO.UpdateParam updateMenuParam = MenuVO.UpdateParam.builder()
					.entityId(entityId)
					.name(updateRequest.getName())
					.levelId(updateRequest.getLevelId())
					.mngrId(updateRequest.getMngrId())
					.ordr(updateRequest.getOrdr())
					.visible(TextUtil.convertBooleanToString(updateRequest.getVisible()))
					.rmrk(updateRequest.getRmrk())
					.lastJobObj(accountVO.getJobObject())
					.build();

			int updateMenuNmResult = menuDAO.updateMenu(updateMenuParam);

			if(updateGroupMenuResult == 1 && updateMenuNmResult == 1) {
				result = 1;
			}
		} else {
			MenuVO.MenuImgFileResult menuImgFileResult = menuDAO.selectMenuImgFile(entityId);
			String deleteResult = deleteImgFile(menuImgFileResult.getStorageName());
			log.info("deleteFileResult === {}", deleteResult);

			String imgFileActlStrgNm = uploadImgFile(newFiles);

			// 정렬 순서 업데이트
			if(updateRequest.getOrdr() > ordr) {
				MenuVO.OrdrParam ordrParam = MenuVO.OrdrParam.builder()
						.entityId(detailResult.getUpperId())
						.exstnOrdr(ordr)
						.changeOrdr(updateRequest.getOrdr())
						.build();

				menuDAO.updateOrdrMinus(ordrParam);
			}

			if(updateRequest.getOrdr() < ordr) {
				MenuVO.OrdrParam ordrParam = MenuVO.OrdrParam.builder()
						.entityId(detailResult.getUpperId())
						.exstnOrdr(ordr)
						.changeOrdr(updateRequest.getOrdr())
						.build();

				menuDAO.updateOrdrPlus(ordrParam);
			}

			// 대분류 메뉴 수정
			MenuVO.UpdateParam updateParam = MenuVO.UpdateParam.builder()
					.entityId(upperIdResult.getUpperId())
					.name(updateRequest.getGroupName())
					.lastJobObj(accountVO.getJobObject())
					.build();

			int updateGroupMenuResult = menuDAO.updateMenuGroup(updateParam);

			// 소분류 메뉴 수정
			MenuVO.UpdateParam updateMenuParam = MenuVO.UpdateParam.builder()
					.entityId(entityId)
					.name(updateRequest.getName())
					.levelId(updateRequest.getLevelId())
					.mngrId(updateRequest.getMngrId())
					.visible(TextUtil.convertBooleanToString(updateRequest.getVisible()))
					.rmrk(updateRequest.getRmrk())
					.ordr(updateRequest.getOrdr())
					.originalName(newFiles.getOriginalFilename())
					.storageName(imgFileActlStrgNm)
					.imgSize(newFiles.getSize())
					.lastJobObj(accountVO.getJobObject())
					.build();

			int updateMenuNmResult = menuDAO.updateMenuImg(updateMenuParam);

			if(updateGroupMenuResult == 1 && updateMenuNmResult == 1) {
				result = 1;
			}
		}

		return result;
	}

	// 메뉴 관리 삭제
	public int deleteMenuList(MenuDTO.deleteList deleteList) {
		// 반환값 설정
		int result = 0;

		// 삭제 시 표시중인 메뉴가 삭제 되지 않게 하기 위한 메뉴 리스트 조회
		MenuVO.DeleteParam param = MenuVO.DeleteParam.builder()
				.list(deleteList.getDeleteList())
				.build();

		List<MenuVO.UseOrNotMenuListResult> useOrNotMenuListResults = menuDAO.selectUseOrNotMenuList(param);

		for(MenuVO.UseOrNotMenuListResult useOrNotMenuListResult : useOrNotMenuListResults) {
			if(useOrNotMenuListResult.getVisible().equals("Y")) {
				throw new GlobalException(ErrorCode.BAD_MENU_DEL, "bad menu delete");
			}
		}

		int deleteMenuResult = menuDAO.deleteMenuList(param);

		if(deleteMenuResult == 1) {
			result = 1;
		}

		return result;
	}



	public String uploadImgFile(MultipartFile file) throws IOException {
		LocalDate localDate = LocalDate.now();
		String date = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String uuid = UUID.randomUUID().toString();
		String saveName = "menu/" + uuid + "_" + date;
		String savePath = filePath + saveName;
		System.out.println("savePath = " + savePath);

		isDirectoryExists(savePath);

		file.transferTo(new File(savePath));

		return saveName;
	}

	public String deleteImgFile(String savePath) {
		File file = new File(filePath + savePath);
		if (file.exists()) {
			if (file.delete()) {
				return "삭제 완료";
			} else {
				return "삭제 실패";
			}
		} else {
			return "파일 없음";
		}
	}

	public void isDirectoryExists(String path) throws IOException {
		Path directory = Paths.get(path);

		if (!Files.exists(directory)) {
			Files.createDirectories(directory);
		}
	}
}
