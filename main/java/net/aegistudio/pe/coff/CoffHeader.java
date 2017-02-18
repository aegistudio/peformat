package net.aegistudio.pe.coff;

import java.io.IOException;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.wrap.Container;

public class CoffHeader {
	// PE32(0x010b) by default.
	public final Wrapper<Integer> peMagic = new Container<>(0x010b);
	
	public final Wrapper<Byte> majLinker = Container.byte0();
	public final Wrapper<Byte> minLinker = Container.byte0();
	
	public final Wrapper<Long> szCode = Container.long0();
	public final Wrapper<Long> szData = Container.long0();
	public final Wrapper<Long> szUnData = Container.long0();
	
	public final Wrapper<Long> entryPoint = Container.long0();
	public final Wrapper<Long> csBase = Container.long0();
	public final Wrapper<Long> dsBase = Container.long0();
	public final Wrapper<Long> imgBase = Container.long0();
	
	public void translate(Translator translator) throws IOException {
		translator.unsigned16(peMagic);
		translator.signed8(majLinker);
		translator.signed8(minLinker);
		translator.unsigned32(szCode);
		
		translator.unsigned32(szData);
		translator.unsigned32(szUnData);
		
		translator.unsigned32(entryPoint);
		translator.unsigned32(csBase);
		translator.unsigned32(dsBase);
		
		translator.unsigned32(imgBase);
	}
}
