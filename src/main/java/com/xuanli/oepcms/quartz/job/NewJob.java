package com.xuanli.oepcms.quartz.job;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class NewJob implements BaseJob {

	private static Logger _log = LoggerFactory.getLogger(NewJob.class);

	public NewJob() {

	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String[] keys = jobDataMap.getKeys();
		_log.error("New Job执行时间: " + new Date());
		for (String key : keys) {
			_log.error("内容: " + key+"---->"+jobDataMap.getString(key));
		}
		

	}
}