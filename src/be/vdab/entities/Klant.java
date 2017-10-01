package be.vdab.entities;

public class Klant {
	private long id;
	private String voornaam;
	private String familienaam;
	private String straat;
	private String huisnr;
	private String postcode;
	private String gemeente;
	private String gebruikersnaam;
	private String paswoord;
	public Klant() {
	}
	public Klant(long id, String voornaam, String familienaam, String straat, String huisnr, String postcode,
			String gemeente, String gebruikersnaam, String paswoord) {
		this.id = id;
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.straat = straat;
		this.huisnr = huisnr;
		this.postcode = postcode;
		this.gemeente = gemeente;
		this.gebruikersnaam = gebruikersnaam;
		this.paswoord = paswoord;
	}
	public long getId() {
		return id;
	}
	public String getVoornaam() {
		return voornaam;
	}
	public String getFamilienaam() {
		return familienaam;
	}
	public String getStraat() {
		return straat;
	}
	public String getHuisnr() {
		return huisnr;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getGemeente() {
		return gemeente;
	}
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}
	public String getPaswoord() {
		return paswoord;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	public void setFamilienaam(String familienaam) {
		this.familienaam = familienaam;
	}
	public void setStraat(String straat) {
		this.straat = straat;
	}
	public void setHuisnr(String huisnr) {
		this.huisnr = huisnr;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}
	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}
	public void setPaswoord(String paswoord) {
		this.paswoord = paswoord;
	}

}