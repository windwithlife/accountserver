package com.simple.core.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import com.simple.common.error.ServiceException;
import com.simple.core.data.message.ResponseMessage;
import com.simple.core.data.request.JsonMessage;

/**
 * 公共异常处理类
 * @author hejinguo
 * @version $Id: CommonExceptionHandle.java, v 0.1 2019年1月20日 上午10:05:37
 */
public class CommonExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandle.class);

    /**
     * 全局异常处理方法
     * @param request
     * @param ex
     * @return
     */
    public static void handleException(ResponseMessage resMessage, JsonMessage jsonMessage,
                                       HttpServletRequest request, Exception ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            logger.error(sysOutErrorMessage(request, jsonMessage, ex, "请求参数不合法错误"), ex);
            resMessage.setMessage("服务器繁忙,请稍后再试.code:400");
            return;
        }
        if (ex instanceof ServiceException) {
            logger.error(sysOutErrorMessage(request, jsonMessage, ex, "业务逻辑错误"), ex);
            resMessage.setMessage(ex.getMessage());
            return;
        }

        //参数类型不匹配
        if (ex instanceof TypeMismatchException) {
            TypeMismatchException sMismatchException = (TypeMismatchException) ex;
            logger.error(
                sysOutErrorMessage(request, jsonMessage, ex,
                    "请求参数类型不匹配,参数：" + sMismatchException.getPropertyName() + "类型必须为"
                            + sMismatchException.getRequiredType()), ex);
            resMessage.setMessage("请求参数类型错误!");
            return;
        }
        //缺少参数异常
        if (ex instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException parameterException = (MissingServletRequestParameterException) ex;
            logger.error(
                sysOutErrorMessage(request, jsonMessage, ex,
                    "请求缺少必要参数,参数名：" + parameterException.getParameterName()), ex);
            resMessage.setMessage("请求缺少必要参数!");
            return;

        }
        //请求数据格式错误
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            HttpMediaTypeNotSupportedException supportedException = (HttpMediaTypeNotSupportedException) ex;
            logger.error(
                sysOutErrorMessage(request, jsonMessage, ex, "请求协议类型错误,ContentType："
                                                             + supportedException.getContentType()
                                                                 .getType()), ex);
            resMessage.setMessage("请求协议类型错误!");
            return;
        }
        if (ex instanceof IllegalStateException) {
            logger.error(sysOutErrorMessage(request, jsonMessage, ex, "请求参数不合法错误"), ex);
            resMessage.setMessage("服务器繁忙,请稍后再试.code:400");
            return;
        }
        if (ex instanceof MissingServletRequestParameterException) {
            logger.error(sysOutErrorMessage(request, jsonMessage, ex, "请求参数不合法错误"), ex);
            resMessage.setMessage("服务器繁忙,请稍后再试.code:400");
            return;
        }
        logger.error(sysOutErrorMessage(request, jsonMessage, ex, "服务器处理异常"), ex);
        resMessage.setMessage("系统繁忙请稍后再试.code:500");
        return;
    }

    /**
     * 统一输出异常数据信息
     * @param request
     * @return
     */
    public static String sysOutErrorMessage(HttpServletRequest request, JsonMessage jsonMessage,
                                            Exception ex, String errorType) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(System.getProperty("line.separator"));
        buffer.append("---->请求方法：" + request.getRequestURI());
        buffer.append(System.getProperty("line.separator"));
        buffer.append("---->请求参数：" + jsonMessage.getSourceMessage());
        buffer.append(System.getProperty("line.separator"));
        buffer.append("---->异常信息：" + errorType);
        return buffer.toString();
    }
}
