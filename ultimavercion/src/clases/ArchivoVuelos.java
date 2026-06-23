package clases;
import java.io.*;
public class ArchivoVuelos {
	private String nf;
	private RandomAccessFile nlog;
	public ArchivoVuelos(String nfi ){
		nf=nfi;
		try {
			nlog= new RandomAccessFile(nf,"rx");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro el archivo");
		} 
	}
	public void reescribirVuelos(Vuelo[] vu) {
		try {
			nlog.setLength(0);
			nlog.seek(0);
			int i=0;
			while(vu[i]!=null && i<100) {
				i++;
			}
		} catch (IOException e) {
			System.out.println("no se pudo reescribir");
		}
	}
}
