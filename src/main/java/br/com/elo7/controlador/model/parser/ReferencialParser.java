package br.com.elo7.controlador.model.parser;

public class ReferencialParser {

	public static ReferencialParser get() {
		return new ReferencialParser();
	}
	
	public int numeroModulo(String z) {
		switch(z) {
		case "N": return 0;
		case "W": return 1;
		case "S": return 2;
		case "E": return 3;
		default: return 0; //não sei desse 0 ainda
		}
	}
	
	public String referencial(int numero) {
		switch(numero % 4) {
		case 0: return "N";
		case 1: return "W";
		case 2: return "S";
		case 3: return "E";
		default: return "N"; //não sei desse n ainda
		}
	}
	
}
