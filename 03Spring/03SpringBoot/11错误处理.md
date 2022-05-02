## 52、错误处理-SpringBoot默认错误处理机制

[Spring Boot官方文档 - Error Handling](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#boot-features-error-handling)

**默认规则**：

- 默认情况下，Spring Boot提供`/error`处理所有错误的映射

- 机器客户端，它将生成JSON响应，其中包含错误，HTTP状态和异常消息的详细信息。对于浏览器客户端，响应一个“ whitelabel”错误视图，以HTML格式呈现相同的数据

```json
{
  "timestamp": "2020-11-22T05:53:28.416+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "No message available",
  "path": "/asadada"
}
```

- 要对其进行自定义，添加`View`解析为`error`

- 要完全替换默认行为，可以实现 `ErrorController `并注册该类型的Bean定义，或添加`ErrorAttributes类型的组件`以使用现有机制但替换其内容。

- `/templates/error/`下的4xx，5xx页面会被自动解析

## 53、错误处理-【源码分析】底层组件功能分析


- `ErrorMvcAutoConfiguration`  自动配置异常处理规则
- **容器中的组件**：类型：`DefaultErrorAttributes` -> id：`errorAttributes`
- `public class DefaultErrorAttributes implements ErrorAttributes, HandlerExceptionResolver`
  - `DefaultErrorAttributes`：定义错误页面中可以包含数据（异常明细，堆栈信息等）。
- **容器中的组件**：类型：`BasicErrorController` --> id：`basicErrorController`（json+白页 适配响应）
- **处理默认 `/error` 路径的请求**，页面响应 `new ModelAndView("error", model);`
  - 容器中有组件 `View`->id是error；（响应默认错误页）
  - 容器中放组件 `BeanNameViewResolver`（视图解析器）；按照返回的视图名作为组件的id去容器中找`View`对象。
- **容器中的组件**：类型：`DefaultErrorViewResolver` -> id：`conventionErrorViewResolver`
- **如果发生异常错误，会以HTTP的状态码 作为视图页地址（viewName），找到真正的页面**（主要作用）。
  - error/404、5xx.html
  - 如果想要返回页面，就会找error视图（`StaticView`默认是一个白页）。

## 54、错误处理-【源码流程】异常处理流程

譬如写一个会抛出异常的控制层：

```java
@Slf4j
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String handle01(){

        int i = 1 / 0;//将会抛出ArithmeticException

        log.info("Hello, Spring Boot 2!");
        return "Hello, Spring Boot 2!";
    }
}
```

当浏览器发出`/hello`请求，`DispatcherServlet`的`doDispatch()`的`mv = ha.handle(processedRequest, response, mappedHandler.getHandler());`将会抛出`ArithmeticException`。

```java
public class DispatcherServlet extends FrameworkServlet {
    ...
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		...
				// Actually invoke the handler.
            	//将会抛出ArithmeticException
				mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

				applyDefaultViewName(processedRequest, mv);
				mappedHandler.applyPostHandle(processedRequest, response, mv);
			}
			catch (Exception ex) {
                //将会捕捉ArithmeticException
				dispatchException = ex;
			}
			catch (Throwable err) {
				...
			}
    		//捕捉后，继续运行
			processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
		}
		catch (Exception ex) {
			triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
		}
		catch (Throwable err) {
			triggerAfterCompletion(processedRequest, response, mappedHandler,
					new NestedServletException("Handler processing failed", err));
		}
		finally {
			...
		}
	}

	private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
			@Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv,
			@Nullable Exception exception) throws Exception {

		boolean errorView = false;

		if (exception != null) {
			if (exception instanceof ModelAndViewDefiningException) {
				...
			}
			else {
				Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);
				//ArithmeticException将在这处理
                mv = processHandlerException(request, response, handler, exception);
				errorView = (mv != null);
			}
		}
		...
	}

	protected ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response,
			@Nullable Object handler, Exception ex) throws Exception {

		// Success and error responses may use different content types
		request.removeAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);

		// Check registered HandlerExceptionResolvers...
		ModelAndView exMv = null;
		if (this.handlerExceptionResolvers != null) {
            //遍历所有的 handlerExceptionResolvers，看谁能处理当前异常HandlerExceptionResolver处理器异常解析器
			for (HandlerExceptionResolver resolver : this.handlerExceptionResolvers) {
				exMv = resolver.resolveException(request, response, handler, ex);
				if (exMv != null) {
					break;
				}
			}
		}
		...
	
        //若只有系统的自带的异常解析器（没有自定义的），异常还是会抛出
		throw ex;
	}

}
```

系统自带的**异常解析器**：

![在这里插入图片描述](D:/StanLong/git_repository/Framework/03Spring/03SpringBoot/笔记/image/20210205011338251.png)


- `DefaultErrorAttributes`先来处理异常，它主要功能把异常信息保存到request域，并且返回null。

```java
public class DefaultErrorAttributes implements ErrorAttributes, HandlerExceptionResolver, Ordered {
    ...
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        this.storeErrorAttributes(request, ex);
        return null;
    }

    private void storeErrorAttributes(HttpServletRequest request, Exception ex) {
        request.setAttribute(ERROR_ATTRIBUTE, ex);//把异常信息保存到request域
    }
    ...
    
}    
```

- 默认没有任何解析器（上图的`HandlerExceptionResolverComposite`）能处理异常，所以最后异常会被抛出。

- 最终底层就会转发`/error` 请求。会被底层的`BasicErrorController`处理。

```java
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController extends AbstractErrorController {

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
       HttpStatus status = getStatus(request);
       Map<String, Object> model = Collections
             .unmodifiableMap(getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
       response.setStatus(status.value());
       ModelAndView modelAndView = resolveErrorView(request, response, status, model);
       //如果/template/error内没有4**.html或5**.html，
       //modelAndView为空，最终还是返回viewName为error的modelAndView
       return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
    }
    
    ...
}
```



```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    ...
    
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ...
     	// Actually invoke the handler.
		mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
		...
        //渲染页面
		processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
        ...
    }
    
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
			@Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv,
			@Nullable Exception exception) throws Exception {

        boolean errorView = false;
        ...
		// Did the handler return a view to render?
		if (mv != null && !mv.wasCleared()) {
			render(mv, request, response);
			if (errorView) {
				WebUtils.clearErrorRequestAttributes(request);
			}
		}
		...
	}
    
    protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
		...

		View view;
		String viewName = mv.getViewName();
		if (viewName != null) {
			// We need to resolve the view name.
            //找出合适error的View，如果/template/error内没有4**.html或5**.html，
            //将会返回默认异常页面ErrorMvcAutoConfiguration.StaticView
            //这里按需深究代码吧！
			view = resolveViewName(viewName, mv.getModelInternal(), locale, request);
			...
		}
		...
		try {
			if (mv.getStatus() != null) {
				response.setStatus(mv.getStatus().value());
			}
            //看下面代码块的StaticView的render块
			view.render(mv.getModelInternal(), request, response);
		}
		catch (Exception ex) {
			...
		}
	}
    
}
```



```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
// Load before the main WebMvcAutoConfiguration so that the error View is available
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties({ ServerProperties.class, ResourceProperties.class, WebMvcProperties.class })
public class ErrorMvcAutoConfiguration {
    
    ...
        
   	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(prefix = "server.error.whitelabel", name = "enabled", matchIfMissing = true)
	@Conditional(ErrorTemplateMissingCondition.class)
	protected static class WhitelabelErrorViewConfiguration {

        //将创建一个名为error的系统默认异常页面View的Bean
		private final StaticView defaultErrorView = new StaticView();

		@Bean(name = "error")
		@ConditionalOnMissingBean(name = "error")
		public View defaultErrorView() {
			return this.defaultErrorView;
		}

		// If the user adds @EnableWebMvc then the bean name view resolver from
		// WebMvcAutoConfiguration disappears, so add it back in to avoid disappointment.
		@Bean
		@ConditionalOnMissingBean
		public BeanNameViewResolver beanNameViewResolver() {
			BeanNameViewResolver resolver = new BeanNameViewResolver();
			resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
			return resolver;
		}

	}     
   
    
	private static class StaticView implements View {

		private static final MediaType TEXT_HTML_UTF8 = new MediaType("text", "html", StandardCharsets.UTF_8);

		private static final Log logger = LogFactory.getLog(StaticView.class);

		@Override
		public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			if (response.isCommitted()) {
				String message = getMessage(model);
				logger.error(message);
				return;
			}
			response.setContentType(TEXT_HTML_UTF8.toString());
			StringBuilder builder = new StringBuilder();
			Object timestamp = model.get("timestamp");
			Object message = model.get("message");
			Object trace = model.get("trace");
			if (response.getContentType() == null) {
				response.setContentType(getContentType());
			}
            //系统默认异常页面html代码
			builder.append("<html><body><h1>Whitelabel Error Page</h1>").append(
					"<p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p>")
					.append("<div id='created'>").append(timestamp).append("</div>")
					.append("<div>There was an unexpected error (type=").append(htmlEscape(model.get("error")))
					.append(", status=").append(htmlEscape(model.get("status"))).append(").</div>");
			if (message != null) {
				builder.append("<div>").append(htmlEscape(message)).append("</div>");
			}
			if (trace != null) {
				builder.append("<div style='white-space:pre-wrap;'>").append(htmlEscape(trace)).append("</div>");
			}
			builder.append("</body></html>");
			response.getWriter().append(builder.toString());
		}

		private String htmlEscape(Object input) {
			return (input != null) ? HtmlUtils.htmlEscape(input.toString()) : null;
		}

		private String getMessage(Map<String, ?> model) {
			Object path = model.get("path");
			String message = "Cannot render error page for request [" + path + "]";
			if (model.get("message") != null) {
				message += " and exception [" + model.get("message") + "]";
			}
			message += " as the response has already been committed.";
			message += " As a result, the response may have the wrong status code.";
			return message;
		}

		@Override
		public String getContentType() {
			return "text/html";
		}

	}
}
```



## 55、错误处理-【源码流程】几种异常处理原理

- 自定义错误页
  - error/404.html   error/5xx.html；有精确的错误状态码页面就匹配精确，没有就找 4xx.html；如果都没有就触发白页

- `@ControllerAdvice`+`@ExceptionHandler`处理全局异常；底层是 `ExceptionHandlerExceptionResolver` 支持的

```java
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ArithmeticException.class,NullPointerException.class})  //处理异常
    public String handleArithException(Exception e){

        log.error("异常是：{}",e);
        return "login"; //视图地址
    }
}
```

- `@ResponseStatus`+自定义异常 ；底层是 `ResponseStatusExceptionResolver` ，把responseStatus注解的信息底层调用 `response.sendError(statusCode, resolvedReason)`，tomcat发送的`/error`

```java
@ResponseStatus(value= HttpStatus.FORBIDDEN,reason = "用户数量太多")
public class UserTooManyException extends RuntimeException {

    public  UserTooManyException(){

    }
    public  UserTooManyException(String message){
        super(message);
    }
}
```

```java
@Controller
public class TableController {
    
	@GetMapping("/dynamic_table")
    public String dynamic_table(@RequestParam(value="pn",defaultValue = "1") Integer pn,Model model){
        //表格内容的遍历
	     List<User> users = Arrays.asList(new User("zhangsan", "123456"),
                new User("lisi", "123444"),
                new User("haha", "aaaaa"),
                new User("hehe ", "aaddd"));
        model.addAttribute("users",users);

        if(users.size()>3){
            throw new UserTooManyException();//抛出自定义异常
        }
        return "table/dynamic_table";
    }
    
}
```

- Spring自家异常如 ` org.springframework.web.bind.MissingServletRequestParameterException`，`DefaultHandlerExceptionResolver` 处理Spring自家异常。

- - `response.sendError(HttpServletResponse.SC_BAD_REQUEST/*400*/, ex.getMessage());` 

- 自定义实现 `HandlerExceptionResolver` 处理异常；可以作为默认的全局异常处理规则

```java
@Order(value= Ordered.HIGHEST_PRECEDENCE)  //优先级，数字越小优先级越高
@Component
public class CustomerHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler, Exception ex) {

        try {
            response.sendError(511,"我喜欢的错误");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
```

- `ErrorViewResolver`  实现自定义处理异常
  - `response.sendError()`，error请求就会转给controller。
  - 你的异常没有任何人能处理，tomcat底层调用`response.sendError()`，error请求就会转给controller。
  - `basicErrorController` 要去的页面地址是 `ErrorViewResolver`  。

```java
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController extends AbstractErrorController {

    ...
    
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		Map<String, Object> model = Collections
				.unmodifiableMap(getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
		response.setStatus(status.value());
		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
		return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
	}
    
    protected ModelAndView resolveErrorView(HttpServletRequest request, HttpServletResponse response, HttpStatus status,
			Map<String, Object> model) {
        //这里用到ErrorViewResolver接口
		for (ErrorViewResolver resolver : this.errorViewResolvers) {
			ModelAndView modelAndView = resolver.resolveErrorView(request, status, model);
			if (modelAndView != null) {
				return modelAndView;
			}
		}
		return null;
	}
    
    ...
    
}
```



```java
@FunctionalInterface
public interface ErrorViewResolver {

	ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model);

}
```

