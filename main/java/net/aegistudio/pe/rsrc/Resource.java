package net.aegistudio.pe.rsrc;

import java.io.IOException;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.ra.RandomAccessible;
import net.aegistudio.uio.wrap.Container;
import net.aegistudio.uio.wrap.Transform;

public class Resource {
	public Wrapper<Integer> offset = Container.int0();
	
	public Wrapper<Integer> size = Container.int0();
	
	public Wrapper<Integer> codepage = Container.int0();
	
	public Wrapper<Integer> reserved = Container.int0();
	
	public void translate(int resourceBase, Translator translator) throws IOException {
		
		Wrapper<Integer> rvaOffset = new Transform<>(offset, 
				phy -> phy + resourceBase, rva -> rva - resourceBase);
		translator.signed32(rvaOffset);
		
		translator.signed32(size);
		translator.signed32(codepage);
		translator.signed32(reserved);
	}
	
	public void data(RandomAccessible ra, Translator translator, byte[] data) throws IOException {
		ra.seek(offset.get());
		translator.block(size.get(), data);
	}
}
