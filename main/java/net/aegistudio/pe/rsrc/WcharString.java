package net.aegistudio.pe.rsrc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.aegistudio.uio.CorruptException;
import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.wrap.Container;

public class WcharString extends Container<String> {
	public WcharString(String t) {
		super(t);
	}

	@SuppressWarnings("unchecked")
	public void translate(Translator translator) throws CorruptException, IOException {
		Wrapper<Integer> wstrlen = new Container<>(super.get().length());
		List<Wrapper<Short>> wcharptr = new ArrayList<>();
		for(int i = 0; i < super.get().length(); i ++) {	
			char current = super.get().charAt(i);
			wcharptr.add(new Container<>((short)current));
		}
		
		translator.unsigned16(wstrlen);
		translator.array(wstrlen.get(), wcharptr, Container::short0, 
				Translator.reverse(Translator::signed16));
		
		StringBuilder charptr = new StringBuilder();
		for(int i = 0; i < wstrlen.get(); i ++) {
			short shortValue = wcharptr.get(i).get();
			charptr.append((char)shortValue);
		}
		
		super.set(new String(charptr));
	}
}
