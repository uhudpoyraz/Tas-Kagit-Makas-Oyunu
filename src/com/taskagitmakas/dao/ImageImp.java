package com.taskagitmakas.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.taskagitmakas.entity.Image;

public class ImageImp implements ImageDao{

	 Session session;
	 SessionFactory sessionFactory;
	public ImageImp() {
	 
		  sessionFactory = new Configuration()
				    .configure().buildSessionFactory(); 
 	}

	@Override
	public void insert(Image image) {
		
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
  		session.persist(image);
 		session.getTransaction().commit(); 
	}

	@Override
	public void get(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Image> all() {
		List<Image> list=new ArrayList<Image>();
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		TypedQuery<Image> query = session.createQuery("from Image order by id asc", Image.class);
		list=query.getResultList();
 		session.getTransaction().commit(); 

		return list;
 
	}

}
