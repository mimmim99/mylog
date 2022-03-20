/**
 * 게시글 추가,수정,삭제
 */

let index = {

	init: function() {
		$("#btn-save").on("click", () => {
			this.save('SAVE');
		});
		
		$("#btn-temp").on("click", () => {
			this.save('TEMP');
		});
		
		$("#btn-edit").on("click", () => {
			this.edit('SAVE');
		});
		
		$("#btn-temp-save").on("click", () => {
			this.edit('SAVE');
		});
		
		$("#btn-delete").on("click", () => {
			this.delete();
		});
		
		$("#btn-reply-save").on("click", () => {
			this.saveReply();
		});
	},
	
	save: function(status) {
		let postYn = true;
		if(status == 'TEMP') {
			postYn = false;
		}
		
		if($("#title").val().length < 1) {
			alert("제목을 입력해주세요.");
			return false;
		}
		
		if($("#content").val().length < 1) {
			alert("내용을 입력해주세요.");
			return false;	
		}

		if($("#accessScope").val().length < 1) {
			alert("공개범위를 선택해주세요.");
			return false;	
		}
		
		let data = {
			title: $("#title").val(),
			keywords: $("#keywords").val(),
			content: $("#content").val(),
			accessScope: $("#accessScope").val(),
			postYn: postYn
		};
		
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			if(res.status != 200) {
				alert("게시글 등록에 실패하였습니다. 관리자에게 문의해주세요.");
				return false;
			} else {
				alert("게시글 등록이 완료되었습니다.");
				if(postYn) {
					location.href = "/board/list";	
				} else {
					location.href = "/board/list/temp";
				}	
			}
		}).fail(function(err) {
			alert("게시글 등록에 실패하였습니다. 관리자에게 문의해주세요.");
			console.log(err);
		});
	},
	
	edit: function(status) {
		let id = $("#id").val();
		
		let postYn = true;
		if(status == 'TEMP') {
			postYn = false;
		}
		
		if($("#title").val().length < 1) {
			alert("제목을 입력해주세요.");
			return false;
		}
		
		if($("#content").val().length < 1) {
			alert("내용을 입력해주세요.");
			return false;	
		}

		if($("#accessScope").val().length < 1) {
			alert("공개범위를 선택해주세요.");
			return false;	
		}
		
		let data = {
			id: id,
			title: $("#title").val(),
			keywords: $("#keywords").val(),
			content: $("#content").val(),
			accessScope: $("#accessScope").val(),
			postYn: postYn
		};
		
		$.ajax({
			type: "PUT",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			if(res.status != 200) {
				alert("게시글 수정에 실패하였습니다. 관리자에게 문의해주세요.");
				return false;
			} else {
				alert("게시글 수정 완료");
				location.href = "/board/" + id;
			}
		}).fail(function(err) {
			alert("게시글 수정에 실패하였습니다. 관리자에게 문의해주세요.");
			console.log(err);
		});
	},
	
	delete: function() {
		
		if(!confirm("게시글을 삭제하시겠습니까?")) {
			return false;
		}
		
		let data = {
			id: $("#boardId").val()
		};
		
		$.ajax({
			type: "DELETE",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			if(res.status != 200) {
				alert("게시글 삭제에 실패하였습니다. 관리자에게 문의하세요.");
				return false;
			} else {
				alert("게시글 삭제 완료");
				location.href = "/board/list"
			}
		}).fail(function(err) {
			alert("게시글 삭제에 실패하였습니다. 관리자에게 문의하세요.");
			return false;
		});
	},
	
	saveReply: function() {
		let boardId = $("#boardId").val();
		
		if($("#reply-content").val().length < 1) {
			alert("내용을 입력해주세요.");
			return false;
		}
		
		$("#reply-content").val().replace(/\r/g, "<br>"); 
		
		let data = {
			boardId: boardId,
			content: $("#reply-content").val()
		};
		
		$.ajax({
			type: "POST",
			url: "/api/board/reply",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			if(res.status != 200) {
				alert("댓글 등록에 실패하였습니다. 관리자에게 문의해주세요.");
				return false;	
			} else {
				alert("댓글 등록 완료");
				location.reload();
			}
		}).fail(function(err) {
			alert("댓글 등록에 실패하였습니다. 관리자에게 문의해주세요.");
			console.log(err);
			return false;
		});
	},
	
	replyDelete: function(boardId, replyId) {
		
		if(!confirm("게시글을 삭제하시겠습니까?")) {
			return false;
		}
		
		let data = {
			id: replyId,
			boardId: boardId
		};
		
		$.ajax({
			type: "DELETE",
			url: "/api/board/reply",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(res) {
			if(res.status != 200) {
				alert("댓글 삭제에 실패하였습니다. 관리자에게 문의해주세요.");
				return false;
			} else {
				alert("댓글 삭제 완료");
				location.reload();
			}
		}).fail(function(err) {
			alert("댓글 삭제에 실패하였습니다. 관리자에게 문의해주세요.");
			return false;
		});
	}
}

index.init();