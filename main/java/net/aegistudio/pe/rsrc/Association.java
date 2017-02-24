package net.aegistudio.pe.rsrc;

import java.io.IOException;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.ra.RandomAccessible;
import net.aegistudio.uio.wrap.Container;

public class Association {
	/**
	 * When key <  0: a pointer to resource string structure.
	 * When key >= 0: just a simple id.
	 */
	public Wrapper<Integer> key = Container.int0();
	public WcharString name = new WcharString("");
	public boolean usesName() {
		return key.get() < 0;
	}
	
	public long effectiveKey() {
		return (usesName()? 1l + Integer.MAX_VALUE : 0l) + key.get();
	}
	
	/**
	 * When offset <  0: a pointer to another Directory structure.
	 * When offset >= 0: a pointer to a ResourceData structure.
	 */
	public Wrapper<Integer> offset = Container.int0();
	public boolean nextSubdirectory() {
		return offset.get() < 0;
	}
	public long effectiveOffset() {
		return (nextSubdirectory()? 1l + Integer.MAX_VALUE : 0l) + offset.get();
	}
	
	public final Directory subdirectory = new Directory();
	public final Resource resource = new Resource();
	
	public void translate(int resourceBase, RandomAccessible ra, Translator translator) throws IOException {
		// Translate the key field.
		translator.signed32(key);
		if(usesName()) {
			long currentName = ra.current();
			long effectiveKey = effectiveKey();
			ra.seek(effectiveKey);
			name.translate(translator);
			ra.seek(currentName);
		}
		
		// Translate the value field.
		translator.signed32(offset);
		long currentOffset = ra.current();
		long effectiveOffset = effectiveOffset();
		ra.seek(effectiveOffset);
		if(nextSubdirectory()) 
			subdirectory.translate(resourceBase, ra, translator);
		else
			resource.translate(resourceBase, translator);
		ra.seek(currentOffset);
	}
}
