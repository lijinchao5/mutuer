/**
 * @fileName:  MobileGameController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年5月7日 下午2:26:56
 */ 
package com.xuanli.oepcms.controller.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.controller.BaseController;
import com.xuanli.oepcms.service.GameService;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
@RestController
@RequestMapping(value = "/mobile/game/")
public class MobileGameController extends BaseController {
	@Autowired
	GameService gameService;
	
	//单词闯关start=======================================================================================================
	
	/**
	 * 获取该教材下的书籍的单元(关卡)列表信息
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:37:11
	 */
	@RequestMapping(value = "getWordList.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getWordList(Long bookId){
		return ok(gameService.getWordList(getCurrentUser().getId(), bookId));
	}
	/**
	 * 获取该关卡下的单词信息
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:37:11
	 */
	@RequestMapping(value = "getWordDetailList.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getWordDetailList(Long sectionDetailId){
		return ok(gameService.getWordDetailList(sectionDetailId));
	}
	/**
	 * 提交该关卡下单词的信息,并返回相关内容
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:44:02
	 */
	@RequestMapping(value = "doWordDetail.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> doWordDetail(Long sectionDetailId,String fileId){
		return ok(gameService.doWordDetail(sectionDetailId, fileId, getCurrentUser().getId()));
	}
	/**
	 * 提交该关卡的分数信息
	 * @CreateName:  QiaoYu[www.codelion.cn]
	 * @CreateDate:  2018年5月7日 下午2:44:02
	 */
	@RequestMapping(value = "submitWordUnit.do", method = RequestMethod.POST)
	public RestResult<String> submitWordUnit(Long unitId,Double score){
		return ok(gameService.submitWordUnit(unitId, getCurrentUser().getId(), score));
	}
	
	
	
	
	
	
	
	
	
	//单词闯关end=======================================================================================================
}
