package jmetal.javaclass;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name="Users")
public class User {

	public interface BasicInformation {}
	public interface NameUserInfo {}
	public interface ActiveInformation {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(BasicInformation.class)
	private Long id;
	
	@JsonView({BasicInformation.class, NameUserInfo.class})
	private String name;
	private String password;
	@JsonView(BasicInformation.class)
	private String email;
	
	@JsonView({BasicInformation.class, ActiveInformation.class})
	private boolean activatedUser;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JsonView(BasicInformation.class)
	private List<WebPageExperiment> experiments = new LinkedList<>();
	
	
	public User() {
	}

	public User(Long id, String name, String password, String email, boolean activatedUser) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.activatedUser = activatedUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivatedUser() {
		return activatedUser;
	}

	public void setActivatedUser(boolean activatedUser) {
		this.activatedUser = activatedUser;
	}
	
	
}
