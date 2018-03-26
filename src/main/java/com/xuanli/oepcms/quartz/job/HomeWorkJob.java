/**
 * @fileName:  HomeWorkJob.java 
 * @Description:  TODO
 * @CreateName:  codelion[QiaoYu]
 * @CreateDate:  2018年3月2日 下午3:37:53
 */ 
package com.xuanli.oepcms.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xuanli.oepcms.config.ApplicationContextProvider;
import com.xuanli.oepcms.service.ReportService;

/** 
 * @author  codelion[QiaoYu]
 */
public class HomeWorkJob implements BaseJob{

	/** 
	 * @CreateUser:codelion[QiaoYu]
	 * @CreateDate:2018年3月2日 下午3:39:22
	 */
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		Long homeworkId = jobDataMap.getLong("homeworkId");
		ReportService reportService = ApplicationContextProvider.getApplicationContext().getBean(ReportService.class);
		reportService.generatorHomeworkReport(homeworkId, null);
	}
	
}
