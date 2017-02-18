package net.aegistudio.pe.mz;

import java.io.IOException;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;
import net.aegistudio.uio.ra.RandomAccessible;
import net.aegistudio.uio.wrap.Container;

/**
 * Actually the content of MZ header and MZ stub are
 * meaningleass for us, so we just verify magic number
 * and read the coff pointer in this case.
 * 
 * 
 * @author aegistudio
 */

public class MzStubHeader implements MzHeader {
	public final Wrapper<Long> ptrPeHeader = Container.long0();
	
	public void translate(Translator translator, RandomAccessible ra) throws IOException {
		ra.seek(0l);
		translator.constString("MZ");
		
		ra.seek(0x3cl);
		translator.unsigned32(ptrPeHeader);
	}

	@Override
	public long pePointer() {
		return ptrPeHeader.get();
	}
}
