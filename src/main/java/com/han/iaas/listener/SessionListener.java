package com.han.iaas.listener;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.han.iaas.cache.SessionCache;
import com.han.iaas.dao.SessionEntity;
@WebListener
public class SessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("调用session销毁方法");
		HttpSession session = se.getSession();
		CopyOnWriteArrayList<SessionEntity> sessionCache = SessionCache.getSessionCache();
		Iterator<SessionEntity> it = sessionCache.iterator();
		SessionEntity sessionEntity = null;
		while(it.hasNext()) {
			sessionEntity = it.next();
			if(session.getId().equals(sessionEntity.getSession().getId())) {
				it.remove();
				System.out.println("Session以及缓存池快照被销毁");
			}
		}
		System.out.println("session销毁");
	}

}
