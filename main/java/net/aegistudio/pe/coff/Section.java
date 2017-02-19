package net.aegistudio.pe.coff;

import java.io.IOException;

import net.aegistudio.uio.CorruptException;
import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.ra.AccessInputStream;
import net.aegistudio.uio.ra.AccessOutputStream;
import net.aegistudio.uio.ra.RandomAccessible;
import net.aegistudio.uio.wrap.Container;
import net.aegistudio.uio.wrap.ZeroPaddingString;

public class Section {
	public final Wrapper<String> name = Container.string0();
	public final Wrapper<String> asciiName = new ZeroPaddingString(8, name);
	
	public Wrapper<Long> rvaSize = Container.long0();
	public Wrapper<Long> rvaBase = Container.long0();
	
	public Wrapper<Integer> size = Container.int0();
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
		
		translator.signed32(size);
		translator.unsigned32(pointer);
		
		translator.unsigned32(ptrLineTable);
		translator.unsigned32(ptrReloc);
		
		translator.unsigned16(cntReloc);
		translator.unsigned16(cntLineTable);
		
		translator.signed32(characteristic);
	}
	
	@SuppressWarnings("resource")
	public byte[] open(RandomAccessible ra) throws IOException {
		byte[] buffer = new byte[size.get()];
		ra.seek(pointer.get());
		CorruptException.check(size.get(),
				new AccessInputStream(ra).read(buffer));
		return buffer;
	}
	
	@SuppressWarnings("resource")
	public void save(RandomAccessible ra, byte[] output) throws IOException {
		ra.seek(pointer.get());
		new AccessOutputStream(ra).write(
				output, 0, size.get());;
	}
}
