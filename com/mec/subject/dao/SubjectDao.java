package com.mec.subject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mec.subject.model.SubjectModel;
import com.mec.util.MecDatabase;

public class SubjectDao {
	public SubjectDao() {
	}
	
	public List<SubjectModel> getSubjectListBySQL(String sql) {
		List<SubjectModel> subjectList = new ArrayList<SubjectModel>();
		String SQLString = sql;
		
		MecDatabase md = new MecDatabase(SQLString);
		ResultSet rs;
		try {
			rs = md.doQuery();
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				boolean status = rs.getBoolean("status");
				boolean complex = rs.getBoolean("complex");
				
				SubjectModel subjectModel = new SubjectModel(id, name, status, complex);
				subjectList.add(subjectModel);
			}
			
			md.exit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return subjectList;
	}
	
	public List<SubjectModel> getSubjectInfoList(List<SubjectModel> subjectList) {
		if(subjectList == null) {
			subjectList = new ArrayList<SubjectModel>();
			String SQLString = "SELECT id, name, status, complex FROM sys_subject_info";
			
			MecDatabase md = new MecDatabase(SQLString);
			ResultSet rs;
			try {
				rs = md.doQuery();
				while(rs.next()) {
					String id = rs.getString("id");
					String name = rs.getString("name");
					boolean status = rs.getBoolean("status");
					boolean complex = rs.getBoolean("complex");
					
					SubjectModel sbjModel = new SubjectModel(id, name, status, complex);
					subjectList.add(sbjModel);
				}
				
				md.exit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return subjectList;
	}
	
	public int getSubjectCountFromDatabase() {
		int count = 0;
		
		String SQLString = "SELECT COUNT(*) FROM sys_subject_info";
		MecDatabase md = new MecDatabase(SQLString);
		try {
			ResultSet rs = md.doQuery();
			while(rs.next()) {
				count = rs.getInt(1);
			}
			
			md.exit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public void insertARecordToDatabase(SubjectModel subjectModel) {
		String SQLString = "INSERT INTO sys_subject_info VALUES('" + subjectModel.getId()
		+ "', '" + subjectModel.getName() + "', " + subjectModel.isStatus() + ", "
		+ subjectModel.isComplex() + ")";
		
		MecDatabase md = new MecDatabase(SQLString);
		try {
			md.doUpdate();
			md.exit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insetRecordsToComplexDatabase(List<SubjectModel> baseSubjectList, String id) {
		if(baseSubjectList == null || id == null) {
			return;
		}
		
		for(SubjectModel subjectModel : baseSubjectList) {
			String SQLString = "INSERT INTO sys_subject_rela (complex_subject_id, base_subject_id) VALUES('"
					+ id + "', '" + subjectModel.getId() + "')";
			
			MecDatabase md = new MecDatabase(SQLString);
			try {
				md.doUpdate();
				md.exit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void modifyRecord(SubjectModel subjectModel) {
		if(subjectModel == null) {
			return;
		}
		
		String SQLString = "UPDATE sys_subject_info SET name = '"
				+ subjectModel.getName() + "', status = " + subjectModel.isStatus()
				+ " ,complex = " + subjectModel.isComplex()
				+ " WHERE id = '" + subjectModel.getId()	+ "'";
		
		MecDatabase md = new MecDatabase(SQLString);
		try {
			md.doUpdate();
			md.exit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRecordsFromComplexDataTable(SubjectModel subjectModel) {
		String SQLString = "DELETE FROM sys_subject_rela WHERE complex_subject_id = '"
				+ subjectModel.getId() + "'";
		
		MecDatabase md = new MecDatabase(SQLString);
		try {
			md.doUpdate();
			md.exit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
