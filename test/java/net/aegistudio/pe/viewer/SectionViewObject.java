package net.aegistudio.pe.viewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.tree.MutableTreeNode;

import net.aegistudio.pe.coff.Section;
import net.aegistudio.uio.ra.RandomAccessible;

public class SectionViewObject implements ViewObject {
	public final Section section;
	public final RandomAccessible ra;
	
	public SectionViewObject(Section section, RandomAccessible ra) {
		this.section = section;
		this.ra = ra;
	}
	
	public String toString() {
		return section.asciiName.get();
	}
	
	public void subNode(MutableTreeNode node) {
		
	}

	@Override
	public List<Entry> openView() {
		try {
			List<Entry> views = new ArrayList<>();
			byte[] data = section.open(ra);
			views.add(new Entry("Section", new SectionView(section)));
			views.add(new Entry("Hex", new HexDumpView(data, 16, 2)));
			return views;
		}
		catch(Exception e) {
			return Arrays.asList();
		}
	}
}
