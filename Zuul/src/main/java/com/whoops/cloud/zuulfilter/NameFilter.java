package com.whoops.cloud.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class NameFilter extends ZuulFilter {
    @Override
    //定义filter的类型，有pre、route、post、error四种
    public String filterType() {
        return "pre";
    }

    @Override
    //定义filter的顺序，数字越小表示顺序越高，越先执行
    public int filterOrder() {
        return 0;
    }

    @Override
    //表示是否需要执行该filter，true表示执行，false表示不执行
    public boolean shouldFilter() {
        return true;
    }

    @Override
    //filter需要执行的具体操作
    public Object run() throws ZuulException {
        RequestContext rct = RequestContext.getCurrentContext();
        HttpServletRequest request = rct.getRequest();
        String name = request.getParameter("name");

        if(StringUtils.isEmpty(name)){
            rct.setSendZuulResponse(false);
            rct.setResponseStatusCode(400);
            rct.set("isSuccess",false);
            rct.setResponseBody("name is empty!");
        }else{
            rct.setSendZuulResponse(true);
            rct.setResponseStatusCode(200);
            rct.set("isSuccess",true);
        }
        return null;
    }
}
