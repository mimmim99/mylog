/**
 * 게시글 추가,수정,삭제
 * 썸머노트 설정 
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
		let contentFiles = new Array();
		$("input[name=contentFiles]").each(function(index, item){
			contentFiles.push($(item).val());
		});
		
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
		
		let data = {
			title: $("#title").val(),
			keywords: $("#keywords").val(),
			content: $("#content").val(),
			postYn: postYn,
			contentFiles: contentFiles
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
		let contentFiles = new Array();
		$("input[name=contentFiles]").each(function(index, item){
			contentFiles.push($(item).val());
		});
		
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
		
		let data = {
			id: id,
			title: $("#title").val(),
			keywords: $("#keywords").val(),
			content: $("#content").val(),
			postYn: postYn,
			contentFiles: contentFiles
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
				location.href = "/public/board/" + id;
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

//썸머노트 사용		
$(".summernote").summernote({
    tabsize: 2,
    height: 300,
	//반응형
    width: "100%",
	disableResizeEditor: true,	// 크기 조절 기능 삭제
    toolbar: [
				['fontname', ['fontname']],
				['fontsize', ['fontsize']],
				['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
				['color', ['forecolor','color']],
				['table', ['table']],
				['para', ['ul', 'ol', 'paragraph']],
				['height', ['height']],
				['insert',['picture','link','video']],
				['view', ['fullscreen', 'help']]
			],
	fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
	fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],    
	callbacks: {
		onImageUpload: function(files) {
			for(let i=0; i<files.length; i++ ) {
				summernoteImageUpload(files[i]);
			}
		},
		onPaste: function (e) {
			let clipboardData = e.originalEvent.clipboardData;
			if (clipboardData && clipboardData.items && clipboardData.items.length) {
				let item = clipboardData.items[0];
				if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
					e.preventDefault();
				}
			}
		}
	}
});

//이미지 업로드
function summernoteImageUpload(file) {
	let data = new FormData();
	data.append("file", file);
	
	$.ajax({
		type: "POST",
		url: "/api/board/upload",
		data: data,
		contentType: false,
		processData: false,
		enctype : 'multipart/form-data'
	}).done(function(res) {
		$(".summernote").summernote('insertImage', res.data);
		
		$('#content').prepend('<input type="hidden" name="contentFiles" value="'+res.data+'">');
		
	}).fail(function(err) {
		console.log(res);
	});

}
