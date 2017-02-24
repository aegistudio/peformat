package net.aegistudio.pe.rsrc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.aegistudio.uio.CorruptException;
import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.ra.RandomAccessible;
import net.aegistudio.uio.wrap.Container;

public class Directory {
	public Wrapper<Long> characteristics = Container.long0();
	
	public Wrapper<Integer> timestamp = Container.int0();
	
	public Wrapper<Short> majorVersion = Container.short0();
	
	public Wrapper<Short> minorVersion = Container.short0();
	
	public List<Association> names = new ArrayList<>();
	
	public List<Association> ids = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public void translate(int resourceBase, 
			RandomAccessible ra, Translator translator) throws IOException, CorruptException {
		translator.unsigned32(characteristics);
		translator.signed32(timestamp);
		translator.signed16(majorVersion);
		translator.signed16(minorVersion);
		
		Wrapper<Integer> nameEntryCount = new Container<>(names.size());
		translator.unsigned16(nameEntryCount);
		
		Wrapper<Integer> idEntryCount = new Container<>(ids.size());
		translator.unsigned16(idEntryCount);
		
		translator.array(nameEntryCount.get(), names, Association::new, 
				(entry, translate) -> entry.translate(resourceBase, ra, translate));
		
		translator.array(idEntryCount.get(), ids, Association::new, 
				(entry, translate) -> entry.translate(resourceBase, ra, translate));
	}
}
