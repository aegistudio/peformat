package net.aegistudio.pe.viewer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.aegistudio.pe.PortableExecutable;
import net.aegistudio.pe.coff.RvaTable;
import net.aegistudio.pe.coff.Section;
import net.aegistudio.pe.coff.Win32Header;
import net.aegistudio.uio.ra.RandomAccessible;

public class PeTableModel extends DefaultTreeModel {
	private static final long serialVersionUID = 1L;
	
	protected final DefaultMutableTreeNode peFile;
	
	public PeTableModel(PortableExecutable pe, RandomAccessible ra, File file) throws IOException {
		super(new DefaultMutableTreeNode(
				new PeFileViewObject(file, pe)));
		
		this.peFile = (DefaultMutableTreeNode) super.getRoot();
		for(int i = 0; i < pe.sections.size(); i ++) {
			Section section = pe.sections.get(i);
			SectionViewObject sectionVo = null;
			switch(section.asciiName.get()) {
				case ".text":
					sectionVo = new SectionViewObject(section, ra) {
						protected void addView(List<Entry> views, byte[] data) {
							super.addView(views, data);
							views.add(new Entry("Disassembly", new DisassemblyView(data)));
						}
					};
				break;
				case ".rsrc":
					if(pe.coff instanceof Win32Header) {
						long resourceStdAddress = ((Win32Header)pe.coff)
							.rvaTable.get(RvaTable.RESOURCE.ordinal()).address.get();
						sectionVo = new ResourceViewObject(
							(int)resourceStdAddress, section, ra);
						break;
					}
				default:
					sectionVo = new SectionViewObject(section, ra);
			}
			DefaultMutableTreeNode sectionNode = 
					new DefaultMutableTreeNode(sectionVo);
			sectionVo.subNode(sectionNode);
			this.peFile.add(sectionNode);
		}
	}
}
