package aed.ficheros;

public class Producto {
	
	private int codProducto;
	private String nombreProducto;
	private int unidades;
	private String codFamilia;
	
	public Producto(int codProducto, String nombreProducto, int unidades, String codFamilia) {
		super();
		this.codProducto = codProducto;
		this.nombreProducto = nombreProducto;
		this.unidades = unidades;
		this.codFamilia = codFamilia;
	}

	public int getCodProducto() {
		return codProducto;
	}

	public void setCodProducto(int codProducto) {
		this.codProducto = codProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public String getCodFamilia() {
		return codFamilia;
	}

	public void setCodFamilia(String codFamilia) {
		this.codFamilia = codFamilia;
	}
	
	
	

}
