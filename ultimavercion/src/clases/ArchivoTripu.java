package clases;
import java.io.*;
public class ArchivoTripu {
	private String nf;
	private RandomAccessFile nlog;
	public ArchivoTripu(String nfi ){
		nf=nfi;
		try {
			nlog= new RandomAccessFile(nf,"rx");
		} catch (FileNotFoundException e) {
			System.out.println("no se encontro el archivo");
		} 
	}
	public void reescribirTripu(Tripulacion[] tripu) {
		try {
			nlog.setLength(0);
			nlog.seek(0);
			int i=0;
			while(tripu[i]!=null && i<100) {
				
			}
		} catch (IOException e) {
			System.out.println("no se pudo reescribir");
		}
	}
}
