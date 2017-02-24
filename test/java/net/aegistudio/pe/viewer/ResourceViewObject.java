package net.aegistudio.pe.viewer;

import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import net.aegistudio.pe.coff.Section;
import net.aegistudio.pe.rsrc.Association;
import net.aegistudio.pe.rsrc.Directory;
import net.aegistudio.pe.rsrc.WinResourceType;
import net.aegistudio.uio.Translator;
import net.aegistudio.uio.ra.AccessInputStream;
import net.aegistudio.uio.ra.ByteBufferAdapter;
import net.aegistudio.uio.ra.RandomAccessible;
import net.aegistudio.uio.stream.InputTranslator;

public class ResourceViewObject extends SectionViewObject {
	public final Directory root = new Directory();
	public final ByteBufferAdapter resourceAdapter;
	
	public ResourceViewObject(int winstdRsrcOffset, Section section, RandomAccessible ra) throws IOException {
		super(section, ra);
		byte[] resourceData = new byte[section.size.get()];
		resourceAdapter = new ByteBufferAdapter(resourceData);
		
		long current = ra.current();
		ra.seek(section.pointer.get());
		Translator translator = new InputTranslator(
				new AccessInputStream(ra), "utf8");
		translator.block(section.size.get(), resourceData);
		ra.seek(current);
		
		Translator resourceTranslator = new InputTranslator(
				new AccessInputStream(resourceAdapter), "utf8");
		root.translate(winstdRsrcOffset, resourceAdapter, resourceTranslator);
	}
	
	public void subNode(DefaultMutableTreeNode node) {
		root.names.forEach(association -> 
			this.buildAssociation(null, node, association));
		
		root.ids.forEach(association -> {
			String name; WinResourceType type = null;
			if(association.usesName()) name = association.name.get();
			else {
				int ordinal = association.key.get();
				if(ordinal < WinResourceType.values().length) {
					type = WinResourceType.values()[ordinal];
					name = type.toString();
				}
				else name = "USER_RESOURCE #" + ordinal;
			}
			
			DefaultMutableTreeNode idNode = new DefaultMutableTreeNode(name);
			node.add(idNode);
			if(association.nextSubdirectory())
				buildNode(type, idNode, association.subdirectory);
		});
	}
	
	public void buildNode(WinResourceType resourceType, 
			DefaultMutableTreeNode current, Directory directory) {
		directory.names.forEach(association -> 
			this.buildAssociation(resourceType, current, association));
		
		directory.ids.forEach(association -> 
			this.buildAssociation(resourceType, current, association));
	}
	
	public void buildAssociation(WinResourceType resourceType, 
			DefaultMutableTreeNode current, Association association) {
		
		String name = association.usesName()? association.name.get() 
				: Integer.toString(association.key.get());
		
		if(association.nextSubdirectory()) {
			DefaultMutableTreeNode associationNode = new DefaultMutableTreeNode(name);
			current.add(associationNode);
			buildNode(resourceType, associationNode, association.subdirectory);
		}
		else {
			DefaultMutableTreeNode resourceNode = new DefaultMutableTreeNode(
					new ResourceDataViewObject(resourceType, name, 
							resourceAdapter, association.resource));
			current.add(resourceNode);
		}
	}
}
