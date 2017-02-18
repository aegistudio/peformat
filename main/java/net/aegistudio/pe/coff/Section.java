package net.aegistudio.pe.coff;

import java.io.IOException;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.wrap.Container;
import net.aegistudio.uio.wrap.ZeroPaddingString;

public class Section {
	public final Wrapper<String> name = Container.string0();
	public final Wrapper<String> asciiName = new ZeroPaddingString(8, name);
	
	public Wrapper<Long> rvaSize = Container.long0();
	public Wrapper<Long> rvaBase = Container.long0();
	
	public Wrapper<Long> size = Container.long0();
	public Wrapper<Long> pointer = Container.long0();
	
	public Wrapper<Long> ptrReloc = Container.long0();	
	public Wrapper<Long> ptrLineTable = Container.long0();
	
	public Wrapper<Integer> cntReloc = Container.int0();
	public Wrapper<Integer> cntLineTable = Container.int0();
	
	public Wrapper<Integer> characteristic = Container.int0();
	
	public void translate(Translator translator) throws IOException {
		translator.string(8, name);
		
		translator.unsigned32(rvaSize);
		translator.unsigned32(rvaBase);
		
		translator.unsigned32(size);
		translator.unsigned32(pointer);
		
		translator.unsigned32(ptrLineTable);
		translator.unsigned32(ptrReloc);
		
		translator.unsigned16(cntReloc);
		translator.unsigned16(cntLineTable);
		
		translator.signed32(characteristic);
	}
}
