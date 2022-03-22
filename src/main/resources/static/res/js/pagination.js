/**
 * 페이징 관련
 * 페이지 입력 후 엔터 클릭 시 해당 페이지 바로가기 
 */

let paging_index = {
	
	init: function() {
		$("#pageNumber").keyup((event) => {
			console.log("test");
			console.log();
			this.movePage(event);
		});	
	},
	
	movePage: function(event) {
		let lastPage = Number($("#lastPageNumber").val());
		let inputPage = Number(event.target.value);

		//console.log(inputPage, lastPage);
		
		if(inputPage < 1) {
			inputPage = 1;
		}
		
		if(inputPage > lastPage) {
			inputPage = lastPage;
		}
		
		event.target.value = inputPage;
		
		if(event.keyCode == 13) {
			location.href = "?page=" + (inputPage-1);
		}
	}
	
}

paging_index.init();