package com.jt.manage.service;

import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

public interface UploadService {
	
	public PicUploadResult uploadPicture(MultipartFile uploadFile);
}
