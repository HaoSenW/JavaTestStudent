package com.mec.subject.test;

import java.awt.EventQueue;

import com.mec.subject.view.SubjectView;

public class ViewTest {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SubjectView();
			}
		});
	}
}
