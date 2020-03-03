package com.jt.manage.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService{

	@Value("${image.uploadPath}")
	private String uploadPath;
	@Value("${image.urlPath}")
	private String urlPath="http://image.jt.com/";
	
	@Override
	public PicUploadResult uploadPicture(MultipartFile uploadFile) {
		String fileName=uploadFile.getOriginalFilename();
		fileName=fileName.toLowerCase();
		PicUploadResult picUploadResult=new PicUploadResult();
//		//拒绝接受不是以.jpg .png .gif 为后缀的文件
//		if(!fileName.matches("^.*\\.(jpg|png|gif)$")) {
//			picUploadResult.setError(1);
//			return picUploadResult;
//		}

		try {
			//检测是否为图片，而非恶意程序
			BufferedImage bufferedImage=ImageIO.read(uploadFile.getInputStream());
			int height=bufferedImage.getHeight();
			int width=bufferedImage.getWidth();
			if(height==0||width==0) {
				picUploadResult.setError(1);
				return picUploadResult;
			}
			picUploadResult.setHeight(height+"");
			picUploadResult.setWidth(width+"");
			String dir=new SimpleDateFormat("yyyy/MM/dd").format(new Date()).toString();
			File root=new File(uploadPath+dir);
			if(!root.exists()) {
				root.mkdirs();
			}
			Random random=new Random();
			System.out.println(fileName);
			fileName=UUID.randomUUID().toString()+random.nextInt(1000)+fileName.substring(fileName.indexOf("."));
			uploadFile.transferTo(new File(root+"/"+fileName));
			picUploadResult.setUrl(urlPath+dir+"/"+fileName);
		}catch (Exception e) {
			e.printStackTrace();
			picUploadResult.setError(1);
			return picUploadResult;
		}
		return picUploadResult;
	}

}
