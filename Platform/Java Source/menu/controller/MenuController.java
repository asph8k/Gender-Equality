package kr.ge.kwdi.gep.platform.domain.menu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.platform.domain.menu.dto.MenuDTO;
import kr.ge.kwdi.gep.platform.domain.menu.service.MenuService;
import kr.ge.kwdi.gep.platform.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "메뉴 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

	private final MenuService menuService;


	@ApiOperation(value = "메뉴 목록 조회 API")
	@GetMapping("")
	public ApiResult<List<MenuDTO>> getMenuList() throws Exception {
		return ApiResult.success(menuService.selectPlatformMenuList());
	}
}
