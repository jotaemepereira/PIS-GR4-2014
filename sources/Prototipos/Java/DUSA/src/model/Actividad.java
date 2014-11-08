package model;


/**
 * 
 * @author santiago
 *
 */
public class Actividad {
	 
	/**
	 * user_activityUsr y log_date se generan en la base de datos 
	 */
	
	private long userId;
	private String userName;
	private long opId;
	private String opName;
	
	public Actividad(Operacion op, String userName){
		this.opId = op.getId();
		this.opName = op.getNombre();
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getOpId() {
		return opId;
	}

	public void setOpId(long opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}
	
	
} 
