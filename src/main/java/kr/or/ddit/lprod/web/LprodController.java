package kr.or.ddit.lprod.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.lprod.service.ILprodService;
import kr.or.ddit.prod.repository.IProdDao;

@Controller
public class LprodController {
	
	@Resource(name = "lprodService")
	private ILprodService lprodService;
	
	@Resource(name = "prodDao")
	private IProdDao prodDao;
	
	/**
	* Method : lprodList
	* 작성자 : PC-02
	* 변경이력 :
	* @param model
	* @return
	* Method 설명 : 제품 그룹 리스트 조회
	*/
	@RequestMapping(path = "lprodList", method = RequestMethod.GET)
	public String lprodList(Model model) {
		model.addAttribute("lprodList", lprodService.getLprodList());
		return "lprod/lprodList";
	}
	
	@RequestMapping(path = "lprodPagingList", method = RequestMethod.GET)
	public String lprodPagingList(Model model, @RequestParam(name = "page", defaultValue = "1") int p, 
												@RequestParam(defaultValue = "5") int pagesize) {
		
		Page page = new Page(p, pagesize);
		model.addAttribute("pageVo", page);

		model.addAttribute("resultMap", lprodService.getLprodPagingList(page));
		
		return "lprod/lprodPagingList";
	}
	
	@RequestMapping(path = "prodList", method = RequestMethod.GET)
	public String prodList(Model model, String lprod_gu) {
		model.addAttribute(prodDao.getProdList(lprod_gu));
		return "prod/prodList";
	}
}
