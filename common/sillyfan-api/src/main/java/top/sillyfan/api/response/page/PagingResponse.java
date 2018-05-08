package top.sillyfan.api.response.page;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 分页的数据结果：
 * 
 * <pre>
 * {
 *   "code":0,
 *   "message":"",
 *   "data":{
 *     "list":[T],
 *     "conf":PagingConf,
 *     "total": {R}
 *   }
 *   
 * }
 * </pre>
 * 
 * @author guxiangguo
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class PagingResponse<T, R> {

	public static <T, R> PagingResponse<T, R> emptyResponse() {
		return new PagingResponse<T, R>(Collections.emptyList(),
				PagingConf.paging(Collections.emptyList()));
	}

	/**
	 * 数据结果集
	 */
	List<T> list;

	/**
	 * 分页信息
	 */
	PagingConf conf;

	R total;

	public PagingResponse(List<T> dataset, PagingConf conf, R total) {
		this.list = dataset;
		this.conf = conf;
		this.total = total;
	}

	public PagingResponse(List<T> dataset, PagingConf conf) {
		this.list = dataset;
		this.conf = conf;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public PagingConf getConf() {
		return conf;
	}

	public void setConf(PagingConf conf) {
		this.conf = conf;
	}

	public R getTotal() {
		return total;
	}

	public void setTotal(R total) {
		this.total = total;
	}

}
