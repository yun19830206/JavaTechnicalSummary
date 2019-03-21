package com.cloud.aiassistant.file.web;

import com.cloud.aiassistant.file.service.FileService;
import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.file.PublicFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/** 
* @dese 文件操作Controller
* @author cheng.yun
* @version 2016年9月1日 下午10:28:12
*/
@Controller
@RequestMapping("/aiassistant/file")
@Slf4j
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * 上传一个附件
	 */
	@RequestMapping("/add/file")
	@ResponseBody
	public AjaxResponse addFile(@RequestParam(required = false) MultipartFile file, HttpServletRequest request){
		if(null == file){
			return AjaxResponse.failed(null,"上传文件为空");
		}
		try {
			PublicFile addedFile = fileService.addFile(file);
			//去除敏感信息
			addedFile.setAbsolutePath(null);
			addedFile.setRelativePath(null);
			return AjaxResponse.success(addedFile,"新增成功");
		} catch (IOException e) {
			e.printStackTrace();
			return AjaxResponse.failed(null,"新增失败");
		}
	}

	/**
	 * 根据文件ID，找到文件存放在磁盘上的绝对路径，并且进行下载
	 */
	@RequestMapping("/get/file")
	public ResponseEntity<byte[]> getFile(Long fileId) throws Exception{
		if (null == fileId) {
			return null;
		}
		PublicFile taskFile = fileService.getFile(fileId);
		if (null == taskFile || null == taskFile.getAbsolutePath()) {
			return null;
		}
		File file = new File(taskFile.getAbsolutePath());
		HttpHeaders headers = new HttpHeaders();
		//为了解决中文名称乱码问题
		String fileName = new String(taskFile.getFileNameOriginal().getBytes("UTF-8"), "iso-8859-1");
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
	

	/**
	 * 统一处理Controller的Exception，不把异常抛给前端
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	public AjaxResponse handle(Exception e) {
		log.error(e.getMessage(),e);
		return AjaxResponse.failed(null,"后台服务调用失败"+e.getMessage());
	}
}
