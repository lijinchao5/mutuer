/**
 * @fileName:  FileController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月7日 上午11:02:17
 */
package com.xuanli.oepcms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.ThirdAliOSSUtil;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@RestController()
@RequestMapping(value = "/file/")
public class FileController extends BaseController {
	@Autowired
	ThirdAliOSSUtil thirdAliOSSUtil;

	@RequestMapping(value = "download.do", method = RequestMethod.GET)
	public void download(String type, String id) {
		if (null == id || id.trim().equals("")) {
		} else {
			String filename = UUID.randomUUID().toString().replace("-", "");
			if (type.equals("mp3")) {
				filename = filename + ".mp3";
				response.setContentType("audio/mpeg");
			} else {
				filename = filename + ".jpg";
				response.setContentType("image/jpeg");
			}
			if (StringUtil.isNotNullUnDefined(id)) {
				InputStream inputStream = null;
				OutputStream outputStream = null;
				try {
					outputStream = response.getOutputStream();
					inputStream = thirdAliOSSUtil.downloadFile(id);
					int read = 0;
					byte[] bytes = new byte[1024];
					while ((read = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}
					outputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("下载文件出现错误");
				} finally {
					try {
						if (null != outputStream) {
							outputStream.close();
						}
						if (null != inputStream) {
							inputStream.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				logger.info("文件id不能为空");
			}
		}

	}

	@RequestMapping(value = "uploadAudio.do", method = RequestMethod.POST)
	public RestResult<String> uploadAudio(@RequestParam("file") MultipartFile file) {

		try {
			if (null != file && !file.isEmpty()) {
				String uuid = thirdAliOSSUtil.uploadFile(file.getInputStream(), "studentaudio", "mp3");
				return ok(uuid);
			} else {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "文件不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "上传出现错误");
		}
	}

}
