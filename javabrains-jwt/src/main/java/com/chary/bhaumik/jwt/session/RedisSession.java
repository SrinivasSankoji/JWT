package com.chary.bhaumik.jwt.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class RedisSession implements Map<String, UserDetails> 
{
	private Map<String, UserDetails> map = new HashMap<>();

	private  RedisSession instance = null;

	private RedisSession() {
	}

	public RedisSession getInstance() {
		if (instance == null)
			instance = new RedisSession();
		return instance;
	}

	public UserDetails put(String token, UserDetails userDetails) {
		return map.put(token, userDetails);
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public UserDetails get(Object key) {
		return map.get(key);
	}

	@Override
	public UserDetails remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends UserDetails> m) 
	{
	}

	@Override
	public void clear() 
	{
		map.clear();
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public Collection<UserDetails> values() {
		return null;
	}

	@Override
	public Set<Entry<String, UserDetails>> entrySet() {
		return null;
	}

}
