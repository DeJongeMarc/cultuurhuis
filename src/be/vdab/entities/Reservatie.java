package be.vdab.entities;

public class Reservatie {
	private long id;
	private long klantId;
	private long voorstellingsId;
	private int plaatsen;
	
	public Reservatie(long klantId, long voorstellingsId, int plaatsen) {
		this.klantId = klantId;
		this.voorstellingsId = voorstellingsId;
		this.plaatsen = plaatsen;
	}
	public Reservatie(long id, long klantId, long voorstellingsId, int plaatsen) {
		this(klantId, voorstellingsId, plaatsen);
		setId(id);
	}
	public long getId() {
		return id;
	}
	public long getKlantId() {
		return klantId;
	}
	public long getVoorstellingsId() {
		return voorstellingsId;
	}
	public int getPlaatsen() {
		return plaatsen;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setKlantId(long klantId) {
		this.klantId = klantId;
	}
	public void setVoorstellingsId(long voorstellingsId) {
		this.voorstellingsId = voorstellingsId;
	}
	public void setPlaatsen(int plaatsen) {
		this.plaatsen = plaatsen;
	}
}
