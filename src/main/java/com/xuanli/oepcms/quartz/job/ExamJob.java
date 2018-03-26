/**
 * @fileName:  ExamJob.java 
 * @Description:  TODO
 * @CreateName:  codelion[QiaoYu]
 * @CreateDate:  2018年3月9日 上午9:47:21
 */ 
package com.xuanli.oepcms.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xuanli.oepcms.config.ApplicationContextProvider;
import com.xuanli.oepcms.service.ExamService;

/** 
 * @author  codelion[QiaoYu]
 */
public class ExamJob implements BaseJob{

	/** 
	 * @CreateUser:codelion[QiaoYu]
	 * @CreateDate:2018年3月9日 上午9:47:31
	 */
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		Long examId = jobDataMap.getLong("examId");
		ExamService examService = ApplicationContextProvider.getApplicationContext().getBean(ExamService.class);
		examService.generatorExamReport(examId, null);
	}
	
}
