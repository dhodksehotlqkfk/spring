package kr.or.ddit.lprod.model;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lprod implements HttpSessionBindingListener{
	
	private static final Logger logger = LoggerFactory.getLogger(Lprod.class);
	
	private int lprod_id;
	private String lprod_gu;
	private String lprod_nm;
	
	public int getLprod_id() {
		return lprod_id;
	}
	public void setLprod_id(int lprod_id) {
		this.lprod_id = lprod_id;
	}
	public String getLprod_gu() {
		return lprod_gu;
	}
	public void setLprod_gu(String lprod_gu) {
		this.lprod_gu = lprod_gu;
	}
	public String getLprod_nm() {
		return lprod_nm;
	}
	public void setLprod_nm(String lprod_nm) {
		this.lprod_nm = lprod_nm;
	}
	@Override
	public String toString() {
		return "LprodVo [lprod_id=" + lprod_id + ", lprod_gu=" + lprod_gu + ", lprod_nm=" + lprod_nm + "]";
	}
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		logger.debug("value bound");
	}
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		logger.debug("value bound");
	}
	
}
