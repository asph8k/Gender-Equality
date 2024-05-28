package kr.ge.kwdi.gep.platform.domain.banner.dao;

import org.apache.ibatis.annotations.Mapper;
import kr.ge.kwdi.gep.platform.domain.banner.dto.BannerVO;

import java.util.List;

@Mapper
public interface BannerDAO {

	List<BannerVO.result> getBannerList();
}
