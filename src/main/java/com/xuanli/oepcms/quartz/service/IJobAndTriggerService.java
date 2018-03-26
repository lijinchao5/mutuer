package com.xuanli.oepcms.quartz.service;


import java.util.List;

import com.xuanli.oepcms.quartz.entity.JobAndTrigger;

public interface IJobAndTriggerService {
	public List<JobAndTrigger> getJobAndTriggerDetails();
}
