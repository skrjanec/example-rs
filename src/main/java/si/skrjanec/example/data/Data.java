package si.skrjanec.example.data;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "data")
@NamedQueries({ @NamedQuery(name = "Data.findAll", query = "SELECT d " + "FROM Data d") })
public class Data implements Serializable {
	
	public Data() {
	}

	public Data(String matchId, int marketId, String outcome, String specifiers) {
		super();
		this.matchId = matchId;
		this.marketId = marketId;
		this.outcome = outcome;
		this.specifiers = specifiers;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "MATCH_ID")
	private String matchId;

	@Column(name = "MARKET_ID")
	private int marketId;

	@Column(name = "OUTCOME_ID")
	private String outcome;

	@Column(name = "SPECIFIERS")
	private String specifiers;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)  
	@Column(name = "date_insert")
	private Date inserted;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public int getMarketId() {
		return marketId;
	}

	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getSpecifiers() {
		return specifiers;
	}

	public void setSpecifiers(String specifiers) {
		this.specifiers = specifiers;
	}

	public Date getInserted() {
		return inserted;
	}

	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

}