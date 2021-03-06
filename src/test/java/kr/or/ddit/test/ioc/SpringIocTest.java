package kr.or.ddit.test.ioc;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:kr/or/ddit/spring/ioc/application-context-ioc-test.xml")
public class SpringIocTest {
	
	@Resource(name="dbProperty")
	private DbProperty dbProperty;
	
	@Resource(name="dbPropertySetter")
	private DbProperty dbPropertySetter;

	/**
	* Method : propertyPlaceHoldertest
	* 작성자 : PC-02
	* 변경이력 :
	* Method 설명 : <context:property-place> 테스트
	*/
	@Test
	public void propertyPlaceHoldertest() {
		/***Given***/

		/***When***/

		/***Then***/
		assertNotNull(dbProperty);
		assertEquals("CJH", dbProperty.getUser());
		assertEquals("java", dbProperty.getPass());
		assertEquals("oracle.jdbc.driver.OracleDriver", dbProperty.getDriver());
		assertEquals("jdbc:oracle:thin:@localhost:1521:XE", dbProperty.getUrl());

		assertNotNull(dbPropertySetter);
		assertEquals("CJH", dbPropertySetter.getUser());
		assertEquals("java", dbPropertySetter.getPass());
		assertEquals("oracle.jdbc.driver.OracleDriver", dbPropertySetter.getDriver());
		assertEquals("jdbc:oracle:thin:@localhost:1521:XE", dbPropertySetter.getUrl());
		
	}
	
}
