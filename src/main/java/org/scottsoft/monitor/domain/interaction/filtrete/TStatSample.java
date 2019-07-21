package org.scottsoft.monitor.domain.interaction.filtrete;


public class TStatSample {
	
	private String temp;
	
	private String tmode;
	
	private String fmode;
	
	private String override;
	
	private String hold;
	
	private String t_heat;
	
	private String tstate;
	
	private String fstate;
	
	private Time time;
	
	private String t_type_post;

	public TStatSample() {
		
	}
	
	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getTmode() {
		return tmode;
	}

	public void setTmode(String tmode) {
		this.tmode = tmode;
	}

	public String getFmode() {
		return fmode;
	}

	public void setFmode(String fmode) {
		this.fmode = fmode;
	}

	public String getOverride() {
		return override;
	}

	public void setOverride(String override) {
		this.override = override;
	}

	public String getHold() {
		return hold;
	}

	public void setHold(String hold) {
		this.hold = hold;
	}

	public String getT_heat() {
		return t_heat;
		//return t_heat != null ? Float.valueOf(t_heat) : Float.NaN;
	}

	public void setT_heat(String t_heat) {
		this.t_heat = t_heat;
	}

	public String getTstate() {
		return tstate;
	}

	public void setTstate(String tstate) {
		this.tstate = tstate;
	}

	public String getFstate() {
		return fstate;
	}

	public void setFstate(String fstate) {
		this.fstate = fstate;
	}
	
	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getT_type_post() {
		return t_type_post;
	}

	public void setT_type_post(String t_type_post) {
		this.t_type_post = t_type_post;
	}

	@Override
	public String toString() {
		return "TStat [temp=" + temp + ", tmode=" + tmode + ", fmode=" + fmode
				+ ", override=" + override + ", hold=" + hold + ", t_heat="
				+ t_heat + ", tstate=" + tstate + ", fstate=" + fstate
				+ ", time=" + time + ", t_type_post=" + t_type_post + "]";
	}
	
	

}
