var projectName = getRootPath();

$(function() {
	// alert("hello");
});

function exportSiteInfoExcel(excelFormat) {
	window.location.href = projectName
			+ "/siteInfo/exportExcel?myid=5&excelFormat=" + excelFormat
			+ "&fileName=siteInfo";
}

function importExcel() {

	var formData = new FormData($("#uploadPic")[0]);
	var ajaxUrl = projectName + "/siteInfo/importExcel";
	// alert(ajaxUrl);
	// $('#uploadPic').serialize() 无法序列化二进制文件，这里采用formData上传
	// 需要浏览器支持：Chrome 7+、Firefox 4+、IE 10+、Opera 12+、Safari 5+。
	$.ajax({
		type : "POST",
		// dataType: "text",
		url : ajaxUrl,
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success : function(data) {
			alert(data);
		},
		error : function(data) {
			alert("error:" + data.responseText);

		}
	});
	return false;
}

function getRootPath() {
	var pathName = window.location.pathname.substring(1);
	var webName = pathName == '' ? '' : pathName.substring(0, pathName
			.indexOf('/'));
	return window.location.protocol + '//' + window.location.host + '/'
			+ webName;// + '/';
}