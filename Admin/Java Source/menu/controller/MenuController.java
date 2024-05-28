package kr.ge.kwdi.gep.admin.domain.menu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.admin.domain.menu.dto.MenuDTO;
import kr.ge.kwdi.gep.admin.domain.menu.service.MenuService;
import kr.ge.kwdi.gep.admin.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "메뉴 관리 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

	private final MenuService menuService;

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'menus', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
	@ApiOperation(value = "메뉴 관리 목록 조회 API")
	@GetMapping("")
	public ApiResult<List<MenuDTO.MenuListResponse>> selectMenuList(MenuDTO.SearchRequest searchRequest) {
		List<MenuDTO.MenuListResponse> menuListResponses = menuService.selectMenuList(searchRequest);

		return ApiResult.success(menuListResponses);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'menus', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
	@ApiOperation(value = "메뉴 관리 상세 조회 API")
	@GetMapping("/{entityId}")
	public ApiResult<MenuDTO.DetailResponse> selectMenuDetail(@PathVariable Long entityId) {
		MenuDTO.DetailResponse detailResponse = menuService.selectMenuDetail(entityId);

		return ApiResult.success(detailResponse);
	}

	@ApiOperation(value = "저작권 유형 목록 조회 API")
	@GetMapping("/PblcNuriLvls")
	public ApiResult<List<MenuDTO.PblcNuriLvlListResponse>> selectPblcNuriLvlList() {
		List<MenuDTO.PblcNuriLvlListResponse> pblcNuriLvlListResponses = menuService.selectPblcNuriLvlList();

		return ApiResult.success(pblcNuriLvlListResponses);
	}

	@ApiOperation(value = "저작권 담당자 목록 조회 API")
	@GetMapping("/PblcNuriMngs")
	public ApiResult<List<MenuDTO.PblcNuriMngListResponse>> selectPblcNuriMngList() {
		List<MenuDTO.PblcNuriMngListResponse> pblcNuriMngListResponses = menuService.selectPblcNuriMngList();

		return ApiResult.success(pblcNuriMngListResponses);
	}

	@ApiOperation(value = "메뉴 그룹 리스트 조회 API")
	@GetMapping("/group")
	private ApiResult<MenuDTO.MenuGrpListResponse> selectMenuGroupList() {
		MenuDTO.MenuGrpListResponse menuGrpListResponse = menuService.selectMenuGroupList();

		return ApiResult.success(menuGrpListResponse);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'menus', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_CREATE)")
	@ApiOperation(value = "메뉴 관리 등록 API")
	@PostMapping("")
	public ApiResult<Integer> insertMenu(MenuDTO.InsertRequest insertRequest,
										 @RequestPart(value = "newFiles", required = false) MultipartFile newFiles) throws IOException {
		int insertMenuResult = menuService.insertMenu(insertRequest, newFiles);

		return ApiResult.success(insertMenuResult);
	}

	//@PreAuthorize("@userAccessAuthorization.authorization(principal, 'menus', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_UPDATE)")
	@ApiOperation(value = "메뉴 관리 수정 API")
	@PutMapping("/{entityId}")
	public ApiResult<Integer> updateMenu(@PathVariable Long entityId, MenuDTO.UpdateRequest updateRequest,
										 @RequestPart(value = "newFiles", required = false) MultipartFile newFiles) throws IOException {
		int updateMenuResult = menuService.updateMenu(entityId, updateRequest, newFiles);

		return ApiResult.success(updateMenuResult);
	}

	@PreAuthorize("@userAccessAuthorization.authorization(principal, 'menus', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_DELETE)")
	@ApiOperation(value = "메뉴 관리 삭제 API")
	@DeleteMapping("")
	public ApiResult<Integer> deleteMenuList(@RequestBody MenuDTO.deleteList deleteList) {
		int deleteMenuResult = menuService.deleteMenuList(deleteList);

		return ApiResult.success(deleteMenuResult);
	}
}
