package guru.bonacci.heroesadmin.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import guru.bonacci.heroesadmin.PoolType;
import guru.bonacci.heroesadmin.jpa.PoolListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "accounts")
@Table
@Entity
@Where(clause = "active = true")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(PoolListener.class)
public class Pool {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, updatable = false, unique = true)
  private String name;
  
  @Column
  private String description;

  @JsonIgnore
  @Builder.Default
  @OneToMany(mappedBy = "pool")
  private List<AccountDetails> accounts = new ArrayList<>(); // grows very large

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private PoolType type;
  
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "admin_id")
  private AdminUser admin;
  
  @JsonIgnore
  @Column 
  @Builder.Default
  private boolean active = true;
}