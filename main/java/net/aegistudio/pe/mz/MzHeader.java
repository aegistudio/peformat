package net.aegistudio.pe.mz;

import java.io.IOException;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.ra.RandomAccessible;

public interface MzHeader {
	/**
	 * @return a pointer to PE header.
	 */
	public long pePointer();
	
	/**
	 * <p>Read the MZ section (Depending on MzHeader implementation).</p>
	 * 
	 * <p>In many case the MZ portion is not so essential to us (it may be a command that 
	 * prints "This program can not be run in DOS Mode."), so we are not actually required 
	 * to read it. For this case use the MzStubHeader is ideal.</p>
	 * 
	 * <p>If you really want to manipulate the dos section. Please use MzActualHeader 
	 * instead.</p>
	 * 
	 * @param translator
	 * @param ra
	 * @throws IOException
	 */
	public void translate(Translator tran, RandomAccessible ra) throws IOException;
}
