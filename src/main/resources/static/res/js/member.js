/**
 * 회원 가입, 수정 비동기 처리
 * /api 패키지 하위에 컨트롤러 존재 
 */

let index = {
	
	init: function() {
		$("#btn-save").on("click", () => { //function() {} 아닌 ()=>{} 사용 이유 : this 값 바인딩 하기 위함.
			this.save();
		});
		
		$("#btn-chkid").on("click", () => {
			this.chkid();
		});
		
		$("#btn-modify").on("click", () => {
			this.modify();
		});
	},
	
	save: function() {
		if($("#isIdCheckedSpan").css("display") == "none") {
			alert("아이디 중복체크를 해주세요.");
			return false;
		}
		
		if($("#password").val().length < 1) {
			alert("비밀번호를 입력해주세요.");
			return false;
		}
		
		if($("#email").val().length < 1) {
			alert("이메일을 입력해주세요.");
			return false;
		}
		
		if($("#nickname").val().length < 1) {
			alert("별명을 입력해주세요.");
			return false;
		}

		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
			nickname: $("#nickname").val()
		};
		
		$.ajax({
			type: "POST",
			url: "/api/member/join",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			if(res.status != 200) {
				alert(res.data);
				return false;	
			} else {
				//완료 알림 후 로그인 페이지로 이동
				alert("회원가입 완료");
				location.href = "/public/member/login";	
			}
		}).fail(function(err) {
			//실패 원인에 따른 처리 로직 구현 예정
			alert("회원가입에 실패하였습니다. 관리자에게 문의해주세요.")
			console.log(JSON.stringify(err));
		});
	},
	
	modify: function() {
		if($("#currPassword").val().length < 1) {
			alert("현재 비밀번호를 입력해주세요.");
			return false;
		}
		
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
			nickname: $("#nickname").val(),
			currPassword: $("#currPassword").val()
		};
		
		$.ajax({
			type: "PUT",
			url: "/api/member/modify",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			console.log(res);
			if(res.status != 200) {
				alert(res.data);
				return false;	
			} else {
				alert("회원정보 수정이 완료되었습니다.");
			location.href = "/member/info";	
			}
		}).fail(function(err) {
			alert("회원정보 수정에 실패하였습니다. 관리자에게 문의해주세요.");
		});
	},
	
	chkid: function() {
		if($("#username").val().length < 1) {
			alert("아이디를 입력해주세요.");
			return false;
		}
		
		let data = {
			username: $("#username").val()
		};
		
		$.ajax({
			type: "POST",
			url: "/api/member/dupl",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			if(res.status != 200) {
				alert("중복된 아이디입니다.");
				$("#isIdCheckedSpan").css("display", "none");
			} else {
				alert("사용 가능한 아이디입니다.");
				$("#isIdCheckedSpan").css("display", "inline-block");
			}
		}).fail(function(err) {
			alert("중복체크에 실패하였습니다. 관리자에게 문의해주세요.");
		});
	}
}

index.init();