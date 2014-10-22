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
	private long opId;
	private String opName;
	
	public Actividad(Operacion op, long userId){
		this.opId = op.getId();
		this.opName = op.getNombre();
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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
