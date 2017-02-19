package net.aegistudio.pe.viewer;

import javax.swing.JPanel;

import net.aegistudio.pe.coff.Section;

public class SectionView extends JPanel {
	private static final long serialVersionUID = 1L;

	public SectionView(Section section) {
		super.add(new Information("Section", section.asciiName.get()));
		
		JPanel rva = new JPanel();
		rva.add(new Information("Base<br>(RVA)", 
				Long.toHexString(section.rvaBase.get())));
		rva.add(new Information("Size<br>(RVA)", 
				Long.toHexString(section.rvaSize.get())));
		super.add(rva);
		
		JPanel physics = new JPanel();
		physics.add(new Information("Base", 
				Long.toHexString(section.pointer.get())));
		physics.add(new Information("Size", 
				Long.toHexString(section.size.get())));
		super.add(physics);
	}
}
