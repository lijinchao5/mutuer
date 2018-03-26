package com.xuanli.oepcms.quartz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.quartz.dao.JobAndTriggerMapper;
import com.xuanli.oepcms.quartz.entity.JobAndTrigger;
import com.xuanli.oepcms.quartz.service.IJobAndTriggerService;


@Service
public class JobAndTriggerImpl implements IJobAndTriggerService{

	@Autowired
	private JobAndTriggerMapper jobAndTriggerMapper;
	
	public List<JobAndTrigger> getJobAndTriggerDetails() {
		List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
		return list;
	}

}