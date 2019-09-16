package kr.or.ddit.lprod.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.lprod.dao.ILprodDao;
import kr.or.ddit.lprod.model.Lprod;

@Service
public class LprodService implements ILprodService{

	private static final Logger logger = LoggerFactory.getLogger(LprodService.class);
	
	@Resource(name = "lprodDao")
	private ILprodDao lprodDao;
	
	public LprodService() {
	}
	
	public LprodService(ILprodDao lprodDao) {
		this.lprodDao = lprodDao;
	}
	
	/**
	* Method : getLprodList
	* 작성자 : SEM-PC
	* 변경이력 :
	* @return
	* Method 설명 : 제품 그룹 리스트 조회
	*/
	@Override
	public List<Lprod> getLprodList() {
		return lprodDao.getLprodList();
	}

	/**
	* Method : getLprodPagingList
	* 작성자 : SEM-PC
	* 변경이력 :
	* @param sqlSession
	* @param page
	* @return
	* Method 설명 : 제품 그룹 페이징 리스트 조회
	*/
	@Override
	public Map<String, Object> getLprodPagingList(Page page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Lprod> lprodList = lprodDao.getLprodPagingList(page);
		int totalCnt = lprodDao.getLprodTotalCnt();
		
		resultMap.put("lprodList", lprodList);
		resultMap.put("paginationSize", (int)Math.ceil( (double)totalCnt / page.getPagesize()));
		
		return resultMap;
	}

}
