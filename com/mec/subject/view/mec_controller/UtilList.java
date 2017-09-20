package com.mec.subject.view.mec_controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;

public class UtilList<T> {
	private DefaultListModel<T> dlm;
	private JList<T> subjectInfoList;
	private JScrollPane jscp;
	
	public UtilList() {
		
	}
	
	public UtilList(int x, int y, int width, int height, Container container) {
		dlm = new DefaultListModel<T>();
		subjectInfoList = new JList<T>(dlm);
		jscp = new JScrollPane(subjectInfoList);
		
		jscp.setBounds(x, y, width, height);
		container.add(jscp);
	}
	
	public boolean isEmpty() {
		return dlm.size() <= 0;
	}
	
	public void addListSelectionListener(ListSelectionListener listSelectionListener) {
		subjectInfoList.addListSelectionListener(listSelectionListener);
	}
	
	public void addMouseListener(MouseListener mouseListener) {
		subjectInfoList.addMouseListener(mouseListener);
	}
	
	public boolean isEnabled() {
		return jscp.isEnabled();
	}

	public void setBounds(int x, int y, int width, int height) {
		jscp.setBounds(x, y, width, height);
	}
	
	public void add(Container container) {
		container.add(jscp);
	}
	
	private TitledBorder getTitledBorder(String title) {
		TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
		titledBorder.setTitleJustification(TitledBorder.CENTER);
		titledBorder.setTitleColor(Color.BLACK);
		
		return titledBorder;
	}
	
	public void setListBorder(String title) {
		this.jscp.setBorder(getTitledBorder(title));
	}
	
	public void requestFocus() {
		this.subjectInfoList.requestFocus();
	}
	
	public T getSelectedValue() {
		return subjectInfoList.getSelectedValue();
	}
	
	public void setEnabled(boolean value) {
		jscp.setEnabled(value);
	}
	
	public void addElement(T element) {
		dlm.addElement(element);
	}
	
	public void refreshList(java.util.List<T> listElements) {
		dlm.removeAllElements();
		
		if(listElements == null || listElements.size() <= 0) {
			return;
		}
		
		for(T element : listElements) {
			dlm.addElement(element);
		}
		
		if(listElements.size() > 0) {
			subjectInfoList.setSelectedIndex(0);
		}
	}
}
