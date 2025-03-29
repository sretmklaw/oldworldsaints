<%@ tag language="java" 
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head></head>
<body>

	<div id="loading">
		<img id="loading-image" src="${context}/LOAD.gif" onerror="this.style.display='none'" />
	</div>

	<div class="container">
		<div class="row h-100 justify-content-center align-items-center">
			<div class="mx-auto">
				<div class="text-area text-center">
					<jsp:doBody />
				</div>	
			</div>
		</div>
	</div>

	<!-- Script Imports -->
	<script src="${context}/static/popper.js/umd/popper.min.js"></script>
	<script src="${context}/static/jquery/jquery.min.js"></script>
	<script src="${context}/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${context}/window-onload.js"></script>

</body>