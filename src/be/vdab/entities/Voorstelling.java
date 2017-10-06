package be.vdab.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Voorstelling implements Comparable<Voorstelling>{
	private long id;
	private String titel;
	private String uitvoerders;
	private LocalDateTime datum;
	private long genreId;
	private BigDecimal prijs;
	private int vrijePlaatsen;
	
	public Voorstelling() {
	}
	public Voorstelling(long id, String titel, String uitvoerders, LocalDateTime datum, long genreId, BigDecimal prijs,
			int vrijePlaatsen) {
		this.id = id;
		this.titel = titel;
		this.uitvoerders = uitvoerders;
		this.datum = datum;
		this.genreId = genreId;
		this.prijs = prijs;
		this.vrijePlaatsen = vrijePlaatsen;
	}
	public long getId() {
		return id;
	}
	public String getTitel() {
		return titel;
	}
	public String getUitvoerders() {
		return uitvoerders;
	}
	public LocalDateTime getDatum() {
		return datum;
	}
	public long getGenreId() {
		return genreId;
	}
	public BigDecimal getPrijs() {
		return prijs;
	}
	public int getVrijePlaatsen() {
		return vrijePlaatsen;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public void setUitvoerders(String uitvoerders) {
		this.uitvoerders = uitvoerders;
	}
	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}
	public void setGenreId(long genreId) {
		this.genreId = genreId;
	}
	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}
	public void setVrijePlaatsen(int vrijePlaatsen) {
		this.vrijePlaatsen = vrijePlaatsen;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voorstelling other = (Voorstelling) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public int compareTo(Voorstelling v) {
		return this.datum.compareTo(v.getDatum());
	}
	

}
