package connectDB;

public class UserBean {
	private String id;
	private String password;
	private String schedule_name;
	private String email;
	
	private boolean canSelect=false;
	private boolean canInsert=false;
	private boolean canDelete=false;
	private boolean canUpdate=false;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSchedule_name() {
		return schedule_name;
	}
	public void setSchedule_name(String schedule_name) {
		this.schedule_name = schedule_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isCanSelect() {
		return canSelect;
	}
	public void setCanSelect(boolean canSelect) {
		this.canSelect = canSelect;
	}
	public boolean isCanInsert() {
		return canInsert;
	}
	public void setCanInsert(boolean canInsert) {
		this.canInsert = canInsert;
	}
	public boolean isCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	public boolean isCanUpdate() {
		return canUpdate;
	}
	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	
}
