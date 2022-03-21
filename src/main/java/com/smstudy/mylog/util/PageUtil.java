package com.smstudy.mylog.util;


public class PageUtil {
	
	private int pageNumber;				//현재페이지
	private int totalPageCount;			//전체페이지
	private int blockStartIndex;		//페이지 블록 시작점
	private int blockEndIndex; 			//페이지 블록 끝점
	private int defaultBlockSize;		//페이지 블록 크기(화면에 출력될 페이지 수)
	private String preDisabled;			//이전 페이지 블록 버튼 비활성화(태그 클래스 속성으로 활용)
	private String nextDisabled;		//다음 페이지 블록 버튼 비활성화(태그 클래스 속성으로 활용)
	

	public PageUtil(int pageNumber, int totalPageCount) {
		this.pageNumber = pageNumber;
		this.totalPageCount = totalPageCount;
	}
	
	public PageUtil(int pageNumber, int totalPageCount, int defaultBlockSize) {
		this.pageNumber = pageNumber;
		this.totalPageCount = totalPageCount;
		this.defaultBlockSize = defaultBlockSize;
	}
	
	private void init() {
		if(pageNumber < 0) {
			pageNumber = 0;
		}
		
		if(pageNumber > totalPageCount - 1) {
			pageNumber = totalPageCount - 1;
		}
		
		if(defaultBlockSize < 1) {
			defaultBlockSize = 5;
		}
		
		blockStartIndex = (pageNumber / defaultBlockSize) * defaultBlockSize;
		blockEndIndex = blockStartIndex + defaultBlockSize;
		if(blockEndIndex > totalPageCount) {
			blockEndIndex = totalPageCount;
		}

		preDisabled = (blockStartIndex == 0) ? "disabled" : "";
		nextDisabled = (blockEndIndex == totalPageCount) ? "disabled" : "";
	}
	
	/*
	 * ul.pagination 태그 하위 텍스드로 사용 시 페이지 리스트 출력
	 */
	public String getPageHtml() {
		init();
		
		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append("<ul class=\"pagination justify-content-center\">");
		strBuilder.append("<li class=\"page-item "+preDisabled+"\" th:classappend=\"${list.first ? 'disabled' : ''}\"><a class=\"page-link\" href=\"?page="+(blockStartIndex-1)+"\"><</a></li>");
		
		for(int i = blockStartIndex; i < blockEndIndex; i++) {
			if(i == pageNumber) {
				strBuilder.append("<li class=\"page-item disabled\"><a class=\"page-link\" href=\"?page="+i+"\"><b>"+(i+1)+"</b></a></li>");
			} else {
				strBuilder.append("<li class=\"page-item\"><a class=\"page-link\" href=\"?page="+i+"\">"+(i+1)+"</a></li>");
			}
		}
		
		strBuilder.append("<li class=\"page-item "+nextDisabled+"\" th:classappend=\"${list.last ? 'disabled' : ''}\"><a class=\"page-link\" href=\"?page="+blockEndIndex+"\">></a></li>");
		strBuilder.append("</ul>");
		
		return strBuilder.toString();
	}
}
