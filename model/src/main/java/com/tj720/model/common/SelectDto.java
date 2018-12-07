package com.tj720.model.common;


import java.io.Serializable;

//@Data
public class SelectDto implements Serializable{

	private static final long serialVersionUID = -3067263031103815640L;

	private String name;
	private String value;
	private String selected;
	private String disabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
