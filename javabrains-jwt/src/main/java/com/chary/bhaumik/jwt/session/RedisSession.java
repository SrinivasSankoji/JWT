package com.chary.bhaumik.jwt.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/** 
 * @author Srinivas Sankoji
 */
@Component
public class RedisSession implements Map<String, Map<String,UserDetails>> 
{
	private Map<String, Map<String,UserDetails>> session = new HashMap<>();
	
	private  RedisSession instance = null;
	
	private RedisSession() {
	}

	public RedisSession getInstance() {
		if (instance == null)
			instance = new RedisSession();
		return instance;
	}

	@Override
	public int size() 
	{
		return session.size();
	}

	@Override
	public boolean isEmpty() 
	{
		return session.size() > 0 ? false : true;
	}

	@Override
	public boolean containsKey(Object key) 
	{
		return session.containsKey(key) ? true : false;
	}

	@Override
	public boolean containsValue(Object value) 
	{
		return session.containsValue(value) ? true : false;
	}

	@Override
	public Map<String, UserDetails> get(Object key) 
	{
		return session.get(key);
	}
	
	@Override
	public Map<String, UserDetails> put(String key, Map<String, UserDetails> value) 
	{
		return session.put(key, value);
	}

	@Override
	public Map<String, UserDetails> remove(Object key) 
	{
		return session.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Map<String, UserDetails>> m)
	{
		
	}

	@Override
	public void clear()
	{
		session.clear();
	}

	@Override
	public Set<String> keySet()
	{
		return session.keySet();
	}

	@Override
	public Collection<Map<String, UserDetails>> values() 
	{
		return session.values();
	}

	@Override
	public Set<Entry<String, Map<String, UserDetails>>> entrySet() 
	{
		return session.entrySet();
	}
	
	public UserDetails getUserDetails(String key)
	{
		return session.entrySet().stream().filter(entry -> entry.getKey().contains(key))
		.map(Map.Entry::getValue).flatMap(mapper -> mapper.entrySet().stream()).map(Map.Entry::getValue)
		.findFirst().orElse(null);
	}
	
	public String getAccessToken(String key)
	{
		return session.entrySet().stream().filter(entry -> entry.getKey().contains(key))
		.map(Map.Entry::getValue).flatMap(mapper -> mapper.entrySet().stream()).map(Map.Entry::getKey)
		.findFirst().orElse("Invalid Token");
	}
	
	public String getUserName(String key)
	{
		return session.entrySet().stream()
		.filter(map -> map.getKey().contains(key))
		.map(Map.Entry::getKey)
		.findFirst().orElse(null);
	}
	
	public long getLastReuestofUser(String key)
	{
		 OptionalLong result=session.entrySet().stream()
		.filter(map -> map.getKey().contains(key))
		.map(Map.Entry::getKey)
		.mapToLong(mapper -> Long.parseLong(mapper.split("|")[1]))
		.findFirst();
		 return result.getAsLong();
	}
	
}
