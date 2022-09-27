package com.simplilearn.A;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.simplilearn.C.ClassRoom;
import com.simplilearn.B.Database;

public class ClassRoomD {

	public ClassRoom getClassRoom(int id) {
		Transaction transaction = null;
		ClassRoom classRoom = null;

		try (Session session = Database.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			classRoom = session.get(ClassRoom.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return classRoom;
	}

	public ClassRoom saveClassRoom(ClassRoom classRoom) {
		Transaction transaction = null;
		ClassRoom createdClassRoom = null;
		Session session = null;
		try {
			session = Database.getSessionFactory().openSession();
			// start a transaction
			transaction = session.beginTransaction();
			// save the classRoom object
			session.save(classRoom);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return createdClassRoom;
	}

	@SuppressWarnings("unchecked")
	public List<ClassRoom> getAllClassRooms() {
		Transaction transaction = null;
		List<ClassRoom> listOfClassRooms = null;
		try (Session session = Database.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an classRoom object
			listOfClassRooms = session.createQuery("from ClassRoom").getResultList();

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return listOfClassRooms;
	}

}