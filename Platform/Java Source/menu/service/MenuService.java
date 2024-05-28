package kr.ge.kwdi.gep.platform.domain.menu.service;

import egovframework.rte.fdl.property.impl.EgovPropertyServiceImpl;
import kr.ge.kwdi.gep.platform.domain.menu.dao.MenuDAO;
import kr.ge.kwdi.gep.platform.domain.menu.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService extends EgovPropertyServiceImpl {

	private final MenuDAO menuDAO;

	/**
	 * 플랫폼 메뉴 조회
	 *
	 * @param
	 * @return List<MenuDTO>
	 * @author mg.jeon
	 * @since 2024. 01.23
	 */
	public List<MenuDTO> selectPlatformMenuList() throws Exception {
		return menuDAO.selectPlatformMenuList();
	}
}
