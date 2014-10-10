package com.avit.lucene;

public class VO2 {
	private String name;
	private String test;
	private int age;
	private String sep;
	public VO2(){}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSep() {
		return sep;
	}
	public void setSep(String sep) {
		this.sep = sep;
		this.test = sep;
	}
	
}
