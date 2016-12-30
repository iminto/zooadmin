package com.baicai.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 猪肉有毒 waitfox@qq.com
 * @version V1.0
 * @Description:
 * @date 2016/12/27 16:44
 */
public class ResultData<T> implements Serializable {

    private static final long serialVersionUID = -1L;
    private String 	code = "1000"; // 状态码
    private Boolean success = true;//默认成功
    private String 	message = "操作成功"; // 返回信息
    private long total = 0; // 总记录数
    private List<T> dataModel = new ArrayList<T>(); // 多条数据列表
    private Object item ; // 单条数据

    public ResultData() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getDataModel() {
        return dataModel;
    }

    public void setDataModel(List<T> dataModel) {
        this.dataModel = dataModel;
    }

    public Boolean getSuccess() {
        if(success==null){
            this.success = true;
        }
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
