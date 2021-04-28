package uniandes.isis2304.vacuandes.negocio;

public class ListContraindicacionesVacuna implements VOListContraindicacionesVacuna{
	private long idVacuna;
	private String condicion;
	
	public ListContraindicacionesVacuna() {
		this.idVacuna = 0;
		this.condicion = "";
	}
	
	public ListContraindicacionesVacuna(long idVacuna, String condicion) {
		this.idVacuna = idVacuna;
		this.condicion = condicion;
	}

	public long getIdVacuna() {
		return idVacuna;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setIdVacuna(long idVacuna) {
		this.idVacuna = idVacuna;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	@Override
	public String toString() {
		return "ListContraindicacionesVacuna [idVacuna=" + idVacuna + ", condicion=" + condicion + "]";
	} 
	
	
}
