<%@ include file="commons/header.jspf"%>
<%@ include file="commons/navigation.jspf"%>

<style>
.container {
	width: 80%;
	height: 60%;
}

.ma {
	margin: 50px ;
}
</style>



<div class="container mt-2" style="margin-bottom: 150px">
	<div class="row">
		<div class="col">
			<img alt="Image" src="images/prescription.jpg" class="img-fluid">

		</div>
		<div class="col">
			<form>
				<div class="mb-3">
					<label for="formFile" class="form-label">Upload Your Prescription</label> <input class="form-control" type="file" id="formFile">
				</div>
				<button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</div>
	</div>
</div>



<%@ include file="commons/footer.jspf"%>
