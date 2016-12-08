package com.taskagitmakas.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
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
	public Image get(int id) {


		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Image image=session.get(Image.class, id);
		session.getTransaction().commit();
		
		return image;
		
	}

	@Override
	public List<Image> all() {
		List<Image> list=new ArrayList<Image>();
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		TypedQuery<Image> query = session.createQuery("from Image", Image.class);
		list=query.getResultList();
 		session.getTransaction().commit(); 

		return list;
 
	}

	
	@Override
	public List<Image> getSampleByCount(int classType,int count) {
		List<Image> list=new ArrayList<Image>();
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		TypedQuery<Image> query = session.createQuery("from Image where classType="+classType, Image.class);
		list=query.setMaxResults(count).getResultList();
 		session.getTransaction().commit(); 

		return list;
 
	}
	
}
