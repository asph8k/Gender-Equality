package kr.ge.kwdi.gep.platform.domain.menu.dao;

import org.apache.ibatis.annotations.Mapper;
import kr.ge.kwdi.gep.platform.domain.menu.dto.MenuDTO;

import java.util.List;

@Mapper
public interface MenuDAO {
	/**
	 * 플랫폼 메뉴 조회
	 *
	 * @param
	 * @return List<MenuDTO>
	 * @author mg.jeon
	 * @since 2024. 01.23
	 */
	List<MenuDTO> selectPlatformMenuList() throws Exception;

	List<MenuDTO.RootNodes> selectMenuRootNodesInfo();

	List<MenuDTO.SubNodes> selectSubNodesInfo();
}
