package com.simplilearn.A;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simplilearn.A.ClassRoomD;
import com.simplilearn.C.ClassRoom;

public class ClassRooms extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassRoomD classRoomD;

	public ClassRooms() {
		super();
	}

	public void init() {
		classRoomD = new ClassRoomD();
	}

	private ClassRoom getClassRoom(HttpServletRequest request, HttpServletResponse response) {
		String classRoomId = request.getParameter("id");
		ClassRoom classRoom = classRoomD.getClassRoom(Integer.parseInt(classRoomId));
		return classRoom;
	}

	private List<ClassRoom> getClassRooms(HttpServletRequest request, HttpServletResponse response) {
		List<ClassRoom> classRooms = classRoomD.getAllClassRooms();
		try {
			HttpSession session = request.getSession();
			session.setAttribute("classRooms", classRooms);
			RequestDispatcher dispatcher = request.getRequestDispatcher("pages/list-classRooms.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classRooms;
	}

	private ClassRoom createClassRoom(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("classRoomName");

		ClassRoom classRoomModel = new ClassRoom(name);
		ClassRoom newClassRoom = classRoomD.saveClassRoom(classRoomModel);
		getClassRooms(request, response);
		return newClassRoom;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {

			case "new":
				createClassRoom(request, response);
				break;

			case "list":
				getClassRooms(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}