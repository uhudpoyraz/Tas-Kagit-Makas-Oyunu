package com.taskagitmakas.dao;

import java.util.List;

import com.taskagitmakas.entity.Image;

public interface ImageDao {

	
	public void insert(Image image);
	public void get(int id);
	public List<Image> all();
}
