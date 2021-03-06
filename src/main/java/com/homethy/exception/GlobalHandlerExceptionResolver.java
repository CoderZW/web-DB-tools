package com.homethy.exception;

import com.google.common.collect.ImmutableMap;
import com.homethy.constant.ErrorCodeEnum;
import com.homethy.constant.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {

	private static final Log LOG = LogFactory.getLog(GlobalHandlerExceptionResolver.class);
	
    public GlobalHandlerExceptionResolver(){
        LOG.info("Init Global Error Ok!");
    }
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (ex == null)
			return null;
		
		Throwable cause = ex.getCause();
		if(cause!= null && cause.getClass() != null && 
				cause.getClass().getSimpleName().equals("ClientAbortException")){
			LOG.error("ClientAbortException Caught");
			return null;
		}
		
    LOG.error("[GlobalHandlerExceptionResolver::ResolveException] Exception:", ex);
    Map<String, Object> status = new HashMap<String, Object>();
    Map<String, Object> result = new HashMap<String, Object>();
    MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

    status.put(Constant.RESULT_CODE, ErrorCodeEnum.UNKNOWN_ERROR.getErrorCode());
    status.put(Constant.RESULT_MSG, ErrorCodeEnum.UNKNOWN_ERROR.getErrorMsg());
    result.put("data",ex.toString());
    result.put("status",status);

    return new ModelAndView(jsonView,result);
	}

}
