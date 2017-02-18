package net.aegistudio.pe;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.ra.AccessInputStream;
import net.aegistudio.uio.ra.ByteBufferAdapter;
import net.aegistudio.uio.ra.RandomAccessible;
import net.aegistudio.uio.stream.InputTranslator;
import net.aegistudio.uio.strmdbg.DebugInputStream;
import net.aegistudio.uio.strmdbg.StackTranslator;

/*
 * Used while you are to read from test resource asset.
 * 
 * @author aegistudio
 */

public class ExecuteReadBase {
	public static final boolean DEBUG = false;
	
	protected final RandomAccessible accessible;
	protected final Translator translator;
	
	protected ExecuteReadBase(String exe) throws IOException {
		InputStream input = getClass()
				.getResourceAsStream("/" + exe);
		byte[] result = new byte[input.available()];
		if(input.read(result) != result.length)
			throw new EOFException();
		
		accessible = new ByteBufferAdapter(result);
		AccessInputStream accessInput = new AccessInputStream(accessible);
		if(DEBUG) {
			DebugInputStream debugInput = new DebugInputStream(
					accessInput, System.out);
			translator = new StackTranslator(debugInput,
					new InputTranslator(debugInput, "utf8"));
		}
		else translator = new InputTranslator(accessInput, "utf8");
	}
}
