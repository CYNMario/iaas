package com.han.iaas.cache;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.han.iaas.dao.SessionEntity;

@Component
public class SessionCache {
	private static CopyOnWriteArrayList<SessionEntity> sessionCopyOnWriteAL = new CopyOnWriteArrayList<SessionEntity>();
	/**
	 *  
	 * @param userName userName
	 * @param session session
	 */
	public static void addInSessionCache(String userName , HttpSession session) {
		SessionEntity se = new SessionEntity();
		se.setUserName(userName);
		se.setSession(session);
		System.out.println("se:" + se);
		sessionCopyOnWriteAL.add(se);
	}
	/**
	 * 
	 * @return sessionCache
	 */
	public static CopyOnWriteArrayList<SessionEntity> getSessionCache(){
		return sessionCopyOnWriteAL;
	}
	/**
	 * 	将session从缓存中移除
	 * @param userName
	 * @return boolean
	 */
	public static boolean removeInSessionCache(String userName) {
		Iterator<SessionEntity> it = sessionCopyOnWriteAL.iterator();
		SessionEntity temp = null;
		boolean isRemoved = false;
		while(it.hasNext()) {
			temp = it.next();
			if(userName.equals(temp.getUserName())) {
				it.remove();
				isRemoved = true;
				break;
			}
		}
		return isRemoved;
	} 
}