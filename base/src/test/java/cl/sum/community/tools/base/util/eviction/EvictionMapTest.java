package cl.sum.community.tools.base.util.eviction;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import org.junit.Test;

public class EvictionMapTest {
	
	private static final String KEY="KEY";

	@Test
	public void basicEviction() throws Exception {
		long validity = 1000l;
		EvictionMap<String, String> map = new EvictionMap<>(validity);
		
		map.put(KEY, "");
		await().atMost(2, SECONDS).until(()->{
			return map.get(KEY)==null;
		});
		

	}
	
	@Test
	public void basic2Eviction() throws Exception {
		long validity = 3000l;
		EvictionMap<String, String> map = new EvictionMap<>(validity);

		map.put(KEY, "");
		await().atMost(2, SECONDS).until(()->{
			return map.get(KEY)!=null;
		});
		

	}

}
