package org.scottsoft.monitor.domain.interaction.filtrete;

public class Time {

	private String day;
	
	private String hour;
	
	private String minute;
	
	public Time() {
		
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	@Override
	public String toString() {
		return "Time [day=" + day + ", hour=" + hour + ", minute=" + minute + "]";
	}
	
	
	
}
