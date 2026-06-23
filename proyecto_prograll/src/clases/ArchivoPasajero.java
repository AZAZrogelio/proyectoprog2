package clases;
import java.io.*;
public class ArchivoPasajero {
	private String nf;
	private RandomAccessFile nlog;
	public ArchivoPasajero(String nfi ){
		nf=nfi;
		try {
			nlog= new RandomAccessFile(nf,"rx");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro el archivo");
		} 
	}
	public void reescribirPasajero(Pasajeros[] pjero) {
		try {
			nlog.setLength(0);
			nlog.seek(0);
			int i=0;
			while(pjero[i]!=null && i<500) {
				i++;
			}
		} catch (IOException e) {
			System.out.println("no se pudo reescribir");
		}
	}
}
