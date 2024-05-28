package kr.ge.kwdi.gep.platform.domain.banner.dto;

import lombok.Getter;

public class BannerVO {

	@Getter
	public static class result {
		private String name;
		private String content;
		private String url;
		private String link;
	}
	public static class param {
	}
}
