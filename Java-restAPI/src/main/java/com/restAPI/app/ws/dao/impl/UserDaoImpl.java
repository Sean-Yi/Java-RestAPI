package com.restAPI.app.ws.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import com.restAPI.app.ws.Utils.HibernateUtils;
import com.restAPI.app.ws.dao.UserDAO;
import com.restAPI.app.ws.dto.UserDTO;
import com.restAPI.app.ws.entity.UserEntity;

public class UserDaoImpl implements UserDAO {

	Session session;

	public void openConnection() {
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		session = sessionFactory.openSession();
	}

	public UserDTO getUserByUserName(String userName) {
		UserDTO userDto = null;
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		// Query
		Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
		criteria.select(profileRoot);
		criteria.where(cb.equal(profileRoot.get("email"), userName));

		// Fetch
		Query<UserEntity> query = session.createQuery(criteria);
		List<UserEntity> resultList = query.getResultList();

		if (resultList != null && resultList.size() > 0) {
			UserEntity userEntity = resultList.get(0);
			userDto = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDto);
		}

		return userDto;
	}

	public UserDTO getUser(String id) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		// Query
		Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
		criteria.select(profileRoot);
		criteria.where(cb.equal(profileRoot.get("userId"), id));

		// Fetch
		UserEntity userEntity = session.createQuery(criteria).getSingleResult();

		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;
	}

	public UserDTO saveUser(UserDTO user) {

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		session.beginTransaction();
		session.save(userEntity);
		session.getTransaction().commit();

		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	public List<UserDTO> getUsers(int start, int end) {

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		// Query
		Root<UserEntity> userRoot = criteria.from(UserEntity.class);
		criteria.select(userRoot);

		// Fetch
		List<UserEntity> searchResults = session.createQuery(criteria).setFirstResult(start).setMaxResults(end)
				.getResultList();

		List<UserDTO> returnValue = new ArrayList<UserDTO>();
		for (UserEntity userEntity : searchResults) {
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}

	public void updateUser(UserDTO userProfile) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userProfile, userEntity);

		session.beginTransaction();
		session.update(userEntity);
		session.getTransaction().commit();
	}

	public void deleteUser(UserDTO userProfile) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userProfile, userEntity);

		session.beginTransaction();
		session.delete(userEntity);
		session.getTransaction().commit();
	}

	public void closeConnection() {
		if (session != null) {
			session.close();
		}
	}

}
