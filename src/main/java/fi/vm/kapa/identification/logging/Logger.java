/**
 * The MIT License
 * Copyright (c) 2015 Population Register Centre
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fi.vm.kapa.identification.logging;

import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class Logger {

    private static final String ALPHANUMERICS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // new ReqID is randomized from these chars 
    private org.slf4j.Logger slf4jLogger; // actual logger inside this wrapper
    private String component; // like "proxy"/"vtj-client"
    private Random random;

    public static final String REQUEST_ID = "ReqID";
    public static final String NO_REQUEST_ID = "no_request"; // will be shown as ReqID if logging outside request scope
    public static final String VTJ_CLIENT = "Vtj-client";


    private Logger() {
    }

    @SuppressWarnings("rawtypes")
    public static Logger getLogger(Class cls, String component) {
        Logger logger = new Logger();
        logger.slf4jLogger = LoggerFactory.getLogger(cls);
        logger.component = component;
        logger.random = new Random(System.currentTimeMillis());
        return logger;
    }

    public void debug(String msg) {
    	if (slf4jLogger.isDebugEnabled()) {
    		slf4jLogger.debug(createMessage(msg));
    	}
    }

    public void info(String msg) {
    	if (slf4jLogger.isInfoEnabled()) {
    		slf4jLogger.info(createMessage(msg));
    	}
    }
    
    public void info(String msg, Throwable t) {
    	if (slf4jLogger.isInfoEnabled()) {
    		slf4jLogger.info(createMessage(msg), t);
    	}
    }

    public void warning(String msg) {
    	if (slf4jLogger.isWarnEnabled()) {
    		slf4jLogger.warn(createMessage(msg));
    	}
    }

    public void warning(String msg, Throwable t) {
    	if (slf4jLogger.isWarnEnabled()) {
    		slf4jLogger.warn(createMessage(msg), t);
    	}
    }

    public void error(String msg) {
    	if (slf4jLogger.isErrorEnabled()) {
    		slf4jLogger.error(createMessage(msg));
    	}
    }

    private String createMessage(String msg) {
        StringBuilder builder = new StringBuilder();
        builder.append(component);
        builder.append(" ");

        builder.append(fetchRequestId());
        builder.append(" ");

        builder.append(msg);
        return builder.toString();
    }

    private String fetchRequestId() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            String requestId = (String) attrs.getAttribute(REQUEST_ID, RequestAttributes.SCOPE_REQUEST);
            if (requestId == null) {
                HttpServletRequest httpRequest = ((ServletRequestAttributes) attrs).getRequest();
                requestId = httpRequest.getHeader(REQUEST_ID);

                if (requestId == null) {
                    StringBuilder sb = new StringBuilder(15);
                    for (int i = 0; i < 15; i++) {
                        sb.append(ALPHANUMERICS.charAt(random.nextInt(ALPHANUMERICS.length())));
                    }
                    requestId = sb.toString();
                }

                attrs.setAttribute(REQUEST_ID, requestId, RequestAttributes.SCOPE_REQUEST);
            }

            return requestId;
        } else {
            return NO_REQUEST_ID;
        }
    }

}
