$(function() {
	var projectid = sessionStorage.getItem("projectidtemp");
	$("#insbackbtnid").click(function() {
		freshT(projectid);
	});

	$("#updbackbtnid").click(function() {
		freshT(projectid);
	});

	$("#selbackbtnid").click(function() {
		freshT(projectid);
	});
});