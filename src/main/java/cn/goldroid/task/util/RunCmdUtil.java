/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.util;

import java.io.UnsupportedEncodingException;
import org.nutz.repo.Base64;

/**
 *
 * @author changshu.li
 */
public class RunCmdUtil {

	public static String encodeCommend(String... cmd) {
		if (cmd == null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (String c : cmd) {
			String cc;
			try {
				cc = Base64.encodeToString(c.getBytes("UTF-8"), false);
				buffer.append(cc).append(" ");
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		return buffer.toString();
	}

	public static String[] decodeCommend(String cmd) {
		if (cmd == null) {
			return null;
		}
		String[] cmds = cmd.split(" ");
		String[] rst = new String[cmds.length];
		for (int i = 0; i < cmds.length; i++) {
			try {
				rst[i] = new String(Base64.decode(cmds[i]), "utf8");
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		return rst;
	}

	public static String join(String[] arr, String split) {
		if (arr == null) {
			return null;
		}
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (split != null && i != 0) {
				buff.append(split);
			}
			buff.append(arr[i]);
		}
		return buff.toString();
	}
}
