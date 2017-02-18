package net.aegistudio.pe.coff;

/**
 * There're 16 items of RVA table item in all. 
 * This enumeration show their order in rva table.
 * 
 * @author aegistudio
 */

public enum RvaTable {
	EXPORT,
	IMPORT,
	RESOURCE,
	EXCEPTION,
	CERTIFICATE,
	RELEASE,
	DEBUG,
	ARCDATA,
	GLOBAL_POINTER,
	TLS,
	LDCFLAG,
	BOUND_IMPORT,
	IMPORT_ADDRESS,
	DELAY_IMPORT,
	CLRRT,
	END;
}
