package com.mec.subject.view;

import java.awt.HeadlessException;

import javax.swing.JCheckBox;

import com.mec.subject.model.SubjectModel;

public class ChildSubjectCheckBox extends JCheckBox{
	private static final long serialVersionUID = -5975723701593042016L;
	
	private SubjectModel subjectModel;
	
	public ChildSubjectCheckBox() {
		this(null);
	}

	public ChildSubjectCheckBox(SubjectModel subjectModel) throws HeadlessException {
		this.subjectModel = subjectModel;
	}

	public SubjectModel getSubjectModel() {
		return subjectModel;
	}

	public void setSubjectModel(SubjectModel subjectModel) {
		this.subjectModel = subjectModel;
	}

	@Override
	public String toString() {
		return subjectModel.getId() + " " + (subjectModel.isComplex() ? "¸´" : "µ¥")
				+ " " + (subjectModel.isStatus() ? "¡Ì" : "¡Á") + " " + subjectModel.getName();
	}
}
