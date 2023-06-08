<%@page import="vo.Movie"%>
<%@page import="dao.ManagerMovieDao"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%

	//세션에서 로그인된 사용자 정보를 조회한다

	// 요청파라미터 조회
	int no = Integer.parseInt(request.getParameter("no"));

	//업무로직 수행 - 요청파라미터로 전달받은 영화번호에 해당하는 영화 상세정보 조회하기
	ManagerMovieDao managerMovieDao = ManagerMovieDao.getInstance();
	// 영화 상세정보 조회하기
	Movie movie = managerMovieDao.getMovieByNo(no);
	
%>

<!doctype html>
<html lang="ko">
<head>
<title></title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
<jsp:include page="../../manager-nav.jsp">
	<jsp:param name="menu" value="영화"/>
</jsp:include>
<div class="container">
	<div class="row mb-3">
    	<div class="col-12">
        	<h1 class="border bg-light fs-4 p-2">영화 상세 정보</h1>
      	</div>
   	</div>
	<div class="row mb-3">
		<div class="col-12">
			<p>영화 상세정보를 확인하세요</p>
			<table class="table table-bordered">
				<tbody>
					<tr>
						<th class="table-dark" style="width: 15%;">제목</th>
						<td style="width: 35%;"><%=movie.getTitle() %></td>
						<th class="table-dark" style="width: 15%;">상영여부</th>
						<td style="width: 35%;"><%="N".equals(movie.getDisable()) ? "상영중" : "예매중" %></td>
					</tr>
					<tr>
						<th class="table-dark" style="width: 15%;">감독</th>
						<td style="width: 35%;"><%=movie.getDirector() %></td>
						<th class="table-dark" style="width: 15%;">배우</th>
						<td style="width: 35%;"><%=movie.getCast() %></td>
					</tr>
					<tr>
						<th class="table-dark" style="width: 15%;">장르</th>
						<td style="width: 35%;"><%=movie.getGenre() %></td>
						<th class="table-dark" style="width: 15%;">개봉일</th>
						<td style="width: 35%;"><%=movie.getReleaseDate() %></td>
					</tr>
				
						<th class="table-dark" style="width: 15%;">설명</th>
						<td style="width: 85%; height: 100px;" colspan="3"><%=movie.getDescription() %></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	

</div>
</body>
</html>