package com.mec.subject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mec.subject.dao.SubjectDao;
import com.mec.subject.model.SubjectModel;
import com.mec.util.MecUtil;

public class SubjectService {
	private SubjectDao subjectDao;
	private static List<SubjectModel> subjectList = null;
	private static Map<SubjectModel, List<SubjectModel>> complexSubjectRelaMap = null;
	
	public SubjectService() {
		subjectDao = new SubjectDao();
		initBuffer();
	}
	
	public void initBuffer() {
		if(subjectList == null) {
			subjectList = subjectDao.getSubjectInfoList(subjectList);
		}
		
		if(complexSubjectRelaMap == null) {
			complexSubjectRelaMap = new HashMap<SubjectModel, List<SubjectModel>>();
			
			String SQLString = "SELECT id, name, status, complex FROM sys_subject_info WHERE complex = 1";
			List<SubjectModel> complexsubjectList = subjectDao.getSubjectListBySQL(SQLString);
			
			for(SubjectModel subjectModel : complexsubjectList) {
				String SQLStr = "SELECT id, name, status, complex FROM sys_subject_info WHERE " + 
						"id = ANY(SELECT base_subject_id FROM sys_subject_rela WHERE "
						+ "complex_subject_id = '"
						+ subjectModel.getId()+ "')";
				List<SubjectModel> baseSubjectList = subjectDao.getSubjectListBySQL(SQLStr);
				complexSubjectRelaMap.put(subjectModel, baseSubjectList);
			}
		}
	}
	
	public static void setMapToNull() {
		complexSubjectRelaMap = null;
	}

	public List<SubjectModel> getSubjectInfo(boolean showAll) {
		List<SubjectModel> subjectInfo = subjectDao.getSubjectInfoList(subjectList);
		
		return subjectInfo;
	}
	
	public Map<SubjectModel, List<SubjectModel>> getComplexSubjectRelationMap() {
		return complexSubjectRelaMap;
	}
	
	public String getASubjectId() {
		int count = subjectDao.getSubjectCountFromDatabase();
		
		return MecUtil.makeAId(count+1, 3);
	}
	
	public void insertASingleRecordToDatabase(SubjectModel subjectModel) {
		subjectDao.insertARecordToDatabase(subjectModel);
	}
	
	public void insetRecordsToComplexDatabase(List<SubjectModel> baseSubjectList, String id) {
		subjectDao.insetRecordsToComplexDatabase(baseSubjectList, id);
	}
	
	public static List<SubjectModel> getSubjectList() {
		return subjectList;
	}
	
	public void addAModelToSubjectList(SubjectModel subjectModel) {
		subjectList.add(subjectModel);
	}
	
	public void modityRecord(SubjectModel subjectModel) {
		subjectDao.modifyRecord(subjectModel);
	}
	
	public void deleteRecordsFromComplexDataTable(SubjectModel subjectModel) {
		subjectDao.deleteRecordsFromComplexDataTable(subjectModel);
	}
}
