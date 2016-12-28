package com.taskagitmakas.dao;

import java.util.List;

import com.taskagitmakas.entity.Image;

public interface ImageDao {

	
	public void insert(Image image);
	public Image get(int id);
	public List<Image> all();
	public List<Image> getSampleByCount(int classType,int count);
	public List<Image> getAllByUser(int id);
	public List<Image> getAllByOtherUsers(int id);
	public Long getCount();
	public Long getCountByUser(int userId);

	
}
