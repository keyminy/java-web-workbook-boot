package org.zerock.b01.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
	
	private String uuid;
	
	private String fileName;
	
	//이미지의 여부
	private boolean img;
	
	//첨부파일의 경로처리
	public String getLink() {
		if(img) {
			return "s_" + uuid + "_" + fileName;//이미지인 경우 썸네일의 경로
		}else {
			return uuid+"_"+fileName;
		}
	}
}
