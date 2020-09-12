$(function(){

	//NavBarのアクティブ変更
	var activePage = $("#active").attr("class");
	$("li").removeClass("active");
	$target = $("#" + activePage);
	$target.addClass("active");


	$("#searchBtn").on("click", function(){

		var request = {
			isbn : $("#isbnBox").val()
		};

		$.ajax({
			url:"/api/bookinfo",
			type:"GET",
			data:request,
			async:true,
			success: function(data){
				console.log(data);
				if(data === ''){
					$("#msg").removeClass("d-none");
					$("#msg").addClass("alert alert-danger");
					return;
				}
				$("#title").val(data.title);
				$("#titleKana").val(data.titleKana.replace(' ', '').replace('　', ''));
				$("#author").val(data.author);
				$("#publishDate").val(data.publishDate);
				$("#index").val(data.index);
				$("#coverPath").val(data.coverPath);
				if(data.coverPath !== ""){
					$("#img").attr("src", data.coverPath);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				console.log("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
			}
		});

	});
});