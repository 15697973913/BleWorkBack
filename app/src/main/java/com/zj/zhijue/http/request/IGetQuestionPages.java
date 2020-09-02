package com.zj.zhijue.http.request;

public interface IGetQuestionPages extends IBaseRequest{
    String PAGENO = "pageNo";//	是	int	当前页码
    String PAGESIZE = "pageSize";//	是	int	每页显示条数
    String QUESTIONTYPEID = "questionTypeId";//	否	string	问题类型
}
