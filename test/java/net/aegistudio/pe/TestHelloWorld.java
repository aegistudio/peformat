package net.aegistudio.pe;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

import net.aegistudio.pe.coff.Win32Header;
import net.aegistudio.pe.mz.MzStubHeader;

public class TestHelloWorld extends ExecuteReadBase {
	public TestHelloWorld() throws IOException {
		super("helloworld.exe");
	}

	public @Test void mzStubHeader() throws IOException {
		accessible.seek(0l);
		MzStubHeader mzStubHeader = new MzStubHeader();
		mzStubHeader.translate(translator, accessible);
		
		assertEquals(mzStubHeader.pePointer(), 0x0e0l);
	}
	
	@SuppressWarnings("deprecation")
	public @Test void full() throws IOException {
		accessible.seek(0l);
		PortableExecutable pe = new PortableExecutable();
		pe.translate(translator, accessible);
		
		assertEquals(pe.pe.cntSection.get(), 3);
		assertEquals(pe.pe.characteristics.get(), 0x010f);
		
		assertEquals((int)pe.coff.majLinker.get(), 6);
		assertEquals((int)pe.coff.minLinker.get(), 0);
		
		assertEquals(pe.coff.szCode.get(), 0x0a000l);
		assertEquals(pe.coff.szData.get(), 0x07000l);
		assertEquals(pe.coff.szUnData.get(), 0l);
		
		assertEquals(pe.coff.entryPoint.get(), 0x0481bl);
		assertEquals(pe.coff.csBase.get(), 0x01000l);
		assertEquals(pe.coff.dsBase.get(), 0x0b000l);
		assertEquals(pe.coff.imgBase.get(), 0x0400000l);
		
		assertTrue(pe.coff instanceof Win32Header);
		Win32Header win32 = (Win32Header) pe.coff;
		assertEquals(win32.sectionAlign.get(), 0x1000);
		assertEquals(win32.fileAlign.get(), 0x1000);
		
		assertEquals(pe.sections.get(0).asciiName.get(), ".text");
		assertEquals(pe.sections.get(1).asciiName.get(), ".rdata");
		assertEquals(pe.sections.get(2).asciiName.get(), ".data");
	}
}
