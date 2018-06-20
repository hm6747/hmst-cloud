package com.syscloud.provider.hander;

import com.syscloud.exception.ParamException;
import com.syscloud.exception.PermissionException;
import com.syscloud.pojo.JsonData;
import com.syscloud.pojo.SystemData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class SpringExceptionResolver {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData resolveException(HttpServletRequest request,Exception ex) {
        String url = request.getRequestURL().toString();
        String defaultMsg = "系统错误";
        JsonData result;
        SystemData systemData = new SystemData();
        // 这里我们要求项目中所有请求json数据，都使用.json结尾
        if (url.endsWith(".json")) {
            if (ex instanceof PermissionException || ex instanceof ParamException) {
                 result = JsonData.fail(ex.getMessage());
            } else {
                log.error("unknown json exception, url:" + url, ex);
                 result = systemData.create(1,defaultMsg);
            }
        } else {
            log.error("unknow exception, url:" + url, ex);
                result = systemData.create(2,defaultMsg);
        }
        return result;
    }
}
