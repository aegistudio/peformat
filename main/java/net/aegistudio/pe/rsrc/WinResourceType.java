package net.aegistudio.pe.rsrc;

/**
 * https://msdn.microsoft.com/en-us/library/ms648009%28v=vs.85%29.aspx
 * 
 * The resource type are defined with prefix RT_*.
 * 
 * @author aegistudio
 */

public enum WinResourceType {
	UNKNOWN_0,
	CURSOR,
	BITMAP,
	ICON,
	MENU,
	DIALOG,
	STRING,
	FONT_DIR,
	FONT,
	ACCELERATOR,
	RCDATA,
	MESSAGE_TABLE,
	
	// DIFFERENCE = 11
	GROUP_CURSOR,
	GROUP_ICON,
	
	UNKNOWN_14,
	UNKNOWN_15,
	
	VERSION,
	DIALOG_INCLUDE,
	
	UNKNOWN_18,
	
	// WINVER > 0x0400
	PLUG_PLAY,
	VXD,
	ANICURSOR,
	ANIICON,
	HTML
}
