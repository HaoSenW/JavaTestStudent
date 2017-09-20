package com.mec.subject.model;

public class SubjectModel {
	private String id;
	private String name;
	
	private boolean status;
	private boolean complex;
	
	public SubjectModel() {
	}

	public SubjectModel(String id, String name, boolean status, boolean complex) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.complex = complex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isComplex() {
		return complex;
	}

	public void setComplex(boolean complex) {
		this.complex = complex;
	}
	
	public static void toSUBModel(String info) {
		SubjectModel subjectModel = new SubjectModel();
		
		if(info == null) {
			return;
		}
		
		String[] elements = info.split(" ");
		subjectModel.setId(elements[0]);
		subjectModel.setComplex(elements[1].equals("¸´") ? true : false);
		subjectModel.setStatus(elements[2].equals("¡Ì") ? true : false);
		subjectModel.setName(elements[3]);
	}
	
	public static SubjectModel toSubjectModel(String id, String name, boolean status, boolean complex) {
		return new SubjectModel(id, name, status, complex);
	}

	@Override
	public String toString() {
		return (complex == true ? "¸´" : "µ¥") + " "
				+ (status == true ? "¡Ì" : "¡Á") + " "
				+ name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (complex ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectModel other = (SubjectModel) obj;
		if (complex != other.complex)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
}
