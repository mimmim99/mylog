/**
 * 회원 가입, 수정 비동기 처리
 * /api 패키지 하위에 컨트롤러 존재 
 */

let index = {
	
	init: function() {
		$("#btn-save").on("click", () => { //function() {} 아닌 ()=>{} 사용 이유 : this 값 바인딩 하기 위함.
			this.save();
		});
	},
	
	save: function() {
		if($("#accssScope").val() ==  "") {
			$("#accssScope").val("PUBLIC");
		}
		
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
			nickname: $("#nickname").val(),
			accessScope: $("#accessScope").val()
		};
		
		$.ajax({
			type: "POST",
			url: "/api/user/join",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			console.log(res);
			console.log(JSON.stringify(res));
		}).fail(function(err){
			console.log(err);
			console.log(JSON.stringify(err));
		});
	}
}

index.init();