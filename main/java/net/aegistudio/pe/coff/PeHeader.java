package net.aegistudio.pe.coff;

import java.io.IOException;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.wrap.Container;

public class PeHeader {
	public final Wrapper<Integer> machine = Container.int0();
	
	/**
	 * <b>Warning: </b>modification to section count will be 
	 * overwritten by the size of section table defined in
	 * PortableExecutable class.
	 * 
	 * @see net.aegistudio.pe.PortableExecutable#sections
	 */
	@Deprecated
	public final Wrapper<Integer> cntSection = Container.int0();
	
	public final Wrapper<Integer> timestamp = Container.int0();
	
	public final Wrapper<Integer> ptrSymTable = Container.int0();
	public final Wrapper<Integer> cntSymTable = Container.int0();
	
	public final Wrapper<Integer> optionalHeader = Container.int0();
	public final Wrapper<Integer> characteristics = Container.int0();
	
	public void translate(Translator translator) throws IOException {
		translator.constString("PE\0\0");
		translator.unsigned16(machine);
		translator.unsigned16(cntSection);
		
		translator.signed32(timestamp);
		translator.signed32(ptrSymTable);
		
		translator.signed32(cntSymTable);
		translator.unsigned16(optionalHeader);
		translator.unsigned16(characteristics);
	}
}
