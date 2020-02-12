package com.chary.bhaumik.jwt.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/** 
 * @author Srinivas Sankoji
 */
@Component
public class RedisSession  implements Map<String, Map<Long,Map<String, UserDetails>>>
{
	private Map<String, Map<Long,Map<String, UserDetails>>> session = new HashMap<>();
	
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
		return session.size()>0 ? true : false;
	}

	@Override
	public boolean containsKey(Object key)
	{
		return session.containsKey(key)? true: false;
	}

	@Override
	public boolean containsValue(Object value) 
	{
		return session.containsValue(value) ? true: false;
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
	public Collection<Map<Long, Map<String, UserDetails>>> values() 
	{
		return session.values();
	}

	@Override
	public Map<Long, Map<String, UserDetails>> get(Object key) 
	{
		return session.get(key);
	}
	
	@Override
	public void putAll(Map<? extends String, ? extends Map<Long, Map<String, UserDetails>>> m) 
	{
		
	}
	
	@Override
	public Set<Entry<String, Map<Long, Map<String, UserDetails>>>> entrySet()
	{
		return session.entrySet();
	}

	@Override
	public Map<Long, Map<String, UserDetails>> remove(Object key) 
	{
		return session.remove(key);
	}

	@Override
	public Map<Long, Map<String, UserDetails>> put(String key, Map<Long, Map<String, UserDetails>> value) 
	{
		return session.put(key, value);
	}
	
	
	public String getUserName(String userName)
	{
		return session.entrySet().stream()
		.filter(map -> map.getKey().equalsIgnoreCase(userName))
		.map(Map.Entry::getKey)
		.findFirst().orElse(null);
	}
	
	public Long getLastRequestofUser(String userName)
	{
		 Optional<Long> result=session.entrySet().stream()
		.filter(map -> map.getKey().equalsIgnoreCase(userName))
		.map(Map.Entry::getValue)
		.flatMap(mapper -> mapper.entrySet().stream())
		.map(Map.Entry::getKey)
		.findFirst();
		 return Long.parseLong((""+result.get()).substring(0, 10));
	}
	
	public String getJWTToken(String userName)
	{
		 return session.entrySet().stream()
		 .filter(entry -> entry.getKey().equalsIgnoreCase(userName))
		 .map(Map.Entry::getValue)
		 .flatMap(mapper -> mapper.entrySet().stream())
		 .map(Map.Entry::getValue)
		 .flatMap(tokenmapper -> tokenmapper.entrySet().stream())
		 .map(Map.Entry::getKey)
		 .findFirst().orElse(null);
	}
	
	public UserDetails getUserDetails(String userName)
	{
		return session.entrySet().stream()
		.filter(entry -> entry.getKey().contains(userName))
		.map(Map.Entry::getValue)
		.flatMap(mapper -> mapper.entrySet().stream())
		.map(Map.Entry::getValue)
		.flatMap(tokenmapper -> tokenmapper.entrySet().stream())
		.map(Map.Entry::getValue)
		.findFirst().orElse(null);
	}
	
	public List<String> getActiveUsers()
	{
		return session.entrySet().stream()
		.map(Map.Entry::getKey)
		.collect(Collectors.toList());
	}
	

}
