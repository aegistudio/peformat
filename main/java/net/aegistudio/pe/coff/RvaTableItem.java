package net.aegistudio.pe.coff;

import java.io.IOException;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.wrap.Container;

public class RvaTableItem {
	public Wrapper<Long> address = Container.long0();
	
	public Wrapper<Long> size = Container.long0();
	
	public void translate(Translator translator) throws IOException {
		translator.unsigned32(address);
		translator.unsigned32(size);
	}
}
