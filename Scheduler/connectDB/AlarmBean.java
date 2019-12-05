package connectDB;

public class AlarmBean {
	private int id;


	private String start_alarm;
	private String schedule_title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getStart_alarm() {
		return start_alarm;
	}

	public void setStart_alarm(String start_alarm) {
		this.start_alarm = start_alarm;
	}

	public String getSchedule_title() {
		return schedule_title;
	}

	public void setSchedule_title(String schedule_title) {
		this.schedule_title = schedule_title;
	}

}
