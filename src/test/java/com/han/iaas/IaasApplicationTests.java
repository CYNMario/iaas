package com.han.iaas;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.han.iaas.cache.SessionCache;
import com.han.iaas.dao.SessionEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IaasApplicationTests {
	@Autowired
	private CopyOnWriteArrayList<HttpSession> sessionCoa;
    @Test
    public void contextLoads() {
//    	SessionEntity se = new SessionEntity();
//    	System.out.println("123456789"+se);
    }

}
