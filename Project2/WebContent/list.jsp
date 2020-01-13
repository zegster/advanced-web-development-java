<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.*" %>
<%@ page import="edu.umsl.math.beans.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

		<!-- jQuery library -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

		<!-- Popper JS -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

		<!-- Latest compiled JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
		
		<!-- Font Awesome 4 Icons -->
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
		<!-- Latest MathJax -->
		<script type="text/javascript" src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
		<script type="text/javascript">
			window.MathJax = 
			{
				tex2jax : 
				{
					inlineMath : [ [ '$', '$' ], [ "\\(", "\\)" ] ],
					processEscapes : true
				}
			};
		</script>
		<script type="text/javascript" src="js/MathBank.js"></script>
		
		<style>
			.table {margin-bottom: 0;}
		</style>
		
		<title>Math Question Bank</title>
	</head>

	<body style="background-color: #191919; background-image: linear-gradient(to top, #0F2027, #203A43, #0F2027);">
		<input id="currentpage" type="hidden" value="${currentpage}" />
		<input id="currentcategory" type="hidden" value="${currentcategory}" />
		
		<c:choose>
			<c:when test="${isError eq 1}">
				<!-- Error Message -->
				<div class="alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong>FAIL!</strong> ${errorMessage}
				</div>
			</c:when>
			<c:when test="${isSuccess eq 1}">
				<!-- Success Message -->
				<div class="alert alert-success alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong>SUCCESS!</strong> ${successMessage}
				</div>
			</c:when>
		</c:choose>
		
		<div class="container">
			<table class="table table-dark table-striped">
				<!-- Top Navigation -->
				<thead>
					<!-- Filter Search -->
					<tr>
						<td colspan="3" style="padding: 0">
							<table class="table">
								<tr>
									<td width="70%" class="text-center">
										<form action="./filter_list_math" method="post">
											<div class="input-group">
								        		<select name="categoryfilter" class="form-control">
								        			<option value="0" disabled selected>Filter problem by category...</option>
								        			<option value="0" >ALL CATEGORY</option>
									        		<c:forEach var="categ" items="${categorylist}">
									        			<option value="${categ.getCid()}">${categ.getName()}</option>
													</c:forEach>
								        		</select>
							        			<button type="submit" class="btn btn-info" style="border-radius: 0 .25rem .25rem 0">
								        			<i class="fa fa-sort"></i>
								        			&emsp;FILTER
								        		</button>
								        	</div>
							        	</form>
									</td>
									<td width="20%">
										<div class="input-group">
											<input id="problempage" type="text" class="form-control" style="width: 5em" placeholder="${currentpage}/${maxpage}">
											<button type="button" onclick="goToProblemsAtPage()" class="btn btn-info"  style="border-radius: 0 .25rem .25rem 0">
												<i class="fa fa-location-arrow"></i>
											</button>
										</div>
									</td>
									<td width="10%">
										<div class="input-group-btn">
											<c:if test="${currentpage > 1}">
												<button onclick="loadProblemsAtPage(-1)" class="btn btn-info">
													<i class="fa fa-arrow-left"></i>
												</button>
											</c:if>
											<c:if test="${currentpage < maxpage}">
												<button onclick="loadProblemsAtPage(1)" class="btn btn-info">
													<i class="fa fa-arrow-right"></i>
												</button>
											</c:if>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</thead>
				
				<!-- Display Problem -->
				<tbody>
					<tr>
						<td style="padding: 0">
							<table class="table table-dark">
								<tr class="table-dark text-info">
									<td style="border: 0;" width="5%"><strong>PID</strong></td>
									<td style="border: 0;" width="75%"><strong>PROBLEM</strong></td>
									<td style="border: 0;" width="20%"><strong>CATEGORY</strong></td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr>
						<td>
							<div style="overflow-y: scroll; height: 500px;">
								<table class="table table-dark table-striped">
									<c:forEach var="prob" items="${problemlist}">
										<tr>
											<td id="pid${prob.getPid()}" width="5%" class="text-center">${prob.getPid()}</td>
											<td width="75%"><c:out value="${prob.getContent()}" /></td>
											<td width="20%" class="text-center">
												<!-- Reassign Category Prompt -->
												<form action="./reassign_category" method="post">
													<div class="input-group">
										        		<select name="reassigncategory" class="form-control" style="font-size: 12px; height: 38px">
											        		<c:forEach var="categ" items="${categorylist}">
																<c:choose>
																	<c:when test="${prob.getCategory_id() eq categ.getCid()}">
																		<option selected value="${prob.getPid()},${categ.getCid()}">${categ.getName()}</option>
																	</c:when>
																	<c:when test="${prob.getCategory_id() ne categ.getCid()}">
																		<option value="${prob.getPid()},${categ.getCid()}">${categ.getName()}</option>
																	</c:when>
																</c:choose>
															</c:forEach>
										        		</select>
									        			<button type="submit" class="btn btn-info" style="border-radius: 0 .25rem .25rem 0">
										        			<i class="fa fa-pencil-square-o"></i>
										        		</button>
										        	</div>
									        	</form>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</td>
					</tr>
				</tbody>
				
				<tfoot>
					<!-- Insert Problem Prompt -->
					<tr>
						<td colspan="3" style="padding: 0">
							<form action="./create_problem" method="post">
								<table class="table table-dark">
									<tr>
										<!-- Input text area field -->
										<td width="70%">
								        	<div style="text-align: center;">
								        		<textarea name="content" rows="5" cols="100%" style="resize: none;" class="form-control" placeholder="Enter a new problem..."></textarea>
								        	</div>
							        	</td>
							        	<!-- Selection of category -->
							        	<td width="30%">
							        		<span><b>Select category:&emsp;</b></span>
							        		<select name="category" class="form-control">
							        			<option value="" disabled selected>Select a category...</option>
								        		<c:forEach var="categ" items="${categorylist}">
								        			<option value="${categ.getCid()}">${categ.getName()}</option>
												</c:forEach>
							        		</select>
							        		<br />
							        		<div align="right">
							        			<button class="btn btn-success" type="submit">
								        			<i class="fa fa-plus"></i>
								        			&emsp;ADD PROBLEM
								        		</button>
							        		</div>
							        	</td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
					
					<!-- Create Category Prompt -->
					<tr>
						<td colspan="3" style="padding: 0">
							<form action="./create_category" method="post">
								<table class="table table-dark">
									<tr class="table-dark">
										<td width="40%">
										<td width="60%">
											<div class="input-group">
												<input type="text" name="category" class="form-control" style="width : 40%;" placeholder="Enter a new category...">
							        			<button type="submit" class="btn btn-success" style="border-radius: 0 .25rem .25rem 0">
								        			<i class="fa fa-plus"></i>
								        			&emsp;ADD CATEGORY
								        		</button>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
					
					<!-- /// -->
				</tfoot>
			</table>
		</div>
	</body>
</html>
