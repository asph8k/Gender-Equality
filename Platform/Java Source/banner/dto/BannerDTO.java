package kr.ge.kwdi.gep.platform.domain.banner.dto;

import lombok.*;

public class BannerDTO {

	public static class request {

	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	public static class Response {
		private String bnnrNm;
		private String bnnrCn;
		private String bnntUrlAddr;
		private String bnnrLink;
	}
}
