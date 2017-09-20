package com.mec.subject.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mec.subject.model.SubjectModel;
import com.mec.subject.service.SubjectService;
import com.mec.subject.view.mec_controller.UtilList;

public class SubjectView {
	private JFrame jfrmMainView;
	private Container container;
	private JLabel jlblTopic;
	
	private UtilList<SubjectModel> lstSubInfoList;
	
	private JButton jbtnExit;
	private JButton jbtnAdd;
	private JButton jbtnModify;
	
	private JLabel jlblId;
	private JLabel jlblName;
	private JLabel jlblSbjType;
	private JLabel jlblStatus;
	
	private JTextField jtxtId;
	private JTextField jtxtName;
	
	private JRadioButton jrdbComplex;
	private JRadioButton jrdbSimple;
	
	private JRadioButton jrdbUse;
	private JRadioButton jrdbUnuse;
	
	private ButtonGroup btgpSubject;
	private ButtonGroup btgpIfUse;
	
	private JPanel jpnlChildSubList;
	
	private boolean showAllInfo;
	
	private SubjectService subjectService;
	
//	private List<JCheckBox> jckbChildList;
	
	public SubjectView() {
		initFrame();
		reinitFrame();
		dealAction();
	}
	
	public void exit() {
		jfrmMainView.dispose();
	}

	private void dealAction() {
		jfrmMainView.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
		jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		lstSubInfoList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				refreshSubjectInfo();
//				jtxtId.setText(lstSubInfoList.getSelectedValue().getId());
//				jtxtName.setText(lstSubInfoList.getSelectedValue().getName());
//				
//				if(lstSubInfoList.getSelectedValue().isComplex()) {
//					jrdbComplex.setSelected(true);
//				} else {
//					jrdbSimple.setSelected(true);
//				}
//				
//				if(lstSubInfoList.getSelectedValue().isStatus()) {
//					jrdbUse.setSelected(true);
//				} else {
//					jrdbUnuse.setSelected(true);
//				}
			}
		});
		
		jbtnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = subjectService.getASubjectId();
				String name = jtxtName.getText();
				boolean complex = jrdbComplex.isSelected() ? true : false;
				boolean status = true;
				
				SubjectModel subjectModel = new SubjectModel(id, name, status, complex);
				if(complex == false) {
					subjectService.insertASingleRecordToDatabase(subjectModel);
				} else {
					List<SubjectModel> baseSubjectList = getSubjectFromJpnl(subjectModel, jpnlChildSubList);
					subjectService.insertASingleRecordToDatabase(subjectModel);
					subjectService.insetRecordsToComplexDatabase(baseSubjectList, id);
				}
				subjectService.addAModelToSubjectList(subjectModel);
				lstSubInfoList.addElement(subjectModel);
				refreshChildListInfo();
			}
		});
		
		jbtnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = jtxtId.getText();
				String name = jtxtName.getText();
				boolean complex = jrdbComplex.isSelected();
				boolean status = jrdbUse.isSelected();
				SubjectModel subjectModel = new SubjectModel(id, name, status, complex);
				
				if(!complex) {
					if(!isComplexToSingle(subjectModel)) {
						subjectService.modityRecord(subjectModel);
						modifyRecordFromSubjectList(subjectModel);
						refreshSubjectListInfo();
					} else {
						int userChoice = 
								JOptionPane.showConfirmDialog(jfrmMainView, "您真的要修改吗？此次修改可能为不可逆操作，如果确定修改请点击确定，否则点击放弃！", 
										"微易码温馨提示", JOptionPane.OK_OPTION);
						if(userChoice == JOptionPane.OK_OPTION) {
							subjectService.modityRecord(subjectModel);
							subjectService.deleteRecordsFromComplexDataTable(subjectModel);
							SubjectService.setMapToNull();
							modifyRecordFromSubjectList(subjectModel);
							subjectService.initBuffer();
							refreshAllList();
						}
					}
				} else {
					if(isSingelToComplex(subjectModel)) {
						List<SubjectModel> baseSujectList = getSubjectFromJpnl(subjectModel, jpnlChildSubList);
						subjectService.getComplexSubjectRelationMap().put(subjectModel, baseSujectList);
						subjectService.modityRecord(subjectModel);
						subjectService.getComplexSubjectRelationMap() ;
						modifyRecordFromSubjectList(subjectModel);
						subjectService.insetRecordsToComplexDatabase(baseSujectList, id);
						refreshSubjectListInfo();
					}
				}
			}
		});
	}
	
//	public void addChildList() {
//		jckbChildList = new ArrayList<JCheckBox>();
//		SubjectService subjectService = new SubjectService();
//		
//		List<SubjectModel> childInfoList = subjectService.getSubjectInfo(showAllInfo);
//		for(SubjectModel subList : childInfoList) {
//			if(subList.isComplex() == false) {
//				JCheckBox jckbBase = new JCheckBox(subList.getName());
//				jckbBase.setName(subList.getId());
//				
//				jckbChildList.add(jckbBase);
//			}
//		}
//	}
	
//	private void setChildList() {
//		Font font = new Font("黑体", Font.PLAIN, 16);
//		
//		int i = 0;
//		for(JCheckBox jckb : jckbChildList) {
//			jckb.setFont(font);
//			jpnlChildSubList.setLayout(null);
//			jckb.setBounds(10, 20+i*35, 200, 30);
//			jpnlChildSubList.add(jckb);
//			i++;
//		}
//	}

	private boolean isSingelToComplex(SubjectModel subjectModel) {
		return (subjectService.getComplexSubjectRelationMap().get(subjectModel) == null);
	}

	private void modifyRecordFromSubjectList(SubjectModel subjectModel) {
		List<SubjectModel> list = SubjectService.getSubjectList();
		for(SubjectModel model : list) {
			if(subjectModel.getId().equals(model.getId())) {
				model.setName(subjectModel.getName());
				model.setStatus(subjectModel.isStatus());
				model.setComplex(subjectModel.isComplex());
			}
		}
	}
	
	private void refreshAllList() {
		refreshSubjectListInfo();
		refreshChildListInfo();
		refreshSubjectInfo();
	}

	private void refreshSubjectInfo() {
		SubjectModel subjectModel = lstSubInfoList.getSelectedValue();
		
		if(subjectModel == null) {
			return;
		}
		
		jtxtId.setText(subjectModel.getId());
		jtxtName.setText(subjectModel.getName());
		
		if(subjectModel.isComplex()) {
			jrdbComplex.setSelected(true);
			jpnlChildSubList.setVisible(true);
			jrdbSimple.setSelected(false);
			Component[] comps = jpnlChildSubList.getComponents();
			for(int i = 0; i < comps.length; i++) {
				if(comps[i] instanceof ChildSubjectCheckBox) {
					ChildSubjectCheckBox childSubjectCheckBox = (ChildSubjectCheckBox) comps[i];
					childSubjectCheckBox.setSelected(false);
				}
			}
			
			List<SubjectModel> subModels = subjectService.getComplexSubjectRelationMap().get(subjectModel);
			for(SubjectModel model : subModels) {
				for(int i = 0; i < comps.length; i++) {
					if(comps[i] instanceof ChildSubjectCheckBox) {
						ChildSubjectCheckBox childSubjectCheckBox = (ChildSubjectCheckBox) comps[i];
						if(model.equals(childSubjectCheckBox.getSubjectModel())) {
							childSubjectCheckBox.setSelected(true);
						}
					}
				}
			}
		} else {
			jrdbComplex.setSelected(false);
			jrdbSimple.setSelected(true);
			jpnlChildSubList.setVisible(false);
		}
		
		if(subjectModel.isStatus()) {
			jrdbUse.setSelected(true);
			jrdbUnuse.setSelected(false);
		} else {
			jrdbUse.setSelected(false);
			jrdbUnuse.setSelected(true);
		}
	}

	private void refreshSubjectListInfo() {
		SubjectService subjectService = new SubjectService();
		lstSubInfoList.refreshList(subjectService.getSubjectInfo(showAllInfo));
	}

	private void refreshChildListInfo() {
		subjectService = new SubjectService();
		
		List<SubjectModel> allsubjectList = subjectService.getSubjectInfo(showAllInfo);
		
		jpnlChildSubList.removeAll();
		int i = 0;
		for(SubjectModel subjectModel : allsubjectList) {
			if(subjectModel.isStatus()) {
				ChildSubjectCheckBox childSubjectCheckBox = new ChildSubjectCheckBox(subjectModel);
				childSubjectCheckBox.setFont(new Font("宋体", Font.PLAIN, 16));
				childSubjectCheckBox.setText(subjectModel.getName());
				childSubjectCheckBox.setBounds(10, 20+i*35, 200, 30);
				jpnlChildSubList.setLayout(null);
				jpnlChildSubList.add(childSubjectCheckBox);
				i++;
			}
		}
	}
	
	private void defaultInfo() {
		//初始化
		SubjectModel subjectInfoList = lstSubInfoList.getSelectedValue();
		jtxtId.setText(subjectInfoList.getId());
		jtxtName.setText(subjectInfoList.getName());
		
		jrdbSimple.setSelected(true);
		jrdbUse.setSelected(true);
	}
	

	private List<SubjectModel> getSubjectFromJpnl(SubjectModel subjectModel, JPanel jpnel) {
		List<SubjectModel> baseSubjectList = new ArrayList<SubjectModel>();
		
		Component[] comps = jpnel.getComponents();
		for(int i = 0; i < comps.length; i++) {
			if(comps[i] instanceof ChildSubjectCheckBox) {
				ChildSubjectCheckBox childSubjectCheckBox = (ChildSubjectCheckBox) comps[i];
				if(childSubjectCheckBox.isSelected()) {
					baseSubjectList.add(childSubjectCheckBox.getSubjectModel());
				}
			}
		}
		Map<SubjectModel, java.util.List<SubjectModel>> map = subjectService.getComplexSubjectRelationMap();
		map.put(subjectModel, baseSubjectList);
		
		return baseSubjectList;
	}
	
	private boolean isComplexToSingle(SubjectModel subModel) {
		java.util.List<SubjectModel> list = SubjectService.getSubjectList();
		for(SubjectModel model : list) {
			if(subModel.getId().equals(model.getId())) {
				if(subModel.isComplex() == model.isComplex()) {
					return false;
				}
			}
		}
		
		return true;
	}

	private void reinitFrame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		jfrmMainView.setLocation((screenWidth - jfrmMainView.getWidth())/2, 
				(screenHeight - jfrmMainView.getHeight())/2);
		
		lstSubInfoList.setListBorder("科目信息列表");
		
		jpnlChildSubList = new JPanel();
		jpnlChildSubList.setBounds(280, 220, 280+30, 250+30);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(border, "子科目列表");
		titledBorder.setTitleJustification(TitledBorder.CENTER);
		jpnlChildSubList.setBorder(titledBorder);
		container.add(jpnlChildSubList);
		
//		addChildList();
//		setChildList();
		refreshAllList();
		defaultInfo();
	}

	private void initFrame() {
		Font jbtnFont = new Font("宋体", Font.ROMAN_BASELINE, 20);
		Font normalFont = new Font("宋体", Font.PLAIN, 18);
		Font topicFont = new Font("宋体", Font.BOLD, 30);
		
		jfrmMainView = new JFrame("微易码科目管理系统");
		container = jfrmMainView.getContentPane();
		container.setLayout(null);
		jfrmMainView.setSize(630, 650);
		jfrmMainView.setVisible(true);
		jfrmMainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jlblTopic = new JLabel("科目管理");
		jlblTopic.setFont(topicFont);
		jlblTopic.setForeground(Color.BLUE);
		jlblTopic.setBounds(230, 10, 300, 25);
		container.add(jlblTopic);
		
		lstSubInfoList = new UtilList<>(20, 50, 250, 475+30, container);
		
		jbtnExit = new JButton("退出");
		jbtnExit.setFont(jbtnFont);
		jbtnExit.setBounds(484+30, 564, 84, 32);
		container.add(jbtnExit);
		
		jbtnAdd = new JButton("添加");
		jbtnAdd.setFont(jbtnFont);
		jbtnAdd.setBounds(280, 490+30, 84, 32);
		container.add(jbtnAdd);
		
		jbtnModify = new JButton("修改");
		jbtnModify.setFont(jbtnFont);
		jbtnModify.setBounds(365, 490+30, 84, 32);
		container.add(jbtnModify);
		
		jlblId = new JLabel("科目编号");
		jlblId.setFont(normalFont);
		jlblId.setBounds(280, 50, 84, 30);
		container.add(jlblId);
		
		jlblName = new JLabel("科目名称");
		jlblName.setFont(normalFont);
		jlblName.setBounds(280, 90, 84, 30);
		container.add(jlblName);
		
		jlblSbjType = new JLabel("科目类型");
		jlblSbjType.setFont(normalFont);
		jlblSbjType.setBounds(280, 130, 84, 30);
		container.add(jlblSbjType);
		
		jlblStatus = new JLabel("状    态");
		jlblStatus.setFont(normalFont);
		jlblStatus.setBounds(280, 170, 84, 30);
		container.add(jlblStatus);
		
		jtxtId = new JTextField();
		jtxtId.setFont(normalFont);
		jtxtId.setForeground(Color.RED);
		jtxtId.setEditable(false);
		jtxtId.setBounds(370, 50, 190+30, 30);
		container.add(jtxtId);
		
		jtxtName = new JTextField();
		jtxtName.setFont(normalFont);
		jtxtName.setBounds(370, 90, 190+30, 30);
		container.add(jtxtName);
		
		jrdbComplex = new JRadioButton("复合科目");
		jrdbComplex.setFont(normalFont);
		jrdbComplex.setBounds(370, 130, 97, 30);
		container.add(jrdbComplex);
		
		jrdbSimple = new JRadioButton("单一科目");
		jrdbSimple.setFont(normalFont);
		jrdbSimple.setBounds(370+113+15, 130, 97, 30);
		container.add(jrdbSimple);
		
		jrdbUse = new JRadioButton("可用");
		jrdbUse.setFont(normalFont);
		jrdbUse.setBounds(370, 170, 61, 30);
		container.add(jrdbUse);
		
		jrdbUnuse = new JRadioButton("停止");
		jrdbUnuse.setFont(normalFont);
		jrdbUnuse.setBounds(370+113+15, 170, 61, 30);
		container.add(jrdbUnuse);
		
		btgpSubject = new ButtonGroup();
		btgpSubject.add(jrdbComplex);
		btgpSubject.add(jrdbSimple);
		jrdbComplex.setSelected(true);
		
		btgpIfUse =new ButtonGroup();
		btgpIfUse.add(jrdbUse);
		btgpIfUse.add(jrdbUnuse);
		jrdbUse.setSelected(true);
	}
}
