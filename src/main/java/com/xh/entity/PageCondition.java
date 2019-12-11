package com.xh.entity;

import lombok.Data;

/**
 * @author xiaohe
 */
@Data
public class PageCondition {

	private Integer currentPage;
	
	private Integer pageSize;
	
	private String searchTerm;
	
	private Long searchId;

}
