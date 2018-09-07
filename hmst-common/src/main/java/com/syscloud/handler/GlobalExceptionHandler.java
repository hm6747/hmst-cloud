//package com.syscloud.handler;
//
//import com.syscloud.base.auth.expection.BaseException;
//import com.syscloud.base.auth.expection.auth.ClientTokenException;
//import com.syscloud.base.auth.expection.auth.UserInvalidException;
//import com.syscloud.base.auth.expection.auth.UserTokenException;
//import com.syscloud.base.auth.pojo.BaseResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//@ControllerAdvice
//@ResponseBody
//public class GlobalExceptionHandler {
//    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(ClientTokenException.class)
//    public BaseResponse clientTokenExceptionHandler(HttpServletResponse response, ClientTokenException ex) {
//        response.setStatus(403);
//        logger.error(ex.getMessage(),ex);
//        return new BaseResponse(ex.getStatus(), ex.getMessage());
//    }
//
//    @ExceptionHandler(UserTokenException.class)
//    public BaseResponse userTokenExceptionHandler(HttpServletResponse response, UserTokenException ex) {
//        response.setStatus(200);
//        logger.error(ex.getMessage(),ex);
//        return new BaseResponse(ex.getStatus(), ex.getMessage());
//    }
//
//    @ExceptionHandler(UserInvalidException.class)
//    public BaseResponse userInvalidExceptionHandler(HttpServletResponse response, UserInvalidException ex) {
//        response.setStatus(200);
//        logger.error(ex.getMessage(),ex);
//        return new BaseResponse(ex.getStatus(), ex.getMessage());
//    }
//
//    @ExceptionHandler(BaseException.class)
//    public BaseResponse baseExceptionHandler(HttpServletResponse response, BaseException ex) {
//        logger.error(ex.getMessage(),ex);
//        response.setStatus(500);
//        return new BaseResponse(ex.getStatus(), ex.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public BaseResponse otherExceptionHandler(HttpServletResponse response, Exception ex) {
//        response.setStatus(500);
//        logger.error(ex.getMessage(),ex);
//        return new BaseResponse(CommonConstants.EX_OTHER_CODE, ex.getMessage());
//    }
//
//}
