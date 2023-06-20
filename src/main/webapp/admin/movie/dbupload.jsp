<%@page import="java.io.IOException"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.BufferedWriter"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page import="vo.Movie"%>
<%@page import="dao.ManagerMovieDao"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
ManagerMovieDao dao= ManagerMovieDao.getInstance();
List<Movie> movies=dao.getMovies();

Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
String json=gson.toJson(movies);
try {
	BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Workspace/movie-open-api/moviedb.json"));
	bw.write(json);
	bw.flush();
	bw.close();
} catch (IOException e) {
	e.printStackTrace();
}
response.sendRedirect("list.jsp");

%>