package top.sillyfan.api.response.page;


import java.util.List;

/**
 * 分页中参数配置
 * 
 * @author guxiangguo
 *
 */
public class PagingConf {


	/**
	 * 总数
	 */
	Integer totalNum;

	/**
	 * 总页数
	 */
	Integer totalPage;

	/**
	 * 每页数量
	 */
	Integer pageSize;

	/**
	 * 当前页面，从1开始编号
	 */
	Integer page;

	public PagingConf() {
	}

	public PagingConf(Integer total, Integer pages, Integer pageSize,
			Integer page) {
		this.totalNum = total;
		this.totalPage = pages;
		this.pageSize = pageSize;
		this.page = page;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * 生成一页数据
	 * 
	 * @param ds
	 * @return
	 */
	public static <T> PagingConf paging(List<T> ds) {
		return new PagingConf(ds.size(), 1, ds.size(), 1);
	}

	/**
	 * 生成分页信息
	 * 
	 * @param totalNum
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public static PagingConf paging(Integer totalNum, Integer page,
			Integer pageSize) {
		int totalPage = PagingConf.countTotalPage(pageSize, totalNum);
		return new PagingConf(totalNum, totalPage, pageSize, page);
	}

	/**
	 * Calculate total pages
	 * 
	 * @param pageSize
	 *            number of records for one page
	 * @param allRow
	 *            number of all records for all pages
	 * @return number of total pages;
	 */

	public static int countTotalPage(final int pageSize, final int allRow) {
		int totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow
				/ pageSize + 1;
		return totalPage;
	}

	/**
	 * Calculate offset of current page;
	 * 
	 * @param pageSize
	 *            number of records for one page
	 * @param currentPage
	 *            current page index
	 * @return offset of records for current page
	 */
	public static Integer countOffset(final int pageSize, final int currentPage) {
		final int offset = pageSize * (currentPage - 1);
		return offset;
	}

	/**
	 * Calculate current page number.
	 * 
	 * @param page
	 *            if page is 0, return 1;
	 * @return current page
	 */
	public static Integer countCurrentPage(int page) {
		final int curPage = (page == 0 ? 1 : page);
		return curPage;
	}

	public static Integer countCurrentPage(int page, int allPages) {
		final int curPage = (page == 0 ? 1 : page);
		final int allPage = (allPages == 0 ? 1 : allPages);
		return Math.min(curPage, allPage);
	}

}
