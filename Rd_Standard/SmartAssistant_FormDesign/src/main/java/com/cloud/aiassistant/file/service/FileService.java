package com.cloud.aiassistant.file.service;

import com.cloud.aiassistant.core.utils.SessionUserUtils;
import com.cloud.aiassistant.file.dao.FileMapper;
import com.cloud.aiassistant.pojo.file.PublicFile;
import com.cloud.aiassistant.pojo.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

/** 
* 文件上传下载Service
* @author cheng.yun
* @version 2019年3月19日 下午8:38:55
*/
@Service
@Slf4j
public class FileService {
	

	@Value("${file.upload.path}")
	private String fileUploadPath ;
	
	@Autowired
	private FileMapper fileDao;

	@Autowired
	private HttpSession session ;



	/**
	 * 增加一个文件到服务器当中: SpringBoot Jar包的统计目录 增加一个upload文件夹。
	 * @param originalFile 文件
	 * @return PublicFile
	 */
	@Transactional
	public PublicFile addFile(MultipartFile originalFile) throws IOException {
		if(null == originalFile){
			return null ;
		}

		//1：根据旧文件名或者新文件名，保存到服务器
		String fileNameNew = this.getFileNameNew(originalFile.getOriginalFilename());
		File _tmpFile=new File(fileUploadPath, fileNameNew);
		FileUtils.copyInputStreamToFile(originalFile.getInputStream(), _tmpFile);

		//2：数据入库
		User user = SessionUserUtils.getUserFromSession(session);
		PublicFile file = new PublicFile();
		file.setCreateUser(user.getId());
		file.setCreateTime(new Timestamp(System.currentTimeMillis()));
		file.setTenantId(user.getTenantId());
		file.setFileNameNew(fileNameNew);
		file.setFileNameOriginal(originalFile.getOriginalFilename());
		file.setAbsolutePath(_tmpFile.getAbsolutePath());
		file.setRelativePath(fileUploadPath+"/"+fileNameNew);
		file.setContentType(originalFile.getContentType());
		fileDao.insert(file);
		file.setUrl("/aiassistant/file/get/file?fileId="+file.getId());
		fileDao.updateUrl(file.getId(),file.getUrl());
		return file;
	}

	/**
	 * 根据fileId获得file实体类
	 * @param fileId
	 * @return
	 */
	public PublicFile getFile(Long fileId) {
		return fileDao.selectByPrimaryKey(fileId);
	}

	/**
	 * 根据原始文件名，获得新文件名，就是在原文件名后面+时间戳
	 * @param originalFilename
	 * @return String
	 */
	private String getFileNameNew(String originalFilename) {
		if(null!=originalFilename && originalFilename.length()>0){
			String[]  nameSplit=originalFilename.split("\\.");
			if(nameSplit.length==1){
				return originalFilename+System.currentTimeMillis();
			}else if(nameSplit.length==2){
				return nameSplit[0]+System.currentTimeMillis()+"."+nameSplit[1];
			}else{
				StringBuffer newName=new StringBuffer(nameSplit[0]);
				for(int i=1,j=nameSplit.length; i<j; i++){
					if(i==j-1){
						newName.append(System.currentTimeMillis()).append(".");
					}
					newName.append(nameSplit[i]);
				}
				return newName.toString();
			}
		}else{
			return System.currentTimeMillis()+"";
		}
	}

}
