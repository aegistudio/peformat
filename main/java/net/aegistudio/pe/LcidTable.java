package net.aegistudio.pe;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class LcidTable {
	public static LcidTable instance = new LcidTable();
	private Map<Integer, String> lcid = new TreeMap<>();
	
	private LcidTable() throws AssertionError {
		try {
			InputStream lcidInput = getClass().getResourceAsStream("/lcid");
			DocumentBuilder lcidDomBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			
			Document lcidDom = lcidDomBuilder.parse(lcidInput);
			Node tbody = getChildWithoutSpace(lcidDom).get(0);
			
			List<Node> lcidEntries = getChildWithoutSpace(tbody);
			for(int i = 1; i < lcidEntries.size(); i ++) {
				Node tableRow = lcidEntries.get(i);	// <tr>
				List<Node> subNodes = getChildWithoutSpace(tableRow);
				Node tableDataKey = subNodes.get(0);	// <td>
				Node tableDataValue = subNodes.get(1);	// <td>
				
				String key = getChildWithoutSpace(tableDataKey).get(0).getTextContent();
				String value = getChildWithoutSpace(tableDataValue).get(0).getTextContent();
				
				lcid.put(Integer.parseInt(key.substring(2), 16) + 1024, value);
			}
		}
		catch(Exception e) {
			throw new AssertionError(e);
		}
	}
	
	private List<Node> getChildWithoutSpace(Node node) {
		NodeList nodeList = node.getChildNodes();
		List<Node> result = new ArrayList<>();
		for(int i = 0; i < nodeList.getLength(); i ++) {
			Node current = nodeList.item(i);
			if(current.getNodeType() == Node.ELEMENT_NODE)
				result.add(current);
		}
		return result;
	}
	
	public String getLanguageName(int lcidValue) {
		return lcid.get(lcidValue);
	}
	
	public Locale getLangugage(int lcidValue) {
		String localeName = getLanguageName(lcidValue);
		return localeName != null? new Locale(localeName) : null;
	}
}
