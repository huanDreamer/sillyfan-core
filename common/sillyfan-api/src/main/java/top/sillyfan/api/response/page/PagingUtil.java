package top.sillyfan.api.response.page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

/**
 * @author huanxing
 * @date 9/21/17
 */
public class PagingUtil {

	/**
	 * 对data进行分页
	 * 
	 * @param offset
	 *            偏移量0-n
	 * @param limit
	 *            返回大小0-n
	 * @param data
	 *            原始数据集
	 * @return
	 */
	public static <T> List<T> doPage(Integer offset, Integer limit,
			List<T> data) {

		if (CollectionUtils.isEmpty(data)) {
			return Collections.emptyList();
		}

		if (offset >= data.size()) {
			return Collections.emptyList();
		}
		return data.stream().skip(offset).limit(limit)
				.collect(Collectors.toList());
	}

	/**
	 * 获取总页数
	 * 
	 * @param totalRow
	 *            总记录数
	 * @param pageSize
	 *            分页大小
	 * @return 总页数
	 */
	public static Integer getTotalPage(Integer totalRow, Integer pageSize) {

		return totalRow % pageSize == 0 ? totalRow / pageSize
				: totalRow / pageSize + 1;
	}

}
