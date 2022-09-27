package com.simplilearn.A;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simplilearn.A.StudentD;
import com.simplilearn.C.Student;
import com.simplilearn.B.Database;

public class Students extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentD studentD;

	public Students() {
		super();
	}

	public void init() {
		studentD = new StudentD();
	}

	private Student getStudent(HttpServletRequest request, HttpServletResponse response) {
		String studentId = request.getParameter("id");
		Student student = studentD.getStudent(Integer.parseInt(studentId));
		return student;
	}

	private List<Student> getStudents(HttpServletRequest request, HttpServletResponse response) {
		List<Student> students = studentD.getAllStudents();
		try {
			HttpSession session = request.getSession();
			session.setAttribute("students", students);
			RequestDispatcher dispatcher = request.getRequestDispatcher("list-students.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return students;
	}

	private Student createStudent(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("studentName");
		String email = request.getParameter("email");

		Student studentModel = new Student(name, email);
		Student newStudent = studentD.saveStudent(studentModel);
		getStudents(request, response);
		return newStudent;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {

			case "new":
				createStudent(request, response);
				break;

			case "list":
				getStudents(request, response);
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
