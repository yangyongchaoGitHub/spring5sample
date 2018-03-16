package com.yyy.model;

import java.io.Serializable;

public class TestModel implements Serializable {
	private String name;
	private int age;
	private long id;
	
	public TestModel(String name, long id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
}
