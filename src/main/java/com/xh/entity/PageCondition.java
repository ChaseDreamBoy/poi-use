package com.xh.entity;

public class PageCondition {

	private Integer currentPage;
	
	private Integer pageSize;
	
	private String searchTerm;
	
	private Long searchId;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public Long getSearchId() {
		return searchId;
	}

	public void setSearchId(Long searchId) {
		this.searchId = searchId;
	}

	@Override
	public String toString() {
		return "PageCondition [currentPage=" + currentPage + ", pageSize=" + pageSize + ", searchTerm=" + searchTerm
				+ ", searchId=" + searchId + "]";
	}

}
