/**
 * @fileName:  QuartzUtil.java 
 * @Description:  TODO
 * @CreateName:  codelion[QiaoYu]
 * @CreateDate:  2018年3月2日 下午3:03:17
 */
package com.xuanli.oepcms.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;

import com.xuanli.oepcms.quartz.job.BaseJob;

/**
 * @author codelion[QiaoYu]
 */
public class QuartzUtil {
	public static void addHomeworkJob(Scheduler scheduler, String jobClassName, String jobGroupName, String cronExpression, Long homeworkId) throws Exception {
		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).usingJobData("homeworkId", homeworkId).build();
		// 表达式调度构建器(即任务执行的时间)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName).withSchedule(scheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
		// 启动调度器
		scheduler.start();
	}
	public static void addExamJob(Scheduler scheduler, String jobClassName, String jobGroupName, String cronExpression, Long homeworkId) throws Exception {
		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).usingJobData("examId", homeworkId).build();
		// 表达式调度构建器(即任务执行的时间)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName).withSchedule(scheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
		// 启动调度器
		scheduler.start();
	}

	public static BaseJob getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (BaseJob) class1.newInstance();
	}

	public static String cron(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String cron = second + " " + minute + " " + hour + " " + day + " " + month + " ? " + year + "-" + year + " ";
		return cron;
	}
}
