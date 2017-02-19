package net.aegistudio.pe.viewer;

import java.io.File;

import net.aegistudio.pe.PortableExecutable;

public class PeFileViewObject {
	public final File file;
	public final PortableExecutable pe;
	public PeFileViewObject(File file, PortableExecutable pe) {
		this.file = file;
		this.pe = pe;
	}
	
	public String toString() {
		return this.file.getName();
	}
}
