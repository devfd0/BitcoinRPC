package rpc.bitcoin.devfd0.bitcoinrpc;




class listaInfo extends datoJson 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	listaInfo(String recibido) {
		super(recibido);
		// TODO Auto-generated constructor stub
	}
	String version 			= getValor("version");
    String protocolversion	= getValor("protocolversion");
    String walletversion	= getValor("walletversion");
    String balance			= getValor("balance");
    String blocks			= getValor("blocks");
    String timeoffset		= getValor("timeoffset");
    String connections		= getValor("connections");
    String proxy			= getValor("proxy");
    String difficulty		= getValor("difficulty");
    String testnet			= getValor("testnet");
    String keypoololdest	= getValor("keypoololdest");
    String keypoolsize		= getValor("keypoolsize");
    String paytxfee			= getValor("paytxfee");
    String errors   		= getValor("errors");    
}
