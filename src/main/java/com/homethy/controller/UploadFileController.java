package com.homethy.controller;

import com.homethy.UserHolder;
import com.homethy.constant.Constant;
import com.homethy.param.MultipartFileParam;
import com.homethy.service.StorageService;
import com.homethy.util.ReturnJacksonUtil;
import com.homethy.vo.ResultStatus;
import com.homethy.vo.ResultVo;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 默认控制层
 * Created by wenwen on 2017/4/11.
 * version 1.0
 */
@Controller
public class UploadFileController {

  private Logger logger = LoggerFactory.getLogger(UploadFileController.class);

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private StorageService storageService;

  @Autowired
  UserHolder userHolder;

  //      /**
//     * 秒传判断，断点判断
//     *
//     * @return
//     */
//    @RequestMapping(value = "upload/checkFileMd5", method = RequestMethod.POST)
//    @ResponseBody
//    public Object checkFileMd5(String md5) throws IOException {
//        Object processingObj = stringRedisTemplate.opsForHash().get(Constant.FILE_UPLOAD_STATUS, md5);
//        if (processingObj == null) {
//            return new ResultVo(ResultStatus.NO_HAVE);
//        }
//        String processingStr = processingObj.toString();
//        boolean processing = Boolean.parseBoolean(processingStr);
//        String value = stringRedisTemplate.opsForValue().get(Constant.FILE_MD5_KEY + md5);
//        if (processing) {
//            return new ResultVo(ResultStatus.IS_HAVE, value);
//        } else {
//            File confFile = new File(value);
//            byte[] completeList = FileUtils.readFileToByteArray(confFile);
//            List<String> missChunkList = new LinkedList<>();
//            for (int i = 0; i < completeList.length; i++) {
//                if (completeList[i] != Byte.MAX_VALUE) {
//                    missChunkList.add(i + "");
//                }
//            }
//            return new ResultVo<>(ResultStatus.ING_HAVE, missChunkList);
//        }
//    }
  @RequestMapping(value = "upload", method = RequestMethod.GET)
  public ModelAndView csrView() {
    ModelAndView view = new ModelAndView("tools/upload/upload_file");
    view.addObject("user", userHolder.getUserInfo());
    view.addObject("env",userHolder.getENV());
    return view;
  }

  /**
   * 上传文件
   *
   * @param param
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "upload/fileUpload", method = RequestMethod.POST)
  @ResponseBody
  public String fileUpload(MultipartFileParam param, HttpServletRequest request) {
    String result;
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    if (isMultipart) {
      logger.info("上传文件start。");
      try {
        result = storageService.upload(param);
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("文件上传失败。{}", param.toString());
        result = ReturnJacksonUtil.resultFail("文件上传失败:" + e.getMessage());
      }
      logger.info("上传文件end。");
    } else {
      result = ReturnJacksonUtil.resultFail("未发送文件至服务器");
    }
    return result;
  }


  @RequestMapping(value = "upload/mergeFile", method = RequestMethod.POST)
  @ResponseBody
  public String mergeFile(@ModelAttribute MultipartFileParam param, HttpServletRequest request) {
    String result;
    logger.info("合并文件start。");
    try {
      if (param.getChunks() <= 1) {
        result = ReturnJacksonUtil.resultOk("合并成功。");
      } else {
        result = storageService.mergeFile(param);
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("合并失败", param.toString());
      result = ReturnJacksonUtil.resultFail("合并失败");
    }
    logger.info("合并文件end。");

    return result;
  }


}
