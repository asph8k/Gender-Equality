package kr.ge.kwdi.gep.platform.domain.menu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@ApiModel(value = "메뉴 DTO")
@Data
public class MenuDTO {
	@ApiModelProperty(value = "메뉴 번호")
	private int menuNo;
	@ApiModelProperty(value = "메뉴 이름")
	private String menuNm;
	@ApiModelProperty(value = "상위 메뉴 번호")
	private String upMenuId;
	@ApiModelProperty(value = "depth")
	private String depth;
	@ApiModelProperty(value = "호출 URL")
	private String menuPath;
	@ApiModelProperty(value = "코드")
	private String menuCd;


	@Getter
	@Builder
	public static class RootNodes {
		private int menuNo;
		private String menuNm;
		private String menuCd;
	}
	@Getter
	@Builder
	public static class SubNodes {
		private int upMenuId;
		private String menuNm;
		private String menuCd;
	}
}
