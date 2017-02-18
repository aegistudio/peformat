package net.aegistudio.pe.coff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.wrap.Container;

public class Win32Header extends CoffHeader {
	public final Wrapper<Integer> sectionAlign = Container.int0();
	public final Wrapper<Integer> fileAlign = Container.int0();
	
	public final Wrapper<Short> majOs = Container.short0();
	public final Wrapper<Short> minOs = Container.short0();
	
	public final Wrapper<Short> majImage = Container.short0();
	public final Wrapper<Short> minImage = Container.short0();
	
	public final Wrapper<Short> majSubSystem = Container.short0();
	public final Wrapper<Short> minSubSystem = Container.short0();
	public final Wrapper<Integer> win32Version = Container.int0();
	
	public final Wrapper<Long> imageSize = Container.long0();
	public final Wrapper<Long> headerSize = Container.long0();
	
	public final Wrapper<Integer> checkSum = Container.int0();
	public final Wrapper<Short> subsystem = Container.short0();
	
	public final Wrapper<Short> dllCharacteristics = Container.short0();
	
	public final Wrapper<Long> stackReserve = Container.long0();
	public final Wrapper<Long> stackCommit = Container.long0();
	
	public final Wrapper<Long> heapReserve = Container.long0();
	public final Wrapper<Long> heapCommit = Container.long0();
	
	public final Wrapper<Integer> loaderFlag = Container.int0();
	
	public final List<RvaTableItem> rvaTable = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public void translate(Translator translator) throws IOException {
		super.translate(translator);
		
		translator.signed32(sectionAlign);
		translator.signed32(fileAlign);
		
		translator.signed16(majOs);
		translator.signed16(minOs);
		translator.signed16(majImage);
		translator.signed16(minImage);
		
		translator.signed16(majSubSystem);
		translator.signed16(minSubSystem);
		translator.signed32(win32Version);
		
		translator.unsigned32(imageSize);
		translator.unsigned32(headerSize);
		
		translator.signed32(checkSum);
		translator.signed16(subsystem);
		translator.signed16(dllCharacteristics);
		
		translator.unsigned32(stackReserve);
		translator.unsigned32(stackCommit);
		
		translator.unsigned32(heapReserve);
		translator.unsigned32(heapCommit);
		
		translator.signed32(loaderFlag);
		
		Wrapper<Integer> rvaItemCount = new Container<>(rvaTable.size());
		translator.signed32(rvaItemCount);
		translator.array(rvaItemCount.get(), rvaTable, 
				RvaTableItem::new, RvaTableItem::translate);
	}
}
