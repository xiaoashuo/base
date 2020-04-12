package tk.mybatis.mapper;

/**
 * @author ys
 * @topic
 * @date 2020/4/11 0:28
 */
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 自己的 Mapper
 * 特别注意，该接口不能被扫描到，否则会出错
 * <p>Title: MyMapper</p>
 * <p>Description: </p>
 *
 * @author Ys
 * @version 1.0.0
 * @date 2019/5/9
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
