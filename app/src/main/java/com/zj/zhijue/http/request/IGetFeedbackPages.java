package com.zj.zhijue.http.request;

public interface IGetFeedbackPages extends IBaseRequest {
    String PAGENO = "pageNo";//	是	int	当前页码
    String PAGESIZE = "pageSize";//	是	int	每页显示条数
    String STATUS = "status";//	是	int	处理状态0-未处理、1-已回复、2-亲测有效
    String TITLE = "title";//	是	string	标题关键字模糊检索
    String CREATEBY = "createBy";//
}
