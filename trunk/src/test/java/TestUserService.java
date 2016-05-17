import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chinanetcenter.mapper.itop.mybatis.entry.TUserLogin;
import com.chinanetcenter.mapper.itop.mybatis.entry.TUserLoginExample;
import com.chinanetcenter.mapper.itop.service.TUserLoginService;
import com.github.pagehelper.PageInfo;

/**
 * Junit test spring mybatis
 * @author zhangweican
 * @version 0.01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-mapper-itop.xml","classpath:applicationContext-service.xml","classpath:spring-mybatis.xml" })
public class TestUserService {

	private static final Logger LOGGER = Logger.getLogger(TestUserService.class);

	@Autowired
	private TUserLoginService tUserLoginService;

	@Test
	public void testInsert() {
		delete("testInsert");
		
		TUserLogin userInfo = new TUserLogin();
		userInfo.setUsername("testInsert");
		userInfo.setPassword("testInsert");
		userInfo.setLoginDate(Calendar.getInstance().getTime());
		int result = tUserLoginService.insert(userInfo);
		
		Assert.assertEquals(1, result);
	}
	
	@Test
	public void testTransaction() {
		TUserLogin userInfo = new TUserLogin();
		userInfo.setUsername("testTransaction");
		userInfo.setPassword("testTransaction");
		userInfo.setLoginDate(Calendar.getInstance().getTime());
		try {
			tUserLoginService.insertByRollBack(userInfo);
		} catch (RuntimeException e) {
			//e.printStackTrace();
		}
		
		TUserLoginExample e = new TUserLoginExample();
		e.createCriteria().andUsernameEqualTo("testTransaction");
		TUserLogin t = tUserLoginService.selectOne(e);
		
		Assert.assertTrue(t == null);
	}
	
	@Test
	public void testQueryPage() {
		add("testQueryPage");
		
		PageInfo<TUserLogin> pi = tUserLoginService.selectByExample(null, 3, 10);
		LOGGER.info("PageInfo:" + pi.toString());
		Assert.assertTrue(pi.getTotal() > 0);
		
		delete("testQueryPage");
	}
	@Test
	public void testSelectOne() {
		add("testSelectOne");
		
		TUserLoginExample e = new TUserLoginExample();
		e.createCriteria().andUsernameEqualTo("testSelectOne");
		TUserLogin t = tUserLoginService.selectOne(e);
		LOGGER.info("XXX:" + t.getUsername());
		Assert.assertTrue(t != null);
		
		delete("testSelectOne");
	}
	@Test
	public void testFindAll() {
		add("testFindAll");
		List<TUserLogin> result = tUserLoginService.findAll();
		Assert.assertTrue(result.size() > 0);
		delete("testFindAll");
	}
	@Test
	public void testDelete() {
		add("testDelete");
		TUserLoginExample e = new TUserLoginExample();
		e.createCriteria().andUsernameEqualTo("testDelete");
		int result = tUserLoginService.deleteByExample(e);
		Assert.assertTrue(result == 1);
	}
	
	//==============公共方法================
	public void add(String username){
		TUserLogin userInfo = new TUserLogin();
		userInfo.setUsername(username);
		userInfo.setPassword(username);
		userInfo.setLoginDate(Calendar.getInstance().getTime());
		tUserLoginService.insert(userInfo);
	}
	public void delete(String username){
		TUserLoginExample e = new TUserLoginExample();
		e.createCriteria().andUsernameEqualTo(username);
		tUserLoginService.deleteByExample(e);
	}

}