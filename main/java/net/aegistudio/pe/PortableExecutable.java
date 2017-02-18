package net.aegistudio.pe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.aegistudio.pe.coff.CoffHeader;
import net.aegistudio.pe.coff.PeHeader;
import net.aegistudio.pe.coff.Section;
import net.aegistudio.pe.coff.Win32Header;
import net.aegistudio.pe.mz.MzHeader;
import net.aegistudio.pe.mz.MzStubHeader;
import net.aegistudio.uio.Translator;
import net.aegistudio.uio.ra.RandomAccessible;

public class PortableExecutable {
	public MzHeader mz = new MzStubHeader();
	public PeHeader pe = new PeHeader();	
	public CoffHeader coff = new Win32Header();
	
	public List<Section> sections = new ArrayList<>();
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void translate(Translator translator, RandomAccessible ra) throws IOException {
		// Read mz header and mz stub (if configured).
		mz.translate(translator, ra);
		
		// Seek pointer to coff header and read.
		ra.seek(mz.pePointer());
		pe.cntSection.set(sections.size());
		pe.translate(translator);
		coff.translate(translator);
		
		// Read the section table.
		translator.array(pe.cntSection.get(), sections, 
				Section::new, Section::translate);
	}
}
