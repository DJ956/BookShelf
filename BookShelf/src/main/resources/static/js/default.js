$(function(){

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
				console.log(data.title);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				console.log("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
			}
		});

	});
});