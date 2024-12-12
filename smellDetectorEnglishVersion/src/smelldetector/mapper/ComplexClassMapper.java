package smelldetector.mapper;

import java.sql.SQLException;

import smelldetector.pojo.ComplexClass;

public interface ComplexClassMapper {
	
	public void insertComplexClass(ComplexClass complexClass) throws SQLException;

}
