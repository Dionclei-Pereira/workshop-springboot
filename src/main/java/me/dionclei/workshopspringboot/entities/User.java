package me.dionclei.workshopspringboot.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.dionclei.workshopspringboot.entities.dto.UserDTO;
import me.dionclei.workshopspringboot.enums.UserRole;

@Entity
@Table(name = "tb_user")
public class User implements Serializable, UserDetails{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name can not be empty")
	@Size(min = 3, max = 20, message = "Name must be between 3 and 20")
	private String name;
	@Email(message = "Email must be valid")
	@NotBlank(message = "Email can not be empty")
	private String email;
	@NotBlank(message = "Phone can not be empty")
	private String phone;
	@NotBlank(message = "Password can not be empty")
	private String password;
	@NotNull
	private UserRole role;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();
	
	public User() {}

	public User(Long id, String name, String email, String phone, String password, UserRole role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.role = role;
	}
	
	public UserDTO toDTO() {
		return new UserDTO(this.id, this.name, this.email, this.orders);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_SELLER"), new SimpleGrantedAuthority("ROLE_USER"));
		else if(role == UserRole.SELLER) return List.of(new SimpleGrantedAuthority("ROLE_SELLER"), new SimpleGrantedAuthority("ROLE_USER"));
		else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public List<Order> getOrders() {
		return orders;
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

	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

}
