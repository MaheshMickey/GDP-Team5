package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.MessageService;
import services.UserService;

@WebServlet(name = "MessageController", urlPatterns = {"/messages/*"})
public class MessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
	private MessageService messageService = new MessageService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if(session == null ||  session.getAttribute("userId") == null) {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			}
			if (!request.getPathInfo().trim().equals("/")) {
				String[] path = request.getPathInfo().split("/");
				String action = path[1];
				if("newMessages".equals(action) && request.getQueryString() != null) {
					handleNewMessagesGet(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				handleMessagesGet(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if(session == null ||  session.getAttribute("userId") == null) {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			}
			if (!request.getPathInfo().trim().equals("/")) {
				String[] path = request.getPathInfo().split("/");
				String action = path[1];
				if ("sendMessage".equals(action)) {
					handleMessagePost(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleMessagesGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		int userId = (int) session.getAttribute("userId");
		request.getRequestDispatcher("/views/html/chat-room.jsp").forward(request, response);
	}
	
	private void handleNewMessagesGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	}
	
	private void handleMessagePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}

}
