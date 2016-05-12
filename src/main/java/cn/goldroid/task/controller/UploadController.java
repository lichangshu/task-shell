/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.controller;

import cn.goldroid.task.domain.TimerTask;
import cn.goldroid.task.service.QuartzService;
import cn.goldroid.task.service.TimerTaskService;
import cn.goldroid.task.util.AjaxMessage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.upload.UploadAdaptor;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;

/**
 *
 * @author changshu.li
 */
@At("/upload")
@IocBean
public class UploadController extends BaseModule {

	private static final Log logger = LogFactory.getLog(UploadController.class);

	@At("/file")
	@GET
	@Ok("jsp:jsp/upload")
	public void upload() throws IOException {
	}

	@At("/file")
	@POST
	@Ok("jsp:jsp/upload")
	@AdaptBy(type = UploadAdaptor.class, args = {"${app.root}/WEB-INF/tmp"})
	public AjaxMessage uploadPost(@Param("path") String path, @Param("file") File f) throws IOException {
		if (!StringUtils.isBlank(path)) {
			File target = new File(path);
			if (target.exists() && target.isDirectory()) {
				target = new File(target, f.getName());
				if (!target.exists()) {
					FileUtils.moveFile(f, target);
				}
				f = target;
			}
		}
		return new AjaxMessage().setData(f.getAbsolutePath());
	}
}
