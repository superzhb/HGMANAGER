package com.hengguang.model;

public class Record implements Comparable<Object> {
	private int id;
	private String name;
	private String type;
	private String startdata;
	private String enddata;
	private long totaldata;
	private long lastmodified;
	private String filename;
	private int totalnum;
	private String offset;

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public int getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getStartdata() {
		return startdata;
	}

	public void setStartdata(String startdata) {
		this.startdata = startdata;
	}

	public String getEnddata() {
		return enddata;
	}

	public void setEnddata(String enddata) {
		this.enddata = enddata;
	}

	public long getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(long totaldata) {
		this.totaldata = totaldata;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(long lastmodified) {
		this.lastmodified = lastmodified;
	}

	@Override
	public int compareTo(Object o) {
		if (this == o) {
			return 0;
		} else if (o != null && o instanceof Record) {
			Record u = (Record) o;
			if (lastmodified <= u.lastmodified) {
				return -1;
			} else {
				return 1;
			}
		} else {
			return -1;
		}
	}
}
