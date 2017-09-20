package com.mec.util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MecUtil {
	public static void showMessage(JFrame frame, String message) {
		JOptionPane.showMessageDialog(frame, message, "Î¢Ò×ÂëÎÂÜ°ÌáÊ¾", JOptionPane.OK_OPTION);
	}
	
	public static String makeAId(int num, int len) {
		int[] power = {0, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
		
		if(num > power.length || len > power.length) {
			return String.valueOf(num);
		}
		
		return (num + power[len] + "").substring(1);
	}
}
