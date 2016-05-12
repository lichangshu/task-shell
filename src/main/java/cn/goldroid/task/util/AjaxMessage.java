/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author changshu.li
 */
public class AjaxMessage {

	private final List<String> errors = new ArrayList();
	private final List<String> msg = new ArrayList();
	private Object data;

	public boolean isSuccess() {
		return !hasError();
	}

	public boolean hasError() {
		return !errors.isEmpty();
	}

	public AjaxMessage addError(String message) {
		errors.add(message);
		return this;
	}

	public List<String> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	public String getError(int index) {
		return errors.get(index);
	}

	public AjaxMessage clearErrors() {
		errors.clear();
		return this;
	}

	public AjaxMessage clearError(int index) {
		errors.remove(index);
		return this;
	}

	public boolean hasMessage() {
		return msg.size() > 0;
	}

	public AjaxMessage addMessage(String message) {
		msg.add(message);
		return this;
	}

	public List<String> getMessages() {
		return Collections.unmodifiableList(msg);
	}

	public String getMessage(int index) {
		return msg.get(index);
	}

	public AjaxMessage clearMessages() {
		msg.clear();
		return this;
	}

	public AjaxMessage clearMessage(int index) {
		msg.remove(index);
		return this;
	}

	public Object getData() {
		return data;
	}

	public AjaxMessage setData(Object data) {
		this.data = data;
		return this;
	}

	public String toJsonString() {
		return JSONObject.toJSONString(this);
	}

	public String toJsonString(boolean disRef) {
		if (disRef) {
			return JSONObject.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
		}
		return this.toJsonString();
	}

}
