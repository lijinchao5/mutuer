/**
 * @fileName:  QuestionService.java 
 * @Description:  TODO
 * @CreateName:  codelion[QiaoYu]
 * @CreateDate:  2018年3月15日 上午10:32:45
 */ 
package com.xuanli.oepcms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.mapper.QuestionOptionEntityMapper;
import com.xuanli.oepcms.mapper.QuestionSubjectDetailEntityMapper;
import com.xuanli.oepcms.mapper.QuestionSubjectEntityMapper;
import com.xuanli.oepcms.util.PageBean;

/** 
 * @author  codelion[QiaoYu]
 */
@Service
public class QuestionService extends BaseService{
	@Autowired
	QuestionSubjectEntityMapper questionSubjectEntityMapper;
	@Autowired
	QuestionSubjectDetailEntityMapper questionSubjectDetailEntityMapper;
	@Autowired
	QuestionOptionEntityMapper questionOptionEntityMapper;
	/**
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月15日 上午10:36:43
	 */
	public void findQuestionDetailByPage(Map<String, Object> requestMap, PageBean pageBean) {
		int total = questionSubjectEntityMapper.findQuestionDetailByPageCount(requestMap);
		pageBean.setTotal(total);
		requestMap.put("start", pageBean.getRowFrom());
		requestMap.put("end", pageBean.getPageSize());
		List<Map<String, Object>> resultMap = questionSubjectEntityMapper.findQuestionDetailByPage(requestMap);
		List<Long> ids = new ArrayList<Long>();
		for (Map<String, Object> map : resultMap) {
			Long id = (Long)map.get("id");
			ids.add(id);
		}
		if (null == ids || ids.size()<=0) {
			
		}else {
			//chaxun
			List<Map<String, Object>> resultMapDetailList = questionSubjectDetailEntityMapper.getSubjectDetailBySubjectId(ids);
			for (Map<String, Object> map : resultMap) {
				List<Map<String, Object>> m2 = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> m1 : resultMapDetailList) {
					if (((Long)m1.get("subjectId")).longValue() == ((Long)map.get("id")).longValue()) {
						m2.add(m1);
					}
				}
				map.put("detailList", m2);
			}
		}
		pageBean.setRows(resultMap);
	}
	
	
	
	
	
}
