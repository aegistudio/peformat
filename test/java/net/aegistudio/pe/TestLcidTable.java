package net.aegistudio.pe;

import static org.junit.Assert.*;
import java.util.Locale;
import org.junit.Test;

public class TestLcidTable {
	public @Test void test() {
		Locale expectEnglish = LcidTable.instance.getLangugage(1033);
		assertEquals(expectEnglish, Locale.ENGLISH);
	}
}
