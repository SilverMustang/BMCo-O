package smelldetector.mapper;

import java.sql.SQLException;

import smelldetector.pojo.RefusedBequest;

public interface RefusedBequestMapper {
	
	public void insertRefusedBequest(RefusedBequest refuesdBequest) throws SQLException;

}
