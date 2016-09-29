package sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;


public class UnmodifiableSetMixinTest {
	static final String EXPECTED_JSON = "[\"java.util.Collections$UnmodifiableSet\",[]]";

	ObjectMapper mapper;

	@Before
	public void setup() {
		mapper = new ObjectMapper();
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
		mapper.addMixIn(Collections.unmodifiableSet(Collections.<String>emptySet()).getClass(), UnmodifiableSetMixin.class);
	}

	@Test
	public void write() throws Exception {
		String json = mapper.writeValueAsString(Collections.unmodifiableSet(Collections.emptySet()));
		assertThat(json).isEqualTo(EXPECTED_JSON);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void read() throws Exception {
		Set<String> foo = mapper.readValue(EXPECTED_JSON, Set.class);
		assertThat(foo).isEmpty();
	}
}
