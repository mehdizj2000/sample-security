package au.com.jaycar.controller;

//@Controller
public class MyErrorController /* implements ErrorController */ {

//	@Override
//	public String getErrorPath() {
//		return "/error";
//	}
//
//	@RequestMapping("/error")
//	public String handleError(HttpServletRequest request) {
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//		if (status != null) {
//			Integer statusCode = Integer.valueOf(status.toString());
//
//			if (statusCode == HttpStatus.NOT_FOUND.value()) {
//				return "error-404";
//			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//				return "error-500";
//			}
//		}
//		return "error";
//	}
}