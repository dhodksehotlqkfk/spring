package kr.or.ddit.user.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.user.model.User;
import kr.or.ddit.user.model.UserValidator;
import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.util.FileUtil;
import kr.or.ddit.util.model.FileInfo;

@RequestMapping("user/")
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name = "userService")
	private IUserService userService;
	
	/**
	* Method : userView
	* 작성자 : PC-02
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 상세화면 요청
	*/
	//사용자가 localhost:8081/spring/user/view.do
	@RequestMapping("view.do")
	public String userView() {
		logger.debug("userController.userview()");
		
		return "user/view";
		
		// prefix + viewName + suffix
		// WEB-INF/views/user/view.jsp
	}
	
	/**
	* Method : userList
	* 작성자 : PC-02
	* 변경이력 :
	* @param model
	* @return
	* Method 설명 : 사용자 전체 리스트 조회
	*/
	@RequestMapping(path = "userList", method = RequestMethod.GET)
	public String userList(Model model) {
		//사용자 정보 전체 조회
//		List<User> userList = userService.getUserList();
//		model.addAttribute("userList", userList);
		model.addAttribute("userList", userService.getUserList());
		return "user/userList";
	}
	
	/**
	* Method : userListOnlyHalf
	* 작성자 : PC-02
	* 변경이력 :
	* @param model
	* @return
	* Method 설명 : 사용자 리스트 절반 조회
	*/
	@RequestMapping(path = "userListOnlyHalf", method = RequestMethod.GET)
	public String userListOnlyHalf(Model model) {
		model.addAttribute("userList", userService.getUserListOnlyHalf());
		return "user/userListOnlyHalf";
	}
	
	/**
	* Method : userPagingList
	* 작성자 : PC-02
	* 변경이력 :
	* @param model
	* @param page
	* @return
	* Method 설명 : 사용자 페이징 리스트 조회
	*/
	@RequestMapping(path = "userPagingList", method = RequestMethod.GET)
	public String userPagingList(Model model, Page page) {
		
		model.addAttribute("pageVo", page);
		
		Map<String, Object> resultMap = userService.getUserPagingList(page);
		model.addAllAttributes(resultMap);
		
		return "user/userPagingList";
	}
	
	@RequestMapping(path = "user", method = RequestMethod.GET)
	public String userDetailView(Model model, String userId) {
		model.addAttribute("user", userService.getUser(userId));
		return "user/user";
	}
	
	@RequestMapping(path = "userPicture", method = RequestMethod.GET)
	public void userPicture(String userId, HttpServletResponse response) throws IOException {
		User user = userService.getUser(userId);
		
		ServletOutputStream sos = response.getOutputStream();
		
		File picture = new File(user.getRealfilename());
		FileInputStream fis = new FileInputStream(picture);
		
		byte[] buff = new byte[512];
		int len = 0;
		
		while( (len = fis.read(buff, 0, 512)) != -1 ) {
			sos.write(buff, 0, len);
		}
		
		fis.close();
	}
	
	/**
	* Method : userFormView
	* 작성자 : PC-02
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 등록 화면 요청
	*/
	@RequestMapping(path = "userForm", method = RequestMethod.GET)
	public String userFormView() {
		return "user/userForm";
	}
	
	/**
	* Method : userForm
	* 작성자 : PC-02
	* 변경이력 :
	* @param user
	* @param result
	* @param picture
	* @return
	* Method 설명 : 사용자 등록 요청
	*/
	@RequestMapping(path = "userForm", method = RequestMethod.POST)
	public String userForm(User user, BindingResult result, @RequestPart("picture") MultipartFile picture) {
		
		new UserValidator().validate(user, result);
		
		if(result.hasErrors()) {
			return "user/userForm";
		}else {
			FileInfo fileInfo = FileUtil.getFileInfo(picture.getOriginalFilename());
			
			if(picture.getSize() > 0) {
				try {
					picture.transferTo(fileInfo.getFile());
					user.setFilename(fileInfo.getOrginalFileName());	//파일이름
					user.setRealfilename(fileInfo.getFile().getPath());	//파일경로
					
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			
			int insertCnt = userService.insertUser(user);
			
			if(insertCnt == 1) {
				//redirect
				return "redirect:/user/user?userId=" + user.getUserId();
			}else {
				//forward
				return "user/userForm";
			}
		}
	}
	
	/**
	* Method : userModifyView
	* 작성자 : PC-02
	* 변경이력 :
	* @param model
	* @param userId
	* @return
	* Method 설명 : 사용자 수정 화면 요청
	*/
	@RequestMapping(path = "userModify", method = RequestMethod.GET)
	public String userModifyView(Model model, String userId) {
		model.addAttribute("user", userService.getUser(userId));
		return "user/userModify";
	}
	
	/**
	* Method : userModify
	* 작성자 : PC-02
	* 변경이력 :
	* @param user
	* @param result
	* @param picture
	* @return
	* Method 설명 : 사용자 수정 요청
	*/
	@RequestMapping(path = "userModify", method = RequestMethod.POST)
	public String userModify(User user, BindingResult result, @RequestPart("picture") MultipartFile picture) {
		
		new UserValidator().validate(user, result);
		
		if(result.hasErrors()) {
			return "user/userForm";
		}else {
			FileInfo fileInfo = FileUtil.getFileInfo(picture.getOriginalFilename());
			
			if(picture.getSize() > 0) {
				try {
					picture.transferTo(fileInfo.getFile());
					user.setFilename(fileInfo.getOrginalFileName());	//파일이름
					user.setRealfilename(fileInfo.getFile().getPath());	//파일경로
					
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			
			int insertCnt = userService.updateUser(user);
			
			if(insertCnt == 1) {
				//redirect
				return "redirect:/user/user?userId=" + user.getUserId();
			}else {
				//forward
				return "user/userForm";
			}
		}
	}
	
	@RequestMapping(path = "userPagingListAjaxView")
	public String userPagingListAjaxView() {
		return "user/userPagingListAjaxView";
	}
	
	@RequestMapping(path = "userPagingListAjax", method = RequestMethod.GET)
	public String userPagingListAjax(Model model, Page page) {
		
		model.addAttribute("pageVo", page);
		
		Map<String, Object> resultMap = userService.getUserPagingList(page);
		model.addAllAttributes(resultMap);
		
		return "jsonView";
	}
	
	/**
	* Method : userPagingListHtmlAjax
	* 작성자 : PC-02
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 페이징 리스트의 결과를 html로 생성한다 (jsp)
	*/
	@RequestMapping("userPagingListHtmlAjax")
	public String userPagingListHtmlAjax(@RequestParam(defaultValue = "1") int page,
										 @RequestParam(defaultValue = "10") int pagesize,
										 Model model) {
		
		Page pageVo = new Page(page, pagesize);
		Map<String, Object> resultMap = userService.getUserPagingList(pageVo);
		model.addAllAttributes(resultMap);
		model.addAttribute("pageVo", pageVo);
		
		return "user/userPagingListHtmlAjax";
	}
}
