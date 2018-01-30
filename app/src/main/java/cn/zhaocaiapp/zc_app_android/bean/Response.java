package cn.zhaocaiapp.zc_app_android.bean;

import java.io.Serializable;

import cn.zhaocaiapp.zc_app_android.refer.BusinessEnum;

/**
 * Created by jinxunmediapty.ltd on 2018/1/5.
 */

public class Response<T> implements Serializable {
    /**
     * 业务处理结果, 0成功 -1失败处理失败，默认成功
     **/
    private Integer code;

    /**
     * 提示信息
     **/
    private String desc;

    /**
     * 记录总数，只在需要分页时使用，没有默认值，使用前必须赋值
     **/
    private Integer total;

    /**
     * 返回值
     **/
    private T data;

    private Response() {
        // 默认业务处理成功
        this.code = BusinessEnum.SUCCESS.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public Response<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Response<T> setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public Response<T> setTotal(Integer total) {
        this.total = total;
        return this;
    }

    /**
     * 简化使用
     *
     * @param <E>
     * @return
     */
    public static <E> Response<E> BUILDER() {
        return new Response<E>();
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}