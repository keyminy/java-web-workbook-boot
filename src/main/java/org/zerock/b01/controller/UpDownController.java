package org.zerock.b01.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.upload.UploadFileDTO;
import org.zerock.b01.dto.upload.UploadResultDTO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@Log4j2
public class UpDownController {
	
	@Value("${org.zerock.upload.path}")
	private String uploadPath;
	
	@ApiOperation(value="Upload POST",notes = "POST 방식으로 파일 등록")
	@PostMapping(value="/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO) {
		log.info(uploadFileDTO);
		if(uploadFileDTO.getFiles() != null) {
			final List<UploadResultDTO> list = new ArrayList<>();
			//file이 있으면
			uploadFileDTO.getFiles().forEach(multipartFile -> {
				String originalName =(multipartFile.getOriginalFilename());
				log.info(originalName);
				String uuid = UUID.randomUUID().toString();
				Path savePath = Paths.get(uploadPath,uuid+"_"+originalName); 
				boolean image = false;
				try {
					multipartFile.transferTo(savePath);//실제 파일 해당 경로로 저장
					//썸네일 이미지도 업로드
					//이미지 파일의 종류일때만!!
					if(Files.probeContentType(savePath).startsWith("image")) {
						image = true;
						File thumbFile = new File(uploadPath,"s_"+uuid+"_"+originalName);
						Thumbnailator.createThumbnail(savePath.toFile(),thumbFile,200,200);
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
				list.add(UploadResultDTO.builder()
							.uuid(uuid)
							.fileName(originalName)
							.img(image).build());
			});//end forEach
			return list;
		}//end if
		return null;
	}
	
	@ApiOperation(value="view 파일",notes = "GET방식으로 첨부파일 조회")
	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
		Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
		
		String resourceName = resource.getFilename();
		//System.out.println("resourceName : " + resourceName);
		//a0d52323-61af-40a3-986c-ef96a976b79a_lip.jpg
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add("Content-Type"
					,Files.probeContentType(resource.getFile().toPath()));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(resource,headers,HttpStatus.OK);
	}
	
	//삭제 : 원본 + 썸네일(파일이 이미지 일 때) 삭제
	@ApiOperation(value="remove 파일",notes = "DELETE 방식으로 파일 삭제")
	@DeleteMapping("/remove/{fileName}")
	public Map<String,Boolean> removeFile(@PathVariable String fileName){
		Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
		String resourceName = resource.getFilename();
		
		Map<String,Boolean> resultMap = new HashMap<>();
		boolean removed = false;
		
		try {
			String contentType = Files.probeContentType(resource.getFile().toPath());
			removed = resource.getFile().delete();
			//System.out.println("contentType : " + contentType);
			//contentType : image/jpeg

			//이미지 파일이면 썸네일도 삭제하게한다.
			if(contentType.startsWith("image")) {
				File thumbFile = new File(uploadPath+File.separator+"s_"+fileName);
				thumbFile.delete();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		resultMap.put("result", removed);
		return resultMap;
	}
}
