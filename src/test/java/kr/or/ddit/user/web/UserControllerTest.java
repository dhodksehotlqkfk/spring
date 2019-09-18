package kr.or.ddit.user.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.config.test.WebTestConfig;

public class UserControllerTest extends WebTestConfig{

	/**
	* Method : userListTest
	* 작성자 : PC-02
	* 변경이력 :
	* Method 설명 : 사용자 전체 리스트 조회 테스트
	 * @throws Exception 
	*/
	@Test
	public void userListTest() throws Exception {
		/***Given***/

		/***When***/
		mockMvc.perform(get("/user/userList"))
			.andExpect(model().attributeExists("userList"))
			.andExpect(view().name("user/userList"));

		/***Then***/
	}
	
	/**
	* Method : userListOnlyHalfTest
	* 작성자 : PC-02
	* 변경이력 :
	* @throws Exception
	* Method 설명 : 사용자 리스트 절반 조회 테스트
	*/
	@Test
	public void userListOnlyHalfTest() throws Exception {
		/***Given***/

		/***When***/
		mockMvc.perform(get("/user/userListOnlyHalf"))
			.andExpect(model().attributeExists("userList"))
			.andExpect(view().name("user/userListOnlyHalf"));

		/***Then***/
	}
	
	/**
	* Method : userPagingListTest
	* 작성자 : PC-02
	* 변경이력 :
	* @throws Exception
	* Method 설명 : 사용자 페이징 리스트 조회 테스트(파라미터 x)
	*/
	@Test
	public void userPagingListTest() throws Exception {
		/***Given***/

		/***When***/
		mockMvc.perform(get("/user/userPagingList"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/userPagingList"))
			.andExpect(model().attributeExists("userList"))
			.andExpect(model().attributeExists("paginationSize"));

		/***Then***/
	}

}
